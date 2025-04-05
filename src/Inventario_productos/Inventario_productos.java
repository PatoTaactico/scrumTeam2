package Inventario_productos;

public class Inventario_productos {
    private int id_producto;
    private String nombre_producto;
    private String categoria;
    private int precio_producto;
    private int cantidad_stock;
    private int id_Proveedor_asociado;



    public Inventario_productos(int id_producto, String nombre_producto, String categoria, int precio_producto, int cantidad_stock, int id_Proveedor_asociado) {
        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
        this.categoria = categoria;
        this.precio_producto = precio_producto;
        this.cantidad_stock = cantidad_stock;
        this.id_Proveedor_asociado = id_Proveedor_asociado;
    }




    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getCantidad_stock() {
        return cantidad_stock;
    }

    public void setCantidad_stock(int cantidad_stock) {
        this.cantidad_stock = cantidad_stock;
    }

    public int getPrecio_producto() {
        return precio_producto;
    }

    public void setPrecio_producto(int precio_producto) {
        this.precio_producto = precio_producto;
    }

    public Integer getId_Proveedor_asociado() {
        return id_Proveedor_asociado;
    }

    public void setId_Proveedor_asociado(Integer id_proveedor_asociado) {
        this.id_Proveedor_asociado = id_proveedor_asociado;
    }
}

