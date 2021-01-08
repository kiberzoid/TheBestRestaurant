package ru.bestrestaurant.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.bestrestaurant.model.Meal;
import ru.bestrestaurant.model.Restaurant;
import ru.bestrestaurant.repository.DataJpaMealRepository;
import ru.bestrestaurant.repository.DataJpaRestaurantRepository;
import ru.bestrestaurant.to.MenuTo;
import ru.bestrestaurant.to.RestaurantTo;
import ru.bestrestaurant.util.UtilTo;
import ru.bestrestaurant.util.ValidationUtil;
import ru.bestrestaurant.util.exception.NotAllowedOpException;
import ru.bestrestaurant.util.exception.NotFoundException;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = AdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController {

    private static final Logger log = LoggerFactory.getLogger(AdminRestController.class);

    static final String REST_URL = "/api/admin/";

    private final DataJpaRestaurantRepository restaurantRepo;
    private final DataJpaMealRepository mealRepo;

    public AdminRestController(DataJpaRestaurantRepository restaurantRepo, DataJpaMealRepository mealRepo) {
        this.restaurantRepo = restaurantRepo;
        this.mealRepo = mealRepo;
    }

    @PostMapping(value = "restaurants", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<RestaurantTo> createRestaurant(@Valid @RequestBody Restaurant restaurant) {
        log.info("Create restaurant {}", restaurant);
        ValidationUtil.checkNew(restaurant);
        Restaurant created = restaurantRepo.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "restaurants/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity
                .created(uriOfNewResource)
                .body(UtilTo.createRestaurantTo(created));
    }

    @PutMapping(value = "restaurants/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    RestaurantTo updateRestaurant(@Valid @RequestBody Restaurant restaurant, @PathVariable("id") int id) {
        log.info("Update restaurant {}", restaurant);
        ValidationUtil.assureIdConsistent(restaurant, id);
        Restaurant updated = ValidationUtil.checkNotFoundWithId(restaurantRepo.save(restaurant), id);
        return UtilTo.createRestaurantTo(updated);

    }

    @DeleteMapping(value = "restaurants/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    void deleteRestaurant(@PathVariable("id") int id) {
        log.info("Delete restaurant with id = {}", id);
        ValidationUtil.checkNotFoundWithId(restaurantRepo.delete(id), id);
    }

    @PostMapping(value = "restaurants/{id}/menu", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    ResponseEntity<Map<LocalDate, List<MenuTo>>> createMenu(@PathVariable("id") int restaurant_id,
                                                            @Valid @RequestBody MenuTo menu) {
        log.info("Create menu {} for restaurant {}", menu, restaurant_id);
        List<Meal> existedMenu = mealRepo.getMealsBetweenForRestaurant(LocalDate.now(), LocalDate.now(), restaurant_id);
        if (!existedMenu.isEmpty()) {
            throw new NotAllowedOpException("Menu already exists for restaurant=" + restaurant_id);
        }
        Restaurant restaurant = restaurantRepo.findById(restaurant_id)
                .orElseThrow(() -> new NotFoundException("Restaurant with id=" + restaurant_id + " not found"));
        Set<Meal> meals = UtilTo.getMealsFromTos(menu.getMeals(), restaurant);
        return new ResponseEntity<>(UtilTo.getMenuTos(mealRepo.saveAll(meals)), HttpStatus.CREATED);
    }

    @PutMapping(value = "restaurants/{id}/menu")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Transactional
    Map<LocalDate, List<MenuTo>> updateMenu(@PathVariable("id") int restaurant_id, @Valid @RequestBody MenuTo menu) {
        log.info("Update menu {} for restaurant {}", menu, restaurant_id);
        ValidationUtil.checkNotFoundWithId(restaurantRepo.existsById(restaurant_id), restaurant_id);
        Restaurant restaurant = restaurantRepo.getWithMealsForDate(restaurant_id, LocalDate.now());
        if (restaurant == null) {
            throw new NotFoundException("Menu not found for restaurant=" + restaurant_id);
        }
        mealRepo.deleteAll(restaurant.getMeals());
        Set<Meal> meals = UtilTo.getMealsFromTos(menu.getMeals(), restaurant);
        return UtilTo.getMenuTos(mealRepo.saveAll(meals));
    }

    @DeleteMapping(value = "restaurants/{id}/menu")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @Transactional
    void deleteMenu(@PathVariable("id") int restaurant_id) {
        log.info("Delete menu for restaurant {}", restaurant_id);
        ValidationUtil.checkNotFoundWithId(restaurantRepo.existsById(restaurant_id), restaurant_id);
        Restaurant restaurant = restaurantRepo.getWithMealsForDate(restaurant_id, LocalDate.now());
        if (restaurant == null) {
            throw new NotFoundException("Menu not found for restaurant=" + restaurant_id);
        }
        mealRepo.deleteAll(restaurant.getMeals());
    }
}
