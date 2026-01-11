package com.examen.signalement.javafx;

import com.examen.signalement.MainApp;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class SmartCityJavaFxApp extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(MainApp.class)
                .headless(false) // Not headless, we need UI
                .run();
        System.out.println("\n\n===========================================================");
        System.out.println("APPLICATION LANCEE ! ACCEDEZ VIA : http://localhost:8080");
        System.out.println("===========================================================\n");
    }

    @Override
    public void start(Stage stage) {
        applicationContext.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }
}
