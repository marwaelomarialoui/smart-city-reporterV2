package com.examen.signalement.javafx.view;

import com.examen.signalement.model.Signalement;
import com.examen.signalement.model.StatutSignalement;
import com.examen.signalement.service.SignalementService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DashboardView {

    private final SignalementService signalementService;
    private TableView<Signalement> table;
    private ObservableList<Signalement> data;

    // Form fields
    private TextField txtTitre = new TextField();
    private TextArea txtDescription = new TextArea();
    private TextField txtType = new TextField();
    private ComboBox<StatutSignalement> cbStatut = new ComboBox<>();
    private TextField txtNom = new TextField();
    private TextField txtEmail = new TextField();
    private TextField txtVille = new TextField(); // Localisation
    private DatePicker dpDate = new DatePicker();

    public DashboardView(SignalementService signalementService) {
        this.signalementService = signalementService;
    }

    public Parent getView() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f4f4f4;");

        // Header
        Label titleLabel = new Label("Tableau de Bord - Signalements");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.DARKSLATEBLUE);
        HBox header = new HBox(titleLabel);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 0, 20, 0));
        root.setTop(header);

        // Center - Table
        table = new TableView<>();
        setupTable();
        loadData();
        VBox centerBox = new VBox(10, new Label("Liste des signalements"), table);
        VBox.setVgrow(table, Priority.ALWAYS);
        centerBox.setPadding(new Insets(0, 20, 0, 0));
        root.setCenter(centerBox);

        // Right - Form
        VBox form = createForm();
        root.setRight(form);

        return root;
    }

    private void setupTable() {
        TableColumn<Signalement, Long> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Signalement, String> colTitre = new TableColumn<>("Titre");
        colTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));

        TableColumn<Signalement, String> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(new PropertyValueFactory<>("typeProbleme"));

        TableColumn<Signalement, StatutSignalement> colStatut = new TableColumn<>("Statut");
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));

        TableColumn<Signalement, LocalDate> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateSignalement"));

        table.getColumns().addAll(colId, colTitre, colType, colStatut, colDate);

        // Selection listener
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                fillForm(newSelection);
            }
        });
    }

    private void loadData() {
        List<Signalement> list = signalementService.afficherTous();
        data = FXCollections.observableArrayList(list);
        table.setItems(data);
    }

    private VBox createForm() {
        VBox form = new VBox(10);
        form.setPadding(new Insets(20));
        form.setStyle(
                "-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0); -fx-background-radius: 5;");
        form.setPrefWidth(350);

        Label lblForm = new Label("Détails / Nouveau");
        lblForm.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));

        cbStatut.getItems().setAll(StatutSignalement.values());

        txtDescription.setPrefRowCount(3);
        txtDescription.setWrapText(true);

        form.getChildren().addAll(
                lblForm,
                new Label("Titre:"), txtTitre,
                new Label("Description:"), txtDescription,
                new Label("Type:"), txtType,
                new Label("Statut:"), cbStatut,
                new Separator(),
                new Label("Auteur (Nom):"), txtNom,
                new Label("Email:"), txtEmail,
                new Label("Localisation:"), txtVille,
                new Label("Date:"), dpDate,
                createButtons());

        return form;
    }

    private HBox createButtons() {
        Button btnSave = new Button("Enregistrer");
        btnSave.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnSave.setOnAction(e -> saveSignalement());

        Button btnDelete = new Button("Supprimer");
        btnDelete.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");
        btnDelete.setOnAction(e -> deleteSignalement());

        Button btnClear = new Button("Vider");
        btnClear.setOnAction(e -> clearForm());

        HBox buttons = new HBox(10, btnSave, btnDelete, btnClear);
        buttons.setPadding(new Insets(10, 0, 0, 0));
        buttons.setAlignment(Pos.CENTER);
        return buttons;
    }

    private void fillForm(Signalement s) {
        txtTitre.setText(s.getTitre());
        txtDescription.setText(s.getDescription());
        txtType.setText(s.getTypeProbleme());
        cbStatut.setValue(s.getStatut());
        txtNom.setText(s.getNom());
        txtEmail.setText(s.getEmail());
        txtVille.setText(s.getLocalisation());
        dpDate.setValue(s.getDateSignalement());
    }

    private void clearForm() {
        table.getSelectionModel().clearSelection();
        txtTitre.clear();
        txtDescription.clear();
        txtType.clear();
        cbStatut.setValue(null);
        txtNom.clear();
        txtEmail.clear();
        txtVille.clear();
        dpDate.setValue(null);
    }

    private void saveSignalement() {
        Signalement s = table.getSelectionModel().getSelectedItem();
        if (s == null) {
            s = new Signalement();
        }

        s.setTitre(txtTitre.getText());
        s.setDescription(txtDescription.getText());
        s.setTypeProbleme(txtType.getText());
        s.setStatut(cbStatut.getValue());
        s.setNom(txtNom.getText());
        s.setEmail(txtEmail.getText());
        s.setLocalisation(txtVille.getText());
        s.setDateSignalement(dpDate.getValue() != null ? dpDate.getValue() : LocalDate.now());

        try {
            if (s.getId() == null) {
                signalementService.ajouterSignalement(s);
            } else {
                signalementService.modifierSignalement(s);
            }
            loadData();
            clearForm();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur: " + e.getMessage());
            alert.show();
        }
    }

    private void deleteSignalement() {
        Signalement s = table.getSelectionModel().getSelectedItem();
        if (s != null) {
            signalementService.supprimerSignalement(s.getId());
            loadData();
            clearForm();
        }
    }
}
