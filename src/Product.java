public class Product {
    private String name;
    private int expirationDate;
    private String arrivalDate;
    private String manufacturer;
    private Unit unit;
    private int availableQuantity;
    private String position;
    private String comment;

    public Product(String name, String comment, String position, int availableQuantity, Unit unit, String manufacturer, int expirationDate, String arrivalDate) {
        this.name = name;
        this.expirationDate = expirationDate;
        this.arrivalDate = arrivalDate;
        this.manufacturer = manufacturer;
        this.unit = unit;
        this.availableQuantity = availableQuantity;
        this.position = position;
        this.comment = comment;
    }
    public String toString(){
        return "Name: "+name+
                "\n Expiration Date: "+expirationDate+
                "\nArrival Date: "+arrivalDate+
                "\nManufacturer: "+manufacturer+
                "\nUnit: "+unit+
                "\nAvailable Quantity: "+availableQuantity+
                "\nPosition: "+position+
                "\nComment: "+comment;
    }

    public int getExpirationDate() {
        return expirationDate;
    }

    public String getName() {
        return name;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }
}
