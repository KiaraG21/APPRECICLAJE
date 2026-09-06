package com.example.appecolim;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appecolim.ui.historial.HistorialActivity;
import com.example.appecolim.ui.registro.RegistroActivity;
import com.example.appecolim.ui.reporte.ReporteActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ESTO ES TEMPORAL PARA QUE PUEDAS PROBAR TUS PANTALLAS AL DAR "PLAY"
        // Tus compañeros luego pondrán esto en el menú principal real.

        Button btnVerRegistro = new Button(this);
        btnVerRegistro.setText("PROBAR REGISTRO");
        btnVerRegistro.setOnClickListener(v -> startActivity(new Intent(this, RegistroActivity.class)));

        Button btnVerHistorial = new Button(this);
        btnVerHistorial.setText("PROBAR HISTORIAL");
        btnVerHistorial.setOnClickListener(v -> startActivity(new Intent(this, HistorialActivity.class)));

        Button btnVerReporte = new Button(this);
        btnVerReporte.setText("PROBAR REPORTE");
        btnVerReporte.setOnClickListener(v -> startActivity(new Intent(this, ReporteActivity.class)));

        // Añadirlos al layout principal (suponiendo que activity_main tiene un contenedor)
        // Por simplicidad, abriremos directamente RegistroActivity para que veas tu trabajo
        startActivity(new Intent(this, RegistroActivity.class));
        finish();
    }
}
