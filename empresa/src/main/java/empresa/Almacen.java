package empresa;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author alvaro
 */
public class Almacen {

    private String codigoAlmacen;
    private String localizacion;
    private ArrayList<Producto> productos;

    public Almacen() {
    }

    public Almacen(String codigoAlmacen, String localizacion, ArrayList<Producto> productos) {
        this.codigoAlmacen = codigoAlmacen;
        this.localizacion = localizacion;
        this.productos = productos;
    }

    public String getCodigoAlmacen() {
        return codigoAlmacen;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setCodigoAlmacen(String codigoAlmacen) {
        this.codigoAlmacen = codigoAlmacen;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }
    
    public static void informacionAlmacen(Almacen a){
        Scanner sc = new Scanner(System.in);
        ArrayList<Producto> productos = a.getProductos();
        
        System.out.println("[INFO] Almacén: " + a.getCodigoAlmacen() +
                " , " + a.getLocalizacion() + " y " + a.getProductos().size() + " productos.");
        System.out.println("-> El almacén tiene " + a.getProductos().size() + " productos en stock.");
        System.out.println("-> Hay " + contarProductosEstado("libre", a) +  " productos en estado libre.");
        System.out.println("-> Hay " + contarProductosEstado("vendido", a) +  " productos en estado vendido.");
        System.out.println("-> Hay " + contarProductosEstado("reservado", a) +  " productos en estado reservado.");
        
        System.out.println("Productos libres caducados: ");
        for(Producto p : productos){
            if(p.getEstado().equals("libre") && Producto.comprobarProductoCaducado(p)){
                System.out.println("Referencia: " + p.getReferencia() + ", PVP: " + p.getPrecioVenta()
                        + ", " + p.getStock() + " unidades de stock.");
            }
        }
        
        eliminarproductosCaducados(a);
        
        System.out.println("Productos que caducarán próximamente: ");
        for(Producto p : productos){
            if(Producto.comprobarCaducidadproximamente(p)){
                System.out.println("Referencia: " + p.getReferencia() + ", PVP: " + p.getPrecioVenta()
                        + ", " + p.getStock() + " unidades de stock.");
            }
        }
        System.out.println("¿Deseas trasladar productos de este almacen a otro?");
        System.out.println("[1] para sí y [0] para no");
        int opc = sc.nextInt();
        if(opc==1){
            trasladarProductoAlmacen(a);
        } else {
            System.out.println("No se trasladarán productos de un almacén a otro");
        }
    }
    
    private static int contarProductosEstado(String estado, Almacen a){
        Integer cont = 0;
        ArrayList<Producto> productos = a.getProductos();
        for(Producto p : productos){
            if(p.getEstado().equals(estado)){
                cont++;
            }
        }
        
        return cont;
    }
    
    private static void eliminarproductosCaducados (Almacen a){
        Scanner sc = new Scanner(System.in);
        System.out.println("¿Desea eliminar los productos caducados del almacén?");
        System.out.println("[1] para sí y [0] para no");
        int opc = sc.nextInt();
        if(opc==1){
            for(int i =0; i < a.getProductos().size(); i++){
                if(Producto.comprobarProductoCaducado(a.getProductos().get(i))){
                    a.getProductos().get(i).setStock(0);
                    a.getProductos().remove(i);
                }
            }
            System.out.println("Se eliminaron los productos caducados del almacén");
        } else {
            System.out.println("No se eliminarán los productos caducados del almacén");
        }
    }
    
    private static void trasladarProductoAlmacen(Almacen a) {
        Scanner ss = new Scanner(System.in);
        Producto prod = new Producto();
        for (Producto p : a.getProductos()){
            System.out.println("REFERENCIA: " + p.getReferencia());
        }
        
        System.out.println("Introduce la referencia del producto que deseas desplazar "
                + "a otro almacen: ");
        String ref = ss.nextLine();
        //Se obtiene l producto y se retira de su almacen
        for (Producto p : a.getProductos()){
            if(p.getReferencia().equals(ref)){
                prod = p;
            }
        }
        a.getProductos().remove(prod);
        
        for (Almacen al : Main.almacenes){
            System.out.println("Codigo de almacen: " + al.getCodigoAlmacen());
        }
        System.out.println("Introduce el codigo de almacen al que quieras mover"
                + " el producto.");
        String codigoAlmacen = ss.nextLine();
        
        //Se añade al nuevo almacen
        for (Almacen al : Main.almacenes){
            if(al.getCodigoAlmacen().equals(codigoAlmacen)){
                al.getProductos().add(prod);
            }
        }
    }
}
