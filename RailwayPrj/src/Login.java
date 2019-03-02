import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Login extends JPanel implements ActionListener{

	JLabel lusernm, lpassword;
	JTextField tusernm;
	JTextField tpassword;
	JButton btsubmit, btcancel;
	public static String username;
	
	//public void init()
	public Login()
	{
		
		lusernm=new JLabel("User name: ");
		lpassword=new JLabel("Password: ");
		
		tusernm=new JTextField(20);
		tpassword=new JTextField(20);
		
		btsubmit=new JButton("Submit");
		btcancel=new JButton("Cancel");
		
		setLayout(new GridLayout(3,2));
		add(lusernm);
		add(tusernm);
	
		add(lpassword);
		add(tpassword);	
	
		add(btsubmit);
		add(btcancel);
	
		btsubmit.addActionListener(this);
		btcancel.addActionListener(this);
	
	}
	
	@Override
	public void actionPerformed(ActionEvent AE) {
		Object src=AE.getSource();
		
		if(src==btsubmit)
		{
			username=tusernm.getText();
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con;
				try {
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306","root","");
					Statement stmt=con.createStatement();
					stmt.execute("Use RailwayDb");   //database selection or open
					PreparedStatement pstmt=con.prepareStatement("select count(*) from UserTb where username=? and password=? ");
					pstmt.setString(1, username);
					pstmt.setString(2, tpassword.getText());
					ResultSet rs=pstmt.executeQuery(); 
					int c=0;
					while(rs.next())
						c=rs.getInt(1);
					
					
					if(c==0)
					{
						JOptionPane.showMessageDialog(null,"illegal user");
					}
					else
					{
						Welcome.btbooking.setEnabled(true);
						
					}
					/*while(rs.next())
					{
						if(tusernm.getText().equals(rs.getString(1)) &&  tpassword.getText().equals(rs.getString(2)))
						{
							JOptionPane.showMessageDialog(null,"user found");
						
						}
					
						if(!rs.next())
						{
							JOptionPane.showMessageDialog(null,"illegal user");
						}
				}*/
					con.close();
				
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
			if(src==btcancel)
			{

				tusernm.setText("");
				tpassword.setText(" ");

			}
		
		
	}

}
