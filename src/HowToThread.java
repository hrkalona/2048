
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hrkalona2
 */
public class HowToThread  extends Thread {
  private Game2048 ptr;
  
    HowToThread(Game2048 ptr) {
        this.ptr = ptr;
    }
    
    @Override
    public void run() {
        long time = System.currentTimeMillis();
        
        while (System.currentTimeMillis() - time < 500) { 
            yield();
        }
        
        ptr.howTo();
        
    }
    
}
