package empresa;

/**
 *
 * @author alvaro
 */
public class Cliente {

    private String nombre;
    private String NIF;
    private String direccion;
    private String codigo;
    private Integer credito;

    public Cliente() {
    }

    public Cliente(String nombre, String NIF, String direccion, Integer credito) {
        this.nombre = nombre;
        this.NIF = NIF;
        this.direccion = direccion;
        this.codigo = NIF + nombre;
        this.credito = credito;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNIF() {
        return NIF;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCodigo() {
        return codigo;
    }

    public Integer getCredito() {
        return credito;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNIF(String NIF) {
        this.NIF = NIF;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setCredito(Integer credito) {
        this.credito = credito;
    }

}
