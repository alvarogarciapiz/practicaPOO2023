package empresa;

/**
 *
 * @author alvaro
 */
public class Factura {
    private Albaran albaran;
    private String observaciones;
    private String formaPago;
    private boolean cobrado;

    public Factura() {
    }
    
    

    public static void generarFactura(Albaran albaran, String observaciones, String formaPago, boolean cobrado) {
        Factura f = new Factura();
        f.setAlbaran(albaran);
        f.setObservaciones(observaciones);
        f.setFormaPago(formaPago);
        f.setCobrado(cobrado);
        Main.facturas.add(f);
    }

    public Albaran getAlbaran() {
        return albaran;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public boolean isCobrado() {
        return cobrado;
    }

    public void setAlbaran(Albaran albaran) {
        this.albaran = albaran;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public void setCobrado(boolean cobrado) {
        this.cobrado = cobrado;
    }
    
    
}
