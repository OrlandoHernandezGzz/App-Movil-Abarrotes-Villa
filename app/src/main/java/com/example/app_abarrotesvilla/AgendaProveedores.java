package com.example.app_abarrotesvilla;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AgendaProveedores extends AppCompatActivity {
    //Atributos
    private RecyclerView recycler;
    private ArrayList<Proveedor> listProveedores;
    private RequestQueue rq;
    private RecyclerView rv1;
    private AdapterProveedores adaptadorProveedor;
    private SearchView txtBuscarProveedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_proveedores);
        //Comando para habilitar que enseñe un icono en el action bar.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Establecemos el icono con el siguiente comando.
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        listProveedores = new ArrayList<>();
        rq = Volley.newRequestQueue(this);

        //LLamando nuestro metodo
        cargarProveedores();

        //Conectando la parte logica con el diseño.
        rv1 = findViewById(R.id.rv1);
        //txtBuscarProveedor = findViewById(R.id.txtBuscarProveedor);
        LinearLayoutManager linearlayoutmanager = new LinearLayoutManager(this);
        rv1.setLayoutManager(linearlayoutmanager);
        adaptadorProveedor = new AdapterProveedores(listProveedores);

        adaptadorProveedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatProveedores.class);
                intent.putExtra("telefono", listProveedores.get(rv1.getChildAdapterPosition(view)).getTelefono());
                startActivity(intent);
                finish();
                //Toast.makeText(getApplicationContext(), "Seleccion: " + listProveedores.get
                        //(rv1.getChildAdapterPosition(view)).getTelefono(), Toast.LENGTH_SHORT).show();
            }
        });

        rv1.setAdapter(adaptadorProveedor);

        //Declaramos que va hacer la funcionalidad de buscar un contacto.
        //txtBuscarProveedor.setOnQueryTextListener(this);
    }

    //Método para cargar los proveedores
    private void cargarProveedores() {
        String URL = "http://192.168.1.78/AbarrotesVilla/consultarProveedor_service.php";
        JsonObjectRequest requestObject = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String jsonProveedor = response.get("proveedor").toString();
                    JSONArray arrayProveedor = new JSONArray(jsonProveedor);
                    //obtenemos nuestro objeto json en el indice 0, osea el id del proveedor.
                    for(int i = 0; i < 1000; i++){
                        JSONObject jsonobject = new JSONObject(arrayProveedor.get(i).toString());
                        //Una vez que detecta el id, establece los objetos de la base de datos hacía nuestros campos de texto.
                        String nombreProveedor = jsonobject.getString("nombre_proveedor");
                        String telProveedor = jsonobject.getString("tel_proveedor");
                        //Mandamos nuestros datos al contructor de nuestra clase
                        Proveedor proveedores = new Proveedor(nombreProveedor, telProveedor);
                        //Añadimos los datos a nuestro arraylist.
                        listProveedores.add(proveedores);
                        adaptadorProveedor.notifyItemRangeInserted(listProveedores.size(), 1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Para guardar nuestra solitud de los datos.
        rq.add(requestObject);
    }

    //Método para crear la opción del menú.
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.overflow_proveedores, menu);
        return true;
    }

    //Método para seleccionar los items de nuestro menú.
    public boolean onOptionsItemSelected(MenuItem item){
        int item_seleccionado = item.getItemId();

        if(item_seleccionado == R.id.opRegistrar) {
            //Ir a la vista para registrar contactos de proveedores
            Intent intent = new Intent(getApplicationContext(), Proveedores.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    } //FIN DEL METODO SELECCIONAR OPCION DE ITEM

    //Método para el botón regresar.
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MenuPrincipal.class);
        startActivity(intent);
        finish();
    }

    //Metodos del searchView
    /*@Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adaptadorProveedor.filtrado(s);
        return false;
    } */
}