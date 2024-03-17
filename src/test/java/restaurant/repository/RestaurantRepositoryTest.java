package restaurant.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import restaurant.model.Restaurant;
import restaurant.model.RestaurantType;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantRepositoryTest {
    private RestaurantRepository restaurantRepository;

    @BeforeEach
    void setup_repository() {
        Set<Restaurant> restaurants = new HashSet<>();
        restaurants.add(new Restaurant(UUID.randomUUID(), "Kebab u grubego", "Warszawska 11, Kraków 31-222", RestaurantType.TURKISH));
        restaurants.add(new Restaurant(UUID.randomUUID(), "Milano Pizza", "Sobczyka 12/3, Warszawa 33-312", RestaurantType.ITALIAN));
        restaurants.add(new Restaurant(UUID.randomUUID(), "Meat Burger", "Lipowa 31b/1, Gdańsk 18-111", RestaurantType.AMERICAN));
        restaurantRepository = new RestaurantRepository(restaurants);
    }

    @Test
    void should_create_restaurant() {
        // Given
        Restaurant newRestaurant = new Restaurant(UUID.randomUUID(), "Sushi Express", "Nowa 22, Wrocław 55-555", RestaurantType.ASIAN);

        // When
        Restaurant createdRestaurant = restaurantRepository.create(newRestaurant);

        // Then
        assertTrue(restaurantRepository.restaurants.contains(newRestaurant));
        assertEquals(newRestaurant, createdRestaurant);
    }

    @Test
    void should_get_restaurant_by_id() {
        // Given
        UUID restaurantId = restaurantRepository.restaurants.iterator().next().id;

        // When
        Restaurant retrievedRestaurant = restaurantRepository.getById(restaurantId);

        // Then
        assertNotNull(retrievedRestaurant);
    }

    @Test
    void should_throw_exception_when_getting_non_existing_restaurant() {
        // Given
        UUID nonExistingId = UUID.randomUUID();

        // When / Then
        assertThrows(NoSuchElementException.class, () -> restaurantRepository.getById(nonExistingId));
    }
}