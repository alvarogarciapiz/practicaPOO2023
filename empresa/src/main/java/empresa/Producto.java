package empresa;

import java.util.Scanner;

/**
 *
 * @author alvaro
 */
public class Producto {

    private String referencia;
    private Integer stock;
    private Integer ancho;
    private Integer alto;
    private String estado;
    private Integer precioCompra;
    private Integer precioVenta;
    private Integer descuento;
    private String fechaCaducidad;

    public Producto() {
    }

    public Producto(String referencia, Integer stock, Integer ancho, Integer alto, String estado, Integer precioCompra, Integer precioVenta, Integer descuento, String fechaCaducidad) {
        this.referencia = referencia;
        this.stock = stock;
        this.ancho = ancho;
        this.alto = alto;
        this.estado = estado;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.descuento = descuento;
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getReferencia() {
        return referencia;
    }

    public Integer getStock() {
        return stock;
    }

    public Integer getAncho() {
        return ancho;
    }

    public Integer getAlto() {
        return alto;
    }

    public String getEstado() {
        return estado;
    }

    public Integer getPrecioCompra() {
        return precioCompra;
    }

    public Integer getPrecioVenta() {
        return precioVenta;
    }

    public Integer getDescuento() {
        return descuento;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setAncho(Integer ancho) {
        this.ancho = ancho;
    }

    public void setAlto(Integer alto) {
        this.alto = alto;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setPrecioCompra(Integer precioCompra) {
        this.precioCompra = precioCompra;
    }

    public void setPrecioVenta(Integer precioVenta) {
        this.precioVenta = precioVenta;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public static boolean comprobarProductoCaducado(Producto p) { //  dd/MM/yyyy/
        String fechaActual = Main.obtenerFechaActual();
        String[] actuales = fechaActual.split("/");
        String fechaProducto = p.getFechaCaducidad();
        String[] productos = fechaProducto.split("/");

        if (Integer.parseInt(actuales[2]) < Integer.parseInt(productos[2])) {
            return false;
        } else if (Integer.parseInt(actuales[1]) < Integer.parseInt(productos[1])) {
            return false;
        } else if (Integer.parseInt(actuales[0]) < Integer.parseInt(productos[0])) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * Se entiende por caducidad próxima en los siguientes 3 meses = 90 dias nat
     * REVISAR, !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * @param p
     * @return 
     */
    public static boolean comprobarCaducidadproximamente(Producto p) { //  dd/MM/yyyy/ 
        String fechaActual = Main.obtenerFechaActual();
        String[] actuales = fechaActual.split("/");
        String fechaProducto = p.getFechaCaducidad();
        String[] productos = fechaProducto.split("/");

        int diasHoy = (Integer.parseInt(actuales[2])*360) + (Integer.parseInt(actuales[1])*30
                + (Integer.parseInt(actuales[0])));
        
        int diasProducto = (Integer.parseInt(productos[2])*360) + (Integer.parseInt(productos[1])*30
                + (Integer.parseInt(productos[0])));
        
        if((diasHoy - diasProducto)<=90){
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean comprobarStock (Producto p){
        if(p.getStock()>0){
            return true;
        } else {
            return false;
        }
    }
    
    public static void nuevoProducto(){
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Introduce los datos asociados al producto: ");
        System.out.println("Referencia: ");
        String referencia = sc.nextLine();
        System.out.println("Stock: ");
        int stock = sc.nextInt();
        System.out.println("Ancho: ");
        int ancho = sc.nextInt();
        System.out.println("Largo: ");
        int largo = sc.nextInt();
        System.out.println("Estado: [libre] [vendido] [reserado]");
        String estado = sc.nextLine();
        System.out.println("Precio compra: ");
        int precioCompra = sc.nextInt();
        System.out.println("Precio venta: [50% más que el precio de comprar (aproximar a entero)]");
        int precioVenta = sc.nextInt();
        System.out.println("Descuento: ");
        int descuento = sc.nextInt();
        System.out.println("Fecha caducidad: [yyyy/MM/dd/]");
        String fechaCaducidad = sc.nextLine();
        
        Producto p = new Producto(referencia, stock, ancho, largo, estado, precioCompra, precioVenta, descuento, fechaCaducidad);
        Main.productos.add(p);
        
        System.out.println("¿En qué almacén deseas almacenar este producto?");
        Main.listarAlmacenes();
        System.out.println("Introduce el código del almacén deseado: ");
        String codigoAlmacen = sc.nextLine();
        
        for(int i =0; i< Main.almacenes.size(); i++){
            if(Main.almacenes.get(i).getCodigoAlmacen().equals(codigoAlmacen)){
                Main.almacenes.get(i).getProductos().add(p);
            }
        }
    }

}
