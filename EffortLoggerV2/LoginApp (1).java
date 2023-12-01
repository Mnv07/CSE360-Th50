package com.example.myjavafx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginApp extends Application {

    private final EffortLoggerDatabase database = new EffortLoggerDatabase();

    @Override
    public void start(Stage primaryStage) {
        GridPane loginGrid = createLoginGrid(primaryStage);

        Scene scene = new Scene(loginGrid, 400, 200);
        primaryStage.setTitle("Effort Logger Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createLoginGrid(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        grid.setStyle("-fx-background-color: #f0f0f0;");
        grid.setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Welcome to Effort Logger");
        welcomeLabel.setTextFill(Color.DARKBLUE);
        GridPane.setConstraints(welcomeLabel, 1, 0);

        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 1);
        TextField usernameInput = new TextField();
        usernameInput.setPromptText("username");
        GridPane.setConstraints(usernameInput, 1, 1);

        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 2);
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("password");
        GridPane.setConstraints(passwordInput, 1, 2);

        Button loginButton = new Button("Login");
        loginButton.setTextFill(Color.WHITE);
        loginButton.setStyle("-fx-background-color: #0073e6;");
        GridPane.setConstraints(loginButton, 1, 3);

        loginButton.setOnAction(e -> {
            if (database.authenticateUser(usernameInput.getText(), passwordInput.getText())) {
                EffortLoggerApp effortLoggerApp = new EffortLoggerApp();
                Scene effortLoggerScene = new Scene(effortLoggerApp.createEffortLoggerGrid(primaryStage), 600, 400);
                primaryStage.setScene(effortLoggerScene);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect username or password.");
                alert.showAndWait();
            }
        });

        grid.getChildren().addAll(welcomeLabel, usernameLabel, usernameInput, passwordLabel, passwordInput, loginButton);
        return grid;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
