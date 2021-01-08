package ru.bestrestaurant.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

public class MenuTo {

    @JsonIgnore
    private LocalDate date;

    @JsonProperty("restaurant_id")
    private Integer id;

    @JsonProperty("restaurant_name")
    private String name;

    @JsonProperty("restaurant_menu")
    @NotEmpty
    private Set<MealTo> meals;

    public MenuTo() {

    }

    public MenuTo(Integer id, String name, LocalDate date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<MealTo> getMeals() {
        return meals;
    }

    public void setMeals(Collection<MealTo> meals) {
        this.meals = Set.copyOf(meals);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuTo)) return false;
        MenuTo menuTo = (MenuTo) o;
        return id.equals(menuTo.id) &&
                Objects.equals(date, menuTo.date) &&
                Objects.equals(name, menuTo.name) &&
                Objects.equals(meals, menuTo.meals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, id, name, meals);
    }

    @Override
    public String toString() {
        return "MenuTo{" +
                "date=" + date +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", meals=" + meals +
                '}';
    }
}
