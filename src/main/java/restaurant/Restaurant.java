package restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Restaurant {

    public final UUID id;
    public String name;
    public final String address;
    public final RestaurantType type;
    public final List<Meal> meals = new ArrayList<>();

    public Restaurant(UUID id, String name, String address, RestaurantType type) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.type = type;
    }

    @Override
    public String toString() {
        return "ID: [" + id + "], " + name + ", " + address + ", " + type + ", Meals " + meals;
    }
}