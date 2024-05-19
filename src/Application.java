import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
import java.io.*;

public class Application {

    private static Warehouse warehouse = new Warehouse();
    private static long currentDate;
    private static Scanner scanner = new Scanner(System.in);
    private static File file;

    public static void main(String[] args) {


        Map<String, Consumer<String>> commands = new HashMap<>();
        commands.put("1", s -> printProducts());
        commands.put("2", s ->removeProduct());
        commands.put("3", s -> addProduct());
        commands.put("4", s -> printLogs());
        commands.put("5", s -> cleanWarehouse());
        //commands.put("6", s -> calculateLoss());

        System.out.println("=== Warehouse Management System ===");
        System.out.println("Type 'help' for a list of commands.");
        boolean running = true;
        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

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

            String[] parts = input.split(" ", 2);
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
        System.out.println("1. Add Product");
        System.out.println("2. Remove Product");
        System.out.println("3. Print Products");
        System.out.println("4. Log Entries");
        System.out.println("5. Clean Warehouse");
        System.out.println("6. Calculate Loss");
        System.out.println("7. Exit");
    }

    private static void open(){
        System.out.println("Enter file path: ");
        file = new File(scanner.nextLine());
        if (file.exists()) {
            System.out.println("File exists.");
            try {
                Scanner reader = new Scanner(file);
                while (reader.hasNextLine()) {
                    String data = reader.nextLine();
                    System.out.println(data);
                }
                reader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
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

    private static void exit() {
        System.out.println("Exiting...");
        System.exit(0);
    }
}