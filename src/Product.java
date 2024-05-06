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
        this.comment = comment;//ne zaduljitelno
        this.position = position;
        this.availableQuantity = availableQuantity;
        this.unit = unit;
        this.manufacturer = manufacturer;
        this.expirationDate = expirationDate;
        this.arrivalDate = arrivalDate;
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
}
