import models.GUI.MainFrame;
import models.chatClients.ChatClient;
import models.chatClients.InMemoryChatClient;

public class Main {


    public static void main(String[] args)
    {
        MainFrame window = new MainFrame (800, 600);





    }

    private static void TextChat(){

        ChatClient chatClient = new InMemoryChatClient();

        chatClient.login("Matthias");
        chatClient.sendMessage("Hello");
        chatClient.logout();

    }
}