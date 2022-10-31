import com.google.gson.Gson;
import models.GUI.MainFrame;
import models.chatClients.ChatClient;
import models.chatClients.FileChatClient;
import models.chatClients.FileOperations.ChatFileOperations;
import models.chatClients.FileOperations.JSonChatFileOperations;
import models.chatClients.InMemoryChatClient;

public class Main {


    public static void main(String[] args)
    {
        Gson gson = new Gson();
        ChatFileOperations chatFileOperations = new JSonChatFileOperations(gson);
        ChatClient chatClient = new FileChatClient(chatFileOperations);
        MainFrame window = new MainFrame (800, 600, chatClient);





    }

    private static void TestChat(){

        ChatClient chatClient = new InMemoryChatClient();

        chatClient.login("Matthias");
        chatClient.sendMessage("Hello");
        chatClient.logout();

    }
}