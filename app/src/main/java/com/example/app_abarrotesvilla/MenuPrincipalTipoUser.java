package com.example.app_abarrotesvilla;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MenuPrincipalTipoUser extends AppCompatActivity {
    //Declaramos nuestros atributos
    private ImageButton btnVentaUs;
    String nombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_tipouser);

        //Comando para habilitar que enseñe un icono en el action bar.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Establecemos el icono con el siguiente comando.
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //Puente de nuestros atributos a los componentes del xml.
        btnVentaUs = findViewById(R.id.btnVentaUs);

        //nombreUsuario = getIntent().getStringExtra("usuario");

        //Métodos de acciones de los botones.
        btnVentaUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PuntoVenta_user.class);
                startActivity(intent);
                finish();
            }
        });

    }

    //Método para crear la opción del menú.
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.overflowtipous, menu);
        return true;
    }

    //Método para seleccionar los items de nuestro menú.
    public boolean onOptionsItemSelected(MenuItem item){
        int item_seleccionado = item.getItemId();

        if(item_seleccionado == R.id.opPerfil) {
            Intent intent = new Intent(getApplicationContext(), PerfilUsuario_user.class);
            //intent.putExtra("usuario", nombreUsuario);
            startActivity(intent);
            finish();
        } else if(item_seleccionado == R.id.opCerrar){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    } //FIN DEL METODO SELECCIONAR OPCION DE ITEM

    //Método para el botón regresar.
    @Override
    public void onBackPressed(){

    }
}