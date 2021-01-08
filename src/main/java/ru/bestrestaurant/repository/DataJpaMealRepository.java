package ru.bestrestaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bestrestaurant.model.Meal;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
@Repository
public interface DataJpaMealRepository extends JpaRepository<Meal, Integer> {

    @Query("SELECT m FROM Meal m JOIN FETCH m.restaurant WHERE m.date >= :fromD AND m.date <= :toD")
    List<Meal> getMealsBetween(@Param("fromD") LocalDate fromD, @Param("toD") LocalDate toD);

    @Query("SELECT m FROM Meal m JOIN FETCH m.restaurant r WHERE r.id = :id " +
            "AND m.date >= :fromD AND m.date <= :toD")
    List<Meal> getMealsBetweenForRestaurant(@Param("fromD") LocalDate fromD, @Param("toD") LocalDate toD,
                                            @Param("id") Integer id);
}
