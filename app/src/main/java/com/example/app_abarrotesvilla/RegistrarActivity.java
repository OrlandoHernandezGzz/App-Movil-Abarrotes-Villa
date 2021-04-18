package com.example.app_abarrotesvilla;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrarActivity extends AppCompatActivity {
    //Declaramos nuestros atributos.
    private AutoCompleteTextView cboTipo;
    private TextInputEditText txtNombre, txtApellidos, txtTelefono, txtUsuario, txtPassword;
    private MaterialButton btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        //Comando para habilitar que enseñe un icono en el action bar.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Establecemos el icono con el siguiente comando.
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //Declaramos un objeto de la clase list, con los items que llevara nuestro despegable.
        final List<String> opciones = Arrays.asList("Administrador","Usuario");

        //Puente de los objetos con los componentes del xml.
        cboTipo = findViewById(R.id.cboTipo);
        txtNombre = findViewById(R.id.txtNombre);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtUsuario = findViewById(R.id.txtUsuario);
        txtPassword = findViewById(R.id.txtPassword);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        //Creamos un objeto de la clase ArrayAdapter, para colocar los items en el despegable.
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.item_list, opciones);
        cboTipo.setAdapter(adapter);

        //Generamos la opcion del botón registrar.
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = txtNombre.getText().toString();
                String apellidos = txtApellidos.getText().toString();
                String telefono = txtTelefono.getText().toString();
                String usuario = txtUsuario.getText().toString();
                String password = txtPassword.getText().toString();
                String tipo = cboTipo.getText().toString();

                if(!nombre.isEmpty() && !apellidos.isEmpty() && !telefono.isEmpty() && !usuario.isEmpty() && !password.isEmpty()
                    && !tipo.isEmpty()){
                    registrar("http://192.168.1.70/AbarrotesVilla/registro_service.php");
                } else{
                    Toast.makeText(RegistrarActivity.this, "No se permiten campos vacíos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    } //FIN DEL MÉTODO ONCREATE.

    //Método para hacer el registro.
    private void registrar(String URL){
        StringRequest stringrequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Registro Exitoso!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("nombre", txtNombre.getText().toString());
                parametros.put("apellidos", txtApellidos.getText().toString());
                parametros.put("telefono", txtTelefono.getText().toString());
                parametros.put("usuario", txtUsuario.getText().toString());
                parametros.put("password", txtPassword.getText().toString());
                parametros.put("tipo_usuario", cboTipo.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestqueue = Volley.newRequestQueue(this);
        //Ayuda a procesar las peticiones hechas de nuestra app.
        requestqueue.add(stringrequest);
    }
}