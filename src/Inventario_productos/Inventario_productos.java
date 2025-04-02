package Inventario_productos;

import javax.swing.*;

public class Inventario_productos {
    private int id;
    private String nombre;
    private String categoria;
    private int precio;
    private int cantidad;
    private int proveedorId;

    public Inventario_productos(int id, String nombre, String categoria, int precio, int cantidad, int proveedorId)
    {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
        this.proveedorId = proveedorId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public int getPrecio() { return precio; }
    public void setPrecio(int precio) { this.precio = precio; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public int getProveedorId() { return proveedorId; }
    public void setProveedorId(int proveedorId) { this.proveedorId = proveedorId; }
}