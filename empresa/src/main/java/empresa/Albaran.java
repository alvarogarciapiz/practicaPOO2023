package empresa;

import java.util.ArrayList;

/**
 *
 * @author alvaro
 */
public class Albaran {

    private String fecha;
    private Integer numero;
    private Integer importeTotal;
    private Cliente cliente;
    private ArrayList<Producto> productos;

    public Albaran() {
    }

    public Albaran(String fecha, Integer numero, Integer importeTotal, Cliente cliente, ArrayList<Producto> productos) {
        this.fecha = fecha;
        this.numero = numero;
        this.importeTotal = importeTotal;
        this.cliente = cliente;
        this.productos = productos;
    }
    

    public static Albaran generarAlbaran(Cliente c, ArrayList<Producto> productos) {
        int sumaTotal = 0;
        int num = 0;

        Albaran a = new Albaran();
        a.setFecha(Main.fecha);
        a.setCliente(c);
        a.setProductos(productos);

        for (Producto pro : productos) {
            sumaTotal += pro.getPrecioVenta();
        }
        a.setImporteTotal(sumaTotal);

        for (Albaran al : Main.albaranes) {
            num = al.getNumero();
        }
        a.setNumero(num);

        Main.albaranes.add(a);
        
        return a;
    }

    public String getFecha() {
        return fecha;
    }

    public Integer getNumero() {
        return numero;
    }

    public Integer getImporteTotal() {
        return importeTotal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public void setImporteTotal(Integer importeTotal) {
        this.importeTotal = importeTotal;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }
}
