package ru.bestrestaurant.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.bestrestaurant.config.AppConfig;
import ru.bestrestaurant.to.RestaurantTo;
import ru.bestrestaurant.util.UtilTo;

import java.util.List;

@SpringJUnitConfig(classes = {AppConfig.class})
@Sql(scripts = {"classpath:db/populateDB.sql"}, config = @SqlConfig(encoding = "UTF-8"))
public class AbstractRepositoryTest {

    @Autowired
    DataJpaRestaurantRepository repo;

    @Test
    void myTest() {
        List<RestaurantTo> list = UtilTo.getRestaurantTos(repo.findAll());
        list.toArray();
    }
}
