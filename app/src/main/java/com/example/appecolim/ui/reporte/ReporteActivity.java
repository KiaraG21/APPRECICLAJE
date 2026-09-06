package com.example.appecolim.ui.reporte;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appecolim.R;
import com.example.appecolim.ui.historial.HistorialActivity;
import com.example.appecolim.ui.registro.RegistroActivity;
import com.google.android.material.button.MaterialButton;

public class ReporteActivity extends AppCompatActivity {

    private TextView txtMesReporte, txtTotalKg;
    private LinearLayout containerMateriales, containerLeyenda;
    private MaterialButton btnExportPdf, btnExportCsv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reporte);

        // Inicializar vistas
        txtMesReporte = findViewById(R.id.txt_mes_reporte);
        txtTotalKg = findViewById(R.id.txt_reporte_total_kg);
        containerMateriales = findViewById(R.id.container_resumen_materiales);
        containerLeyenda = findViewById(R.id.container_leyenda);
        btnExportPdf = findViewById(R.id.btn_export_pdf);
        btnExportCsv = findViewById(R.id.btn_export_csv);

        // Botón de Volver
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Lógica para Exportar PDF
        btnExportPdf.setOnClickListener(v -> {
            exportarReporte("PDF");
        });

        // Lógica para Exportar CSV
        btnExportCsv.setOnClickListener(v -> {
            exportarReporte("CSV");
        });

        // Lógica de navegación entre pestañas
        findViewById(R.id.tab_hoy).setOnClickListener(v -> {
            startActivity(new Intent(this, RegistroActivity.class));
            finish();
        });

        findViewById(R.id.tab_historial).setOnClickListener(v -> {
            startActivity(new Intent(this, HistorialActivity.class));
            finish();
        });

        // Simulación inicial de carga de datos
        cargarDatosReporte();
    }

    private void exportarReporte(String formato) {
        // En una app real, aquí se usaría una librería como iText (PDF) u OpenCSV.
        // Por ahora, preparamos la acción y notificamos al usuario.
        String mensaje = "Generando archivo " + formato + "...";
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();

        // AQUÍ tus compañeros conectarán la lógica de guardado de archivos
        // y el envío del GET /reporte/:mes/:anio para obtener los datos crudos.
    }

    private void cargarDatosReporte() {
        // AQUÍ se llamará a la API GET /reporte/:mes/:anio
        // Cuando llegue la respuesta, se limpiarán los contenedores y se inflarán
        // los 7 tipos de materiales dinámicamente.
        
        Toast.makeText(this, "Cargando reporte mensual...", Toast.LENGTH_SHORT).show();
    }
}
