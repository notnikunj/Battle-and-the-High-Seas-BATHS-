
package wars;

import java.io.Serializable;

/**
 *
 * @author nikunjbhavsar
 */
public class Encounter implements Serializable {
    private int encounterId;
    private String encounterType;
    private String location;
    private int skillRequired;
    private int prizeMoney;
    
    public Encounter(int encounterId, String encounterType, String location, int skillRequired, int prizeMoney){
        this.encounterId = encounterId;
        this.encounterType = encounterType;
        this.location = location;
        this.skillRequired = skillRequired;
        this.prizeMoney = prizeMoney;
    }
    
    //getters
    public int getEncounterId(){
        return encounterId;
    }
    public String getEncounterType(){
        return encounterType;
    }
    public String getlocation(){
        return location;
    }
    public int getSkillRequired(){
        return skillRequired;
    }
    public int getPrizeMoney(){
        return prizeMoney;
    }
    
    //setters
    public void setEncounterId(int x){
        encounterId = x;
    }
    public void setEncounterType(String x){
        encounterType = x;
    }
    public void setlocation(String x){
        location = x;
    }
    public void setSkillRequired(int x){
        skillRequired = x;
    }
    public void setPrizeMoney(int x){
        prizeMoney = x;
    }
    
    public String toString() {
        // This format ensures the test can find the expected substrings.
        return encounterId + " " + encounterType + " " + location + " " + skillRequired + " " + prizeMoney;
    }
}
