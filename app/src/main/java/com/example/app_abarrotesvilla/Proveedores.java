package com.example.app_abarrotesvilla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Proveedores extends AppCompatActivity {
    //Atributos.
    private TextInputEditText txtIdProveedor, txtNombreProveedor, txtTelefonoProveedor;
    private ImageButton btnBuscarProveedor, btnModificar, btnEliminar, btnCancelar;
    private MaterialButton btnGuardarProve;
    private String nombreProveedor, telProveedor;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue requestqueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedorescrud);
        //Activamos el display del actionbar.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Se establece el icono de nuestro logo.
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //Conectar parte logica con el diseño.
        txtIdProveedor = findViewById(R.id.txtIdProveedor);
        txtNombreProveedor = findViewById(R.id.txtNombreProveedor);
        txtTelefonoProveedor = findViewById(R.id.txtTelefonoProveedor);
        btnBuscarProveedor = findViewById(R.id.btnBuscarProveedor);
        btnModificar = findViewById(R.id.btnModificar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnGuardarProve = findViewById(R.id.btnGuardarProve);

        //Hacemos invisibles los botones.
        btnModificar.setVisibility(View.INVISIBLE);
        btnCancelar.setVisibility(View.INVISIBLE);
        btnEliminar.setVisibility(View.INVISIBLE);

        // Accion para el boton buscar.
        btnBuscarProveedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id_proveedor = txtIdProveedor.getText().toString();

                if(!id_proveedor.isEmpty()){
                    consultarProveedor();
                    btnModificar.setVisibility(View.VISIBLE);
                    btnCancelar.setVisibility(View.VISIBLE);
                    btnEliminar.setVisibility(View.VISIBLE);
                    txtIdProveedor.setEnabled(false);
                } else{
                    Toast.makeText(getApplicationContext(), "Debe digitar el id del proveedor", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Accion del botón Guardar.
        btnGuardarProve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombreProveedor = txtNombreProveedor.getText().toString();
                telProveedor = txtTelefonoProveedor.getText().toString();

                //No permitas que mande campos vacíos a nuestra base de datos.
                if(!nombreProveedor.isEmpty() && !telProveedor.isEmpty()){
                    registrarProveedor("https://appabarrotesvilla.000webhostapp.com/registroProveedor_service.php");
                } else {
                    Toast.makeText(Proveedores.this, "No se permiten campos vacíos.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Accion de boton modificar.
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificarProveedor();
            }
        });

        //Acción del botón eliminar.
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarProveedor();
            }
        });

        //Acción del botón cancelar.
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiar();
                txtIdProveedor.setEnabled(true);
                btnModificar.setVisibility(View.INVISIBLE);
                btnCancelar.setVisibility(View.INVISIBLE);
                btnEliminar.setVisibility(View.INVISIBLE);
                btnGuardarProve.setVisibility(View.VISIBLE);
            }
        });

    } // Fin del metodo onCreate.


    //Método para mostrar los datos.
    private void consultarProveedor(){
        //Variable donde esta nuestra URL del service asignandole el codigo a buscar.
        String URL = "https://appabarrotesvilla.000webhostapp.com/consultarIdProveedor_service.php?id_proveedor=" + txtIdProveedor.getText().toString();
        //
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Hacemos un array de tipo vector para traer nuestro json almacenado en la base de datos producto
                JSONArray json = response.optJSONArray("proveedor");
                //
                JSONObject jsonobject = null;

                try {
                    //obtenemos nuestro objeto json en el indice 0, osea el id del producto.
                    jsonobject = json.getJSONObject(0);
                    //Una vez que detecta el id, establece los objetos de la base de datos hacía nuestros campos de texto.
                    txtNombreProveedor.setText(jsonobject.optString("nombre_proveedor"));
                    txtTelefonoProveedor.setText(jsonobject.optString("tel_proveedor"));
                    btnGuardarProve.setVisibility(View.INVISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No se logro la consulta.", Toast.LENGTH_SHORT).show();
                txtIdProveedor.setEnabled(true);
                btnGuardarProve.setVisibility(View.VISIBLE);
                btnModificar.setVisibility(View.INVISIBLE);
                btnEliminar.setVisibility(View.INVISIBLE);
                btnCancelar.setVisibility(View.INVISIBLE);
                limpiar();
            }
        });

        requestqueue = Volley.newRequestQueue(this);
        requestqueue.add(jsonObjectRequest);

    } // Fin de metodo consultar producto.


    //Método para registrar un proveedor.
    private void registrarProveedor(String URL){
        StringRequest stringrequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                limpiar();
                Toast.makeText(getApplicationContext(), "Registro Exitoso!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Servidor fuera de servicio", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("nombreProveedor", txtNombreProveedor.getText().toString());
                parametros.put("telefonoProveedor", txtTelefonoProveedor.getText().toString());
                return parametros;
            }
        };

        requestqueue = Volley.newRequestQueue(this);
        //Ayuda a procesar las peticiones hechas de nuestra app.
        requestqueue.add(stringrequest);
    } // Fin del metodo registrar.


    //Metodo para editar un proveedor
    private void modificarProveedor(){

        //Cadena de texto que se manda nuestro URL de servicio modificar.
        String URL = "https://appabarrotesvilla.000webhostapp.com/modifProveedor_service.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Si el webService nos regresa la palabra se actualizo quiere decir que se ha registrado con exito.
                if(response.trim().equalsIgnoreCase("SeActualizo")){
                    Toast.makeText(getApplicationContext(),"Se ha Actualizado con exito",Toast.LENGTH_SHORT).show();
                    txtIdProveedor.setEnabled(true);
                    limpiar();
                    btnGuardarProve.setVisibility(View.VISIBLE);
                    btnModificar.setVisibility(View.INVISIBLE);
                    btnEliminar.setVisibility(View.INVISIBLE);
                    btnCancelar.setVisibility(View.INVISIBLE);
                }else{
                    Toast.makeText(getApplicationContext(),"No se ha Actualizado ",Toast.LENGTH_SHORT).show();
                    txtIdProveedor.setEnabled(true);
                    limpiar();
                    btnGuardarProve.setVisibility(View.VISIBLE);
                    btnModificar.setVisibility(View.INVISIBLE);
                    btnEliminar.setVisibility(View.INVISIBLE);
                    btnCancelar.setVisibility(View.INVISIBLE);
                }

                //limpiar();
                //Toast.makeText(getApplicationContext(), "Registro Actualizado!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Servidor fuera de servicio", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("idProveedor", txtIdProveedor.getText().toString());
                parametros.put("nombreProveedor", txtNombreProveedor.getText().toString());
                parametros.put("telefonoProveedor", txtTelefonoProveedor.getText().toString());
                return parametros;
            }
        };

        requestqueue = Volley.newRequestQueue(this);
        requestqueue.add(stringRequest);
    } // Fin del metodo modificar.


    //Método eliminar.
    private void eliminarProveedor(){
        String URL = "https://appabarrotesvilla.000webhostapp.com/eliminarProveedor_service.php?idProveedor=" + txtIdProveedor.getText().toString();

        StringRequest stringrequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equalsIgnoreCase("elimina")){
                    Toast.makeText(getApplicationContext(),"Se ha Eliminado con exito",Toast.LENGTH_SHORT).show();
                    txtIdProveedor.setEnabled(true);
                    limpiar();
                    btnGuardarProve.setVisibility(View.VISIBLE);
                    btnModificar.setVisibility(View.INVISIBLE);
                    btnEliminar.setVisibility(View.INVISIBLE);
                    btnCancelar.setVisibility(View.INVISIBLE);

                } else if(response.trim().equalsIgnoreCase("noElimina")){
                    Toast.makeText(getApplicationContext(),"No se ha Eliminado ",Toast.LENGTH_SHORT).show();
                    txtIdProveedor.setEnabled(true);
                    limpiar();
                    btnGuardarProve.setVisibility(View.VISIBLE);
                    btnModificar.setVisibility(View.INVISIBLE);
                    btnEliminar.setVisibility(View.INVISIBLE);
                    btnCancelar.setVisibility(View.INVISIBLE);

                } else{
                    Toast.makeText(getApplicationContext(),"No se encuentra el proveedor ",Toast.LENGTH_SHORT).show();
                    txtIdProveedor.setEnabled(true);
                    limpiar();
                    btnGuardarProve.setVisibility(View.VISIBLE);
                    btnModificar.setVisibility(View.INVISIBLE);
                    btnEliminar.setVisibility(View.INVISIBLE);
                    btnCancelar.setVisibility(View.INVISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No se logro eliminar", Toast.LENGTH_SHORT).show();
                txtIdProveedor.setEnabled(true);
                limpiar();
                btnGuardarProve.setVisibility(View.VISIBLE);
                btnModificar.setVisibility(View.INVISIBLE);
                btnEliminar.setVisibility(View.INVISIBLE);
                btnCancelar.setVisibility(View.INVISIBLE);
            }
        });

        requestqueue = Volley.newRequestQueue(this);
        requestqueue.add(stringrequest);
    } //FIN DEL METODO ELIMINAR.


    //Método limpiar cajas de texto.
    private void limpiar(){
        txtIdProveedor.setText("");
        txtNombreProveedor.setText("");
        txtTelefonoProveedor.setText("");
    } //FIN DEL METODO LIMPIAR

    //Método para el botón regresar.
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), AgendaProveedores.class);
        startActivity(intent);
        finish();
    }

}