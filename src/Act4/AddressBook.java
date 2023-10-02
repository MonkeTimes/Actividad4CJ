package Act4;

//importo las bibliotecas que se necesitan para usar los metodos para leer y cargar datos en archivos externos .io y para hacer arraylists .util
import java.io.*;
import java.util.*;

public class AddressBook {
    //En la actividad nos piden declarar una variable tipo map que guarde el array de datos tipo clave valor, en este caso el primer string seria la clave el cual sera el numero de telefono y el segundo seria el nombre del usuario
    private Map<String, String> contactos;
    //Variable string la cual va a almacenar el nombre del archivo el cual vamos a usar para extraer en este caso es agenda.csv para que los metodos load y save funcionen bien
    private String archivo;
    //inicializo la variable contactos como un array hashmap y la variable de archivo

    public AddressBook(String archivo) {
        this.archivo = archivo;
        this.contactos = new HashMap<>();
    }

    //Creo el metodo load el cual va a cargar los datos del archivo al programa, en este caso seria agenda.csv
    public void load() {
        //creo la variable bufferedReader la cual se va a utilizar para leer el archivo guardado en la variable archivo
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            //inicializo la variable line la cual va a almacenar los datos que se encuentren en cada linea del archivo, almacena toda la linea, en este caso numero,nombre se almacena
            String line;
            //Va a leer siempre y cuando la linea tenga datos osea no este vacia
            while ((line = br.readLine()) != null) {
                //va a separar la variable linea segun las comas que posea en dos datos diferentes, osea nombre va a ser un dato y numero otro y almacenarlas en un array llamado partes
                String[] partes = line.split(",");
                //Si el array de partes tiene dos datos nombre y numero entonces pone ese dato en una linea dentro del hashmap contactos
                if (partes.length == 2) {
                    contactos.put(partes[0], partes[1]);
                }
            }
            //Si no carga bien el archivo o no se encuentra en el respositorio marca este error lo que imposibilita leer el archivo
        } catch (IOException e) {
            System.err.println("Error al cargar la agenda: " + e.getMessage());
        }
    }
    //Metodo save que escribe en el archivo (agenda.csv), no confundirse con el que crea un dato nuevo, este escribe el dato actual en el hashmap
    public void save() {
        //Creo variable bufferedwriter la cual escribe datos en el archivo
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            //Cada linea dentro el hasmap se escribe en el archivo
            for (Map.Entry<String, String> entry : contactos.entrySet()) {
                //Menciono que escrie la clave del dato (numero) luego un coma y escribe el valor el cual es el nombre
                bw.write(entry.getKey() + "," + entry.getValue());
                //va a la linea de abajo, en vez de escribirlos todos seguidos para que salga como quieren en la actividad
                bw.newLine();
            }
            System.out.println("Guardado excitosamente");
        } catch (IOException e) {
            //Si algo falla marca este error
            System.err.println("Error al guardar la agenda: " + e.getMessage());
        }
    }
    //Metodo que imprime en consola todos los datos dentro de la variable hashmap contactos osea todos los datos hasta ahora
    public void list() {
        System.out.println("Contactos:");
        //Inicializo una variable entrada la cual recorre todos el hashmap contactos y imprime cada linea en consola
        for (Map.Entry<String, String> entrada : contactos.entrySet()) {
            System.out.println(entrada.getKey() + " : " + entrada.getValue());
        }
    }
    //Metodo que agrega el dato a la variable hashmap contactos, los datos se escriben en main y estos vienen aca
    public void create(String phoneNumber, String nombre) {
        //put para agregar esos datos al hashmap
        contactos.put(phoneNumber, nombre);
    }
    //Metodo que elimina el dato clave escrito en la variable telefono y con la herramienta remove se elimina toda la linea
    public void delete(String telefono) {
        contactos.remove(telefono);
    }
    //Metodo main
    public static void main(String[] args) {
        //inicializo una variable llamada adressbook la cual tiene el archivo a utilizar el cual luego se manda dentro de la variable archivo el cual se usa en los metodos
        AddressBook addressBook = new AddressBook("agenda.csv");
        //load que carga lo datos del hashmap dentro de la instancia adressbook para que se utilizen
        addressBook.load();
        //herramienta scanner que lee del teclado
        Scanner scanner = new Scanner(System.in);
        //variable booleana exit que empieza en falso para determinar cuantas veces se opera el programa
        boolean exit = false;
        //bucle que se dara siempre y cuando siga en falso la variable exit
        while (!exit) {
            //Imprime las opciones en pantalla
            System.out.println("\nMenú de la Agenda Telefónica:");
            System.out.println("1. Listar contactos");
            System.out.println("2. Crear contacto");
            System.out.println("3. Borrar contacto");
            System.out.println("4. Guardar cambios");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opción: ");
            //lectura de la opcion
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume la nueva línea después de nextInt() para que no se bugee el programa
            //switch que elige que se hace con la opcion agregada
            switch (choice) {
                case 1:
                    //Llama al metodo list
                    addressBook.list();
                    break;
                case 2:
                    //Llama al metodo create pero antes crea y almacena las variables que se utilizan en el metodo
                    System.out.print("Ingresa el número telefónico: ");
                    String telefono = scanner.nextLine();
                    System.out.print("Ingresa el nombre: ");
                    String nombre = scanner.nextLine();
                    //llama al metodo con las variables creadas
                    addressBook.create(telefono, nombre);
                    break;
                case 3:
                    //Llama el metodo delete con el dato introducido el cual es el telefono para eliminar ese registro
                    System.out.print("Ingresa el número telefónico a eliminar: ");
                    telefono = scanner.nextLine();
                    addressBook.delete(telefono);
                    break;
                case 4:
                    //Llama al metodo save el cual guarda los datos actuales
                    addressBook.save();
                    break;
                case 5:
                    //Cambia la variable exit a true para que se termine el programa
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }

        scanner.close();
    }
}
