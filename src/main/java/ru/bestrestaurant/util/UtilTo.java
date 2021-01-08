package ru.bestrestaurant.util;

import ru.bestrestaurant.model.Meal;
import ru.bestrestaurant.model.Restaurant;
import ru.bestrestaurant.model.User;
import ru.bestrestaurant.model.Vote;
import ru.bestrestaurant.to.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class UtilTo {

    private UtilTo() {

    }

    public static UserTo createUserTo(User user){
        return new UserTo(user.id(), user.getName(), user.getPassword());
    }

    public static List<RestaurantTo> getRestaurantTos(Collection<Restaurant> restaurants) {
        return restaurants.stream().map(UtilTo::createRestaurantTo).collect(Collectors.toList());
    }

    public static RestaurantTo createRestaurantTo(Restaurant r) {
        return new RestaurantTo(r.id(), r.getName(), r.getAddress());
    }

    public static List<VoteTo> getVoteTos(Collection<Vote> votes) {
        return votes.stream().map(UtilTo::createVoteTo).collect(Collectors.toList());
    }

    public static VoteTo createVoteTo(Vote v) {
        return new VoteTo(v.id(), v.getDate(), v.getRestaurant().getId(), v.getRestaurantName());
    }

    public static Map<LocalDate, List<MenuTo>> getMenuTos(Collection<Meal> meals) {
        Set<MenuTo> menus = meals.stream()
                .map(m -> new MenuTo(m.getRestaurant().getId(), m.getRestaurant().getName(), m.getDate()))
                .collect(Collectors.toSet());
        return menus.stream()
                .peek(menu -> menu.setMeals(filterMeals(menu, meals)))
                .sorted(Comparator.comparing(MenuTo::getId))
                .collect(Collectors.groupingBy(MenuTo::getDate));
    }

    public static Set<MealTo> filterMeals(MenuTo menu, Collection<Meal> meals) {
        return meals.stream()
                .filter(m -> m.getDate().equals(menu.getDate()))
                .filter(m -> m.getRestaurant().getId().equals(menu.getId()))
                .map(m -> new MealTo(m.getName(), m.getPrice()))
                .collect(Collectors.toSet());
    }

    public static Set<Meal> getMealsFromTos(Collection<MealTo> mealTos, Restaurant restaurant){
        return mealTos.stream()
                .map(mt -> new Meal(mt.getName(), mt.getPrice(), LocalDate.now(), restaurant))
                .collect(Collectors.toSet());
    }
}
