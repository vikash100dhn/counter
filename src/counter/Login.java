/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package counter;

/**
 *
 * @author Vikash
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

class login extends JFrame implements ActionListener
{
	static login log;
	static Color bgc;
	static Font lbf;
	static Font btf;
	static Font text;
	JPanel pan;
	JLabel lUser,lPass,lWelcome,lPic;
	JTextField tUser;
	JPasswordField pPass;
	JButton bSubmit;
	GridBagLayout gbl;
	GridBagConstraints gbc;
	login()
	{
		pan=new JPanel();
		lbf=new Font("Century Schoolbook",Font.BOLD,11);
		btf=new Font("Century",Font.BOLD,12);
		bgc=new Color(200,255,230);
		text=new Font("Bookman Old Style",Font.PLAIN,15);
		lUser=new JLabel("UserName:");
		lPass=new JLabel("Password:");
		lWelcome=new JLabel("Login");
		Icon img=new ImageIcon("image/11.png");
		Font ff1=new Font("Bell MT",Font.BOLD,20);
		
	
		lPic=new JLabel(img);
		tUser=new JTextField(20);
		pPass=new JPasswordField(20);
		bSubmit=new JButton("Submit");
		
		gbl=new GridBagLayout();
		gbc=new GridBagConstraints();
		
		setTitle("Login Window");
		pan.setBackground(bgc);

		setVisible(true);
		setSize(600,500);
		
		setLocation(300,100);
		//setResizable(false);
		//setLineWrap.false;
		setDefaultCloseOperation(3);
		getContentPane().add(pan);
		
		pan.setLayout(gbl);

		gbc.gridx=0;
		gbc.gridy=0;
		gbc.anchor=GridBagConstraints.WEST;
		Insets ins= new Insets(0,0,0,0);
		ins.right=20;
		gbc.insets=ins;
		ins.bottom=15;
		gbc.insets=ins;
		lUser.setFont(lbf);
		gbl.setConstraints(lPic,gbc);
		pan.add(lPic);

		gbc.gridx=1;
		gbc.gridy=0;
		gbl.setConstraints(lWelcome,gbc);
		pan.add(lWelcome);
                lWelcome.setFont(ff1);
		lWelcome.setForeground(Color.red);

		gbc.gridx=0;
		gbc.gridy=1;
		gbl.setConstraints(lUser,gbc);
		lUser.setFont(lbf);
		pan.add(lUser);

		gbc.gridx=1;
		gbl.setConstraints(tUser,gbc);
		pan.add(tUser);

		gbc.gridx=0;
		gbc.gridy=2;
		lPass.setFont(lbf);
		gbl.setConstraints(lPass,gbc);
		pan.add(lPass);

		gbc.gridx=1;
		gbl.setConstraints(pPass,gbc);
		pan.add(pPass);
                
                Font s=new Font("Viner Hand ITC", Font.BOLD,15);

                                    gbc.gridx=0;
		gbc.gridy=3;
		gbc.ipadx=20;
		gbc.ipady=10;
		gbc.anchor=gbc.CENTER;
		gbc.gridwidth=2;
		bSubmit.setFont(s);
		gbl.setConstraints(bSubmit,gbc);
		pan.add(bSubmit);

		//tUser.addFocusListener(new FocusColor());
		//pPass.addFocusListener(new FocusColor());

		//pan.setBackground(Color.pink);

		validate();
		bSubmit.addActionListener(this);

	}

	public void actionPerformed(ActionEvent ae)
	{
	
		String user_name=tUser.getText().trim();
		String pass=pPass.getText().trim();


			if(user_name.equals("admin") && pass.equals("admin"))
			{
				
						login.log.setVisible(false);
						new Counter();

			}	
			else
				JOptionPane.showMessageDialog(null, "Incorrect Username/password" );	
	}
	public static void main(String ar[])
	{
		log= new login();
	}
}

