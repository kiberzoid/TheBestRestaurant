package ru.bestrestaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.bestrestaurant.model.User;

@Transactional(readOnly = true)
public interface DataJpaUserRepository extends JpaRepository<User, Integer> {
    User getByEmail(String email);
}
