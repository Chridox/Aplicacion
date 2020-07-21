package com.example.aguacoop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.loopj.android.http.AsyncHttpClient;


public class Formulario_Trabajador extends AppCompatActivity{

    private EditText idMedidorFormulario,observacionesTrabajador,resultadoQR;
    private Spinner trabajoRealizado;
    private AsyncHttpClient cliente;
    private Button btnguardarTrabajo,btnvolverTrabajo,btnQR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario_trabajador);
        idMedidorFormulario = (EditText)findViewById(R.id.idMedidorFormulario);
        observacionesTrabajador = (EditText)findViewById(R.id.observacionesTrabajador);
        resultadoQR = (EditText)findViewById(R.id.resultadoQR);
        trabajoRealizado = (Spinner)findViewById(R.id.trabajoRealizado);
        btnguardarTrabajo = (Button)findViewById(R.id.guardarTrabajo);
        btnvolverTrabajo = (Button)findViewById(R.id.volverTrabajo);
        btnQR = (Button)findViewById(R.id.btnQR);
        cliente = new AsyncHttpClient();

        btnvolverTrabajo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Formulario_Trabajador.this, Tareas_Trabajador.class);
                startActivity(i);
            }
        });

        Intent recibo = getIntent();
        int recibo2= recibo.getIntExtra("enviado2",0);
       String recibofinal=  String.valueOf(recibo2);
        idMedidorFormulario.setText(recibofinal.toString());

        btnQR.setOnClickListener(mOnCLickListener);


       /*Bundle extras = getIntent().getExtras();
        if (extras!=null){
            String idRecibido = extras.getString("enviado2");

            idMedidorFormulario.setText(idRecibido);
            AlertDialog.Builder msg = new AlertDialog.Builder(Formulario_Trabajador.this);
            msg.setCancelable(true);
            msg.setTitle("Detalles Tarea");
            msg.setMessage("Tarea a realizar: " +idRecibido );
            msg.show();
        }else{
            Toast.makeText(Formulario_Trabajador.this,"Error en la carga de datos",Toast.LENGTH_LONG).show();
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result !=null)
            if (result.getContents()!=null){
                resultadoQR.setText(result.getContents());
            }else{
                resultadoQR.setText("Error al escanear");
            }
    }

    private View.OnClickListener mOnCLickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnQR:
                    new IntentIntegrator(Formulario_Trabajador.this).initiateScan();
                    break;
            }
        }
    };

}
