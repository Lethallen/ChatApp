package models.GUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    JTextArea txtChatArea = new JTextArea();


    public MainFrame(int width, int height){
        super("ChatApp"); //defaultní title, SetTitle;
        setSize(width, height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initGUI();


        setVisible(true); //až jako poslední

    }

    private void initGUI(){
        JPanel panelMain = new JPanel(new BorderLayout());
        panelMain.add(initLoginPanel(), BorderLayout.NORTH);
        panelMain.add(initChatPanel(), BorderLayout.CENTER);
        panelMain.add(initMessagePanel(), BorderLayout.SOUTH);





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
                System.out.println("Button Clicked");
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



        return panel;

    }

    private JPanel initMessagePanel(){

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtInputMessage = new JTextField("", 50);
        panel.add(txtInputMessage);

        JButton btnSendMessage = new JButton("Send");
        btnSendMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Message sent: ");
                txtChatArea.append(txtInputMessage.getText() + "\n");
                txtInputMessage.setText("");
            }
        });
        panel.add(btnSendMessage);

        return panel;

    }







}
