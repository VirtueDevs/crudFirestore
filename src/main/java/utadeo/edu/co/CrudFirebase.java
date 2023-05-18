package utadeo.edu.co;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CrudFirebase {

    private Firestore database;
    private Scanner entrada;

    public CrudFirebase() throws Exception {
//Autenticacion del firestore
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("src/main/resources/crudstore-ab1e6-b51e10e99e33.json"));
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);

        database = FirestoreClient.getFirestore();

        entrada = new Scanner(System.in);
    }

    //usuarios
    public void crearUsuario() throws Exception {
        System.out.println("Ingrese el nombre del usuario:");
        String nombre = entrada.nextLine();
        System.out.println("Ingrese la edad del usuario:");
        int edad = entrada.nextInt();
        entrada.nextLine();  

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("nombre", nombre);
        data.put("edad", edad);

        database.collection("usuarios").document(nombre).set(data).get();
        System.out.println("Usuario creado con éxito.");
    }

    public void buscarUsuario() throws Exception {
        System.out.println("Ingrese el nombre del usuario a buscar:");
        String nombre = entrada.nextLine();

        DocumentSnapshot document = database.collection("usuarios").document(nombre).get().get();
        if (document.exists()) {
            System.out.println("Datos del usuario: " + document.getData());
        } else {
            System.out.println("No existe tal usuario!");
        }
    }

    public void actualizarUsuario() throws Exception {
        System.out.println("Ingrese el nombre del usuario a actualizar:");
        String nombre = entrada.nextLine();
        System.out.println("Ingrese la nueva edad:");
        int edad = entrada.nextInt();
        entrada.nextLine(); 

        Map<String, Object> updates = new HashMap<String, Object>();
        updates.put("edad", edad);

        database.collection("usuarios").document(nombre).update(updates).get();
        System.out.println("Usuario actualizado con éxito.");
    }

    public void eliminarUsuario() throws Exception {
        System.out.println("Ingrese el nombre del usuario para eliminar:");
        String nombre = entrada.nextLine();

        database.collection("usuarios").document(nombre).delete().get();
        System.out.println("Usuario eliminado con éxito.");
    }
    
    public void listarUsuarios() throws Exception {
        ApiFuture<QuerySnapshot> querySnapshot = database.collection("usuarios").get();
        
        System.out.println("-------------------------------------");
        System.out.printf("| %-10s | %-5s |%n", "Nombre", "Edad");
        System.out.println("-------------------------------------");

        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            Map<String, Object> user = document.getData();
            System.out.printf("| %-10s | %-5s |%n", user.get("nombre"), user.get("edad"));
        }

        System.out.println("-------------------------------------");
    }
  // Productos
    public void crearProducto() throws Exception {
        System.out.println("Ingrese el nombre del producto:");
        String nombre = entrada.nextLine();
        System.out.println("Ingrese el precio del producto:");
        int precio = entrada.nextInt();
        entrada.nextLine();  // consume newline left-over

        Map<String, Object> data = new HashMap<String, Object>();
        data.put("nombre", nombre);
        data.put("precio", precio);

        database.collection("productos").document(nombre).set(data).get();
        System.out.println("Producto creado con éxito.");
    }

    public void bucarProducto() throws Exception {
        System.out.println("Ingrese el nombre del producto a buscar:");
        String nombre = entrada.nextLine();

        DocumentSnapshot document = database.collection("productos").document(nombre).get().get();
        if (document.exists()) {
            System.out.println("Datos del producto: " + document.getData());
        } else {
            System.out.println("No existe tal producto!");
        }
    }

    public void actualizarProducto() throws Exception {
        System.out.println("Ingrese el nombre del producto a actualizar:");
        String nombre = entrada.nextLine();
        System.out.println("Ingrese el nuevo precio:");
        int precio = entrada.nextInt();
        entrada.nextLine();

        Map<String, Object> updates = new HashMap<String, Object>();
        updates.put("precio", precio);

        database.collection("productos").document(nombre).update(updates).get();
        System.out.println("Producto actualizado con éxito.");
    }

    public void eliminarProducto() throws Exception {
        System.out.println("Ingrese el nombre del producto para eliminar:");
        String nombre = entrada.nextLine();

        database.collection("productos").document(nombre).delete().get();
        System.out.println("Producto eliminado con éxito.");
    }
    
    public void listarProductos() throws Exception {
        ApiFuture<QuerySnapshot> querySnapshot = database.collection("productos").get();

        System.out.println("-------------------------------------");
        System.out.printf("| %-15s | %-10s |%n", "Producto", "Precio");
        System.out.println("-------------------------------------");

        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            Map<String, Object> product = document.getData();
            System.out.printf("| %-15s | %-10s |%n", product.get("nombre"), product.get("precio"));
        }

        System.out.println("-------------------------------------");
    }


    public static void main(String[] args) throws Exception {
        CrudFirebase app = new CrudFirebase();

        boolean running = true;
        while (running) {
            System.out.println("Por favor seleccione una opción:");
            System.out.println("1. Usuarios");
            System.out.println("2. Productos");
            System.out.println("3. Salir");

            int option = app.entrada.nextInt();
            app.entrada.nextLine();  

            switch (option) {
                case 1:
                    boolean subRunning = true;
                    while(subRunning) {
                        System.out.println("Por favor seleccione una opción:");
                        System.out.println("1. Crear Usuario");
                        System.out.println("2. Leer Usuario");
                        System.out.println("3. Actualizar Usuario");
                        System.out.println("4. Eliminar Usuario");
                        System.out.println("5. Listar Usuarios");
                        System.out.println("5. Volver al menú principal");

                        int subOption = app.entrada.nextInt();
                        app.entrada.nextLine();  
                        switch (subOption) {
                            case 1:
                                app.crearUsuario();
                                break;
                            case 2:
                                app.buscarUsuario();
                                break;
                            case 3:
                                app.actualizarUsuario();
                                break;
                            case 4:
                                app.eliminarUsuario();
                                break;
                            case 5:
                            	app.listarUsuarios();
                            case 6:
                                subRunning = false;
                                break;
                            default:
                                System.out.println("Esa opción no existe, vuelva a intentarlo");
                        }
                    }
                    break;
                case 2:
                    boolean subRunning2 = true;
                    while(subRunning2) {
                        System.out.println("Elija una opción:");
                        System.out.println("1. Crear Producto");
                        System.out.println("2. Leer Producto");
                        System.out.println("3. Actualizar Producto");
                        System.out.println("4. Eliminar Producto");
                        System.out.println("5. Listar Productos");
                        System.out.println("6. Volver al menú principal");

                        int subOption2 = app.entrada.nextInt();
                        app.entrada.nextLine(); 

                        switch (subOption2) {
                            case 1:
                                app.crearProducto();
                                break;
                            case 2:
                                app.bucarProducto();
                                break;
                            case 3:
                                app.actualizarProducto();
                                break;
                            case 4:
                                app.eliminarProducto();
                                break;
                            case 5:
                            	app.listarProductos();
                            case 6:
                                subRunning2 = false;
                                break;
                            default:
                                System.out.println("Esa opción no existe, vuelva a intentarlo");
                        }
                    }
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Esa opción no existe, vuelva a intentarlo");
            }
        }

        app.entrada.close();
        System.out.println("gracias por usar la app");
    }
}
