package restaurant;


import java.util.UUID;

public class Meal {

    private final String name;
    private final double price;
    private final UUID id;

    public Meal(String name, double price, UUID id) {
        this.name = name;
        this.price = price;
        this.id = id;
    }

    @Override
    public String toString() {
        return name + ", " + price + "pln";
    }
}
