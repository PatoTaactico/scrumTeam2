package Ordenes_compra;

public class Ordenes_compra {
    private int id_producto;
    private int id_cliente;
    private int id_empleado;
    private String fecha_compra;
    private double total;
    private String estado_orden;

    public Ordenes_compra(int id_producto, int id_cliente, int id_empleado, String fecha_compra, double total, String estado_orden) {
        this.id_producto = id_producto;
        this.id_cliente = id_cliente;
        this.id_empleado = id_empleado;
        this.fecha_compra = fecha_compra;
        this.total = total;
        this.estado_orden = estado_orden;
    }

    // Getters
    public int getid_producto() {
        return id_producto;
    }

    public int getid_cliente() {
        return id_cliente;
    }

    public int getid_empleado() {
        return id_empleado;
    }

    public String getfecha_compra() {
        return fecha_compra;
    }

    public double getTotal() {
        return total;
    }

    public String getestado_orden() {
        return estado_orden;
    }

    // Setters
    public void setid_producto(int id) {
        this.id_producto = id_producto;
    }

    public void setid_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public void setid_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public void setfecha_compra(String fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setestado_orden(String estado_orden) {
        this.estado_orden = estado_orden;
    }
}

