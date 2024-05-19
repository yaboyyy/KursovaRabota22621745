import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Warehouse implements Serializable {
    Scanner scanner = new Scanner(System.in);
    private List<Product>products=new ArrayList<>();
    private List<Log>logList=new ArrayList<>();

    public void printProducts(){
        int i = 1;
        for(Product product : products){
            System.out.println(i+".\n"+product.toString());
            i++;
        }
    }

    public Product addingDialog(){
        System.out.println("Adding product:\n");
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter expiry date (as an integer  20230518): ");
        long expirationDate = Integer.parseInt(scanner.nextLine());
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

    public String addProduct(long currDate) {
        Product product = addingDialog();
        for(Product p : products){
            if(product.getName().equals(p.getName())&&product.getExpirationDate()!=p.getExpirationDate()){
                products.add(product);
                logList.add(new Log(product, Change.Added, currDate));
                return  "Product added as separate.\n";
            }
            else if(product.getName().equals(p.getName())&&product.getExpirationDate()==p.getExpirationDate()){
                p.setAvailableQuantity(product.getAvailableQuantity()+p.getAvailableQuantity());
                logList.add(new Log(product, Change.Added, currDate));
                return  "Product added to existing product.\n";
            }
        }
        logList.add(new Log(product, Change.Added, currDate));
        products.add(product);
        return  "Product added.\n";
    }



    public String removeProduct(String name, int quantity, long currDate) {
        String answer;

        List<Product> matchedProducts = new ArrayList<>();
        for (Product p : products) {
            if (p.getName().equals(name)) {
                matchedProducts.add(p);
            }
        }

        if (matchedProducts.isEmpty()) {
            return "Product not found.\n";
        }
        matchedProducts.sort(Comparator.comparingLong(Product::getExpirationDate));
        int totalAvailableQuantity = 0;
        long closestToExpiration = matchedProducts.get(0).getExpirationDate();
        for(Product p : matchedProducts) {
            totalAvailableQuantity += p.getAvailableQuantity();
        }


        int i = 0;
        while(!matchedProducts.isEmpty()){
            for(Product p : products){
                if (matchedProducts.get(i).getName().equals(p.getName())&&totalAvailableQuantity>=quantity){
                    if(matchedProducts.get(i).getAvailableQuantity()-quantity>=0){
                        p.setAvailableQuantity(p.getAvailableQuantity()-quantity);
                        logList.add(new Log(p, Change.Removed, currDate));
                        return  "Products removed.\n";
                    } else {
                        p.setAvailableQuantity(p.getAvailableQuantity()-quantity);
                        quantity-=matchedProducts.get(i).getAvailableQuantity();
                        i++;
                        logList.add(new Log(p, Change.Removed, currDate));
                    }
                } else if (matchedProducts.get(0).getName().equals(p.getName())&&totalAvailableQuantity < quantity) {
                    System.out.println("This product has "+p.getAvailableQuantity()+" available. Do you still want to remove them.(answer with yes or no)");
                    answer = scanner.nextLine();
                    if (answer.equalsIgnoreCase("yes")){
                        p.setAvailableQuantity(0);
                        logList.add(new Log(p, Change.Removed, currDate));
                        return  "Products removed.\n";
                    }
                }
            }
        }
        return  "No such products.\n";
    }

    public String printLog(long from, long to){
        for(Log l : logList) {
            if (l.getDate()>from && l.getDate()<to) {
                return l.toString() + "\n";
            }
        }
        return "No change between these dates.\n";
    }

    public void clear(int daysBeforeExpiration, long currDate){
        int i=0;
        List<Product> cleanableProducts = new ArrayList<>();
        for (Product p : products) {
            if (currDate==p.getExpirationDate()||currDate-p.getExpirationDate()<=daysBeforeExpiration){
                cleanableProducts.add(p);
            }
        }
        if (cleanableProducts.isEmpty()){
            System.out.println("No products need to be cleared.\n");
            return;
        }
        for (Product p : products) {
            if (p == cleanableProducts.get(i)){

                p.setAvailableQuantity(0);

                logList.add(new Log(p, Change.Cleaned, currDate));
                System.out.println(cleanableProducts.toString()+"\nHas been cleared from the warehouse.\n");
                i++;
            }
        }
    }

    public List<Log> getLogList() {
        return logList;
    }

    public void setLogList(List<Log> logList) {
        this.logList = logList;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
