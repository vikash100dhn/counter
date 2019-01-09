/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package counter;

import com.google.gson.Gson;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Vikash
 */
public class Counter extends JFrame {

    /**
     * @param args the command line arguments
     */
    static JLabel incrementCounter, counterValue, currentTime, systemTIme, internetLabel;
    JButton resetCounter, stopCounter;
    JPanel pan;
    GridBagLayout gbl;
    GridBagConstraints gbc;
    static Font lbf, cv;
    private int counter;
    private boolean resetFlag = false;
    private boolean stopFlag = false;
    Thread background;
    Timer timer;
    private String sysDate;

    Counter() {

        lbf = new Font("Century Schoolbook", Font.BOLD, 11);
        cv = new Font("Bell MT", Font.BOLD, 40);
        pan = new JPanel();
        incrementCounter = new JLabel("Increment Counter:");
        resetCounter = new JButton("Reset Counter");
        stopCounter = new JButton("Stop Counter");
        //check
        counterValue = new JLabel("0");
        gbl = new GridBagLayout();
        gbc = new GridBagConstraints();

        setVisible(true);
        setSize(600, 500);

        setLocation(300, 100);

        getContentPane().add(pan);

        pan.setLayout(gbl);

        Insets ins = new Insets(0, 0, 0, 0);
        ins.right = 20;
        gbc.insets = ins;
        ins.left = 20;
        gbc.insets = ins;
        ins.top = 30;
        gbc.insets = ins;
        ins.bottom = 15;
        gbc.insets = ins;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbl.setConstraints(incrementCounter, gbc);
        incrementCounter.setFont(lbf);
        pan.add(incrementCounter);


        gbc.gridx = 1;
        gbc.gridy = 0;
        gbl.setConstraints(counterValue, gbc);
        counterValue.setFont(cv);
        pan.add(counterValue);
        counterValue.setForeground(Color.red);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbl.setConstraints(resetCounter, gbc);
        resetCounter.setFont(lbf);
        pan.add(resetCounter);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbl.setConstraints(stopCounter, gbc);
        stopCounter.setFont(lbf);
        pan.add(stopCounter);


        //calculating current time from api

        Gson gsonObj = new Gson();
        String internetTime = "";
        try {
            StringBuffer output = getOutputJson("http://worldclockapi.com/api/json/utc/now", "GET", "");
            System.out.println(output.toString());
            Now dateNow = gsonObj.fromJson(output.toString(), Now.class);
            System.out.println("date : " + dateNow.getCurrentDateTime());
            internetTime = dateNow.getCurrentDateTime().substring(0, 16);

        } catch (Exception e) {
            currentTime = new JLabel("Not connected to internet.");
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbl.setConstraints(currentTime, gbc);
            currentTime.setFont(lbf);
            pan.add(currentTime);
        }
        //get the system date and time
        //calculating system time

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ");
        f.setTimeZone(TimeZone.getTimeZone("GMT"));
        System.out.println(f.format(new Date()));//2018-11-23T14:35+0530 - current date in this format
        String systemTime = f.format(new Date()).substring(0, 16);
        sysDate = systemTime;
        System.out.println("System Time:" + systemTime + ",InternetTime:" + internetTime);
        boolean previousEntry = checkPreviousEntry();
        if (systemTime.equals(internetTime) || previousEntry) {

            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    counterValue.setText(String.valueOf(counter));
                    counter++;
                    if (stopFlag) {
                        //timer.removeActionListener(this); 
                        timer.stop();
                        timer.removeActionListener(this);
                    }
                    if (resetFlag) {
                        counter = 0;
                        //timer.stop();
                        resetFlag = false;
                        timer.restart();

                    }
                }
            });
            timer.start();
            // counterValue.setText(getCounter()+"");

        } else {
            timer.stop();

        }
        validate();

        resetCounter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetFlag = true;

            }
        });

        stopCounter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopFlag = true;
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                FileOperation fileOperation = new FileOperation();
                System.out.println("Writing to file");
                fileOperation.updateFile(counter + "", sysDate);
                e.getWindow().dispose();
            }
        });
    }

    public static StringBuffer getOutputJson(String address, String method, String request) throws IOException {
        //StreamLambdaHandler.logger.log("getOutputJson address: " + address);
        URL url = new URL(address);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setDoOutput(true);
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type", "application/json");
        OutputStream outputStream = null;
        if (method.equals("POST")) {
            outputStream = connection.getOutputStream();
            outputStream.write(request.getBytes());
            outputStream.flush();
        }
        //	StreamLambdaHandler.logger.log("outputStream: " + outputStream);
        if (connection.getResponseCode() != 200 && connection.getResponseCode() != 201) {
            //	StreamLambdaHandler.logger.log(ServiceCallConstants.ERR_LOGGER + connection.getResponseCode());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String inputLine;
        StringBuffer outputJson = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            outputJson.append(inputLine);
        }
        connection.disconnect();
        if (method.equals("POST")) {
            if (outputStream != null) {
                outputStream.close();
            }
        }
        br.close();
        return outputJson;
    }

    public static void main(String[] args) {
        // TODO code application logic here
    }

    private boolean checkPreviousEntry() {

        boolean flag = false;
        String value = "";
        File file = new File("D:\\time.txt");
        if (file.exists()) {
            System.out.println("file exist");
            //if file exist check the time is less than current time
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader("D:\\time.txt"));
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                value = sb.toString();
                System.out.println(value);
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            } finally {
                if (null != br) {
                    try {
                        br.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Counter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            String arr[] = value.split(":");
            //comparting if the date from file is not later than current date
            String fileDatetime = arr[1];
            String fileDate = fileDatetime.substring(0, 10);
            System.out.println(fileDate);

            //extracting system date
            String systemDatetime = sysDate.substring(0, 10);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date date1 = format.parse(systemDatetime);
                Date date2 = format.parse(fileDatetime);

                if (date1.compareTo(date2) <= 0) {
                    flag = false;
                }
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }

        } else {
            flag = true;
        }
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return flag;
    }
}
