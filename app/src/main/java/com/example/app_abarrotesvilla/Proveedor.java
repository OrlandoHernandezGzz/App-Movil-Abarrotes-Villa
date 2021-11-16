package com.example.app_abarrotesvilla;

public class Proveedor {
    //Atributos.
    String nombre;
    String telefono;

    //Constructor vacio
    public Proveedor() {

    }

    //Constructor con nuestros atributos
    public Proveedor(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
