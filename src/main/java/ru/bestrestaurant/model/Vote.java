package ru.bestrestaurant.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "votes")
public class Vote extends AbstractBaseEntity {

    @Column(name = "date_vote")
    private LocalDate date;

    //    @JsonProperty(value = "restaurantId")
//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//    @JsonIdentityReference(alwaysAsId = true)
//    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "user_email")
    private String userEmail;

    protected Vote() {

    }

    public Vote(User user, Restaurant restaurant) {
        super(null);
        this.date = LocalDate.now();
        this.user = user;
        this.userEmail = user.getEmail();
        this.restaurant = restaurant;
        this.restaurantName = restaurant.getName();
    }

//    public Vote(LocalDate date_vote, User user, Restaurant restaurant){
//        super(null);
//        this.date_vote = LocalDate.now();
//        this.user = user;
//        this.userEmail = user.getEmail();
//        this.restaurant = restaurant;
//        this.restaurantName = restaurant.getName();
//    }


    public LocalDate getDate() {
        return date;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public User getUser() {
        return user;
    }

    public String getUserEmail() {
        return userEmail;
    }

    @Override
    public String toString() {
        return "Vote{" + super.getId() +
                "date=" + date +
                ", restaurant_id=" + restaurant.getId() +
                ", user_id=" + user.getId() +
                '}';
    }
}
