package com.example.app_abarrotesvilla;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ChatProveedores extends AppCompatActivity {
    //Atributos.
    private AutoCompleteTextView cboProveedor;
    private TextInputEditText txtProveedor, txtMensaje;
    private MaterialButton btnEnviar;
    private String proveedor, mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedores);
        //Comando para habilitar que enseñe un icono en el action bar.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Establecemos el icono con el siguiente comando.
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //Hacemos el puente de nuestros atributos(logica) con los componentes(Diseño).
        txtProveedor = findViewById(R.id.txtProveedor);
        txtMensaje = findViewById(R.id.txtMensaje);
        btnEnviar = findViewById(R.id.btnEnviar);

        Intent intent = getIntent();
        txtProveedor.setText(intent.getStringExtra("telefono"));

        //Acción del botón enviar.
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Recuperamos la información del numero y mensaje.

                //txtProveedor.setText(proveedor);
                mensaje = txtMensaje.getText().toString();


                //Variable para asegurarnos que tenga wpp instalado en su dispositivo movil.
                boolean wppIntalado = appInstalada("com.whatsapp");

                //Validador para hacer las acciones cuando esta instalada o no.
                if(wppIntalado){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+"+52"+ txtProveedor +"&text="+ mensaje));
                    startActivity(intent);
                } else{
                    Toast.makeText(ChatProveedores.this, "Tienes que tener instalado WhatsApp", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //Método para saber si whatsapp esta instalado en el celular.
    private boolean appInstalada(String URL){
        //Objeto de la clase packageManager para poder entrar a nuestro administrador de paquetes.
        //De esa forma podremos obtener si tenemos instalado wpp.
        PackageManager admPaquetes = getPackageManager();
        boolean appInstalada;
        try {
            //Obtenemos información de la url pasada que sería la de wpp.
            admPaquetes.getPackageInfo(URL, PackageManager.GET_ACTIVITIES);
            appInstalada = true;
        } catch (PackageManager.NameNotFoundException e) {
            appInstalada = false;
        }
        return appInstalada;
    } // Fin del metodo appInstalada.


    //Método para establecer el telefono
    public void setTelefono(String proveedor){
        txtProveedor.setText(proveedor);
    }


    //Método para el botón regresar.
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), AgendaProveedores.class);
        startActivity(intent);
        finish();
    }

}
