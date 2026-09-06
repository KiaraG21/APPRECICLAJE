package com.example.appecolim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appecolim.data.local.EmpleadoDAO;

public class LoginActivity extends AppCompatActivity {

    private EditText etCodigo, etPassword;
    private Button btnIniciarSesion;
    private TextView txtError;
    private EmpleadoDAO empleadoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Conectamos las variables con los elementos del XML
        etCodigo = findViewById(R.id.etCodigo);
        etPassword = findViewById(R.id.etPassword);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        txtError = findViewById(R.id.txtError);

        empleadoDAO = new EmpleadoDAO(this);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentarLogin();
            }
        });
    }

    private void intentarLogin() {
        String codigo = etCodigo.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validación simple: que no estén vacíos
        if (codigo.isEmpty() || password.isEmpty()) {
            mostrarError("Ingresa tu código y contraseña");
            return;
        }

        // TODO (próximo paso): aquí va la llamada real a la API POST /login
        // Por ahora, esto es un placeholder para probar la navegación:
        Toast.makeText(this, "Intentando iniciar sesión...", Toast.LENGTH_SHORT).show();

        // Cuando conectemos la API de verdad, aquí se hará:
        // 1. apiService.login(codigo, password, callback)
        // 2. Si la API responde OK:
        //      empleadoDAO.guardarEmpleado(idEmpleado, nombre, cargo, turno, codigo, token, fechaActual);
        //      ir al Menú Principal
        // 3. Si la API falla (sin internet, por ejemplo):
        //      revisar si ya existe un empleado guardado en SQLite (login offline)
        //      empleadoDAO.obtenerEmpleadoGuardado()
    }

    private void mostrarError(String mensaje) {
        txtError.setText(mensaje);
        txtError.setVisibility(View.VISIBLE);
    }
}