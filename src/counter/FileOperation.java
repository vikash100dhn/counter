package counter;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.File;
import java.nio.file.Files;

public class FileOperation {
    
    public void updateFile(String counter, String time)
    {
      
		File file = new File("C:\\temp\\time.txt");
		if(file.exists())
                {
			//this is or appending data
			/*try {
			    Files.write(Paths.get("C:\\\\temp\\\\time.txt"), data.getBytes(), StandardOpenOption.APPEND);
			}catch (IOException e) {
			    //exception handling left as an exercise for the reader
			}*/
			
			try {
				FileWriter fw = new FileWriter("D:\\time.txt");
				fw.write(counter+":");
                                
                                Files.write(Paths.get("D:\\time.txt"), time.getBytes(), StandardOpenOption.APPEND);
				fw.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else {
			String data =counter+":"+time;
			createNew(data);
		}  
    }

	private static void createNew(String data) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(new File("D:\\time.txt"));
            os.write(data.getBytes(), 0, data.length());
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
