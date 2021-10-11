package com.example.app_abarrotesvilla;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    //Declaramos nuestros atributos.
    TextInputEditText txtUsuario, txtPassword, txtUsuarioEm, txtPasswordEm;
    MaterialButton btnIngresar, btnIngresarEm;
    TextView btnUsuarioNuevo;
    String usuario, password, usuarioEm, passwordEm, tipoUsuario;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Comando para habilitar que enseñe un icono en el action bar.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Establecemos el icono con el siguiente comando.
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //Hacemos el puente de nuestros componentes de clase con el xml.
        txtUsuario = findViewById(R.id.txtUsuario);
        txtPassword = findViewById(R.id.txtPassword);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnUsuarioNuevo = findViewById(R.id.btnUsuarioNuevo);

        //Creamos el botón ingresar.
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = txtUsuario.getText().toString();
                password = txtPassword.getText().toString();

                if(!usuario.isEmpty() && !password.isEmpty()){
                    login_service("http://192.168.1.73/AbarrotesVilla/login_service.php");
                } else{
                    Toast.makeText(Login.this, "No se permiten campos vacíos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Creamos el botón para dirigirnos a el registro de usuarios.
        btnUsuarioNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginEmergente();
            }
        });


    } // Fin del metodo onCreate()

    //Método validar usuario.
    private void login_service(String URL){
        //Hacemos una solicitud de cadena con el método de envío post.
        StringRequest stringrequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            //El metodo on response reacciona si la peticion se procesa.
            @Override
            public void onResponse(String response) {
                //si el response no esta vacío, nos da entender que el usuario y contraseña existen
                if(!response.isEmpty()){
                    try{

                        JSONObject obResultado = new JSONObject(response);
                        tipoUsuario = obResultado.get("us_tipo").toString();
                        usuario = txtUsuario.getText().toString();

                        if(tipoUsuario.equalsIgnoreCase("Administrador")){
                            Intent intent = new Intent(getApplicationContext(), MenuPrincipal.class);
                            intent.putExtra("usuario", usuario);
                            startActivity(intent);
                            finish();
                        } else{
                            Intent intent = new Intent(getApplicationContext(), MenuPrincipalTipoUser.class);
                            intent.putExtra("usuario", usuario);
                            startActivity(intent);
                            finish();
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                    }

                } else{
                    Toast.makeText(Login.this, "Usuario o Contraseña incorrectas", Toast.LENGTH_SHORT).show();
                    txtUsuario.setText("");
                    txtPassword.setText("");
                }
            }
        }, new Response.ErrorListener() {
            //Si no se procesa la peticion al servidor.
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            //Se colocaran los parametros que nuestro servicio solicita para devolvernos una respueta.
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Objeto map, creamos instancia de nombre parametros.
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("usuario", txtUsuario.getText().toString());
                parametros.put("password", txtPassword.getText().toString());
                //se retorna la coleccion de datos.
                return parametros;
            }
        };

        RequestQueue requestqueue = Volley.newRequestQueue(this);
        //Ayuda a procesar las peticiones hechas de nuestra app.
        requestqueue.add(stringrequest);

    } //FIN DEL METODO VALIDAR USUARIO.

    //Método del login emergente
    public void loginEmergente(){
        dialogBuilder = new AlertDialog.Builder(this);
        View login_view = getLayoutInflater().inflate(R.layout.login_emergente, null);
        txtUsuarioEm = login_view.findViewById(R.id.txtPago);
        txtPasswordEm = login_view.findViewById(R.id.txtPasswordEm);
        btnIngresarEm = login_view.findViewById(R.id.btnIngresarEm);

        dialogBuilder.setView(login_view);
        alert = dialogBuilder.create();
        alert.show();

        btnIngresarEm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuarioEm = txtUsuarioEm.getText().toString();
                passwordEm = txtPasswordEm.getText().toString();

                if(!usuarioEm.isEmpty() && !passwordEm.isEmpty()){
                    login_serviceEmergente("http://192.168.1.73/AbarrotesVilla/login_service.php");
                } else{
                    Toast.makeText(Login.this, "No se permiten campos vacíos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //Método validar usuario.
    private void login_serviceEmergente(String URL){
        //Hacemos una solicitud de cadena con el método de envío post.
        StringRequest stringrequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            //El metodo on response reacciona si la peticion se procesa.
            @Override
            public void onResponse(String response) {
                //si el response no esta vacío, nos da entender que el usuario y contraseña existen
                if(!response.isEmpty()){
                    try{

                        JSONObject obResultado = new JSONObject(response);
                        tipoUsuario = obResultado.get("us_tipo").toString();

                        if(tipoUsuario.equalsIgnoreCase("Administrador")){
                            Intent intent = new Intent(getApplicationContext(), RegistrarActivity.class);
                            startActivity(intent);
                            finish();
                        } else{
                            Toast.makeText(getApplicationContext(), "Tipo de usuario restringido para esta acción", Toast.LENGTH_SHORT).show();
                            txtUsuarioEm.setText("");
                            txtPasswordEm.setText("");
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                    }

                } else{
                    Toast.makeText(Login.this, "Usuario o Contraseña incorrectas", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            //Si no se procesa la peticion al servidor.
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            //Se colocaran los parametros que nuestro servicio solicita para devolvernos una respueta.
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Objeto map, creamos instancia de nombre parametros.
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("usuario", txtUsuarioEm.getText().toString());
                parametros.put("password", txtPasswordEm.getText().toString());
                //se retorna la coleccion de datos.
                return parametros;
            }
        };

        RequestQueue requestqueue = Volley.newRequestQueue(this);
        //Ayuda a procesar las peticiones hechas de nuestra app.
        requestqueue.add(stringrequest);

    } //FIN DEL METODO VALIDAR USUARIO.

    //Método para el botón regresar.
    @Override
    public void onBackPressed(){

    }
}