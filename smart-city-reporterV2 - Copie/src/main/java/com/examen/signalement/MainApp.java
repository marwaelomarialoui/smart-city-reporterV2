package com.examen.signalement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApp {
    public static void main(String[] args) {
        javafx.application.Application.launch(com.examen.signalement.javafx.SmartCityJavaFxApp.class, args);
    }
}