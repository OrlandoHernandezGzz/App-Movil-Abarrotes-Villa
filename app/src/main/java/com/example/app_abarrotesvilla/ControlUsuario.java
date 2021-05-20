package com.example.app_abarrotesvilla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControlUsuario extends AppCompatActivity {
    //Declaramos nuestros atributos.
    private TextInputEditText txtCodigoUser, txtNombre, txtApellidos, txtTelefono;
    private ImageButton btnBuscar, btnModificar, btnCancelar, btnEliminar;
    private AutoCompleteTextView cboTipo;
     JsonObjectRequest jsonObjectRequest;
     RequestQueue requestqueue;
    private String codigoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_usuario);
        //Comando para habilitar que enseñe un icono en el action bar.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Establecemos el icono con el siguiente comando.
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //Hacemos el puente de nuetra parte logica con el diseño
        txtCodigoUser = findViewById(R.id.txtCodigoUser);
        txtNombre = findViewById(R.id.txtNombre);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtTelefono = findViewById(R.id.txtTelefono);
        cboTipo = findViewById(R.id.cboTipo);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnModificar = findViewById(R.id.btnModificar);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnEliminar = findViewById(R.id.btnEliminar);

        //Establecemos los atributos de la lista despegable tipo de usuario
        listTipoUser();

        //Hacemos invisibles los botones.
        btnModificar.setVisibility(View.INVISIBLE);
        btnCancelar.setVisibility(View.INVISIBLE);
        btnEliminar.setVisibility(View.INVISIBLE);

        //Acción del botón buscar.
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codigoUser = txtCodigoUser.getText().toString();

                if(!codigoUser.isEmpty()){
                    buscarUsuario();
                    btnModificar.setVisibility(View.VISIBLE);
                    btnCancelar.setVisibility(View.VISIBLE);
                    btnEliminar.setVisibility(View.VISIBLE);
                    txtCodigoUser.setEnabled(false);
                } else{
                    Toast.makeText(getApplicationContext(), "Debe digitar el codigo de usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Acción del botón cancelar.
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiar();
                txtCodigoUser.setEnabled(true);
                btnModificar.setVisibility(View.INVISIBLE);
                btnCancelar.setVisibility(View.INVISIBLE);
                btnEliminar.setVisibility(View.INVISIBLE);
            }
        });

        //Acción del botón modificar.
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificarUsuario();
            }
        });

        //Acción del botón eliminar.
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarUsuario();
            }
        });

    } //Fin del método on create.

    //Método para buscar un usuario.
    private void buscarUsuario(){
        //Variable donde esta nuestra URL del service asignandole el codigo a buscar.
        String URL = "http://192.168.1.70/AbarrotesVilla/consultarUser_service.php?codigo=" + txtCodigoUser.getText().toString();
        //
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Hacemos un array de tipo vector para traer nuestro json almacenado en la base de datos producto
                JSONArray json = response.optJSONArray("usuario");
                JSONObject jsonobject = null;

                try {
                    //obtenemos nuestro objeto json en el indice 0, osea el id del producto.
                    jsonobject = json.getJSONObject(0);
                    //Una vez que detecta el id, establece los objetos de la base de datos hacía nuestros campos de texto.
                    txtNombre.setText(jsonobject.optString("us_nombre"));
                    txtApellidos.setText(jsonobject.optString("us_apellidos"));
                    txtTelefono.setText(jsonobject.optString("us_telefono"));
                    cboTipo.setText(jsonobject.optString("us_tipo"));
                    //Establecemos los atributos de la lista despegable tipo de usuario
                    listTipoUser();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No se logro la consulta.", Toast.LENGTH_SHORT).show();
                txtCodigoUser.setEnabled(true);
                btnCancelar.setVisibility(View.INVISIBLE);
                btnModificar.setVisibility(View.INVISIBLE);
                btnEliminar.setVisibility(View.INVISIBLE);
                limpiar();
            }
        });

        requestqueue = Volley.newRequestQueue(this);
        requestqueue.add(jsonObjectRequest);
    } //Fin del método buscar usuario.

    //Método para modificar un usuario.
    private void modificarUsuario(){
        //Cadena de texto que se manda nuestro URL de servicio modificar.
        String URL = "http://192.168.1.70/AbarrotesVilla/modifUser_service.php?";

        StringRequest stringrequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Si el webService nos regresa la palabra se actualizo quiere decir que se ha registrado con exito.
                if (response.trim().equalsIgnoreCase("SeActualizo")){
                    limpiar();
                    Toast.makeText(getApplicationContext(),"Se ha Actualizado con exito",Toast.LENGTH_SHORT).show();
                    btnCancelar.setVisibility(View.INVISIBLE);
                    btnModificar.setVisibility(View.INVISIBLE);
                    btnEliminar.setVisibility(View.INVISIBLE);
                    txtCodigoUser.setEnabled(true);
                }else{
                    Toast.makeText(getApplicationContext(),"No se ha Actualizado ",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No se logro actualizar", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("codigo", txtCodigoUser.getText().toString());
                parametros.put("nombre", txtNombre.getText().toString());
                parametros.put("apellidos", txtApellidos.getText().toString());
                parametros.put("telefono", txtTelefono.getText().toString());
                parametros.put("tipo_usuario", cboTipo.getText().toString());
                return parametros;
            }
        };

        requestqueue = Volley.newRequestQueue(this);
        requestqueue.add(stringrequest);
    } //Fin del método modificar un usuario.

    //Método para dar de baja un usuario.
    private void eliminarUsuario(){
        String URL = "http://192.168.1.70/AbarrotesVilla/eliminarUser_service.php?codigo=" + txtCodigoUser.getText().toString();

        StringRequest stringrequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equalsIgnoreCase("elimina")){
                    txtCodigoUser.setEnabled(true);
                    limpiar();
                    btnCancelar.setVisibility(View.INVISIBLE);
                    btnModificar.setVisibility(View.INVISIBLE);
                    btnEliminar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Se ha Eliminado con exito",Toast.LENGTH_SHORT).show();

                } else if(response.trim().equalsIgnoreCase("noElimina")){
                    txtCodigoUser.setEnabled(true);
                    limpiar();
                    btnCancelar.setVisibility(View.INVISIBLE);
                    btnModificar.setVisibility(View.INVISIBLE);
                    btnEliminar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"No se ha Eliminado ",Toast.LENGTH_SHORT).show();

                } else{
                    txtCodigoUser.setEnabled(true);
                    limpiar();
                    btnCancelar.setVisibility(View.INVISIBLE);
                    btnModificar.setVisibility(View.INVISIBLE);
                    btnEliminar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"No se encuentra la persona ",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No se logro eliminar", Toast.LENGTH_SHORT).show();
            }
        });

        requestqueue = Volley.newRequestQueue(this);
        requestqueue.add(stringrequest);
    }

    //Método para limpiar los campos.
    public void limpiar(){
        txtCodigoUser.setText("");
        txtNombre.setText("");
        txtApellidos.setText("");
        txtTelefono.setText("");
        cboTipo.setText("");
    }

    //Método para el botón regresar.
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MenuPrincipal.class);
        startActivity(intent);
        finish();
    }

    //Método para establecer la lista desplegable de tipo de usuario.
    public void listTipoUser(){
        final List<String> opciones = Arrays.asList("Administrador","Usuario");
        //Creamos un objeto de la clase ArrayAdapter, para colocar los items en el despegable.
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.item_list, opciones);
        cboTipo.setAdapter(adapter);
    }
}