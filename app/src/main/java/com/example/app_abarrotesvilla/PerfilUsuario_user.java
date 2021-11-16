package com.example.app_abarrotesvilla;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import androidx.appcompat.app.AppCompatActivity;

public class PerfilUsuario_user extends AppCompatActivity {
    //Declaramos nuestros atributos.
    private JsonObjectRequest jsonObjectRequest;
    private RequestQueue requestqueue;
    private TextInputEditText txtUsuario, txtPassword;
    private MaterialButton btnModificar, btnGuardar;
    private String nombreUsuario, us_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);
        //Comando para habilitar que enseñe un icono en el action bar.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Establecemos el icono con el siguiente comando.
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //Conectamos la parte logica con el diseño.
        txtUsuario = findViewById(R.id.txtUsuario);
        txtPassword = findViewById(R.id.txtPassword);
        btnModificar = findViewById(R.id.btnModificar);
        btnGuardar = findViewById(R.id.btnGuardar);

        //Pasa el nombre del usuario que a ingresado.
        //nombreUsuario = getIntent().getStringExtra("usuario");

        //LLama el método llamar perfil, para que establezca los datos del usuario.
        SharedPreferences preferences = getSharedPreferences("sesion",
                Context.MODE_PRIVATE);
        nombreUsuario = preferences.getString("user", "No existe el usuario");

        //Carga los datos del usuario que inicio sesion.
        llamarPerfil();

        //Desactivamos nuestras cajas de texto para que no podamos modificarlas.
        txtUsuario.setEnabled(false);
        txtPassword.setEnabled(false);

        //Acciones del botón modificar.
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtUsuario.setEnabled(true);
                txtPassword.setEnabled(true);
            }
        });

        //Acciones del botón guardar.
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = txtUsuario.getText().toString();
                String password = txtPassword.getText().toString();

                if(!usuario.isEmpty() && !password.isEmpty()){
                    modificarPerfil();
                } else {
                    Toast.makeText(getApplicationContext(), "No puede dejar campos vacíos!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    } //Fin del método on create.

    //Método para buscar un usuario.
    private void llamarPerfil(){
        //Variable donde esta nuestra URL del service asignandole el codigo a buscar.
        String URL = "https://appabarrotesvilla.000webhostapp.com/perfilUser_service.php?usuario=" + nombreUsuario;
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
                    us_id = jsonobject.optString("us_id");
                    txtUsuario.setText(jsonobject.optString("us_usuario"));
                    txtPassword.setText(jsonobject.optString("us_password"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No se logro la consulta.", Toast.LENGTH_SHORT).show();
            }
        });

        requestqueue = Volley.newRequestQueue(this);
        requestqueue.add(jsonObjectRequest);
    } //Fin del método buscar usuario.

    //Método para modificar el perfil del usuario.
    private void modificarPerfil(){
        //Cadena de texto que se manda nuestro URL de servicio modificar.
        String URL = "https://appabarrotesvilla.000webhostapp.com/modifPerfilUser_service.php?";

        StringRequest stringrequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Si el webService nos regresa la palabra se actualizo quiere decir que se ha registrado con exito.
                if (response.trim().equalsIgnoreCase("SeActualizo")) {
                    Toast.makeText(getApplicationContext(),"Se ha Actualizado con exito",Toast.LENGTH_SHORT).show();
                    txtUsuario.setEnabled(false);
                    txtPassword.setEnabled(false);
                }else{
                    Toast.makeText(getApplicationContext(),"No se ha Actualizado ",Toast.LENGTH_SHORT).show();
                    txtUsuario.setEnabled(false);
                    txtPassword.setEnabled(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No se logro actualizar", Toast.LENGTH_SHORT).show();
                txtUsuario.setEnabled(false);
                txtPassword.setEnabled(false);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("us_id", us_id);
                parametros.put("us_usuario", txtUsuario.getText().toString());
                parametros.put("us_password", txtPassword.getText().toString());
                return parametros;
            }
        };

        requestqueue = Volley.newRequestQueue(this);
        requestqueue.add(stringrequest);
    } //Fin del método modificar un usuario.

    //Método para crear la opción del menú.
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.overflow_perfil, menu);
        return true;
    }

    //Método para seleccionar los items de nuestro menú.
    public boolean onOptionsItemSelected(MenuItem item){
        int item_seleccionado = item.getItemId();

        if(item_seleccionado == R.id.opCerrar) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    } //FIN DEL METODO SELECCIONAR OPCION DE ITEM

    //Método para el botón regresar.
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MenuPrincipalTipoUser.class);
        startActivity(intent);
        finish();
    }
}