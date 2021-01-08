package ru.bestrestaurant.to;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Objects;

public class MealTo {

    @JsonProperty("meal_name")
    @NotBlank
    @Size(min=2, max=30)
    private String name;

    @JsonProperty("meal_price")
    @DecimalMin(value = "1.00")
    @DecimalMax(value = "10000.00")
    @Digits(integer=3, fraction=2)
    private BigDecimal price;

    public MealTo(){

    }

    public MealTo(String name, BigDecimal price){
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MealTo)) return false;
        MealTo mealTo = (MealTo) o;
        return Objects.equals(getName(), mealTo.getName()) &&
                Objects.equals(getPrice(), mealTo.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPrice());
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
