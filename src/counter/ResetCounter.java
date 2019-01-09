/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package counter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Vikash
 */
public class ResetCounter implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        Counter.counterValue.setText("0");
    }
 
}
