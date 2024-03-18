package restaurant;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantRepositoryTest {

    private final Set<Restaurant> restaurants = new HashSet<>();
    private RestaurantRepository restaurantRepository;

    @BeforeEach
    void setup() {
        restaurants.add(new Restaurant(randomUUID(), "Kebab u grubego", "Warszawska 11, Kraków 31-222", RestaurantType.TURKISH));
        restaurants.add(new Restaurant(randomUUID(), "Milano Pizza", "Sobczyka 12/3, Warszawa 33-312", RestaurantType.ITALIAN));
        restaurants.add(new Restaurant(randomUUID(), "Meat Burger", "Lipowa 31b/1, Gdańsk 18-111", RestaurantType.AMERICAN));
        restaurantRepository = new RestaurantRepository(restaurants);
    }

    @AfterEach
    void afterAll() {
        restaurants.clear();
    }

    @Test
    void should_create_restaurant() {
        // Given
        Restaurant newRestaurant = new Restaurant(randomUUID(), "Sushi Express", "Nowa 22, Wrocław 55-555", RestaurantType.ASIAN);

        // When
        Restaurant createdRestaurant = restaurantRepository.create(newRestaurant);

        // Then
        assertTrue(restaurantRepository.restaurants.contains(newRestaurant));
        assertEquals(newRestaurant, createdRestaurant);
    }

    @Test
    void should_get_restaurant_by_id() {
        // Given
        Restaurant aRestaurant = new Restaurant(randomUUID(), "Kebab u grubego", "Warszawska 11, Kraków 31-222", RestaurantType.TURKISH);
        restaurants.add(aRestaurant);

        // When
        Restaurant result = restaurantRepository.getById(aRestaurant.id);

        // Then
        assertNotNull(result);
        assertEquals(aRestaurant.name, result.name);
        assertEquals(aRestaurant.address, result.address);
        assertEquals(aRestaurant.type, result.type);
    }

    @Test
    void should_throw_exception_when_getting_non_existing_restaurant() {
        // Given
        UUID nonExistingId = randomUUID();

        // When / Then
        assertThrows(NoSuchElementException.class, () -> restaurantRepository.getById(nonExistingId));
    }
}