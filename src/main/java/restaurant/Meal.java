package restaurant;


public class Meal {
    // creating meal class attributes
    private final String name;
    private final double price;
    private final int id;

    // creating class constructor, with parameters type string for name
    // double for price and int for id
    public Meal(String name, double price, int id) {
        this.name = name;
        this.price = price;
        this.id = id;
    }

    // overriding toString() method
    @Override
    public String toString() {
        return name + " " + price + " pln";
    }
}
