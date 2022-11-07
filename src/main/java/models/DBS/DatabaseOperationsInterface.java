package models.DBS;

import models.Message;

import java.util.List;

public interface DatabaseOperationsInterface {
    void addMessage(Message message);
    List<Message> getMessages();
}
