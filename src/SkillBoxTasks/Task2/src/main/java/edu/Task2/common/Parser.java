package edu.Task2.common;

import edu.Task2.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class Parser {
    @Value("${init-file.path}")
    private String filePath;

    public List<User> parseInitFile() {
        var users = new ArrayList<User>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while (reader.ready()) {
                users.add(parseUser(reader.readLine()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    private User parseUser(String inputData) {
        var data = inputData.split(";");
        return new User(data[0], data[1], Integer.parseInt(data[2]));
    }
}