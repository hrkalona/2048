
import java.io.Serializable;
import java.util.Random;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class Number implements Serializable {
  private int number;
  private boolean not_used;
  
    Number() {
        Random generator = new Random(); 
        number = generator.nextDouble() < 0.9 ? 2 : 4;

        not_used = true;       
    }
    
    Number(Number clone) {
        
        number = clone.number;
        not_used = true; 
    }
    
    public void times2() {
        
        number *= 2;
        not_used = false;
        
    }
    
    public int getNumber() {
        
        return number;
        
    }
    
    public boolean notUsed() {
        
        return not_used;
        
    }
    
    public boolean is2048() {
        
        return number == 2048;
        
    }
    
    public boolean is65536() {
        
        return number == 65536;
        
    }
    
    public void resetNotUsed() {
        
        not_used = true;
        
    }
    
    public void setNumber(int number) {
        
        this.number = number;
        
    }
    
}
