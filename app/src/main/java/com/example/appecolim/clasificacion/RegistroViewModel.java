package com.example.appecolim.clasificacion;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.appecolim.data.local.DataBaseHelper;
import com.example.appecolim.utils.FechaUtils;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** Mantiene un único guardado durante rotaciones y persiste fuera del hilo de interfaz. */
public class RegistroViewModel extends AndroidViewModel {
    public static final class Resultado {
        final long id; final String error;
        Resultado(long id, String error) { this.id = id; this.error = error; }
    }
    final MutableLiveData<Resultado> resultado = new MutableLiveData<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    volatile boolean guardando;
    public RegistroViewModel(Application app) { super(app); }
    void guardar(Categoria categoria, int cantidad, boolean vistaPrevia) {
        if (guardando || cantidad <= 0 || cantidad > Cantidad.MAXIMO) return;
        guardando = true;
        executor.execute(() -> {
            Resultado salida;
            boolean pruebas = com.example.appecolim.BuildConfig.CLASIFICACION_PREVIEW && vistaPrevia;
            try (DataBaseHelper helper = new DataBaseHelper(getApplication(), pruebas)) {
                SQLiteDatabase db = helper.getWritableDatabase();
                db.beginTransaction();
                try {
                    String empleado = null;
                    try (Cursor cursor = db.rawQuery("SELECT id_empleado FROM empleado_local LIMIT 1", null)) {
                        if (cursor.moveToFirst()) empleado = cursor.getString(0);
                    }
                    if (pruebas) empleado = "prueba-clasificacion";
                    if (empleado == null || empleado.trim().isEmpty()) {
                        salida = new Resultado(-1, "Inicia sesión antes de guardar un registro.");
                    } else {
                        ContentValues values = new ContentValues();
                        values.put("id_empleado", empleado);
                        values.put("tipo", categoria.nombre);
                        values.put("cantidad_kg", cantidad / 100.0);
                        values.put("fecha_hora", FechaUtils.obtenerFechaHoraActual());
                        values.put("estado_sync", "pendiente");
                        values.putNull("id_mongo");
                        long id = db.insertOrThrow("residuo_local", null, values);
                        db.setTransactionSuccessful();
                        salida = new Resultado(id, null);
                    }
                } finally { db.endTransaction(); }
            } catch (RuntimeException e) {
                salida = new Resultado(-1, "No se pudo guardar el registro. Inténtalo de nuevo.");
            }
            // Se libera el bloqueo al consumir el resultado en el hilo principal.
            resultado.postValue(salida);
        });
    }
    @Override protected void onCleared() { executor.shutdown(); }
}
