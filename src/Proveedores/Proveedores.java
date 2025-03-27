package Proveedores;

public class Proveedores {

    private int id_proveedor;
    private String nombre;
    private String telefono;
    private String categoria_producto;
    private String nombre_producto;
    private int precio_proveedor;

    public Proveedores(int id_proveedor, String nombre, String telefono, String categoria_producto, String nombre_producto, int precio_proveedor) {
        this.id_proveedor = id_proveedor;
        this.nombre = nombre;
        this.telefono = telefono;
        this.categoria_producto = categoria_producto;
        this.nombre_producto = nombre_producto;
        this.precio_proveedor = precio_proveedor;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
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

    public String getCategoria_producto() {
        return categoria_producto;
    }

    public void setCategoria_producto(String categoria_producto) {
        this.categoria_producto = categoria_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public int getPrecio_proveedor() {
        return precio_proveedor;
    }

    public void setPrecio_proveedor(int precio_proveedor) {
        this.precio_proveedor = precio_proveedor;
    }
}