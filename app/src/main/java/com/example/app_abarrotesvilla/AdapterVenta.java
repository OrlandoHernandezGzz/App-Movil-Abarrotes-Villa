package com.example.app_abarrotesvilla;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterVenta extends RecyclerView.Adapter<AdapterVenta.ViewHolderDatos> {
    ArrayList<ListaVentas> listventas;

    public AdapterVenta(ArrayList<ListaVentas> listventas) {
        this.listventas = listventas;
    }

    @Override
    public AdapterVenta.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Crea una nueva vista, que define la interfaz de usuario del elemento de la lista
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_venta, null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVenta.ViewHolderDatos holder, int position) {
        holder.tvProducto.setText(listventas.get(position).getNombreProducto());
        holder.tvPrecio.setText(listventas.get(position).getPrecio());
        holder.tvCantidad.setText(listventas.get(position).getCantidad());
        holder.tvSubtotal.setText(listventas.get(position).getSubtotal().toString());
    }

    @Override
    public int getItemCount() {
        return listventas.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        //Variable de tipo clase.
        TextView tvProducto, tvPrecio, tvCantidad, tvSubtotal;

        public ViewHolderDatos(@NonNull View itemView) {
                super(itemView);
            tvProducto = itemView.findViewById(R.id.tvProducto);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvCantidad = itemView.findViewById(R.id.tvCantidad);
            tvSubtotal = itemView.findViewById(R.id.tvSubtotal);
            }

            public void asignarDatos(String s) {
                tvProducto.setText(s);
                tvPrecio.setText(s);
                tvCantidad.setText(s);
                tvSubtotal.setText(s);
            }

    }
}
