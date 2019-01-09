/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package counter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Vikash
 */
public class ReadProperty {
  public static String getProperty(String key) {

      String value="";
	     Properties prop = new Properties();
             
	     InputStream input = null;

	try {

		input = new FileInputStream("config.properties");
               // input = getClass().getClassLoader().getResourceAsStream("/config.properties");
		// load a properties file
		prop.load(input);

		// get the property value and print it out
		System.out.println(prop.getProperty(key));
		value = prop.getProperty(key) ;

	} catch (IOException ex) {
		ex.printStackTrace();
	} finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
        
        return value;

  }
 
}
