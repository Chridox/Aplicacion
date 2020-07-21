package com.example.aguacoop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


import cz.msebera.android.httpclient.Header;

public class Tareas_Trabajador extends AppCompatActivity {
    private ListView listaTareasTrabajador;
    private Button btnvolverListaMedidor;
    private AsyncHttpClient cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tareas_trabajador);
        listaTareasTrabajador = (ListView) findViewById(R.id.listaTareasTrabajador);
        btnvolverListaMedidor = (Button) findViewById(R.id.volverListaMedidor);
        cliente = new AsyncHttpClient();
        obtenerDatos();

        btnvolverListaMedidor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Tareas_Trabajador.this, Menu_Trabajador.class);
                startActivity(i);
            }
        });

    }


 private void obtenerDatos(){
        String url="http://aguaproyect.com/login/getData.php";
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                actualizarGrilla(new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
 }
 private void actualizarGrilla(String datos){
        final ArrayList<TareasTrabajador> lisTareas = new ArrayList<TareasTrabajador>();
        try{
            JSONArray json = new JSONArray(datos);
            for (int i=0; i<json.length(); i++){
                int idTT = json.getJSONObject(i).getInt("idTarea");
                int idMedidorTT = json.getJSONObject(i).getInt("idMedidor");
                String nombreUsuarioTT = json.getJSONObject(i).getString("nombreUsuario");
                String nombreComunaTT = json.getJSONObject(i).getString("nomComuna");
                String tareaTarea = json.getJSONObject(i).getString("tareaTarea");
                String direccion = json.getJSONObject(i).getString("direccionMedidor");
                String fecha = json.getJSONObject(i).getString("fecha");
                TareasTrabajador TT = new TareasTrabajador(idTT,idMedidorTT,tareaTarea,fecha,nombreUsuarioTT,nombreComunaTT,direccion);
                lisTareas.add(TT);
            }
            ArrayAdapter<TareasTrabajador> adaptador = new ArrayAdapter<TareasTrabajador>(Tareas_Trabajador.this,android.R.layout.simple_list_item_1, lisTareas);
            listaTareasTrabajador.setAdapter(adaptador);
            listaTareasTrabajador.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    TareasTrabajador TT = lisTareas.get(position);
                    String url="http://aguaproyect.com/login/getData.php";
                    cliente.post(url, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            TareasTrabajador TT = lisTareas.get(position);
                            AlertDialog.Builder msg = new AlertDialog.Builder(Tareas_Trabajador.this);
                            msg.setCancelable(true);
                            msg.setTitle("Detalles Tarea");
                            msg.setMessage("Tarea a realizar: " + TT.getTareaAsignadaTT()+ "\nDireccion: "+TT.getDireccionMedidorTT()+"\nComuna: "+TT.getNomComunaTT() );
                            msg.show();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
                }
            });
            listaTareasTrabajador.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    TareasTrabajador TT = lisTareas.get(position);
                    int idEnviar = TT.getIdMedidorTT();
                    Integer.toString(idEnviar);
                    Intent i = new Intent(Tareas_Trabajador.this, Formulario_Trabajador.class);
                    i.putExtra("enviado2", idEnviar);
                    startActivity(i);
                 return true;
                }
            });
        }catch (Exception e){
            Toast.makeText(Tareas_Trabajador.this,"Error en la carga de datos",Toast.LENGTH_LONG).show();
        }
 }

}

