package empresa;

import static java.lang.System.exit;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author alvaro
 */
public class Main {

    public static ArrayList<Producto> productos = new ArrayList();
    public static ArrayList<Almacen> almacenes = new ArrayList();
    public static ArrayList<Albaran> albaranes = new ArrayList();
    public static ArrayList<Factura> facturas = new ArrayList();
    public static ArrayList<Cliente> clientes = new ArrayList();
    public static String fecha = obtenerFechaActual();

    public static void main(String[] args) {
        init();
        System.out.println(fecha);

        while (true) {
            menu();
        }
    }

    /**
     * Menú con las diferentes opciones
     */
    private static void menu() {
        Scanner sc = new Scanner(System.in);

        System.out.println("______________________________________________________________");
        System.out.println("| [1] Listar almacenes disponibles                            |");
        System.out.println("| [2] Información sobre un almacén                            |");
        System.out.println("| [3] Listar productos disponibles                            |");
        System.out.println("| [4] Listar productos mayores a X€                           |");
        System.out.println("| [5] Stock de un producto concreto                           |");
        System.out.println("| [6] Añadir nuevo producto                                   |");
        System.out.println("| [7] Vender un producto                                      |");
        System.out.println("| [8] Listar albaranes                                        |");
        System.out.println("| [9] Listar albaranes de una fecha concreta                  |");
        System.out.println("| [10] Suma total económica de productos vendidos             |");
        System.out.println("| [11] Listar facturas pendientes de cobro                    |");
        System.out.println("| [12] Listar facturas de un cliente concreto                 |");
        System.out.println("| ____________________________________________________________|");
        System.out.println("| [0] SALIR                                                   |");
        System.out.println("|______________________________________________________________");

        Integer opcion = sc.nextInt();
        opciones(opcion);
    }

    /**
     * Una vez se selecciona una opción, se pasa por este método para llamar a
     * la clase correspondiente
     *
     * @param opcion
     */
    public static void opciones(int opcion) {
        Scanner scn = new Scanner(System.in);
        switch (opcion) {

            case 1: //Listar almacenes disponibles
                listarAlmacenes();
                break;

            case 2: //Info sobre un almacén
                infoAlmacen();
                break;

            case 3: //Listar productos
                listarproductos();
                break;

            case 4: //Listar con precio maximo
                System.out.println("¿Qué precio de venta es el máximo por el que quieres filtrar?");
                Integer pMax = scn.nextInt();
                for (Producto p : productos) {
                    if (pMax <= p.getPrecioVenta()) {
                        System.out.println("[" + p.getReferencia() + "] " + p.getStock() + " unidades en Stock. "
                                + p.getAlto() + " x " + p.getAlto() + " en estado " + p.getEstado() + " a un precio de "
                                + p.getPrecioVenta() + "€ cuando fue comprado a " + p.getPrecioCompra() + " y que caduca el "
                                + p.getFechaCaducidad());
                    }
                }
                break;

            case 5: //Stock de un producto concreto
                System.out.println("A continuación se muestran todos los productos: ");
                listarproductos();
                System.out.println("Introduce la referencia del producto que deseas conocer el stock: ");
                String ref = scn.nextLine();
                for (Producto p : productos) {
                    if (p.getReferencia().equals(ref)) {
                        System.out.println("La cantidad en stock es de: " + p.getStock() + " unidades.");
                    }
                }
                break;

            case 6: //Añadir nuevo producto y agregarlo a un almacén
                Producto.nuevoProducto();
                break;

            case 7: //Vender un producto -- Esto habría q llevárselo a un método aparte
                Cliente c = new Cliente();
                ArrayList<Producto> prods = new ArrayList();
                Integer precio = 0;
                System.out.println("A continuación se muestran todos los productos disponibles: ");
                listarproductos();
                System.out.println("Introduce la referencia del producto que quieres vender: ");
                String referencia = scn.nextLine();
                for (Producto p : productos) {
                    if (Producto.comprobarStock(p)) {
                        prods.add(p);
                        p.setStock(p.getStock() - 1);
                        precio = p.getPrecioVenta();
                    } else {
                        System.out.println("No hay Stock de este producto.");
                    }
                }

                System.out.println("¿Quién es el comprador?");
                listarCLientes();
                System.out.println("Introduce el código del cliente.");
                String codigoCliente = scn.nextLine();
                for (Cliente cli : clientes) {
                    if (codigoCliente.equals(cli.getCodigo())) {
                        cli.setCredito(cli.getCredito() - precio);
                        c = cli;
                    }
                }
                Albaran a = Albaran.generarAlbaran(c, prods);
                //Aquí debería pasar un tiempo que simulamos
                System.out.println("¿Desea introducir alguna obervacion en la factura?");
                String observaciones = scn.nextLine();
                System.out.println("¿Se ha cobrado la factura? [1] Si [0] No");
                String cobrado = scn.nextLine();
                if (cobrado.equals("1")) {
                    Factura.generarFactura(a, observaciones, fecha, true);
                } else {
                    Factura.generarFactura(a, observaciones, fecha, false);
                }
                break;

            case 8: //Listar albaranes
                listarAlbaranes();
                break;

            case 9: //Listar albaranes fecha concreta
                System.out.println("Introduce la fecha de los albaranes que deseas visualizar [dd/MM/yyyy/]");
                String date = scn.nextLine();
                listarAlbaranes(date);
                break;
            case 0: //Salir
                exit(0);

            case 10: // Suma total económica productos vendidos
                System.out.println("TOTAL: " + obtenerTotalVendido() + "€");
                break;

            case 11: //Listar facturas pendientes de cobro
                listarFacturasPendientesCobro();
                break;

            case 12: //Listar facturas de un cliente
                listarFacturasCliente();
                break;
            default:
                System.out.println("[ERROR] Introduce un número dentro de las opciones.");
        }
    }

    public static void init() {
        Ficheros.cargarProductos();
        Ficheros.cargarAlmacenes();
        Ficheros.cargarClientes();
        Ficheros.cargarAlbaranes();
        Ficheros.cargarFacturas();
    }

    private static void infoAlmacen() {
        Integer fin = 0;
        Scanner scn = new Scanner(System.in);
        System.out.println("A continuación se muestran los almacenes disponibles: \n");
        for (Almacen alm : almacenes) {
            System.out.println("Almacen con código: " + alm.getCodigoAlmacen() + "; " + alm.getLocalizacion()
                    + " con " + alm.getProductos().size() + " productos.");
        }
        System.out.println("Introduce el código de almacén deseado: ");
        String codigoAlm = scn.nextLine();

        for (Almacen alm : almacenes) {
            while (fin == 0) {
                if (alm.getCodigoAlmacen().equals(codigoAlm)) {
                    fin += 1;
                    Almacen.informacionAlmacen(alm);
                } else {
                    System.out.println("No se ha encontrado ningún almacén con ese código");
                    fin += 1;
                }
            }
        }
    }

    private static void listarproductos() {
        for (Producto p : productos) {
            System.out.println("[" + p.getReferencia() + "] " + p.getStock() + " unidades en Stock. "
                    + p.getAlto() + " x " + p.getAlto() + " en estado " + p.getEstado() + " a un precio de "
                    + p.getPrecioVenta() + "€ cuando fue comprado a " + p.getPrecioCompra() + " y que caduca el "
                    + p.getFechaCaducidad());
        }
    }

    public static String obtenerFechaActual() {
        LocalDateTime fechaSinFormato = LocalDateTime.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy/");

        String fecha = fechaSinFormato.format(formatoFecha);

        return fecha;
    }

    public static void listarAlmacenes() {
        for (Almacen alm : almacenes) {
            System.out.println("Almacen: " + alm.getCodigoAlmacen() + "; " + alm.getLocalizacion()
                    + " con " + alm.getProductos().size() + " productos.");
        }
    }

    public static void listarCLientes() {
        for (Cliente cli : clientes) {
            System.out.println(cli.getNombre() + ", " + cli.getNIF() + ", "
                    + cli.getDireccion() + " y código: " + cli.getCodigo());
        }
    }

    public static void listarAlbaranes() {
        for (Albaran al : albaranes) {
            System.out.println(al.getNumero() + ", " + al.getImporteTotal() + ", "
                    + al.getCliente().getNombre());
        }
    }

    public static void listarAlbaranes(String date) {
        for (Albaran al : albaranes) {
            if (date.equals(al.getFecha())) {
                System.out.println(al.getNumero() + ", " + al.getImporteTotal() + ", "
                        + al.getCliente().getNombre());
            }
        }
    }

    public static int obtenerTotalVendido() {
        int total = 0;
        for (Albaran al : albaranes) {
            total += al.getImporteTotal();
        }
        return total;
    }

    public static void listarFacturasPendientesCobro() {
        for (Factura f : facturas) {
            if (f.isCobrado() == false) {
                System.out.println(f.getFormaPago() + ", " + f.getObservaciones()
                        + ", " + f.getAlbaran().getNumero());
            }
        }
    }

    public static void listarFacturasCliente() {
        Scanner ss = new Scanner(System.in);
        System.out.println("A continuacion se visualizan los clientes: ");
        listarCLientes();
        System.out.println("Introduce el codigo de cliente del que deseas visualziar sus facturas: ");
        String codigo = ss.nextLine();
        
        for (Factura f : facturas) {
            if (f.getAlbaran().getCliente().getCodigo().equals(codigo)) {
                System.out.println(f.getFormaPago() + ", " + f.getObservaciones()
                        + ", " + f.getAlbaran().getNumero());
            }
        }
    }
}
