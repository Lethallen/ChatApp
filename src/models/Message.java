package models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Message {


    private String username;
    private String text;
    private LocalDateTime created;

    public Message(String username, String text) {
        this.username = username;
        this.text = text;
        created = LocalDateTime.now();
    }

    public String getUsername() {
        return username;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getCreated() {
        return created;
    }
}
