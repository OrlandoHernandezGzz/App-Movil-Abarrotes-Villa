package com.example.app_abarrotesvilla;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterProveedores extends RecyclerView.Adapter<AdapterProveedores.ViewHolderDatos> implements View.OnClickListener {
    private ArrayList<Proveedor> listproveedores;
    private View.OnClickListener listener;

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }
    }
    //private ArrayList<Proveedor> listOriginal;

    public interface OnItemClickListener {
        void onItemClick(Proveedor item);
    }

    public AdapterProveedores(ArrayList<Proveedor> listproveedores) {
        this.listproveedores = listproveedores;
        //listOriginal = new ArrayList<>();
        //listOriginal.addAll(listproveedores);
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Crea una nueva vista, que define la interfaz de usuario del elemento de la lista
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_list_proveedores, parent,false);

        //Para que pueda escuchar el evento de seleccion
        view.setOnClickListener(this);

        return new AdapterProveedores.ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.tvNombre.setText("Nombre: " + listproveedores.get(position).getNombre());
        holder.tvTelefono.setText("Telefono: " + listproveedores.get(position).getTelefono());
    }

    //Metodo para filtrar los datos.
    /*public void filtrado(final String txtBuscar){
        int longitud = txtBuscar.length();
        if(longitud == 0){
            listproveedores.clear();
            listproveedores.addAll(listOriginal);
        } else{
            //if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Proveedor> collecion = listproveedores.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listproveedores.clear();
                listproveedores.addAll(collecion);
            //} else {
                for (Proveedor c : listOriginal) {
                    if (c.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listproveedores.add(c);
                    }
                }
            //}
        }
        notifyDataSetChanged();
    } */

    @Override
    public int getItemCount() {
        return listproveedores.size();
    }

    //Clase
    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        //Variable de tipo clase.
        TextView tvNombre, tvTelefono;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvTelefono = itemView.findViewById(R.id.tvTelefono);
        }
    }
}
