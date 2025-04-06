
package wars;

import java.io.Serializable;

/**
 *
 * @author nikunjbhavsar
 */
public class Frigate extends Ships implements Serializable {
    boolean hasPinnace;
    int cannonsNum;
    
    public Frigate(String name, String captain, int battleSkill, int commissionFee, boolean hasPinnace, int cannonsNum){
        super(name, captain, battleSkill, commissionFee);
        this.hasPinnace = hasPinnace;
        this.cannonsNum = cannonsNum;
        
    }
    
    //getters
    public boolean doesShipHavePinnace(){
        return hasPinnace;
    }
    
    public int getCannonsNum(){
        return cannonsNum;
    }
    
    public boolean canFight(Encounter encounter) {
        String type = encounter.getEncounterType();
        if (type.equalsIgnoreCase("Blockade")) {
            return hasPinnace;
        } else if (type.equalsIgnoreCase("Battle") || type.equalsIgnoreCase("Skirmish")) {
            return true;
        }
        return false;
    }
    
    //setters
    public void addPinnace(){
        hasPinnace = true;
    }
    public void removePinnace(){
        hasPinnace = false;
    }
    
    public void setCannonsNum(int x){
        cannonsNum = x;
    }
    
    @Override
    public String toString() {
        return super.toString() + " " + cannonsNum +" "+(hasPinnace ? "true" : "false") + " Frigate";
    }
}
