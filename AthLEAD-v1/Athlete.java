import java.util.ArrayList;
/**
 * Write a description of class Athlete here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Athlete
{
 // instance variables - replace the example below with your own
private String school;
private String name;
 private  String grade;
 private  String seeding;
 private String event;
 private boolean checkedIn;

 /**
  * Constructor for objects of class Athlete
  */
 public Athlete(String n, String sch,  String g,  String seed, String e)
 {
  // initialise instance variables
  name = n;
  school = sch;
  grade = g;
  seeding = seed;
  event = e;
  checkedIn = false;
 }

 public String getName() {
  return name;
 }
 
 public String getSchool() {
  return school;
 }
 
 public String getGrade() {
  return grade;
 }
 
 public  String getSeeding() {
  return seeding;
 }
 public  String getEvent() {
  return event;
 }
  public  void setCheckedIn(boolean b) {
   checkedIn = b;
 }
 public  boolean getCheckedIn() {
   return checkedIn;
 }
 
 
 public String toString() {
     return "name: " + name + "\n"
     + "school: " + school + "\n"
     + "grade: " + grade + "\n"
     + "seed: " + seeding + "\n"
     + "event: " + event + "\n";
    }
}