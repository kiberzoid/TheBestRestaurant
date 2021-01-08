package ru.bestrestaurant.to;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Objects;

public class VoteTo {

    @JsonProperty("vote_id")
    private Integer id;

    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("restaurant_id")
    private Integer restaurantId;

    @JsonProperty("restaurant_name")
    private String restaurantName;

    public VoteTo(){

    }

    public VoteTo(Integer id, LocalDate date, Integer restaurantId, String restaurantName) {
        this.id = id;
        this.date = date;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VoteTo)) return false;
        VoteTo voteTo = (VoteTo) o;
        return Objects.equals(getId(), voteTo.getId()) &&
                Objects.equals(getDate(), voteTo.getDate()) &&
                Objects.equals(getRestaurantId(), voteTo.getRestaurantId()) &&
                Objects.equals(getRestaurantName(), voteTo.getRestaurantName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDate(), getRestaurantId(), getRestaurantName());
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + id +
                ", date=" + date +
                ", restaurantId=" + restaurantId +
                ", restaurantName='" + restaurantName + '\'' +
                '}';
    }
}
