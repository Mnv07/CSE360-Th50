package com.example.myjavafx;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class EffortLoggerDatabase {
    private final String filePath = "/Users/mnv/IdeaProjects/MyJavaFX/src/main/java/com/example/myjavafx/userDatabase.csv"; // Update this path
    private final Map<String, String> userDatabase = new HashMap<>();

    public EffortLoggerDatabase() {
        loadUserData();
    }

    private void loadUserData() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                userDatabase.put(userData[0], userData[1]); // assuming CSV format: username,password
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean authenticateUser(String username, String password) {
        return userDatabase.containsKey(username) && userDatabase.get(username).equals(password);
    }

    public void addUser(String username, String password) {
        userDatabase.put(username, password);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(username + "," + password + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
