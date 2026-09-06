package com.example.appecolim.ui.registro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appecolim.R;
import com.example.appecolim.adapter.ResiduoAdapter;
import com.example.appecolim.data.model.Residuo;
import com.example.appecolim.ui.historial.HistorialActivity;
import com.example.appecolim.ui.reporte.ReporteActivity;
import com.example.appecolim.utils.FechaUtils;
import java.util.ArrayList;
import java.util.List;

public class RegistroActivity extends AppCompatActivity {

    private RecyclerView rvHoy;
    private ResiduoAdapter adapter;
    private List<Residuo> listaHoy = new ArrayList<>();
    private TextView txtFechaChip, txtTotalDia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        // Inicializar vistas
        txtFechaChip = findViewById(R.id.txt_fecha_chip);
        txtTotalDia = findViewById(R.id.txt_total_dia);
        rvHoy = findViewById(R.id.rv_registros_hoy);

        // Mostrar fecha actual en el chip superior
        txtFechaChip.setText(FechaUtils.obtenerFechaHoraActual().split(" ")[0]);

        // Configurar RecyclerView
        rvHoy.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ResiduoAdapter(listaHoy, false); // false porque es registro (permite editar)
        rvHoy.setAdapter(adapter);

        // Botón de Volver
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Lógica de navegación entre pestañas
        findViewById(R.id.tab_historial).setOnClickListener(v -> {
            startActivity(new Intent(this, HistorialActivity.class));
            finish();
        });

        findViewById(R.id.tab_reporte).setOnClickListener(v -> {
            startActivity(new Intent(this, ReporteActivity.class));
            finish();
        });
        
        cargarDatosDeHoy();
    }

    private void cargarDatosDeHoy() {
        // AQUÍ se llamará al DAO para obtener registros de la fecha actual
        // Luego se calculará el total
        double sumaTotal = 0;
        for (Residuo r : listaHoy) {
            sumaTotal += r.getCantidadKg();
        }
        txtTotalDia.setText(getString(R.string.total_format, String.valueOf(sumaTotal)));
    }
}
