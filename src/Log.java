import java.io.Serializable;

public class Log implements Serializable {
    private Product product;
    private Change typeChange;
    private long date;

    public Log(Product product, Change typeChange, long date) {
        this.product = product;
        this.typeChange = typeChange;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Product " + product.getName() + " has been " + typeChange + " on " + date;
    }

    public String save(){
        return product.save() + "\n" + typeChange + "\n" + date;
    }

    public long getDate() {
        return date;
    }
}
