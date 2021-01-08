package ru.bestrestaurant.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.bestrestaurant.model.Meal;
import ru.bestrestaurant.model.Restaurant;
import ru.bestrestaurant.repository.DataJpaMealRepository;
import ru.bestrestaurant.repository.DataJpaRestaurantRepository;
import ru.bestrestaurant.to.MenuTo;
import ru.bestrestaurant.to.RestaurantTo;
import ru.bestrestaurant.util.DateTimeUtil;
import ru.bestrestaurant.util.UtilTo;
import ru.bestrestaurant.util.ValidationUtil;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = RootRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RootRestController {
    public static final String REST_URL = "/api/restaurants";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final DataJpaRestaurantRepository restaurantRepo;
    private final DataJpaMealRepository mealRepo;

    public RootRestController(DataJpaRestaurantRepository restaurantRepo,
                              DataJpaMealRepository mealRepo) {
        this.restaurantRepo = restaurantRepo;
        this.mealRepo = mealRepo;
    }

    @GetMapping
    List<RestaurantTo> getAllRestaurants() {
        log.info("Get all restaurants");
        List<Restaurant> restaurants = restaurantRepo.findAll();
        return restaurants.isEmpty() ? Collections.emptyList() : UtilTo.getRestaurantTos(restaurants);
    }

    @GetMapping(value = "/menus")
    Map<LocalDate, List<MenuTo>> getAllMenus(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromD,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toD) {
        log.info("Get all menus from {} to {}", fromD, toD);
        LocalDate[] correctInterval = DateTimeUtil.interval(fromD, toD);
        List<Meal> meals = mealRepo.getMealsBetween(correctInterval[0], correctInterval[1]);
        return meals.isEmpty() ? Collections.emptyMap() : UtilTo.getMenuTos(meals);
    }

    @GetMapping(value = "/{id}/menus")
    Map<LocalDate, List<MenuTo>> getRestaurantMenus(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromD,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toD,
            @PathVariable("id") int restaurant_id) {
        log.info("Get menus from {} to {} for restaurantt {}", fromD, toD, restaurant_id);
        ValidationUtil.checkNotFoundWithId(restaurantRepo.existsById(restaurant_id), restaurant_id);
        LocalDate[] correctInterval = DateTimeUtil.interval(fromD, toD);
        List<Meal> meals = mealRepo.getMealsBetweenForRestaurant(correctInterval[0], correctInterval[1], restaurant_id);
        return meals.isEmpty() ? Collections.emptyMap() : UtilTo.getMenuTos(meals);
    }
}
