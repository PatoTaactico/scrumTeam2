package Inventario_productos;

public class Inventario_productos {
    private int id;
    private String nombre;
    private String categoria;
    private int precio;
    private int cantidad;
    private int idProveedor;

    public Inventario_productos(int id, String nombre, String categoria, int precio, int cantidad, int idProveedor) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
        this.idProveedor = idProveedor;
    }


    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getProveedorId() {
        return idProveedor;
    }
}