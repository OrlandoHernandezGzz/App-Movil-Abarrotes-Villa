package com.example.app_abarrotesvilla;

public class ListaVentas {
    //Declaramos nuestros atributos.
    private String nombreProducto;
    private String precio;
    private String cantidad;
    private Double subtotal;

    //constructor vacío.
    public ListaVentas(){

    }

    //Creamos constructor de nuestra clase.
    public ListaVentas(String nombreProducto, String precio, String cantidad, Double subtotal){
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    //Métodos getter and setter.
    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
}
