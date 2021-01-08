package ru.bestrestaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.bestrestaurant.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
@Repository
public interface DataJpaVoteRepository extends JpaRepository<Vote, Integer> {
    Vote getByUserIdAndDate(Integer id, LocalDate date);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant r JOIN FETCH v.user u WHERE u.id = :id " +
            "AND v.date >= :fromD AND v.date <= :toD ORDER BY v.date DESC")
    List<Vote> getAll(@Param("id") Integer id, @Param("fromD") LocalDate fromD, @Param("toD") LocalDate toD);
}
