package restaurant;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


public class RestaurantRepository {
    public final Set<Restaurant> restaurants;

    public Set<Restaurant> getRestaurants() {
        return restaurants;
    }

    public RestaurantRepository(Set<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public RestaurantRepository() {
        restaurants = new HashSet<>();
        restaurants.add(new Restaurant(UUID.randomUUID(), "Kebab u grubego", "Warszawska 11, Kraków 31-222", RestaurantType.TURKISH));
        restaurants.add(new Restaurant(UUID.randomUUID(), "Milano Pizza", "Sobczyka 12/3, Warszawa 33-312", RestaurantType.ITALIAN));
        restaurants.add(new Restaurant(UUID.randomUUID(), "Meat Burger", "Lipowa 31b/1, Gdańsk 18-111", RestaurantType.AMERICAN));
    }

    public Restaurant create(Restaurant restaurant) {
        restaurants.add(restaurant);
        return restaurant;
    }

    public Restaurant getById(UUID restaurantId) {
        return restaurants.stream()
                .filter(restaurant -> restaurant.id.equals(restaurantId))
                .findAny()
                .orElseThrow();
    }
}