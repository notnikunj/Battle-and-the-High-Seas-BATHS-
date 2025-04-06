
package wars;

import java.io.Serializable;

/**
 *
 * @author apurvaraval
 */
public abstract class Ships implements Serializable {
    private String name;
    private String captain;
    private int battleSkill;
    private int commissionFee;
    private ShipState state;
    
    public Ships (String name, String captain, int battleSkill, int commissionFee){
        this.name = name;
        this.captain = captain;
        this.battleSkill = battleSkill;
        this.commissionFee = commissionFee;
        this.state = ShipState.RESERVE;
    }
    //getters
    public String getName(){
        return name;
    }
    
    public String getCaptain(){
        return captain;
    }
    
    public int getBattleSkill(){
        return battleSkill;
    }
    
    public int getCommissionFee(){
        return commissionFee;
    }
    
    public ShipState getState(){
        return state;
    }
    
    public abstract boolean canFight(Encounter encounter);

    
    //setters
    public void setState(ShipState x){
        state = x;
    }
    public void setBattleSkill( int x){
        battleSkill = x;
    }
    
    public void setCommissionFee(int x){
        commissionFee = x;
    }
    public String toString() {
        return String.format("%s - Captain: %s, Battle Skill: %d, Commission Fee: %d, State: %s",
                name, captain, battleSkill, commissionFee, state);
    }

}
