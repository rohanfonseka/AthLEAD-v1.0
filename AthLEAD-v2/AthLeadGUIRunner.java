import java.util.*;
import java.io.*;

// gui
import javax.swing.*;
import java.awt.event.*;

public class AthLeadGUIRunner extends JFrame {

    //////////////////////////////// instance vars /////////////////////////////////////////

    public static JPanel panel = new JPanel(); //creates panel
    public static JTextField eventEnter = new JTextField(); // typing area
    public static JTextField heatEnter = new JTextField(); // typing area
    public static JTextField checkInEnter = new JTextField(); // typing area
    public static JTextArea txtChat; // chat area
    public static JScrollPane scroll = new JScrollPane(); // allows scrolling
    public static JButton checkIn = new JButton(); // check in button
    public static JButton printBtn = new JButton(); // print button (given event and heat)
    public String user = ""; // private variable to store the username
    public String pass = ""; // private variable to store the password
    private String rv = ""; // return value for method
    private ArrayList<Athlete> athletes;
    
    private static boolean DEBUGGING = true;

    ///////////////////////////////// ctors ////////////////////////////////////////////////

    /**
     * default constuctor, creates GUI environment and checks if text is entered.
     */
    public AthLeadGUIRunner() {
        // general gui config
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 700);
        this.setVisible(true);
        this.setResizable(true);
        this.setLayout(null);
        this.setTitle("AthLEAD");
        this.getContentPane().setBackground(new java.awt.Color(194, 24, 6));
        
        //initializes JTextArea object txtChat
        initTxtChat();
        
        //initializes JTextField object checkInEnter
        checkInEnter.setBackground(new java.awt.Color(255, 180 , 0));
        checkInEnter.setLocation(15, 440);
        checkInEnter.setSize(660, 40);
        this.add(checkInEnter);
        
        //initializes JButton object checkIn
        checkIn.setLocation(15, 460);
        checkIn.setSize(200, 40);
        checkIn.setText("Check In Athlete");
        this.add(checkInEnter);
        
        // initial text/instructions
        ArrayList<Athlete> athletes = AthLead.athletesInit();
        txtChat.append(" Welcome to AthLead's Self Check-In Program!!\n\n");
        txtChat.append("    Instructions:\n");
        txtChat.append("         To check in, type your first and last name (Ex: John Doe)\n");
        txtChat.append("         To view all athletes in an event, type \"print\" followed by the event name  (Ex: print boys1600fs)\n"
         + "             * Note: varsity code is \"vy\" and frosh soph code is \"fs\"\n");
        txtChat.append("         To view all athletes in the meet, type \"print\"\n\n");
        
        
        /*// My code
        JFrame frame = new JFrame ();
        frame.add (this);
        frame.pack ();
        frame.setLocationRelativeTo ( null );
        frame.setVisible ( true );*/
        
        
        
        // chat entered
        checkInEnter.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
            
            String uText = checkInEnter.getText().toLowerCase();
            if (DEBUGGING) {
                //txtChat.append("inputText: " + uText + "\n");
            }
            if (uText.length() >= 5 && (uText.substring(0,5).equals("girls"))
            || (uText.length() >= 4 && uText.substring(0,4).equals("boys"))){
                AthLead.printHeat(uText,athletes);
                
            } else if (uText.indexOf("print girls") != -1
            || uText.indexOf("print boys") != -1) {
                AthLead.printHeat(uText,athletes);
                
            } else if (uText.indexOf("print") != -1) {
                if (uText.equals("print")) {
                    AthLead.printPaper(athletes);
                } else {
                    txtChat.append("");
                }
            } else if (uText.equals("clear")) {
                clearTxtChat();
                txtChat.append(" Welcome to AthLead's Self Check-In Program!!\n\n");
                txtChat.append("    Instructions:\n");
                txtChat.append("         To check in, type your first and last name (Ex: John Doe)\n");
                txtChat.append("         To view all athletes in an event, type \"print\" followed by the event name  (Ex: print boys1600fs)\n"
                    + "             * Note: varsity code is \"vy\" and frosh soph code is \"fs\"\n");
                txtChat.append("         To view all athletes in the meet, type \"print\"\n\n");
            } else {
                AthLead.checkIn(uText, athletes);
                
            }
            checkInEnter.setText("");
        }
           
        });
    }

    ////////////////////////////////// methods /////////////////////////////////////////////
    
    public void initTxtChat() {
        txtChat = new JTextArea();
        txtChat.setBackground(new java.awt.Color(200, 200 , 200));
        txtChat.setLocation(15, 15);
        txtChat.setSize(660, 380);
        txtChat.setEditable(false);
        scroll = new JScrollPane(txtChat);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setBounds(15, 15, 675, 395);
        this.add(scroll);
    }
    
    public void clearTxtChat() {
        txtChat.setText("");
    }
}