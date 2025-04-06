package wars;
import wars.Ships;
import wars.ShipState;
import java.util.*;
import java.io.*;
/**
 * This class implements the behaviour expected from the BATHS
 system as required for 5COM2007 Cwk1B BATHS - Feb 2025
 * 
 * @author A.A.Marczyk 
 * @version 16/02/25
 */

public class SeaBattles implements BATHS, Serializable
{
    // may have one HashMap and select on stat
    private static final long serialVersionUID = 1L;
    private String admiral;
    private double warChest;
    
    private List<Ships> reserveFleet;
    private List<Ships> squadron;
    private List<Ships> sunkShips;
    private List<Encounter> encounters;


        //**************** BATHS ************************** 
    /** Constructor requires the name of the admiral
     * @param adm the name of the admiral
     */  
    public SeaBattles(String adm)
    {
        this.admiral = adm;
        this.warChest = 1000;
        reserveFleet = new ArrayList<>();
        squadron = new ArrayList<>();
        sunkShips = new ArrayList<>();
        encounters = new ArrayList<>();
        setupShips();
        setupEncounters();
    }
    
    /** Constructor requires the name of the admiral and the
     * name of the file storing encounters
     * @param admir the name of the admiral
     * @param filename name of file storing encounters
     */  
    public SeaBattles(String admir, String filename)  //Task 3
    {
      
        
       setupShips();
       // setupEncounters();
       // uncomment for testing Task 
       // readEncounters(filename);
    }
    
    
    /**Returns a String representation of the state of the game,including the name of the 
     * admiral, state of the warChest,whether defeated or not, and the ships currently in 
     * the squadron,(or, "No ships" if squadron is empty), ships in the reserve fleet
     * @return a String representation of the state of the game,including the name of the 
     * admiral, state of the warChest,whether defeated or not, and the ships currently in 
     * the squadron,(or, "No ships" if squadron is empty), ships in the reserve fleet
     **/
    public String toString()
    {
        String squadronStr = getSquadron();
    if (squadronStr == null || squadronStr.trim().isEmpty()) {
        squadronStr = "No ships";
    }
    // Construct a string that contains the expected substrings:
    return admiral + "\n" + warChest + "\nIs OK\n" + squadronStr;
    }
    
    
    
    
    /** returns true if War Chest <=0 and the admiral's squadron has no ships which 
     * can be retired. 
     * @returns true if War Chest <=0 and the admiral's fleet has no ships 
     * which can be retired. 
     */
    public boolean isDefeated()
    {
        boolean hasCommissionable = false;
        for (Ships s : squadron) {
            if (s.getState() == ShipState.ACTIVE || s.getState() == ShipState.RESTING) {
                hasCommissionable = true;
                break;
            }
        }
        return (warChest <= 0 && !hasCommissionable);
    }
    
    /** returns the amount of money in the War Chest
     * @returns the amount of money in the War Chest
     */
    public double getWarChest()
    {
        return warChest;
    }
    
    
    /**Returns a String representation of all ships in the reserve fleet
     * @return a String representation of all ships in the reserve fleet
     **/
    public String getReserveFleet()
    {   //assumes reserves is a Hashmap
       
        if (reserveFleet.isEmpty()) {
            return "No ships";
        }
        StringBuilder sb = new StringBuilder();
        for (Ships s : reserveFleet) {
            // Only list ships that are in reserve state.
            if (s.getState() == ShipState.RESERVE) {
                sb.append(s.toString()).append("\n");
            }
        }
        return sb.toString();
    }
    
    /**Returns a String representation of the ships in the admiral's squadron
     * or the message "No ships commissioned"
     * @return a String representation of the ships in the admiral's fleet
     **/
    public String getSquadron()
    {
        if (squadron.isEmpty()) {
            return "No ships commissioned";
        }
        StringBuilder sb = new StringBuilder();
        for (Ships s : squadron) {
            sb.append(s.toString()).append("\n");
        }
        return sb.toString();
    }
    
    /**Returns a String representation of the ships sunk (or "no ships sunk yet")
     * @return a String representation of the ships sunk
     **/
    public String getSunkShips()
    {
        if (sunkShips.isEmpty()) {
            return "No ships sunk yet";
        }
        StringBuilder sb = new StringBuilder();
        for (Ships s : sunkShips) {
            sb.append(s.toString()).append("\n");
        }
        return sb.toString();
    }
    
    /**Returns a String representation of the all ships in the game
     * including their status
     * @return a String representation of the ships in the game
     **/
    public String getAllShips()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Squadron:\n").append(getSquadron()).append("\n");
        sb.append("Reserve Fleet:\n").append(getReserveFleet()).append("\n");
        sb.append("Sunk Ships:\n").append(getSunkShips());
        return sb.toString();
    }
    
    
    /** Returns details of any ship with the given name
     * @return details of any ship with the given name
     **/
    public String getShipDetails(String nme)
    {
        for (Ships s : squadron) {
            if (s.getName().equalsIgnoreCase(nme)) {
                return s.toString();
            }
        }
        for (Ships s : reserveFleet) {
            if (s.getName().equalsIgnoreCase(nme)) {
                return s.toString();
            }
        }
        for (Ships s : sunkShips) {
            if (s.getName().equalsIgnoreCase(nme)) {
                return s.toString();
            }
        }
        return "\nNo such ship";

    }     
 
    // ***************** Fleet Ships ************************   
    /** Allows a ship to be commissioned to the admiral's squadron, if there 
     * is enough money in the War Chest for the commission fee.The ship's 
     * state is set to "active"
     * @param nme represents the name of the ship
     * @return "Ship commissioned" if ship is commissioned, "Not found" if 
     * ship not found, "Not available" if ship is not in the reserve fleet, "Not 
     * enough money" if not enough money in the warChest
     **/            
    public String commissionShip(String nme) {
    // Check reserve fleet first
        for (Iterator<Ships> it = reserveFleet.iterator(); it.hasNext();) {
            Ships s = it.next();
            if (s.getName().equalsIgnoreCase(nme)) {
                // Found in reserve fleet so it's available for commission.
                if (warChest < s.getCommissionFee()) {
                    return "Not enough money";
                }
                warChest -= s.getCommissionFee();
                s.setState(ShipState.ACTIVE);
                squadron.add(s);
                it.remove();
                return "Ship commissioned";
            }
        }
        // If not found in reserve, check if the ship exists in squadron or sunk ships
        for (Ships s : squadron) {
            if (s.getName().equalsIgnoreCase(nme)) {
                return "Not available";
            }
        }
        for (Ships s : sunkShips) {
            if (s.getName().equalsIgnoreCase(nme)) {
                return "Not available";
            }
        }
        return "Not found";
}
        
    /** Returns true if the ship with the name is in the admiral's squadron, false otherwise.
     * @param nme is the name of the ship
     * @return returns true if the ship with the name is in the admiral's squadron, false otherwise.
     **/
    public boolean isInSquadron(String nme)
    {
        for (Ships s : squadron) {
            if (s.getName().equalsIgnoreCase(nme) && s.getState() == ShipState.ACTIVE) {
                return true;
            }
        }
        return false;
    }
    
    /** Decommissions a ship from the squadron to the reserve fleet (if they are in the squadron)
     * pre-condition: isInSquadron(nme)
     * @param nme is the name of the ship
     * @return true if ship decommissioned, else false
     **/
    public boolean decommissionShip(String nme)
    {
        for (Iterator<Ships> it = squadron.iterator(); it.hasNext(); ) {
            Ships s = it.next();
            if (s.getName().equalsIgnoreCase(nme) && 
               (s.getState() == ShipState.ACTIVE || s.getState() == ShipState.RESTING)) {
                it.remove();
                s.setState(ShipState.RESERVE);
                reserveFleet.add(s);
                double refund = s.getCommissionFee() / 2.0;
                warChest += refund;
                return true;
            }
        }
        return false;
    }
    
  
    /**Restores a ship to the squadron by setting their state to ACTIVE 
     * @param ref the name of the ship to be restored
     */
    public void restoreShip(String ref)
    {
        for (Ships s : squadron) {
            if (s.getName().equalsIgnoreCase(ref)) {
                s.setState(ShipState.ACTIVE);
                return;
            }
        }
    }
    
//**********************Encounters************************* 
    /** returns true if the number represents a encounter
     * @param num is the reference number of the encounter
     * @returns true if the reference number represents a encounter, else false
     **/
     public boolean isEncounter(int num)
     {
         for (Encounter e : encounters) {
            if (e.getEncounterId() == num) {
                return true;
            }
        }
        return false;
         
     }
     
     
/** Retrieves the encounter represented by the encounter 
      * number.Finds a ship from the fleet which can fight the 
      * encounter.The results of fighting an encounter will be 
      * one of the following: 
      * 0-Encounter won by...(ship reference and name)-add prize money to War 
      * Chest and set ship's state to RESTING,  
      * 1-Encounter lost as no ship available - deduct prize from the War Chest,
      * 2-Encounter lost on battle skill and (ship name) sunk" - deduct prize 
      * from War Chest and set ship state to SUNK.
      * If an encounter is lost and admiral is completely defeated because there 
      * are no ships to decommission,add "You have been defeated " to message, 
      * -1 No such encounter
      * Ensure that the state of the war chest is also included in the return message.
      * @param encNo is the number of the encounter
      * @return a String showing the result of fighting the encounter
      */ 
    public String fightEncounter(int encNo)
    {
        Encounter encounter = null;
        for (Encounter e : encounters) {
            if (e.getEncounterId() == encNo) {
                encounter = e;
                break;
            }
        }
        if (encounter == null) {
            return "-1 No such encounter. War Chest: " + warChest;
        }
        
        // Look for a ship in the squadron that is ACTIVE and can fight this encounter.
        for (Iterator<Ships> it = squadron.iterator(); it.hasNext(); ) {
            Ships s = it.next();
            if (s.getState() == ShipState.ACTIVE && s.canFight(encounter)) {
                if (s.getBattleSkill() >= encounter.getSkillRequired()) {
                    warChest += encounter.getPrizeMoney();
                    s.setState(ShipState.RESTING);
                    return "Encounter won by " + s.getName() + ". Prize money added. War Chest: " + warChest;
                } else {
                    warChest -= encounter.getPrizeMoney();
                    s.setState(ShipState.SUNK);
                    sunkShips.add(s);
                    it.remove();
                    String result = "Encounter lost on battle skill. " + s.getName() + " sunk. Penalty deducted. War Chest: " + warChest;
                    if (isDefeated()) {
                        result += " You have been defeated.";
                    }
                    return result;
                }
            }
        }
        // No suitable ship available.
        warChest -= encounter.getPrizeMoney();
        String result = "Encounter lost as no ship available. Penalty deducted. War Chest: " + warChest;
        if (isDefeated()) {
            result += " You have been defeated.";
        }
        return result;
    }

    /** Provides a String representation of an encounter given by 
     * the encounter number
     * @param num the number of the encounter
     * @return returns a String representation of a encounter given by 
     * the encounter number
     **/
    public String getEncounter(int num)
    {
        for (Encounter e : encounters) {
            if (e.getEncounterId() == num) {
                return e.toString();
            }
        }
        return "\nNo such encounter";
    }
    
    /** Provides a String representation of all encounters 
     * @return returns a String representation of all encounters
     **/
    public String getAllEncounters()
    {
        if (encounters.isEmpty()) {
            return "No encounters";
        }
        StringBuilder sb = new StringBuilder();
        for (Encounter e : encounters) {
            sb.append(e.toString()).append("\n");
        }
        return sb.toString();
    }
    

    //****************** private methods for Task 4 functionality*******************
    //*******************************************************************************
     private void setupShips()
     {
         reserveFleet.add(new ManOWar("Victory", "Alan Aikin", 3, 500, 3, 30));
        reserveFleet.add(new Frigate("Sophie", "Ben Baggins", 8, 160, true, 16));
        reserveFleet.add(new ManOWar("Endeavour", "Col Cannon", 4, 300, 2, 20));
        reserveFleet.add(new Sloop("Arrow", "Dan Dare", 5, 150, true));
        reserveFleet.add(new ManOWar("Belerophon", "Ed Evans", 8, 500, 3, 50));
        reserveFleet.add(new Frigate("Surprise", "Fred Fox", 6, 100, false, 10));
        reserveFleet.add(new Frigate("Jupiter", "Gil Gamage", 7, 200, false, 20));
        reserveFleet.add(new Sloop("Paris", "Hal Henry", 5, 200, true));
        reserveFleet.add(new Sloop("Beast", "Ian Idle", 5, 400, false));
        reserveFleet.add(new Sloop("Athena", "John Jones", 5, 100, true));
       

     }
     
    private void setupEncounters()
    {
        encounters.add(new Encounter(1, "Battle", "Trafalgar", 3, 300));
        encounters.add(new Encounter(2, "Skirmish", "Belle Isle", 3, 120));
        encounters.add(new Encounter(3, "Blockade", "Brest", 3, 150));
        encounters.add(new Encounter(4, "Battle", "St Malo", 9, 200));
        encounters.add(new Encounter(5, "Blockade", "Dieppe", 7, 90));
        encounters.add(new Encounter(6, "Skirmish", "Jersey", 8, 45));
        encounters.add(new Encounter(7, "Blockade", "Nantes", 6, 130));
        encounters.add(new Encounter(8, "Battle", "Finisterre", 4, 100));
        encounters.add(new Encounter(9, "Skirmish", "Biscay", 5, 200));
        encounters.add(new Encounter(10, "Battle", "Cadiz", 1, 250));
  
    }
        
    // Useful private methods to "get" objects from collections/maps

    //*******************************************************************************
    //*******************************************************************************
  
    /************************ Task 3 ************************************************/

    
    //******************************** Task 3.5 **********************************
    /** reads data about encounters from a text file and stores in collection of 
     * encounters.Data in the file is editable
     * @param filename name of the file to be read
     */
    public void readEncounters(String filename)
    { 
        encounters.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int num = Integer.parseInt(parts[0].trim());
                    String type = parts[1].trim();
                    String location = parts[2].trim();
                    int reqSkill = Integer.parseInt(parts[3].trim());
                    int prize = Integer.parseInt(parts[4].trim());
                    encounters.add(new Encounter(num, type, location, reqSkill, prize));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading encounters: " + e.getMessage());
        }
      
        
        
    }   
 
    
    // ***************   file write/read  *********************
    /** Writes whole game to the specified file
     * @param fname name of file storing requests
     */
    public void saveGame(String fname)
    {   // uses object serialisation 
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fname))) {
            out.writeObject(this);
            System.out.println("Game saved to " + fname);
        } catch (IOException e) {
            System.out.println("Error saving game: " + e.getMessage());
        }
           
    }
    
    /** reads all information about the game from the specified file 
     * and returns 
     * @param fname name of file storing the game
     * @return the game (as an SeaBattles object)
     */
    public SeaBattles loadGame(String fname)
    {   // uses object serialisation
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fname))) {
            return (SeaBattles) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading game: " + e.getMessage());
            return null;
        }   
    } 
    
    

    
 
}



