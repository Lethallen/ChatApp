package models.chatClients.FileOperations.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.Message;
import models.chatClients.ChatClient;
import models.chatClients.FileOperations.LocalDateTimeDeserializer;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class APIChatClient implements ChatClient {



    private String loggedUser;
    private List<String> loggedUsers;
    private List<Message> messages;
    private List<ActionListener> listeners = new ArrayList<>();

    private final String URL = "http://fimuhkpro22021.aspifyhost.cz/";
    private String token;
    private Gson gson;

    public APIChatClient(){
        loggedUsers = new ArrayList<>();
        messages = new ArrayList<>();
        this.gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer()).registerTypeAdapter(LocalDateTime.class,new LocalDateTimeDeserializer()).create();
        Runnable refreshData = ()->{Thread.currentThread().setName("refreshData");
        try{
            while(true)
            {
                if(isAuthenticated()) {
                    refreshLoggedUsers();
                    refreshMessages();
                }
                TimeUnit.SECONDS.sleep(3);
            }

        }catch(Exception e){}
        };
        Thread refreshDataThread = new Thread(refreshData);
        refreshDataThread.start();
    }


    @Override
    public void sendMessage(String text) {
        try {
            SendMessageRequest msgRequest = new SendMessageRequest(token,text);

            String url_endpoint = URL + "/api/Chat/Login";
            HttpPost post = new HttpPost(url_endpoint);
            StringEntity body = new StringEntity(gson.toJson(msgRequest),"utf-8");
            body.setContentType("application/json");
            post.setEntity(body);

            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(post);

            if(response.getStatusLine().getStatusCode()==204)
            {
                System.out.println("Message Sent");
                refreshMessages();
            }

        }catch(Exception e){e.printStackTrace();}

    }

    @Override
    public void login(String userName) {
       try {

           String url_endpoint = URL + "/api/Chat/Login";
           HttpPost post = new HttpPost(url_endpoint);
           StringEntity body = new StringEntity("\""+userName+"\"","utf-8");
           body.setContentType("application/json");
           post.setEntity(body);

           CloseableHttpClient httpClient = HttpClients.createDefault();
           CloseableHttpResponse response = httpClient.execute(post);

           if(response.getStatusLine().getStatusCode()==200)
           {
               System.out.println("User Logged In");
               token = EntityUtils.toString(response.getEntity());
               token = token.replace("\"","").trim();
           }

       }catch(Exception e){e.printStackTrace();}

    }

    @Override
    public void logout() {
        try {

            String url_endpoint = URL + "/api/Chat/Logout";
            HttpPost post = new HttpPost(url_endpoint);
            StringEntity body = new StringEntity("\""+token+"\"","utf-8");
            body.setContentType("application/json");
            post.setEntity(body);

            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(post);

            if(response.getStatusLine().getStatusCode()==204)
            {
                System.out.println("User Logged Out");
                token = null;
                loggedUser = null;
                loggedUsers.clear();
                raiseEventLoggedUsersChanged();
            }

        }catch(Exception e){e.printStackTrace();}


    }

    @Override
    public boolean isAuthenticated() {
        boolean isAuthenticated = loggedUser != null;
        System.out.print("User logged: " + isAuthenticated);

        return isAuthenticated;

    }

    @Override
    public List<String> getLoggedUsers() {
        return loggedUsers;

    }

    @Override
    public List<Message> getMessages() {
        return messages;

    }

    @Override
    public void addActionListener(ActionListener toAdd) {
        listeners.add(toAdd);
    }
    private void raiseEventLoggedUsersChanged()
    {
        for(ActionListener al: listeners)
        {
            al.actionPerformed(new ActionEvent(this,1,"usersChanged"));
        }
    }
    private void raiseEventMessagesChanged()
    {
        for(ActionListener al: listeners)
        {
            al.actionPerformed(new ActionEvent(this,2,"messagesChanged"));
        }
    }

    private void addSystemMessage(int type, String username)
    {
        messages.add(new Message(type, username));
        raiseEventMessagesChanged();
    }


    private void refreshLoggedUsers()
    {
        try{

            String url = URL+"/api/Chat/getLoggedUsers";
            HttpGet get = new HttpGet(url);

            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(get);

            if(response.getStatusLine().getStatusCode() == 200)
            {
                String jsonResult = EntityUtils.toString(response.getEntity());
                loggedUsers = gson.fromJson(jsonResult, new TypeToken<ArrayList<String>>(){}.getType());
                raiseEventLoggedUsersChanged();
            }




        }catch(Exception e){}
    }

    private void refreshMessages()
    {
        try{

            String url = URL+"/api/Chat/getMessages";
            HttpGet get = new HttpGet(url);

            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(get);

            if(response.getStatusLine().getStatusCode() == 200)
            {
                String jsonResult = EntityUtils.toString(response.getEntity());
                messages = gson.fromJson(jsonResult, new TypeToken<ArrayList<Message>>(){}.getType());
                raiseEventMessagesChanged();
            }




        }catch(Exception e){}
    }
}
