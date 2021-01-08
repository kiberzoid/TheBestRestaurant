package ru.bestrestaurant;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.bestrestaurant.config.AppConfig;

public class SpringMain {
    private AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

    public static void main(String[] args){


    }
}
