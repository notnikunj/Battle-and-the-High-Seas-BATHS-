package wars;

import java.io.Serializable;

/**
 *
 * @author nikunjbhavsar
 */
public class Sloop extends Ships implements Serializable {
    private boolean hasDoctor;
    
    public Sloop(String name, String captain, int battleSkill, int commissionFee, boolean hasDoctor) {
        super(name, captain, battleSkill, commissionFee);
        this.hasDoctor = hasDoctor;
    }
    //getters
    public boolean doesShipHaveDoctor(){
        return hasDoctor;
    }
    
    public boolean canFight(Encounter encounter){
        String type = encounter.getEncounterType();
        return type.equalsIgnoreCase("Battle") || type.equalsIgnoreCase("Skirmish");
    }
    
    
    //setters
    public void addDoctor(){
        hasDoctor = true;
    }
    
    public void removeDoctor(){
        hasDoctor = false;
    }
    
    @Override
    public String toString() {
        return super.toString() + " " + (hasDoctor ? "true" : "false") + " Sloop";
    }

}
