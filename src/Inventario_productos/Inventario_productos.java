package Inventario_productos;

public class Inventario_productos {
    private int id_producto;
    private String nombre_producto;
    private String categoria;
    private int precio_producto;
    private int cantidad_stock;
    private int id_Proveedor_asociado;

    public Inventario_productos(int id, String nombre, String categoria, int precio, int cantidad, int idProveedor) {
        this.id_producto = id;
        this.nombre_producto = nombre;
        this.categoria = categoria;
        this.precio_producto = precio;
        this.cantidad_stock = cantidad;
        this.id_Proveedor_asociado = idProveedor;
    }


    public int getId() {
        return id_producto;
    }

    public String getNombre() {
        return nombre_producto;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getPrecio() {
        return precio_producto;
    }

    public int getCantidad() {
        return cantidad_stock;
    }

    public int getProveedorId() {
        return id_Proveedor_asociado;
    }
}
