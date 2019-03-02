import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TicketCancel extends JPanel implements ActionListener{

	JLabel lcancel, lpnr;
	JTextField tpnr;
	JButton btcancel;
	
	//public void init()
	public  TicketCancel() 
	{
		lcancel=new JLabel("Cancellation Of Ticket");
		lpnr=new JLabel("Enter  PNR No: ");
		
		tpnr=new JTextField(20);
		
		btcancel=new JButton("Cancel");
	
		setLayout(new FlowLayout());
		add(lcancel);
		add(lpnr);
		add(tpnr);
		add(btcancel);
		
		btcancel.addActionListener(this);
		
	}
	@Override
	public void actionPerformed(ActionEvent AE) {

		Object src=AE.getSource();

		//Date dt=new Date(Integer.parseInt(cbyy.getSelectedItem().toString())-1900,cbmm.getSelectedIndex()+1,Integer.parseInt(cbdd.getSelectedItem().toString()));
		if(src==btcancel)
		{
			int pnr=Integer.parseInt(tpnr.getText().toString());
			String userid=Login.username;
			String usertb=userid+"tb";
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con;
				try
				{
					con = DriverManager.getConnection("jdbc:mysql://localhost:3306","root","");
					Statement stmt=con.createStatement();
					stmt.execute("use RailwayDb");
					
					PreparedStatement pstmt=con.prepareStatement("delete from "+usertb+" where pnr=?");
					pstmt.setInt(1, Integer.parseInt(tpnr.getText().toString()));
					pstmt.executeUpdate();
					
					PreparedStatement pstmt1=con.prepareStatement("delete from UseridbookingTb where pnr=?");
					pstmt1.setInt(1, Integer.parseInt(tpnr.getText().toString()));
					pstmt1.executeUpdate();
					
					PreparedStatement pstmt2=con.prepareStatement("Select * from UseridbookingTb where pnr=?");
					pstmt2.setInt(1, pnr);
					
					ResultSet rs=pstmt2.executeQuery();
					while(rs.next())
					{
						
						String trainid=rs.getString(7);
						Date dt=rs.getDate(3);
						String coach=rs.getString(9);
						int totalseat=rs.getInt(10);
						
						PreparedStatement pstmt3=con.prepareStatement("Select * from TrainidBookingTb where train_id=? and journeyDate=?");
						pstmt3.setString(1, trainid);
						pstmt3.setDate(2, dt);
						
						ResultSet rs3=pstmt3.executeQuery();
						
						while(rs3.next())
						{
							int ac1=rs3.getInt(3);
							int ac2=rs3.getInt(4);
							int ac3=rs3.getInt(5);
							int sl=rs3.getInt(6);
							int gen=rs3.getInt(7);
						
						
						
						PreparedStatement pstmt4=con.prepareStatement("update TrainidBookingTb set train_id=?,journeDate=?,ac1=?,ac2=?,ac3=?,sleeper=?,general=? where train_id=? and journeyDate=?");
						pstmt4.setString(1, trainid);
						pstmt4.setDate(2, dt);
						if(coach=="ac1")
							{
								pstmt4.setInt(3, ac1-totalseat);
								pstmt4.setInt(4, ac2);
								pstmt4.setInt(5, ac3);
								pstmt4.setInt(6, sl);
								pstmt4.setInt(7, gen);
							}
						else if(coach=="ac2")
						{

							pstmt4.setInt(3, ac1);
							pstmt4.setInt(4, ac2-totalseat);
							pstmt4.setInt(5, ac3);
							pstmt4.setInt(6, sl);
							pstmt4.setInt(7, gen);
						}
						else if(coach=="ac3")
						{

							pstmt4.setInt(3, ac1);
							pstmt4.setInt(4, ac2);
							pstmt4.setInt(5, ac3-totalseat);
							pstmt4.setInt(6, sl);
							pstmt4.setInt(7, gen);
						}
						else if(coach=="sl")
						{
							pstmt4.setInt(3, ac1);
							pstmt4.setInt(4, ac2);
							pstmt4.setInt(5, ac3);
							pstmt4.setInt(6, sl-totalseat);
							pstmt4.setInt(7, gen);
						}
						else if(coach=="gen")
						{
							pstmt4.setInt(3, ac1);
							pstmt4.setInt(4, ac2);
							pstmt4.setInt(5, ac3);
							pstmt4.setInt(6, sl);
							pstmt4.setInt(7, gen-totalseat);
							
						}
						}
					}
					
					JOptionPane.showMessageDialog(null, "Your Ticket Has Been Cancelled");
					con.close();
					
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
				
			}
		}
	}
}