import java.util.*;
import java.io.*;

// gui
import javax.swing.*;
import java.awt.event.*;

public class Dovi extends JFrame {

    //////////////////////////////// instance vars /////////////////////////////////////////

    public static JTextField txtEnter = new JTextField(); // typing area
    public static JTextArea txtChat = new JTextArea(); // chat area
    public String user = ""; // private variable to store the username
    public String pass = ""; // private variable to store the password
    private String rv = ""; // return value for method
    private ArrayList<Athlete> athletes;

    ///////////////////////////////// ctors ////////////////////////////////////////////////

    /**
     * default constuctor, creates GUI environment and checks if text is entered.
     */
    public Dovi() {

        // general gui config
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 500);
        this.setVisible(true);
        this.setResizable(false);
        this.setLayout(null);
        this.setTitle("AthLEAD");
        this.getContentPane().setBackground(new java.awt.Color(194, 24, 6));
        txtEnter.setBackground(new java.awt.Color(255, 180 , 0));
        txtChat.setBackground(new java.awt.Color(200, 200 , 200));

        txtEnter.setLocation(15, 420);
        txtEnter.setSize(560, 40 );
        txtChat.setLocation(15, 15);
        txtChat.setSize(560, 380);
        txtChat.setEditable(false);

        this.add(txtEnter);
        this.add(txtChat);
        
        // initial text/instructions
        ArrayList<Athlete> athletes = AthLead.athletesInit();
        txtChat.append("Welcome to AthLead's Self Check-In Program\n");
        txtChat.append("Please type your first and last name to check in!\n");
        
        
        
        // chat entered
        txtEnter.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
            
            String uText = txtEnter.getText();
            if (uText.substring(0,5).equals("Girls") || uText.substring(0,4).equals("Boys")){
                AthLead.printPaper(uText,athletes);
                txtEnter.setText("");
            }
            
            else {
                AthLead.checkIn(uText, athletes);
                txtEnter.setText("");
            }
           
            
            }
           
        });
    }

    ////////////////////////////////// methods /////////////////////////////////////////////
    
    

    
    
}