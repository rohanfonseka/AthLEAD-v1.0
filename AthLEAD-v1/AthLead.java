import java.util.*;
import java.io.*;

// jsoup for remote login
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class AthLead extends Dovi {
    
  //////////////////////////////// instance vars /////////////////////////////////////////
  
  
  
  //////////////////////////////// methods ///////////////////////////////////////////////
  
  /**
   * attempts remote login onto IC; helper method for the Dovi.java class.
   * you may have to manually change the gradesView URL for it to work for your user specifically.
   * @param username and password in a string format.
   */
  public static ArrayList<Athlete> athletesInit() {
    
    try {
      
        final String url = "http://www.diablotiming.com/results/2019-04-27/track_trials.htm";
        
        String html = Jsoup.connect(url).get().html();
    
        List<String> event = new ArrayList<String>(Arrays.asList(html.split("Event")));
        event.remove(0);
        ArrayList<Athlete> athletes = new ArrayList<Athlete>();
        //System.out.println(event.get(event.size()-1));
        for (int eIndex = 0; eIndex < event.size()-2; eIndex++) {
            List<String> heat = new ArrayList<String>(Arrays.asList(event.get(eIndex).split("Heat")));
             //List<String> sections = new ArrayList<String>(Arrays.asList(heat.get(heat.size()-1).split("Section")));
            //System.out.println(heat.get(heat.size()-1));
            //System.out.println(heat.get(0));
            List<String> eventName = new ArrayList<String>(Arrays.asList(heat.get(0).split(" ")));
            
            String eventType = eventName.get(2) + " " + eventName.get(3) +" " +  eventName.get(4) + " " + eventName.get(5)+ " " + eventName.get(6) + " " + eventName.get(7);
            //System.out.println(eventType);
            heat.remove(0);
            heat.remove(0);
            //System.out.println(heat);
            for (int hIndex = 0; hIndex < heat.size();hIndex++){
                
                List<String> athleteInfo = new ArrayList<String>(Arrays.asList(heat.get(hIndex).split("\\r?\\n")));
                athleteInfo.remove(0);
                //ArrayList<String> oneAthlete = new ArrayList<String>(Arrays.asList(athleteInfo.get(1).split(" ")));
                //System.out.println(athleteInfo);
                
                //System.out.println(oneAthlete);
                
                for (int j = 0; j < athleteInfo.size()-1; j++) {
                    //System.out.println(athleteInfo.get(j));
                    ArrayList<String> oneAthlete = new ArrayList<String>(Arrays.asList(athleteInfo.get(j).split(" ")));
                    for (int i = oneAthlete.size()-1; i >=0 ; i--){
                        if (oneAthlete.get(i).equals("")){
                            oneAthlete.remove(i);
                        }
                        else {
                            //System.out.println(oneAthlete.get(i));
                        }
                    }
                    //System.out.println(oneAthlete);
                    //for (){
                        
                        String tempname = (oneAthlete.get(2)+ " " +oneAthlete.get(1)).toLowerCase();
                        String name = tempname.substring(0,tempname.length()-1);
                        String school = oneAthlete.get(4);
                        String grade = oneAthlete.get(3);
                        String seed = oneAthlete.get(0);
                        athletes.add(new Athlete(name, school, grade, seed,eventType));
                    //}
                    
                   // System.out.println(athletes.get(j));
                //System.out.println(athletes.get(j) + "\n");
            
                }   
            }
        }
        return athletes;
        
    }
     catch (IOException e) {
      e.printStackTrace();
      return new ArrayList<Athlete>();
    }
    
        
  }
  public static void checkIn(String searchName, ArrayList <Athlete> athletes) {
      int flag = 0;
        for (Athlete a: athletes){
            
            searchName = searchName.toLowerCase();
            if (searchName.equals(a.getName())){
               
                a.setCheckedIn(true);
                
                txtChat.append("\n     "+ searchName.toUpperCase() + " is now checked in for" + a.getEvent() + "\n     "+" with a sticker number of " + a.getSeeding()+ "\n" );
                flag = 1;
            }
        }
        if (flag == 0 ){
            txtChat.append("\n     "+ searchName.toUpperCase() +" is not registered for an event"+"\n");
        }
        
        
    }
    public static void printPaper(String eventName,ArrayList <Athlete> athletes){
        System.out.println(eventName + ": ");
        for (Athlete a: athletes){
            
            if (a.getCheckedIn() && (a.getEvent().equals(eventName))){
                
                txtChat.append(a.getSeeding() + ". " +a.getName());
                
           } else if ((a.getEvent().equals(eventName))){
               if (!a.getCheckedIn()){
                   txtChat.append("NOT CHECKED IN: " +a.getSeeding() + ". " +a.getName());
                }
           }
       }
    }
}