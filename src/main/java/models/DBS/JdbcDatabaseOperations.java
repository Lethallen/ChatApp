package models.DBS;

import models.Message;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class JdbcDatabaseOperations implements DatabaseOperationsInterface{
    private final Connection connection;
    public JdbcDatabaseOperations (String driver, String url) throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        this.connection = DriverManager.getConnection(url);
    }

    @Override
    public void addMessage(Message message) {
        try{
            String sql="INSERT INTO ChatMessages (author, text, created)" + "VALUES (" + "'" + message.getUsername() + "'," + "'" + message.getText() + "'," + message.getCreated() +
            ")";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
        }catch(Exception e){}
    }

    @Override
    public List<Message> getMessages() {
        return null;
    }
}
