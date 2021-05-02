package com.example.app_abarrotesvilla;

import androidx.annotation.Nullable;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProductosActivity extends AppCompatActivity {
    //Declaramos los atributos.
    private TextInputEditText txtCodigo, txtProducto, txtDescripcion, txtCantidad, txtCantReserva;
    private TextInputEditText txtPrecioCompra, txtPrecioVenta;
    private ImageButton btnScanner, btnGuardar, btnModificar, btnEliminar;
    private String codigo, producto, descripcion, cantidad, cantidadReserva, precioCompra, precioVenta;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue requestqueue;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        //Comando para habilitar que enseñe un icono en el action bar.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Establecemos el icono con el siguiente comando.
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //Puente de nuestros atributos a los componentes de XML.
        txtCodigo = findViewById(R.id.txtCodigo);
        txtProducto = findViewById(R.id.txtProducto);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtCantReserva = findViewById(R.id.txtCantReserva);
        txtPrecioCompra = findViewById(R.id.txtPrecioCompra);
        txtPrecioVenta = findViewById(R.id.txtPrecioVenta);
        btnScanner = findViewById(R.id.btnScanner);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnModificar = findViewById(R.id.btnModificar);
        btnEliminar = findViewById(R.id.btnEliminar);

        //Hacemos invisibles nuestros botones para que el usuario no modifique o borre hasta que se haga la consulta
        btnModificar.setVisibility(View.INVISIBLE);
        btnEliminar.setVisibility(View.INVISIBLE);

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //Método del botón Scanner.
        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codigo = txtCodigo.getText().toString();

                if(codigo.isEmpty()){
                    IntentIntegrator integrator = new IntentIntegrator(ProductosActivity.this);
                    //A nuestro botón le agregamos el método de scanner deseado.
                    integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                    integrator.setPrompt("Lector - CDP Abarrotes villa");
                    integrator.setCameraId(0);
                    integrator.setBeepEnabled(true);
                    integrator.setBarcodeImageEnabled(true);
                    integrator.initiateScan();

                } else {
                    //Llamamos nuestro metodo para que haga la consulta de productos.
                    consultarProducto();
                    //Deshabilitamos nuestra caja de texto de codigo de barras para que no sea modificada.
                    txtCodigo.setEnabled(false);
                    //Hacemos invisible nuestro boton guardar, para que no guarde ese mismo registro.
                    btnGuardar.setVisibility(View.INVISIBLE);
                    //Cuando ya este hecha la consulta ahora si activa los botones de eliminar y modificar.
                    btnModificar.setVisibility(View.VISIBLE);
                    btnEliminar.setVisibility(View.VISIBLE);
                }
            }
        });

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //Método del botón Guardar.
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codigo = txtCodigo.getText().toString();
                producto = txtProducto.getText().toString();
                descripcion = txtDescripcion.getText().toString();
                cantidad = txtCantidad.getText().toString();
                cantidadReserva = txtCantReserva.getText().toString();
                precioCompra = txtPrecioCompra.getText().toString();
                precioVenta = txtPrecioVenta.getText().toString();

                //No permitas que mande campos vacíos a nuestra base de datos.
                if(!codigo.isEmpty() && !producto.isEmpty() && !descripcion.isEmpty() && !cantidad.isEmpty() && !cantidadReserva.isEmpty()
                && !precioCompra.isEmpty() && !precioVenta.isEmpty()){
                    registrarProducto("http://192.168.1.70/AbarrotesVilla/registroProd_service.php");
                } else {
                    Toast.makeText(ProductosActivity.this, "No se permiten campos vacíos.", Toast.LENGTH_SHORT).show();
                }

            }
        });

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        //Método Modificar.
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificarProducto();
            }
        });

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Método Eliminar.
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarProducto();
            }
        });

    } //FIN DEL MÉTODO ONCREATE().


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Método para activar nuestro scanner en otra activity.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentresult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(intentresult != null){
            //Si le damos al botón retroceder.
            if(intentresult.getContents() == null){
                Toast.makeText(this, "Lectura cancelada", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this, intentresult.getContents(), Toast.LENGTH_SHORT).show();
                txtCodigo.setText(intentresult.getContents());
            }
        } else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    } //Fin del método onActivityResult

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Método para hacer el registro de nuestros productos.
    private void registrarProducto(String URL){
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
                parametros.put("codigo", txtCodigo.getText().toString());
                parametros.put("producto", txtProducto.getText().toString());
                parametros.put("descripcion", txtDescripcion.getText().toString());
                parametros.put("cantidad", txtCantidad.getText().toString());
                parametros.put("cantidadReserva", txtCantReserva.getText().toString());
                parametros.put("precioCompra", txtPrecioCompra.getText().toString());
                parametros.put("precioVenta", txtPrecioVenta.getText().toString());
                return parametros;
            }
        };

        requestqueue = Volley.newRequestQueue(this);
        //Ayuda a procesar las peticiones hechas de nuestra app.
        requestqueue.add(stringrequest);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Método para mostrar los datos.
    private void consultarProducto(){
        //Variable donde esta nuestra URL del service asignandole el codigo a buscar.
        String URL = "http://192.168.1.70/AbarrotesVilla/consultarProd_service.php?codigo=" + txtCodigo.getText().toString();
        //
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Hacemos un array de tipo vector para traer nuestro json almacenado en la base de datos producto
                JSONArray json = response.optJSONArray("producto");
                //
                JSONObject jsonobject = null;

                try {
                    //obtenemos nuestro objeto json en el indice 0, osea el id del producto.
                    jsonobject = json.getJSONObject(0);
                    //Una vez que detecta el id, establece los objetos de la base de datos hacía nuestros campos de texto.
                    txtProducto.setText(jsonobject.optString("nombre_prod"));
                    txtDescripcion.setText(jsonobject.optString("descrip_prod"));
                    txtCantidad.setText(jsonobject.optString("cantidad_prod"));
                    txtCantReserva.setText(jsonobject.optString("cantres_prod"));
                    txtPrecioCompra.setText(jsonobject.optString("preciocom_prod"));
                    txtPrecioVenta.setText(jsonobject.optString("precioven_prod"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No se logro la consulta.", Toast.LENGTH_SHORT).show();
                txtCodigo.setEnabled(true);
                btnGuardar.setVisibility(View.VISIBLE);
                btnGuardar.setVisibility(View.VISIBLE);
                btnModificar.setVisibility(View.INVISIBLE);
                btnEliminar.setVisibility(View.INVISIBLE);
                limpiar();
            }
        });

        requestqueue = Volley.newRequestQueue(this);
        requestqueue.add(jsonObjectRequest);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Método para hacer la modificación de nuestros productos.
    private void modificarProducto(){
        //Cadena de texto que se manda nuestro URL de servicio modificar.
        String URL = "http://192.168.1.70/AbarrotesVilla/modifProd_service.php?";

        StringRequest stringrequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Si el webService nos regresa la palabra se actualizo quiere decir que se ha registrado con exito.
                if (response.trim().equalsIgnoreCase("SeActualizo")){
                    txtCodigo.setEnabled(true);
                    limpiar();
                    Toast.makeText(getApplicationContext(),"Se ha Actualizado con exito",Toast.LENGTH_SHORT).show();
                    btnGuardar.setVisibility(View.VISIBLE);
                    btnModificar.setVisibility(View.INVISIBLE);
                    btnEliminar.setVisibility(View.INVISIBLE);
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
                parametros.put("codigo", txtCodigo.getText().toString());
                parametros.put("producto", txtProducto.getText().toString());
                parametros.put("descripcion", txtDescripcion.getText().toString());
                parametros.put("cantidad", txtCantidad.getText().toString());
                parametros.put("cantidadReserva", txtCantReserva.getText().toString());
                parametros.put("precioCompra", txtPrecioCompra.getText().toString());
                parametros.put("precioVenta", txtPrecioVenta.getText().toString());
                return parametros;
            }
        };

        requestqueue = Volley.newRequestQueue(this);
        requestqueue.add(stringrequest);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void eliminarProducto(){
        String URL = "http://192.168.1.70/AbarrotesVilla/eliminarProd_service.php?codigo=" + txtCodigo.getText().toString();

        StringRequest stringrequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equalsIgnoreCase("elimina")){
                    txtCodigo.setEnabled(true);
                    limpiar();
                    Toast.makeText(getApplicationContext(),"Se ha Eliminado con exito",Toast.LENGTH_SHORT).show();
                    btnGuardar.setVisibility(View.VISIBLE);
                    btnModificar.setVisibility(View.INVISIBLE);
                    btnEliminar.setVisibility(View.INVISIBLE);

                } else if(response.trim().equalsIgnoreCase("noElimina")){
                     Toast.makeText(getApplicationContext(),"No se ha Eliminado ",Toast.LENGTH_SHORT).show();

                } else{
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
    } //FIN DEL METODO ELIMINAR.

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Método limpiar cajas de texto.
    private void limpiar(){
        txtCodigo.setText("");
        txtProducto.setText("");
        txtDescripcion.setText("");
        txtCantidad.setText("");
        txtCantReserva.setText("");
        txtPrecioCompra.setText("");
        txtPrecioVenta.setText("");
    } //FIN DEL METODO LIMPIAR

    //Método para el botón regresar.
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MenuPrincipal.class);
        startActivity(intent);
        finish();
    }
}
