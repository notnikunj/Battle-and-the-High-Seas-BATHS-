package wars;

import java.io.Serializable;

/**
 *
 * @author nikunjbhavsar
 */
public class Admiral implements Serializable {
    private String admiralName;
    private int warChest;
    
    public Admiral(String admiralName, int warChest){
        this.admiralName = admiralName;
        this.warChest = warChest;
    }
    
    //getters
    public String getAdmiralName(){
        return admiralName;
    }
    public int getWarChest(){
        return warChest;
    }
    
    //setters
    public void setAdmiralName(String x){
        admiralName = x;
    }
    
    public void setWarChest(int x){
        warChest = x;
    }
    
}
