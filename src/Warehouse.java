import java.util.ArrayList;
import java.util.List;

public class Warehouse {
    private List<Product>products=new ArrayList<>();

    public void printProducts(){
        for(Product product : products){
            System.out.println(product);
        }
    }


}
