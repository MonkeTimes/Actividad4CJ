package Act3;

import java.io.*;
import java.util.*;

public class Agenda {
    private Map<String, String> contacts;
    private String fileName;

    public Agenda(String fileName) {
        this.fileName = fileName;
        this.contacts = new HashMap<>();
    }

    public void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    contacts.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar la agenda: " + e.getMessage());
        }
    }

    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar la agenda: " + e.getMessage());
        }
    }

    public void list() {
        System.out.println("Contactos:");
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    public void create(String phoneNumber, String name) {
        contacts.put(phoneNumber, name);
    }

    public void delete(String phoneNumber) {
        contacts.remove(phoneNumber);
    }

    public static void main(String[] args) {
        Agenda addressBook = new Agenda("agenda.csv");
        addressBook.load();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nMenú de la Agenda Telefónica:");
            System.out.println("1. Listar contactos");
            System.out.println("2. Crear contacto");
            System.out.println("3. Borrar contacto");
            System.out.println("4. Guardar cambios");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opción: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume la nueva línea después de nextInt()

            switch (choice) {
                case 1:
                    addressBook.list();
                    break;
                case 2:
                    System.out.print("Ingresa el número telefónico: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Ingresa el nombre: ");
                    String name = scanner.nextLine();
                    addressBook.create(phoneNumber, name);
                    break;
                case 3:
                    System.out.print("Ingresa el número telefónico a eliminar: ");
                    phoneNumber = scanner.nextLine();
                    addressBook.delete(phoneNumber);
                    break;
                case 4:
                    addressBook.save();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }

        scanner.close();
    }
}
