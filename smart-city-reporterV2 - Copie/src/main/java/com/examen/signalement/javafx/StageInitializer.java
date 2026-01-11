package com.examen.signalement.javafx;

import com.examen.signalement.javafx.view.DashboardView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    private final DashboardView dashboardView;

    public StageInitializer(DashboardView dashboardView) {
        this.dashboardView = dashboardView;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Stage stage = event.getStage();
        Scene scene = new Scene(dashboardView.getView(), 1000, 700);

        // Add basic styles
        if (getClass().getResource("/styles.css") != null) {
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        }

        stage.setScene(scene);
        stage.setTitle("Smart City Reporter - Gestion des Signalements");
        stage.show();
    }
}
