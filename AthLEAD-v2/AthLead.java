import java.util.*;
import java.io.*;

// jsoup for remote login
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class AthLead extends AthLeadGUIRunner {
    
    //////////////////////////////// instance vars /////////////////////////////////////////
  
    private static boolean DEBUGGING = true;
  
    //////////////////////////////// methods ///////////////////////////////////////////////
  
    /**
     * attempts remote login onto IC; helper method for the athLeadGUIRunner.java class.
     * you may have to manually change the gradesView URL for it to work for your user specifically.
     * @param username and password in a string format.
     */
    public static ArrayList<Athlete> athletesInit() {
        try {
            //declares the target URL page
            final String url = "http://www.diablotiming.com/results/2019-04-27/track_trials.htm";
            
            //using jsoup, html string values are pulled and returned into String html
            String html = Jsoup.connect(url).get().html();
            
            //creates athletes arraylist
            ArrayList<Athlete> athletes = new ArrayList<Athlete>();
            
            //creates an arraylist of Strings separated by keyword "event"
            List<String> events = new ArrayList<String>(Arrays.asList(html.split("Event")));
            //removes first section because it is useless (hardcoded)
            events.remove(0);
            
            //cleans up end of last String in event
            String lastEventRaw = events.get(events.size()-1);
            int indexCutoff = -1;
            for (int i = lastEventRaw.length()-1; i >= 0; i--) {
                if (lastEventRaw.charAt(i) == '<')
                    indexCutoff = i;
            }
            String lastEvent = lastEventRaw.substring(0, indexCutoff);
            events.set(events.size()-1, lastEvent);
            if (DEBUGGING) {
                //System.out.println("first event: " + event.get(0));
                //System.out.println("last event: " + lastEventRaw);
                //System.out.println("last event substring: " + event.get(0));
                //System.out.println("length of string: " + lastEventRaw.length());
            }
            
            /*
             * note below, for loop discludes last two events because they are split into
            heats by the word "section" (hardcoded/future improvement)
            */
            for (int eIndex = 0; eIndex < events.size()-2; eIndex++) {
                //for every event, a temporary arraylist of heats is created, divided by keyword "heat"
                List<String> heats = new ArrayList<String>(Arrays.asList(events.get(eIndex).split("Heat")));
                
                //gets the event's name (hardcoded)
                List<String> eventName = new ArrayList<String>(Arrays.asList(heats.get(0).split(" ")));
                String eventType = 
                    eventName.get(2) + " " //" "?
                    + eventName.get(3) +" " //girls
                    + eventName.get(4) + " " //1600
                    + eventName.get(5)+ " " //meter
                    + eventName.get(6) + " " //run
                    + eventName.get(7); //frosh/soph
                
                String shortEventType = 
                    (eventName.get(3) +
                    eventName.get(4) +
                    eventName.get(7).substring(0,1) +
                    eventName.get(7).substring(6,7)).toLowerCase();
                    
                //removes first two sections divided by "heat" because they are useless now
                heats.remove(0);
                heats.remove(0);
                
                //runs through each valid section in "heats"
                 for (int hIndex = 0; hIndex < heats.size();hIndex++){
                    //within each heat, temp arraylist of Strings created, splitting
                    //sections by the new line keyword, each one storing athlete info
                    List<String> athleteInfo = new ArrayList<String>(Arrays.asList(heats.get(hIndex).split("\\r?\\n")));
                    
                    //removes the first element, as it is not useful (hardcoded)
                    athleteInfo.remove(0);
                    
                    //runs through each line known as athleteInfo,
                    //discluding the last index (hardcoded)
                    for (int row = 0; row < athleteInfo.size()-1; row++) {
                        //creates string split by " "
                        ArrayList<String> columnData = new ArrayList<String>(Arrays.asList(athleteInfo.get(row).split(" ")));
                        
                        //runs through columnData deleting all empty elements
                        for (int col = columnData.size()-1; col >=0 ; col--){
                             if (columnData.get(col).equals(""))
                                 columnData.remove(col);
                        }
                        
                        //joins first and last name with space in between
                        String tempname = (columnData.get(2)+ " " +columnData.get(1)).toLowerCase();
                        
                        //pulls data from respective column, storing it in respective
                        //variable names
                        String name = tempname.substring(0,tempname.length()-1);
                        String school = columnData.get(4);
                        String grade = columnData.get(3);
                        String seed = columnData.get(0);
                        
                        //creates new Athlete instance given variables above pulled from data
                        //appends new Athlete to athletes arraylist
                        athletes.add(new Athlete(name, school, grade, seed,eventType,shortEventType));
                    }   
                }
            }
            return athletes;
        } catch (IOException e) {
            e.printStackTrace();
            //if there's an exception, as in the url doesn't lead anywhere,
            //it will return an empty arraylist
            return new ArrayList<Athlete>();
        }
    }
    public static void checkIn(String searchName, ArrayList <Athlete> athletes) {
        boolean flagCheckedIn = false;
        
        //runs through initialized athletes arraylist and
        for (Athlete a: athletes){
            //standardizes casing to avoid human input errors
            searchName = searchName.toLowerCase();
            //checks to see if input matches a name already in the database
            if (searchName.equals(a.getName()) && !a.checkedIn()){
                //chenges Athlete's status to checked in
                a.setCheckedIn(true);
                //prints out confirmation statement with sticker number
                txtChat.append("\n     "+ searchName.toUpperCase() + " is now checked in for" + a.getEvent() + "\n     "+" with a sticker number of " + a.getSeeding()+ "\n" );
                //sets temporary flag to true; able to call it outside for loop
                flagCheckedIn = true;
                
            } else if (searchName.equals(a.getName()) && a.checkedIn()) {
                //if already checked in, prints respective statement
                txtChat.append("\n     "+ searchName.toUpperCase() +" is already checked in for the" + a.getEvent() + "\n");
                
                //sets temporary flag to true; able to call it outside for loop
                flagCheckedIn = true;
            }
        }
        //if the athlete's name doesn't appear in the database, prints denial message
        if (!flagCheckedIn){
            txtChat.append("\n     "+ searchName.toUpperCase() +" is not registered for an event"+"\n");
        }
    }
    public static void printHeat(String input, ArrayList <Athlete> athletes){
        if (DEBUGGING) {
            //txtChat.append("input: " + input + "\n");
            //txtChat.append();
        }
        for (Athlete a: athletes){
            String inputKey = input.substring(6);
            String eventKey = a.getShortenedEvent().toLowerCase();
            if (DEBUGGING) {
                //txtChat.append("\"" + eventKey + "\"" + " = " + "\"" + inputKey + "\"" + "\n");
                //txtChat.append("confirmed");
            }
            if (inputKey.equals(eventKey)) {
                txtChat.append(" Event: " + a.getEvent() + "     Checked in: " + a.checkedIn() + "     Name: " + a.getName().toUpperCase() + "\n\n");
            }
            
            //txtChat.append(a.getName() + "     Checked in: " + a.checkedIn());
            /*
            if (a.checkedIn() && (a.getEvent().substring(0,5).toLowerCase().equals(eventName))){
                txtChat.append(a.getSeeding() + ". " +a.getName());
            } else if (!a.checkedIn() && a.getEvent().equals(eventName)){
                txtChat.append("NOT CHECKED IN: " +a.getSeeding() + ". " +a.getName());
            }
            */
        }
    }
    public static void printPaper(ArrayList<Athlete> athletes) {
        String tempEvent = "";
        for (int i = 0; i < athletes.size(); i++){
            if (!tempEvent.equals(athletes.get(i).getEvent())) {
                txtChat.append(" ---------------------------------\n\n");
            }
            tempEvent = athletes.get(i).getEvent();
            txtChat.append(" Event: " + tempEvent + "     Checked in: " + athletes.get(i).checkedIn() + "     Name: " + athletes.get(i).getName().toUpperCase() + "\n\n");
        }
    }
}