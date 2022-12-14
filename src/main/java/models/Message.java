package models;

import com.google.gson.annotations.Expose;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Message {


    public static final int USER_LOGGED_IN = 1;
    public static final int USER_LOGGED_OUT = 2;
    public static final String AUTHOR_SYSTEM = "SYSTEM";

    @Expose(serialize = true, deserialize = true)
    private String username;
    @Expose(serialize = true, deserialize = true)
    private String text;
    @Expose(serialize = true, deserialize = true)
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


    public String toString() {

        String s = text + " ["+created.toLocalDate()+"]\n";
        return s;
    }

    public String toStringBackup() {
        if(username.toUpperCase().equals(AUTHOR_SYSTEM))
        {
            return text + "\n";
        }
        String s = username + " ["+created.toLocalDate()+"]\n";
        s.concat(text+"\n");
        return s;
    }

    public Message(int type, String username)
    {
        this.username = AUTHOR_SYSTEM;
        this.created = LocalDateTime.now();
        if(type == USER_LOGGED_IN)
        {
            text = username +" has joined the chat";
        }
        else if(type == USER_LOGGED_OUT)
        {
            text = username+" has left the chat";
        }
    }
}
