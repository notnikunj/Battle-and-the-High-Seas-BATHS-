package wars; 


/**
 * Details of your team
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Teamwork
{
    private String[] details = new String[12];
    
    public Teamwork()
    {   // in each line replace the contents of the String 
        // with the details of your team member
        // Please list the member details alphabetically by surname 
        // i.e. the surname of member1 should come alphabetically 
        // before the surname of member 2...etc
        details[0] = "CS50";
        
        details[1] = "Bhavsar";
        details[2] = "Nikunj";
        details[3] = "22018537";

        details[4] = "Dhola";
        details[5] = "Meet";
        details[6] = "21071764";

        details[7] = "Mandaviya";
        details[8] = "Aryan";
        details[9] = "21059577";


        details[10] = "Raval";
        details[11] = "Apurva";
        details[12] = "21083257";

	
	


    }
    
    public String[] getTeamDetails()
    {
        return details;
    }
    
    public void displayDetails()
    {
        for(String temp:details)
        {
            System.out.println(temp.toString());
        }
    }
}
        
