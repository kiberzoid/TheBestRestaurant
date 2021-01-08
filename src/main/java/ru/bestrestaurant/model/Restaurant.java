package ru.bestrestaurant.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {

    @Column(name = "address")
    @NotBlank
    @Size(min=5, max=100)
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<Meal> meals;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name, String address) {
        super(id, name);
        this.address = address;
    }

    public Restaurant(String name, String address) {
        this(null, name, address);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Meal> getMeals() {
        return meals;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name +
                ", address='" + address +
                '}';
    }
}
