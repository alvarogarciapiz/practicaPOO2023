package empresa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * SI SE QUIERE HACER QUE LOS CAMBIOS SE QUEDEN GUARDADOS HABRÍA QUE HACER MÉTODOS
 * PARA SOBREESCRIBIR LOS FICHEROS CON LSO CAMBIOS, ESTA CLASE FICHEROS NO ES NECESARIA, SIMPLEMENTE 
 * FACILITA TENER OBJETOS PRECARGADOS PARA REALIZAR PRUEBAS
 *
 * @author alvaro
 */
public class Ficheros {

    /**
     * Carga en el arrayList de la clase principal los productos del archivo
     * 'productos.dat'
     */
    public static void cargarProductos() {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String linea = "";

        try {
            archivo = new File("productos.dat");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            //Lectura del fichero
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("-");
                Producto p = new Producto(partes[0], Integer.parseInt(partes[1]), Integer.parseInt(partes[2]), Integer.parseInt(partes[3]), partes[4],
                        Integer.parseInt(partes[5]), Integer.parseInt(partes[6]), Integer.parseInt(partes[7]), partes[8]);
                Main.productos.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * Carga en el arrayList de la clase principal los productos del archivo
     * 'almacenes.dat'
     */
    public static void cargarAlmacenes() {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String linea = "";

        try {
            archivo = new File("almacenes.dat");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            //Lectura del fichero
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("-");
                String[] refProductos = partes[2].split("/");
                ArrayList<Producto> productos = new ArrayList();
                for (int i = 0; i < Main.productos.size(); i++) {
                    for (int j = 0; j < refProductos.length; j++) {
                        if (Main.productos.get(i).getReferencia().equals(refProductos[j])) {
                            productos.add(Main.productos.get(i));
                        }
                    }
                }

                Almacen a = new Almacen(partes[0], partes[1], productos);
                Main.almacenes.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * Carga en el arrayList de la clase principal los productos del archivo
     * 'clientes.dat'
     */
    public static void cargarClientes() {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String linea = "";

        try {
            archivo = new File("clientes.dat");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            //Lectura del fichero
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("-");

                Cliente c = new Cliente(partes[0], partes[1], partes[2], Integer.parseInt(partes[4]));
                Main.clientes.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * Carga en el arrayList de la clase principal los productos del archivo
     * 'albaranes.dat'
     */
    public static void cargarAlbaranes() {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String linea = "";
        Cliente client = new Cliente();
        ArrayList<Producto> products = new ArrayList();

        try {
            archivo = new File("albaranes.dat");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            //Lectura del fichero
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("-");
                String[] idProductos = partes[4].split("/");

                for (Cliente c : Main.clientes) {
                    if (c.getCodigo().equals(partes[3])) {
                        client = c;
                    }
                }

                for (int i = 0; i < idProductos.length; i++) {
                    for (int j = 0; j < Main.productos.size(); j++) {
                        if (Main.productos.get(j).getReferencia().equals(idProductos[i])) {
                            products.add(Main.productos.get(i));
                        }
                    }
                }

                Albaran al = new Albaran(partes[0], Integer.parseInt(partes[1]), Integer.parseInt(partes[2]),
                        client, products);
                Main.albaranes.add(al);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
    
    
    /**
     * Carga en el arrayList de la clase principal los productos del archivo
     * 'facturas.dat'
     */
    public static void cargarFacturas() {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        String linea = "";
        Cliente client = new Cliente();
        ArrayList<Producto> products = new ArrayList();

        try {
            archivo = new File("facturas.dat");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            //Lectura del fichero
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("-");
                
                Albaran al = new Albaran();

                for(Albaran a : Main.albaranes){
                    if(partes[0].equals(a.getCliente().getCodigo())){
                        al = a;
                    }
                }
                Factura.generarFactura(al, partes[1], partes[2], Boolean.parseBoolean(partes[3]));
                //En el propio método se añade a la Main
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}