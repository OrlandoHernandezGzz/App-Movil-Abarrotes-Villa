package com.example.app_abarrotesvilla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MenuPrincipal extends AppCompatActivity {
    //Declaramos nuestros atributos
    private ImageButton btnAlmacen, btnVenta, btnControlUsuarios, btnProveedores;
    String nombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        //Comando para habilitar que enseñe un icono en el action bar.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Establecemos el icono con el siguiente comando.
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //Puente de nuestros atributos a los componentes del xml.
        btnAlmacen = findViewById(R.id.btnAlmacen);
        btnVenta = findViewById(R.id.btnVenta);
        btnControlUsuarios = findViewById(R.id.btnControlUsuarios);
        btnProveedores = findViewById(R.id.btnProveedores);
        nombreUsuario = getIntent().getStringExtra("usuario");

        //Métodos de acciones de los botones.
        btnAlmacen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductosActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PuntoVenta.class);
                startActivity(intent);
                finish();
            }
        });

        btnControlUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ControlUsuario.class);
                startActivity(intent);
                finish();
            }
        });

        btnProveedores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Proveedores.class);
                startActivity(intent);
                finish();
            }
        });

    }

    //Método para crear la opción del menú.
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    //Método para seleccionar los items de nuestro menú.
    public boolean onOptionsItemSelected(MenuItem item){
        int item_seleccionado = item.getItemId();

        if(item_seleccionado == R.id.opPerfil) {
            Intent intent = new Intent(getApplicationContext(), PerfilUsuario.class);
            intent.putExtra("usuario", nombreUsuario);
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