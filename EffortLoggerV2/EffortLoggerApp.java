package com.example.myjavafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class EffortLoggerApp extends Application {

    private Timer timer;
    private long secondsPassed;
    private TextArea logArea;
    private final EffortLoggerDatabase database = new EffortLoggerDatabase();

    @Override
    public void start(Stage primaryStage) {
        LoginApp loginApp = new LoginApp();
        loginApp.start(primaryStage);
    }

    public GridPane createEffortLoggerGrid(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);

        EffortLoggerMenu menu = new EffortLoggerMenu();
        MenuBar menuBar = menu.createMenuBar();
        grid.add(menuBar, 0, 0);

        Label timerLabel = new Label("00:00:00");
        timerLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #0073e6;");
        GridPane.setConstraints(timerLabel, 0, 1);

        ProgressBar progressBar = new ProgressBar(0);
        GridPane.setConstraints(progressBar, 0, 2);

        ComboBox<String> taskDropdown = new ComboBox<>();
        taskDropdown.getItems().addAll("Task 1", "Task 2", "Task 3");
        GridPane.setConstraints(taskDropdown, 0, 3);

        TextArea descriptionBox = new TextArea();
        descriptionBox.setPromptText("Describe your task...");
        GridPane.setConstraints(descriptionBox, 0, 4);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");
        Button saveButton = new Button("Save");
        buttonBox.getChildren().addAll(startButton, stopButton, saveButton);
        GridPane.setConstraints(buttonBox, 0, 5);

        startButton.setOnAction(e -> startTimer(timerLabel, progressBar));
        stopButton.setOnAction(e -> stopTimer());
        saveButton.setOnAction(e -> saveEffort(taskDropdown, descriptionBox, timerLabel));

        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefHeight(200);
        GridPane.setConstraints(logArea, 0, 6, 3, 1);

        grid.getChildren().addAll(timerLabel, progressBar, taskDropdown, descriptionBox, buttonBox, logArea);

        return grid;
    }

    private void startTimer(Label timerLabel, ProgressBar progressBar) {
        stopTimer(); // Stop any existing timer
        timer = new Timer();
        secondsPassed = 0;

        // Assuming 1 hour for full progress
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    secondsPassed++;
                    timerLabel.setText(formatSeconds(secondsPassed));
                    progressBar.setProgress((double) secondsPassed / 3600); // Assuming 1 hour for full progress
                });
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

    private void saveEffort(ComboBox<String> taskDropdown, TextArea descriptionBox, Label timerLabel) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = dateFormat.format(new Date());

        String task = taskDropdown.getValue();
        String description = descriptionBox.getText();
        String timeLogged = timerLabel.getText();

        String logMessage = String.format("Date: %s\nTask: %s\nDescription: %s\nTime Logged: %s\n\n", currentTime, task, description, timeLogged);
        logArea.appendText(logMessage); // Append new log to existing logs

        // Save tasks log immediately after appending the new task
        saveTasksLog(logArea.getText());

        taskDropdown.getSelectionModel().clearSelection();
        descriptionBox.clear();
        timerLabel.setText("00:00:00");
        stopTimer();
    }

    private String formatSeconds(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }

    private void showPlanningPoker() {
        // Implement the Planning Poker functionality here
        PlanningPoker.display();
    }

    private void showEffortLoggerEstimator() {
        // Implement the Effortlogger Estimator functionality here
        System.out.println("Opening Effortlogger Estimator");
    }

    private void showViewTasks() {
        // Display tasks in a new window
        Stage tasksStage = new Stage();
        tasksStage.setTitle("View Tasks");

        TextArea tasksArea = new TextArea();
        tasksArea.setEditable(false);
        tasksArea.setWrapText(true);

        // Read tasks from the "tasksLog.txt" file and display them
        String tasksLogContent = readTasksLog();
        tasksArea.setText(tasksLogContent);

        Scene tasksScene = new Scene(tasksArea, 400, 300);
        tasksStage.setScene(tasksScene);
        tasksStage.show();
    }

    private String readTasksLog() {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("tasksLog.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private void saveTasksLog(String content) {
        File file = new File("tasksLog.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
