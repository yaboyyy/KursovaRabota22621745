import java.util.*;
import java.util.function.Consumer;
import java.io.*;

import static java.io.FileDescriptor.in;

public class Application {

    private static Warehouse warehouse = new Warehouse();
    private static long currentDate;
    private static Scanner scanner = new Scanner(System.in);
    private static File file;
    private static boolean opened = false;

    public static void main(String[] args) {
        Map<String, Consumer<String>> fileCommands = new HashMap<>();
        fileCommands.put("open", s -> open());

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            if (input.equalsIgnoreCase("help")) {
                help();
                continue;
            }

            if (input.equalsIgnoreCase("exit")) {
                exit();
            }

            String[] parts = input.split(" ", 2);
            String fileCommand = parts[0].toLowerCase();

            if (fileCommands.containsKey(fileCommand)) {
                if (parts.length > 1) {
                    fileCommands.get(fileCommand).accept(parts[1]);
                } else {
                    fileCommands.get(fileCommand).accept(null);
                }
            } else {
                System.out.println("Invalid command. Type 'help' for a list of commands.");
            }

            Map<String, Consumer<String>> commands = new HashMap<>();
            fileCommands.put("open", s -> open());
            commands.put("close", s -> close());
            commands.put("save", s -> save());
            commands.put("saveas", s -> saveAs());
            commands.put("1", s -> printProducts());
            commands.put("2", s -> addProduct());
            commands.put("3", s -> removeProduct());
            commands.put("4", s -> printLogs());
            commands.put("5", s -> cleanWarehouse());
            //commands.put("6", s -> calculateLoss());

            System.out.println("=== Warehouse Management System ===");
            System.out.println("Type 'help' for a list of commands.");

            while (true) {
                System.out.print("> ");
                input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    continue;
                }

                if (input.equalsIgnoreCase("help")) {
                    printMenu();
                    continue;
                }

                if (input.equalsIgnoreCase("exit")) {
                    exit();
                }

                parts = input.split(" ", 2);
                String command = parts[0].toLowerCase();

                if (commands.containsKey(command)) {
                    if (parts.length > 1) {
                        commands.get(command).accept(parts[1]);
                    } else {
                        commands.get(command).accept(null);
                    }
                } else {
                    System.out.println("Invalid command. Type 'help' for a list of commands.");
                }
            }
        }
    }

    private static void printProducts() {
        warehouse.printProducts();
    }

    private static void addProduct() {
        System.out.println("What is the current date:");
        currentDate = scanner.nextLong();

        scanner.nextLine();

        warehouse.addProduct(currentDate);
    }

    private static void removeProduct() {
        System.out.println("Enter product name: ");
        String productName = scanner.nextLine();

        System.out.println("Enter quantity to remove: ");
        int quantity = scanner.nextInt();

        System.out.println("What is the current date:");
        currentDate = scanner.nextLong();

        scanner.nextLine();

        warehouse.removeProduct(productName, quantity, currentDate);
    }

    private static void printLogs() {
        System.out.print("Enter start date (yyyyMMdd): ");
        long startDate = scanner.nextLong();

        System.out.print("Enter end date (yyyyMMdd): ");
        long endDate = scanner.nextLong();

        scanner.nextLine();

        warehouse.printLog(startDate, endDate);
    }

    private static void cleanWarehouse() {
        System.out.print("Enter days before expiration: ");
        int daysBeforeExpiration = scanner.nextInt();

        System.out.println("What is the current date:");
        currentDate = scanner.nextLong();

        scanner.nextLine();

        warehouse.clear(daysBeforeExpiration, currentDate);
    }

    private static void printMenu() {
        System.out.println("=== Warehouse Management System Commands ===");
        System.out.println("open <file> - Opens a file");
        System.out.println("close - Closes the currently open file");
        System.out.println("save - Saves the currently open file");
        System.out.println("saveas <file> - Saves the currently open file as <file>");
        System.out.println("help - Prints this information");
        System.out.println("exit - Exits the program");
        System.out.println("=== Warehouse Management System Menu ===");
        System.out.println("1. Print Products");
        System.out.println("2. Add Product");
        System.out.println("3. Remove Product");
        System.out.println("4. Print Logs");
        System.out.println("5. Clear Warehouse");
        //System.out.println("6. Calculate Loss");
        //System.out.println("7. Exit");
    }

    private static void open(){
        System.out.println("Enter file path: ");
        file = new File(scanner.nextLine());
        ArrayList<Product> readProducts;
        ArrayList<Log> readLogs;
        if (file.exists()) {
            System.out.println("File exists.");
            try (FileInputStream fileIn = new FileInputStream(file);
                 ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

                readProducts = (ArrayList<Product>) objectIn.readObject();
                readLogs = (ArrayList<Log>) objectIn.readObject();
                warehouse.setProducts(readProducts);
                warehouse.setLogList(readLogs);
                System.out.println("Read from " + file);

            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error reading from file " + file + ": " + e.getMessage());
            }

        } else {
            System.out.println("File does not exist.");
            try {
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    private static void close() {
        if (file == null) {
            System.out.println("No file is open.");
            return;
        }
        file = null;
        warehouse = new Warehouse();
        System.out.println("Successfully closed the file.");
    }

    private static void save() {
        if (file == null) {
            System.out.println("No file is open.");
            return;
        }
        try (FileOutputStream fileOut = new FileOutputStream(file);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            objectOut.writeObject(warehouse.getProducts());
            objectOut.writeObject(warehouse.getLogList());
            System.out.println("Saved to " + file);

        } catch (IOException e) {
            System.err.println("Error saving to file " + file + ": " + e.getMessage());
        }

    }

    private static void saveAs() {
        if (file == null) {
            System.out.println("No file is open.");
            return;
        }
        System.out.println("Enter new file location: ");
        File newFile = new File(scanner.nextLine());

        try (FileOutputStream fileOut = new FileOutputStream(newFile);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            objectOut.writeObject(warehouse.getProducts());
            objectOut.writeObject(warehouse.getLogList());
            System.out.println("ArrayList of objects has been written to " + newFile);

        } catch (IOException e) {
            System.err.println("Error writing to file " + newFile + ": " + e.getMessage());
        }
    }

    private static void help() {
        System.out.println("The following commands are supported currently:");
        System.out.println("open <file> - opens a file");
        System.out.println("close - closes the currently opened file");
        System.out.println("save - saves the currently opened file");
        System.out.println("saveas <file> - saves the currently opened file to a new file");
        System.out.println("help - prints this information");
        System.out.println("exit - exits the program");
    }

    private static void exit() {
        System.out.println("Exiting...");
        System.exit(0);
    }
}