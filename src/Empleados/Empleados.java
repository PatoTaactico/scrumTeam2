package Empleados;

/**
 * Clase que representa a un empleado dentro del sistema.
 * Contiene atributos básicos como ID, nombre, cargo y salario.
 */
public class Empleados {
    /** Identificador único del empleado */
    private int id_empleado;

    /** Nombre del empleado */
    private String nombre;

    /** Cargo o puesto del empleado */
    private String cargo;

    /** Salario del empleado */
    private int salario;

    /**
     * Constructor que inicializa un objeto Empleados con todos sus atributos.
     *
     * @param id_empleado Identificador del empleado
     * @param nombre      Nombre del empleado
     * @param cargo       Cargo del empleado
     * @param salario     Salario del empleado
     */
    public Empleados(int id_empleado, String nombre, String cargo, int salario) {
        this.id_empleado = id_empleado;
        this.nombre = nombre;
        this.cargo = cargo;
        this.salario = salario;
    }

    /**
     * Obtiene el ID del empleado.
     *
     * @return ID del empleado
     */
    public int getId_empleado() {
        return id_empleado;
    }

    /**
     * Establece el ID del empleado.
     *
     * @param id_empleado ID a asignar
     */
    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    /**
     * Obtiene el nombre del empleado.
     *
     * @return Nombre del empleado
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del empleado.
     *
     * @param nombre Nombre a asignar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el cargo del empleado.
     *
     * @return Cargo del empleado
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * Establece el cargo del empleado.
     *
     * @param cargo Cargo a asignar
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /**
     * Obtiene el salario del empleado.
     *
     * @return Salario del empleado
     */
    public int getSalario() {
        return salario;
    }

    /**
     * Establece el salario del empleado.
     *
     * @param salario Salario a asignar
     */
    public void setSalario(int salario) {
        this.salario = salario;
    }

    /**
     * Devuelve una representación en texto del empleado.
     *
     * @return Cadena que representa al empleado con nombre y cargo
     */
    @Override
    public String toString() {
        return nombre + " - " + cargo;
    }
}
