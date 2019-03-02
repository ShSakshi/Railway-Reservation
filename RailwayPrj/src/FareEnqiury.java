import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class FareEnqiury extends JPanel implements ActionListener, ItemListener{

	JLabel lsource, ldestination;
	JComboBox cbcoach, cbsource, cbdestination;
	JButton btget;
	
	public FareEnqiury()
	//public void init()
	{
		lsource=new JLabel("Source: ");
		ldestination=new JLabel("Destination: ");
		
		cbsource=new JComboBox();
		cbsource.addItem("Select Source Station");
		cbdestination=new JComboBox();
		cbdestination.addItem("Select Destination Station");
		
		cbcoach=new JComboBox();
		cbcoach.addItem("select coach");
		cbcoach.addItem("ac1");
		cbcoach.addItem("ac2");
		cbcoach.addItem("ac3");
		cbcoach.addItem("sleeper");
		cbcoach.addItem("general");
		
		
		
		btget=new JButton("Get Fare");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306","root","");
				Statement stmt=con.createStatement();
				stmt.execute("Use RailwayDb"); 
				ResultSet rs=stmt.executeQuery("Select distinct source from TrainMasterTb");
				while(rs.next())
				{
					cbsource.addItem(rs.getString(1));
				}
				
				PreparedStatement pstmt5=con.prepareStatement("Select train_id from TrainMasterTb");
				ResultSet rs5=pstmt5.executeQuery();
				while(rs5.next())
				{
					String table=rs5.getString(1)+"tb";
//					Statement stmt1=con.createStatement();
					ResultSet rs1=stmt.executeQuery("Select distinct st_name from " +table);
				
					while(rs1.next())
					{
						cbsource.addItem(rs1.getString(1));
					}
				}
				
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		setLayout(new FlowLayout());
		add(lsource);
		add(cbsource);
	
		add(ldestination);
		add(cbdestination);
	
		add(new JLabel("Select coach: "));
		add(cbcoach);
		
		add(btget);
		
		cbdestination.addItemListener(this);
		cbsource.addItemListener(this);
		
		btget.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent AE) {
		Object src=AE.getSource();
		
		if(src==btget)
		{
			//String source=cbsource.getSelectedItem().toString();
			//String dest=cbdestination.getSelectedItem().toString();
			int coach=cbcoach.getSelectedIndex();
			
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con;
			try
			{
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306","root","");
				Statement stmt=con.createStatement();
				stmt.execute("use RailwayDb");
				
				PreparedStatement pstmt=con.prepareStatement("Select fare from TrainMasterTb where source=? and dest=?");
		
				pstmt.setString(1, cbsource.getSelectedItem().toString());
				pstmt.setString(2, cbdestination.getSelectedItem().toString());
				ResultSet rs=pstmt.executeQuery();
				
				while(rs.next())
				{
					int genfare=rs.getInt(1);
					if(coach==1)
					JOptionPane.showMessageDialog(null,"your fare is "+ genfare*10);
					else if(coach==2)
						JOptionPane.showMessageDialog(null, "your fare is "+ genfare*7);
					else if(coach==3)
						JOptionPane.showMessageDialog(null, "your fare is "+ genfare*5);
					else if(coach==4)
						JOptionPane.showMessageDialog(null, "your fare is "+ genfare*3);
					else if(coach==5)
						JOptionPane.showMessageDialog(null, "your fare is "+ genfare);
	
				}
				
				PreparedStatement pstmt1=con.prepareStatement("Select train_id from TrainMasterTb where dest=?");
				pstmt1.setString(1,cbdestination.getSelectedItem().toString());
				ResultSet rs1=pstmt1.executeQuery();
				while(rs1.next())
				{
				
					String trainid=rs1.getString(1);
					String table1=trainid+"Tb";
				
					
					PreparedStatement pstmt2=con.prepareStatement("Select fare from "+table1+" where st_name=?");
				pstmt2.setString(1, cbsource.getSelectedItem().toString());
				
				ResultSet rs2=pstmt2.executeQuery();
				
				while(rs2.next())
				{
					int genfare=rs2.getInt(1);
					if(coach==1)
					JOptionPane.showMessageDialog(null,"your fare is "+ genfare*10);
					else if(coach==2)
						JOptionPane.showMessageDialog(null, "your fare is "+ genfare*7);
					else if(coach==3)
						JOptionPane.showMessageDialog(null, "your fare is "+ genfare*5);
					else if(coach==4)
						JOptionPane.showMessageDialog(null, "your fare is "+ genfare*3);
					else if(coach==5)
						JOptionPane.showMessageDialog(null, "your fare is "+ genfare);
	
				}
				}
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
	@Override
	public void itemStateChanged(ItemEvent IE) {
		if(IE.getSource()==cbsource)
		{
			
		if(cbsource.getSelectedIndex() ==0 || IE.getStateChange()==ItemEvent.DESELECTED)return;
		
		cbdestination.removeAllItems();
		cbdestination.addItem("Select Destination");
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306","root","");
			Statement stmt=con.createStatement();
			
			stmt.execute("use RailwayDb");
			PreparedStatement pstmt=con.prepareStatement("Select distinct dest from TrainMasterTb where source=?");
			pstmt.setString(1,cbsource.getSelectedItem().toString());
			ResultSet rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				
				String destination=rs.getString(1);
				cbdestination.addItem(destination);
			}			
			
			rs=stmt.executeQuery("Select train_id from TrainMasterTb");
			while(rs.next())
			{
			
			String trainid=rs.getString(1);
			String table1=trainid+"Tb";
					
			 pstmt=con.prepareStatement("Select count(*) from "+table1 + " where st_name=?");
						
			pstmt.setString(1,cbsource.getSelectedItem().toString());
			ResultSet rs1=pstmt.executeQuery();
			int c=0;
			while(rs1.next())
				c=rs1.getInt(1);
			
			
			//JOptionPane.showMessageDialog(null, c+"");
			if(c!=0)
			{
				 pstmt=con.prepareStatement("Select dest from TrainMasterTb where train_id=?");
				 pstmt.setString(1, trainid);
				ResultSet rs11=pstmt.executeQuery();
				 while(rs11.next())
				{
					
					String destination=rs11.getString(1);
					cbdestination.addItem(destination);
				}			
				
			
			}
			}
			con.close();
			validate();
		} 
		
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}	
		}
		
		
		

		
	}

}
