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
public class StopCounter implements ActionListener{
 
    	public void actionPerformed(ActionEvent ev)
	{
            Counter.counterValue.setText("0");
	//AddFee.tcode.setText("");
	
	}
}