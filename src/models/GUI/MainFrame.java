package models.GUI;


import models.Message;
import models.chatClients.ChatClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//ID1 = změna lognutých uživatelů, ID2 = změna zpráv

public class MainFrame extends JFrame {
    JTextArea txtChatArea = new JTextArea();
    private ChatClient chatClient;
    private JTextField txtInputMessage;
    private LoggedUsersTableModel loggedUsersTableModel = new LoggedUsersTableModel(chatClient);

    public MainFrame(int width, int height, ChatClient chatClient){

        super("ChatApp"); //defaultní title, SetTitle;
        setSize(width, height);
        this.chatClient = chatClient;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initGUI();


        setVisible(true); //až jako poslední

    }

    private void initGUI(){
        JPanel panelMain = new JPanel(new BorderLayout());
        panelMain.add(initLoginPanel(), BorderLayout.NORTH);
        panelMain.add(initChatPanel(), BorderLayout.CENTER);
        panelMain.add(initMessagePanel(), BorderLayout.SOUTH);
        panelMain.add(initLoggedUsers(), BorderLayout.EAST);





        add(panelMain);
    }

    private JPanel initLoginPanel(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel("Username"));
        JTextField txtInputUserName = new JTextField("", 50);
        panel.add(txtInputUserName);


        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = txtInputUserName.getText();
                System.out.println("Button Clicked " + userName);
                if(chatClient.isAuthenticated())
                {
                    chatClient.logout();
                    btnLogin.setText("Login");
                    txtInputUserName.setEditable(true);
                    txtInputMessage.setEditable(false);
                    txtChatArea.setEnabled(false);


                }
                else
                {
                    if(userName.length()<1)
                    {
                        JOptionPane.showMessageDialog(null, "Enter your username", "Error", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    chatClient.login(userName);
                    btnLogin.setText("Logout");
                    txtInputUserName.setEditable(false);
                    txtInputMessage.setEnabled(true);
                    txtChatArea.setEnabled(true);
                }

            }
        });
        panel.add(btnLogin);


        return panel;

    }

    private JPanel initChatPanel(){

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        txtChatArea = new JTextArea();
        txtChatArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(txtChatArea);

        panel.add(txtChatArea);
        panel.add(scrollPane);


        chatClient.addActionListener(e -> {
            if(e.getID()==2)
            {
                refreshMessages();
            }
        });
        chatClient.addActionListener(e -> {
            if(e.getID()==1)
            {
                loggedUsersTableModel.fireTableDataChanged();
            }
        });
        return panel;

    }

    private JPanel initMessagePanel(){

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtInputMessage = new JTextField("", 50);
        txtInputMessage.setEnabled(false);
        panel.add(txtInputMessage);

        JButton btnSendMessage = new JButton("Send");
        btnSendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Message sent: ");
                String msgText = txtInputMessage.getText();
                //txtChatArea.append(msgText + "\n");
                if(msgText.length()<1)
                {return;}
                if(!chatClient.isAuthenticated())
                {return;}
                chatClient.sendMessage(msgText);
                txtInputMessage.setText("");
                //refreshMessages(); tady to neřešit, jinak by to mohlo hodit null exception
            }
        });
        panel.add(btnSendMessage);

        return panel;

    }

    private JPanel initLoggedUsers()
    {
        /*
        Object[][] data = new Object[][]{
                {"O,O", "1,1"},
                {"2,2", "3,3"}
        };
        String [] colNames = new String[] {"Col1", "Col 2"};

         */
        // JTable tblLoggedUsers = new JTable(data, colNames);


        JPanel panel = new JPanel();
        JTable tblLoggedUsers = new JTable();
        loggedUsersTableModel = new LoggedUsersTableModel(chatClient);
        tblLoggedUsers.setModel(loggedUsersTableModel);
        JScrollPane scrollPane = new JScrollPane(tblLoggedUsers);
        scrollPane.setPreferredSize(new Dimension(250,500));
        panel.add(scrollPane);




        return panel;
    }


    private void refreshMessages (){
        if(!chatClient.isAuthenticated())return;
        txtChatArea.setText("");
        for(Message msg:
            chatClient.getMessages()){
            txtChatArea.append(msg.toString());
            txtChatArea.append("\n");
        }
    }





}
