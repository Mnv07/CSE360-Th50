package com.example.myjavafx;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class EffortLoggerMenu {

    private TextArea logArea;

    public MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu toolsMenu = new Menu("Tools");

        MenuItem planningPokerItem = new MenuItem("Planning Poker");
        planningPokerItem.setOnAction(e -> showPlanningPoker());

        MenuItem estimatorItem = new MenuItem("Effortlogger Estimator");
        estimatorItem.setOnAction(e -> showEffortLoggerEstimator());

        MenuItem viewTasksItem = new MenuItem("View Tasks");
        viewTasksItem.setOnAction(e -> showViewTasks());

        toolsMenu.getItems().addAll(planningPokerItem, estimatorItem, viewTasksItem);
        menuBar.getMenus().add(toolsMenu);

        return menuBar;
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
}
