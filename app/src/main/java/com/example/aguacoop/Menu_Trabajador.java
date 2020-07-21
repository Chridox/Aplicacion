package com.example.aguacoop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu_Trabajador extends AppCompatActivity {
private Button btnCerrarSesion,btntareasTrabajador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_trabajador);
        btnCerrarSesion = findViewById(R.id.cerrarSesionTrabajador);
        btntareasTrabajador = findViewById(R.id.tareasTrabajador);

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
                preferences.edit().clear().commit();
                Intent i = new Intent(Menu_Trabajador.this, InicioSesion.class);
                startActivity(i);
                finish();
            }
        });

        btntareasTrabajador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu_Trabajador.this, Tareas_Trabajador.class);
                startActivity(i);
            }
        });

    }
}
