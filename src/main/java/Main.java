import com.google.gson.Gson;
import models.DBS.DbInitializer;
import models.DBS.JdbcDatabaseOperations;
import models.GUI.MainFrame;
import models.Message;
import models.chatClients.ChatClient;
import models.chatClients.FileChatClient;
import models.chatClients.FileOperations.API.APIChatClient;
import models.chatClients.FileOperations.ChatFileOperations;
import models.chatClients.FileOperations.JSonChatFileOperations;
import models.chatClients.InMemoryChatClient;

public class Main {


    public static void main(String[] args)
    {
        String databaseDriver = "org.apache.derby.jdbc.EmbeddedDriver";
        String databaseUrl = "jdbc:derby:ChatClientDb_skC";
        DbInitializer dbInitializer = new DbInitializer(databaseDriver, databaseUrl);
        //dbInitializer.init();

        try{
            JdbcDatabaseOperations databaseOperations = new JdbcDatabaseOperations(databaseDriver, databaseUrl);
            databaseOperations.addMessage(new Message("ano","ano"));
        }catch(Exception a){}


        ChatFileOperations chatFileOperations = new JSonChatFileOperations();
        ChatClient chatClient = new APIChatClient();
        MainFrame window = new MainFrame (800, 600, chatClient);





    }

    private static void TestChat(){

        ChatClient chatClient = new InMemoryChatClient();

        chatClient.login("Matthias");
        chatClient.sendMessage("Hello");
        chatClient.logout();

    }
}