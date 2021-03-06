package com.example.app_abarrotesvilla;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PuntoVenta_user extends AppCompatActivity {
    //Declaramos nuestros atributos.
    private ArrayList<ListaVentas> listVentas;
    private RecyclerView recycler;
    private ImageButton btnScanner, btnBuscar, btnGuardar, btnCancelar;
    private TextView tvProducto, tvPrecio, tvTotal;
    private TextInputEditText txtCodigo, txtCantidad;
    private String codigo, producto, precio, cantidad, cantidadDB;
    private Double subtotal, total = 0.0;
    private JsonObjectRequest jsonObjectRequest;
    private RequestQueue requestqueue;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog alert;
    private TextInputEditText txtPagoEm;
    private MaterialButton btnCobrarEm;
    private TextView tvCambioEm;
    private String pagoEm, strRestaCantProd;
    private int restaCantProd;
    private String channelID = "channelID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punto_venta);
        //Comando para habilitar que ense??e un icono en el action bar.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Establecemos el icono con el siguiente comando.
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        //Establecemos la parte logica con nuestros componentes del layout.
        recycler = findViewById(R.id.rvVenta);
        btnScanner = findViewById(R.id.btnScanner);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);
        tvProducto = findViewById(R.id.tvProducto);
        tvPrecio = findViewById(R.id.tvPrecio);
        tvTotal = findViewById(R.id.tvTotal);
        txtCodigo = findViewById(R.id.txtCodigo);
        txtCantidad = findViewById(R.id.txtCantidad);

        //Establecemos nuestros botones como invisibles.
        btnGuardar.setVisibility(View.INVISIBLE);
        btnCancelar.setVisibility(View.INVISIBLE);
        btnBuscar.setVisibility(View.INVISIBLE);

        //Establecemos el tipo de layout Cuando se pone this, el linear layout manager quiere decir que estara en vertical.
        recycler.setLayoutManager(new LinearLayoutManager(this));
        //Instanciamos nuestro objeto de tipo array List
        listVentas = new ArrayList<ListaVentas>();

        //Instanciamops nuestra clase AdapterVenta, para que muestre los valores de nuestro vector en su layout.
        AdapterVenta adapter = new AdapterVenta(listVentas);
        //Establecemos el adapter en nuestro componente recycler.
        recycler.setAdapter(adapter);

        //Acci??n del bot??n Scanner.
        btnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codigo = txtCodigo.getText().toString();

                if(codigo.isEmpty()){
                    IntentIntegrator integrator = new IntentIntegrator(PuntoVenta_user.this);
                    //A nuestro bot??n le agregamos el m??todo de scanner deseado.
                    integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                    integrator.setPrompt("Lector - CDP Abarrotes villa");
                    integrator.setCameraId(0);
                    integrator.setBeepEnabled(true);
                    integrator.setBarcodeImageEnabled(true);
                    integrator.initiateScan();

                    btnBuscar.setVisibility(View.VISIBLE);
                    btnScanner.setVisibility(View.INVISIBLE);

                } else {
                    //Llamamos nuestro metodo para que haga la consulta de productos.
                    consultarProducto();
                    //Deshabilitamos nuestra caja de texto de codigo de barras para que no sea modificada.
                    //txtCodigo.setEnabled(false);
                    //Hacemos invisible nuestro boton guardar, para que no guarde ese mismo registro.
                    btnGuardar.setVisibility(View.VISIBLE);
                    btnCancelar.setVisibility(View.VISIBLE);
                }
            }
        });

        //Accion del boton buscar.
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Llamamos nuestro metodo para que haga la consulta de productos.
                consultarProducto();
                //Deshabilitamos nuestra caja de texto de codigo de barras para que no sea modificada.
                //txtCodigo.setEnabled(false);
                //Hacemos invisible nuestro boton guardar, para que no guarde ese mismo registro.
                btnGuardar.setVisibility(View.VISIBLE);
                btnCancelar.setVisibility(View.VISIBLE);
                btnBuscar.setVisibility(View.INVISIBLE);
                btnScanner.setVisibility(View.VISIBLE);
            }
        });

        //Acci??n del bot??n Cancelar.
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiar();
                btnGuardar.setVisibility(View.INVISIBLE);
                btnCancelar.setVisibility(View.INVISIBLE);
                txtCodigo.setEnabled(true);
            }
        });

        //Acci??n del bot??n Guardar venta
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Para saber si cantidad esta vac??o.
                cantidad = txtCantidad.getText().toString();

                if(!cantidad.isEmpty()){
                    //El m??todo para restar la cantidad de almacen.
                    modificarCantidadProd();
                    llenarLista();
                    limpiar();
                    btnGuardar.setVisibility(View.INVISIBLE);
                    btnCancelar.setVisibility(View.INVISIBLE);
                    txtCodigo.setEnabled(true);

                    //Sumamos los subtotales de cada compra
                    total += subtotal;
                    //Establecemos a nuestra etiqueta total su valor.
                    tvTotal.setText(total.toString());

                } else{
                    Toast.makeText(getApplicationContext(), "Debe digitar la cantidad", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //M??todo para activar nuestro scanner en otra activity.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentresult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(intentresult != null){
            //Si le damos al bot??n retroceder.
            if(intentresult.getContents() == null){
                Toast.makeText(this, "Lectura cancelada", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this, intentresult.getContents(), Toast.LENGTH_SHORT).show();
                txtCodigo.setText(intentresult.getContents());
            }
        } else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    } //Fin del m??todo onActivityResult

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //M??todo para mostrar los datos.
    private void consultarProducto(){
        //Variable donde esta nuestra URL del service asignandole el codigo a buscar.
        String URL = "https://appabarrotesvilla.000webhostapp.com/consultarProd_service.php?codigo=" + txtCodigo.getText().toString();
        //
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Hacemos un array de tipo vector para traer nuestro json almacenado en la base de datos producto
                JSONArray json = response.optJSONArray("producto");
                JSONObject jsonobject = null;

                try {
                    //obtenemos nuestro objeto json en el indice 0, osea el id del producto.
                    jsonobject = json.getJSONObject(0);
                    cantidadDB = jsonobject.optString("cantidad_prod");
                    //Una vez que detecta el id, establece los objetos de la base de datos hac??a nuestros campos de texto.
                    tvProducto.setText(jsonobject.optString("nombre_prod"));
                    tvPrecio.setText(jsonobject.optString("precioven_prod"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No se logro la consulta.", Toast.LENGTH_SHORT).show();
                //Activa de nuevo el txtCodigo.
                txtCodigo.setEnabled(true);
                //En dado caso que no se encuentre la consulta, los botones guardar y cancelar no deben habilitarse.
                btnGuardar.setVisibility(View.INVISIBLE);
                btnCancelar.setVisibility(View.INVISIBLE);
            }
        });

        requestqueue = Volley.newRequestQueue(this);
        requestqueue.add(jsonObjectRequest);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //M??todo para hacer la modificaci??n de nuestros productos.
    private void modificarCantidadProd(){
        //Cadena de texto que se manda nuestro URL de servicio modificar.
        String URL = "https://appabarrotesvilla.000webhostapp.com/modifiCantProd_service.php?";
        //La resta que se hace cuando se compra un producto.
        restaCantProd = Integer.parseInt(cantidadDB) - Integer.parseInt(txtCantidad.getText().toString());
        strRestaCantProd = String.valueOf(restaCantProd);
        //Obtenemos nuestro valor de la caja de texto.
        codigo = txtCodigo.getText().toString();

        StringRequest stringrequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Si el webService nos regresa la palabra se actualizo quiere decir que se ha registrado con exito.
                if (response.trim().equalsIgnoreCase("SeActualizo")){
                    Toast.makeText(getApplicationContext(),"Quedan: " + restaCantProd + " elementos",Toast.LENGTH_SHORT).show();
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
                Map<String, String> parametros = new HashMap<>();
                parametros.put("codigo", codigo);
                parametros.put("cantidad", strRestaCantProd);
                return parametros;
            }
        };

        requestqueue = Volley.newRequestQueue(this);
        requestqueue.add(stringrequest);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //M??todo para crear la opci??n del men??.
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.overflow_venta, menu);
        return true;
    }

    //M??todo para seleccionar los items de nuestro men??.
    public boolean onOptionsItemSelected(MenuItem item){
        int item_seleccionado = item.getItemId();

        if(item_seleccionado == R.id.opCobrarVenta) {
            cambioTotalVentanaEmergente();
        } else if(item_seleccionado == R.id.opCerrar){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        } else if(item_seleccionado == R.id.opNuevaVenta){
            Intent intent = new Intent(getApplicationContext(), PuntoVenta_user.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    } //FIN DEL METODO SELECCIONAR OPCION DE ITEM

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //M??todo del login emergente
    public void cambioTotalVentanaEmergente(){
        dialogBuilder = new AlertDialog.Builder(this);
        View login_view = getLayoutInflater().inflate(R.layout.emergente_cambio, null);
        txtPagoEm = login_view.findViewById(R.id.txtPagoEm);
        btnCobrarEm = login_view.findViewById(R.id.btnCobrarEm);
        tvCambioEm = login_view.findViewById(R.id.tvCambioEm);

        dialogBuilder.setView(login_view);
        alert = dialogBuilder.create();
        alert.show();

        btnCobrarEm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Obtenemos el valor digitado de pago, de la caja de texto emergente
               pagoEm = txtPagoEm.getText().toString();
               //Para hacer la resta, para saber cuanto devoleremos de cambio
               Double cambio = Double.parseDouble(pagoEm) - total;
               //Finalmente mostramos el cambio en el textview.
               tvCambioEm.setText("$" + cambio.toString());
            }
        });
    }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //M??todo para limpiar las cajas de texto y textView.
    public void limpiar(){
        txtCodigo.setText("");
        txtCantidad.setText("");
        tvProducto.setText("");
        tvPrecio.setText("");
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //M??todo para llenar nuestra lista de las cantidades.
    public void llenarLista(){
        producto = tvProducto.getText().toString();
        precio = tvPrecio.getText().toString();
        cantidad = txtCantidad.getText().toString();
        subtotal = Double.parseDouble(precio) * Double.parseDouble(cantidad);
        listVentas.add(new ListaVentas(producto, precio, cantidad, subtotal));
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* //M??todo para crear notificaciones.
    public void notificacion(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelID);
        builder.setSmallIcon(R.drawable.logo_villa);
        builder.setContentTitle("Advertencia de la cantidad de productos!");
        builder.setContentText("")
    } */

    //M??todo para el bot??n regresar.
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MenuPrincipalTipoUser.class);
        startActivity(intent);
        finish();
    }
}