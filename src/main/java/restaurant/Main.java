package restaurant;

public class Main {
    public static void main(String[] args) {
        // creating 3 initial restaurants with start of an app
        Restaurant.createInitialRestaurants();
        // printing menu with instructions for user
        Restaurant.printAppMenu();
        // running restaurant manager app
        Restaurant.runRestaurantManager();
    }
}
