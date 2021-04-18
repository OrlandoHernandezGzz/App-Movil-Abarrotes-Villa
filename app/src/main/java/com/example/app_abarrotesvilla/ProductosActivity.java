package com.example.app_abarrotesvilla;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import com.google.android.material.textfield.TextInputEditText;

public class ProductosActivity extends AppCompatActivity {
    //Declaramos los atributos.
    private TextInputEditText txtCodigo, txtProducto, txtDescripcion, txtCantidad, txtCantReserva;
    private TextInputEditText txtPrecioCompra, txtPrecioVenta;
    private ImageButton btnScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        //Comando para habilitar que enseñe un icono en el action bar.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Establecemos el icono con el siguiente comando.
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);



    }
}