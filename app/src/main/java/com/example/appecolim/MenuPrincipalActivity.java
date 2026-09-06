package com.example.appecolim;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.appecolim.data.local.EmpleadoDAO;

public class MenuPrincipalActivity extends AppCompatActivity {

    private LinearLayout cardClasificacion, cardRegistroReportes;
    private DrawerLayout drawerLayout;
    private ImageButton btnMenuHamburguesa, btnCerrarDrawer;
    private LinearLayout btnMiPerfil, btnCerrarSesion;
    private TextView txtNombreDrawer, txtCodigoDrawer;
    private EmpleadoDAO empleadoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        drawerLayout = findViewById(R.id.drawerLayout);
        btnMenuHamburguesa = findViewById(R.id.btnMenuHamburguesa);
        btnCerrarDrawer = findViewById(R.id.btnCerrarDrawer);
        btnMiPerfil = findViewById(R.id.btnMiPerfil);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);
        txtNombreDrawer = findViewById(R.id.txtNombreDrawer);
        txtCodigoDrawer = findViewById(R.id.txtCodigoDrawer);

        cardClasificacion = findViewById(R.id.cardClasificacion);
        cardRegistroReportes = findViewById(R.id.cardRegistroReportes);

        empleadoDAO = new EmpleadoDAO(this);
        cargarDatosDelEmpleado();

        btnMenuHamburguesa.setOnClickListener(v ->
                drawerLayout.openDrawer(GravityCompat.START));

        btnCerrarDrawer.setOnClickListener(v ->
                drawerLayout.closeDrawer(GravityCompat.START));

        cardClasificacion.setOnClickListener(v -> {
            // TODO: navegar a ClasificacionActivity
        });

        cardRegistroReportes.setOnClickListener(v -> {
            // TODO: navegar a RegistroReportesActivity
        });

        btnMiPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(MenuPrincipalActivity.this, PerfilActivity.class);
            startActivity(intent);
        });

        btnCerrarSesion.setOnClickListener(v -> {
            // TODO: empleadoDAO.cerrarSesion(); + volver a LoginActivity
        });
    }

    // Lee el empleado guardado en SQLite y lo muestra en el Drawer
    private void cargarDatosDelEmpleado() {
        Cursor cursor = empleadoDAO.obtenerEmpleadoGuardado();

        if (cursor.moveToFirst()) {
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
            String codigo = cursor.getString(cursor.getColumnIndexOrThrow("codigo"));

            txtNombreDrawer.setText(nombre);
            txtCodigoDrawer.setText(codigo);
        }
        cursor.close();
    }
}