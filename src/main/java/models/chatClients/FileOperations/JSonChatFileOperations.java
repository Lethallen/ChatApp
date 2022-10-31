package models.chatClients.FileOperations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.Message;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSonChatFileOperations implements ChatFileOperations{
    private Gson gson;
    private static final String MESSAGES_FILE = "./messages.json";

    public JSonChatFileOperations(Gson gson) {
        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
    }

    @Override
    public void writeMessages(List<Message> messages)
    {

        String jsonText = gson.toJson(messages);
        System.out.println(jsonText);
        try {
            FileWriter writer = new FileWriter(MESSAGES_FILE);
            writer.write(jsonText);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Message> readMessages() {

        try {
            FileReader reader = new FileReader(MESSAGES_FILE);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String jsonText = "";
            String line;
            while((line = bufferedReader.readLine())!=null)
            {
                jsonText.append(line);
            }
            reader.close();
            Type targetType = new TypeToken<ArrayList<Message>>(){}.getType();
            List<Message> messages = gson.fromJson(jsonText.toString(),targetType);
            return messages;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ArrayList<>();
    }
}
