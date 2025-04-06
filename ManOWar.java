
package wars;

import java.io.Serializable;

/**
 *
 * @author nikunjbhavsar
 */
public class ManOWar extends Ships implements Serializable {
    int marinesNum;
    int decksNum;
    
    public ManOWar(String name, String captain, int battleSkill, int commissionFee, int marinesNum, int decksNum){
        super(name, captain, battleSkill, commissionFee);
        this.marinesNum = marinesNum;
        this.decksNum = decksNum;
    }
    
    //getters
    public int getMarinesNum(){
        return marinesNum;
    }
    public int getDecksNum(){
        return decksNum;
    }
    
    public boolean canFight(Encounter encounter){
        String type = encounter.getEncounterType();
        return type.equalsIgnoreCase("Blockade") || type.equalsIgnoreCase("Battle");
    }
    
    //setters
    public void setMarinesNum(int x){
        marinesNum = x;
    }
    public void setDecksNum(int x){
        decksNum = x;
    }
    
    @Override
    public String toString() {
        return super.toString() + " " + decksNum + " " + marinesNum + " ManOWar";
    }

}

