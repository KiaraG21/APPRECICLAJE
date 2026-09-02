package com.example.appecolim;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appecolim.data.local.ResiduoDAO;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ---- PRUEBA TEMPORAL de ResiduoDAO (borrar cuando llegue el Front) ----
        ResiduoDAO residuoDAO = new ResiduoDAO(this);

        // 1. Guardamos un residuo de prueba
        long resultado = residuoDAO.guardarResiduo(
                "EMP-TEST",       // id_empleado (inventado, solo para probar)
                "Metal",          // tipo
                8.5,              // cantidad_kg
                "2026-08-25 10:30" // fecha_hora
        );
        Log.d("PRUEBA_SQL", "Residuo guardado, ID generado: " + resultado);

        // 2. Leemos lo que hay guardado para HOY
        Cursor cursor = residuoDAO.obtenerResiduosDeHoy("2026-08-25");
        Log.d("PRUEBA_SQL", "Cantidad de registros encontrados: " + cursor.getCount());

        while (cursor.moveToNext()) {
            String tipo = cursor.getString(cursor.getColumnIndexOrThrow("tipo"));
            double cantidad = cursor.getDouble(cursor.getColumnIndexOrThrow("cantidad_kg"));
            Log.d("PRUEBA_SQL", "Encontrado -> Tipo: " + tipo + ", Cantidad: " + cantidad + "kg");
        }
        cursor.close();
        // ---- FIN de la prueba temporal ----
    }
}