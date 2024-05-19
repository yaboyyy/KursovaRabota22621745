import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Warehouse {
    Scanner scanner = new Scanner(System.in);
    private List<Product>products=new ArrayList<>();

    public void printProducts(){
        int i = 1;
        for(Product product : products){
            System.out.println(i+":\n"+product.toString());
            i++;
        }
    }

    public Product addingDialog(){
        System.out.println("Adding product:\n");
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter expiry date (as an integer, e.g., 20230518): ");
        int expirationDate = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter arrival date: ");
        String arrivalDate = scanner.nextLine();
        System.out.print("Enter manufacturer: ");
        String manufacturer = scanner.nextLine();
        System.out.print("Enter unit (Kilogram or Liter): ");
        Unit unit = Unit.valueOf(scanner.nextLine());
        System.out.print("Enter quantity: ");
        int availableQuantity = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter location: ");
        String position = scanner.nextLine();
        System.out.print("Enter comment: ");
        String comment = scanner.nextLine();

        return new Product(name, comment, position, availableQuantity, unit, manufacturer, expirationDate, arrivalDate);
    }

    public String addProduct(Product product) {
        for(Product p : products){
            if(product.getName().equals(p.getName())&&product.getExpirationDate()!=p.getExpirationDate()){
                products.add(addingDialog());
                return  "Product added as separate.\n";
            }
            else if(product.getName().equals(p.getName())&&product.getExpirationDate()==p.getExpirationDate()){
                p.setAvailableQuantity(product.getAvailableQuantity()+p.getAvailableQuantity());
                return  "Product added to existing product.\n";
            }
        }
        products.add(product);
        return  "Product added.\n";
    }
    public String removeProduct(Product product) {
        products.remove(product);
        return  "Product removed.\n";
    }
}
