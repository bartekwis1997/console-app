package restaurant;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JSONGenerator {
    private static final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public static void generateJSON(List<Restaurant> restaurants) {
        ObjectNode rootNode = objectMapper.createObjectNode();
        ArrayNode restaurantArray = rootNode.putArray("restaurants");

        for (Restaurant restaurant : restaurants) {
            ObjectNode restaurantNode = objectMapper.createObjectNode();
            restaurantNode.put("id", restaurant.id.toString());
            restaurantNode.put("name", restaurant.name);
            restaurantNode.put("type", restaurant.type.toString());

            ArrayNode mealsArray = restaurantNode.putArray("meals");
            for (Meal meal : restaurant.meals) {
                ObjectNode mealNode = objectMapper.createObjectNode();
                mealNode.put("id", meal.getId().toString());
                mealNode.put("name", meal.getName());
                mealsArray.add(mealNode);
            }

            restaurantArray.add(restaurantNode);
        }

        try {
            objectMapper.writeValue(new File("restaurants.json"), rootNode);
            System.out.println("JSON file successfully generated.");
        } catch (IOException e) {
            System.err.println("Error writing JSON file: " + e.getMessage());
        }
    }
}
