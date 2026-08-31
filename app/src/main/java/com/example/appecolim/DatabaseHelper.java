package com.example.appecolim;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ecolimapp.db";
    private static final int DATABASE_VERSION = 1;

    // Nombre de la tabla
    public static final String TABLE_RESIDUOS = "residuos";

    // Columnas de la tabla
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TIPO = "tipo"; // Soporta cualquier tipo (Orgánico, Peligroso, PET, etc.)
    public static final String COLUMN_CANTIDAD = "cantidad";
    public static final String COLUMN_FECHA = "fecha";
    public static final String COLUMN_HORA = "hora";
    public static final String COLUMN_SINCRONIZADO = "sincronizado";
    public static final String COLUMN_ACCION_PENDIENTE = "accion_pendiente";

    // Sentencia SQL para crear la tabla
    private static final String CREATE_TABLE_RESIDUOS = "CREATE TABLE " + TABLE_RESIDUOS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TIPO + " TEXT, " + // Al ser TEXT, recibe cualquier nombre enviado por la interfaz sin dar error
            COLUMN_CANTIDAD + " REAL, " +
            COLUMN_FECHA + " TEXT, " +
            COLUMN_HORA + " TEXT, " +
            COLUMN_SINCRONIZADO + " INTEGER DEFAULT 0, " +
            COLUMN_ACCION_PENDIENTE + " TEXT DEFAULT 'CREAR'" +
            ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RESIDUOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESIDUOS);
        onCreate(db);
    }

    // 1. Guardar nuevo registro (Recibe el tipo de residuo enviado desde los botones de la pantalla)
    public long insertarResiduo(String tipo, double cantidad, String fecha, String hora) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIPO, tipo);
        values.put(COLUMN_CANTIDAD, cantidad);
        values.put(COLUMN_FECHA, fecha);
        values.put(COLUMN_HORA, hora);
        values.put(COLUMN_SINCRONIZADO, 0);
        values.put(COLUMN_ACCION_PENDIENTE, "CREAR");

        return db.insert(TABLE_RESIDUOS, null, values);
    }

    // 2. Consulta para pestaña "HOY"
    public Cursor obtenerResiduosDeHoy(String fechaHoy) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_RESIDUOS + " WHERE " + COLUMN_FECHA + " = ? ORDER BY " + COLUMN_ID + " DESC", new String[]{fechaHoy});
    }

    // 3. Consulta para pestaña "HISTORIAL"
    public Cursor obtenerTodosLosResiduos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_RESIDUOS + " ORDER BY " + COLUMN_ID + " DESC", null);
    }

    // 4. Consulta para la pantalla de "MI PERFIL" (Estadísticas del día actual)
    public Cursor obtenerResumenHoy(String fechaHoy) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) AS total_registros, SUM(" + COLUMN_CANTIDAD + ") AS total_kg FROM " +
                TABLE_RESIDUOS + " WHERE " + COLUMN_FECHA + " = ?";
        return db.rawQuery(query, new String[]{fechaHoy});
    }

    // 5. Consulta para pestaña "REPORTE" (Agrupa por categoría: Orgánico, Peligroso, etc.)
    public Cursor obtenerReporteMensual(String mesAnio) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_TIPO + ", SUM(" + COLUMN_CANTIDAD + ") AS total_kg FROM " +
                TABLE_RESIDUOS + " WHERE " + COLUMN_FECHA + " LIKE ? GROUP BY " + COLUMN_TIPO;
        return db.rawQuery(query, new String[]{"%" + mesAnio + "%"});
    }

    // 6. Editar registro existente
    public boolean actualizarResiduo(int id, String tipo, double cantidad) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIPO, tipo);
        values.put(COLUMN_CANTIDAD, cantidad);
        values.put(COLUMN_SINCRONIZADO, 0);
        values.put(COLUMN_ACCION_PENDIENTE, "ACTUALIZAR");

        int resultado = db.update(TABLE_RESIDUOS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        return resultado > 0;
    }

    // 7. Eliminar registro (Exclusivo para uso del Administrador)
    public boolean eliminarResiduo(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int resultado = db.delete(TABLE_RESIDUOS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        return resultado > 0;
    }
}