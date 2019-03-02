import java.awt.GridLayout;
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
import java.sql.Time;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TrainSubStationMasterForm extends JApplet implements ActionListener, ItemListener{

	JLabel ltrnid, ltrnnm, lstnm, larrtym, ldeptym, ldistance1, lfare1;
	JTextField ttrnnm, tstnm, tarrtym, tdeptym, tdistance1, tfare1;
	JButton btinsert;
	JComboBox cbtrnid;
	JPanel p1, p2;
	
	public void init()
	{
		ltrnid=new JLabel("Train Id: ");
		ltrnnm=new JLabel("Train Name: ");
		lstnm=new JLabel("Station Name: ");
		larrtym=new JLabel("Arrival Time: ");
		ldeptym=new JLabel("Departure Time: ");
		ldistance1=new JLabel("Distance Travelled: ");
		lfare1=new JLabel("Fare: ");
		
		ttrnnm=new JTextField(20);
		tstnm=new JTextField(20);
		tarrtym=new JTextField(20);
		tdeptym=new JTextField(20);
		tdistance1=new JTextField(20);
		tfare1=new JTextField(20);
		
		
		btinsert=new JButton("Insert");
		
		cbtrnid=new JComboBox();
		cbtrnid.addItem("Select Train Id");
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306","root","");
				Statement stmt=con.createStatement();
				stmt.execute("Use RailwayDb"); 
				ResultSet rs=stmt.executeQuery("Select train_id from TrainMasterTb");
				while(rs.next())
				{
					cbtrnid.addItem(rs.getString(1));
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
	
		p1=new JPanel();
		p1.setLayout(new GridLayout(7, 2));
		p1.add(ltrnid);
		p1.add(cbtrnid);
		
		p1.add(ltrnnm);
		p1.add(ttrnnm);
		
		p1.add(lstnm);
		p1.add(tstnm);
		
		p1.add(larrtym);
		p1.add(tarrtym);
		
		p1.add(ldeptym);
		p1.add(tdeptym);
		
		p1.add(ldistance1);
		p1.add(tdistance1);
		
		p1.add(lfare1);
		p1.add(tfare1);
		
		
		p2=new JPanel();
		p2.add(btinsert);
		
		
		setLayout(new GridLayout(2,1));
		add(p1);
		add(p2);
		
		cbtrnid.addItemListener(this);
		btinsert.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306","root","");
				Statement stmt=con.createStatement();
				stmt.executeUpdate("Create database if not exists RailwayDb");
				stmt.execute("Use RailwayDb");   //database selection or open
				String tbname1=cbtrnid.getSelectedItem().toString()+"Tb";
				
				
				PreparedStatement pstmt=con.prepareStatement("Insert into "+tbname1+"(st_name,arrival_time, dept_time,distance, fare) values(?,?,?,?,?)");
				
				
				pstmt.setString(1, tstnm.getText());
				
				String arrtime=tarrtym.getText();
				int i= arrtime.indexOf(':');
				int hh=Integer.parseInt(arrtime.substring(0,i));
				
				int j= arrtime.indexOf(':',i+1);
				int mm=Integer.parseInt(arrtime.substring(i+1,j));
				
				int ss=Integer.parseInt(arrtime.substring(j+1));
				
				//JOptionPane.showMessageDialog(null, hh+" "+mm+" "+ss);
				
				
				java.sql.Time arr_tm=new Time(hh, mm, ss);
				
				pstmt.setTime(2, arr_tm);
				
				String depttime=tdeptym.getText();
				int k=depttime.indexOf(':');
				int h=Integer.parseInt(depttime.substring(0,k));
				
				int l= arrtime.indexOf(':',k+1);
				int m=Integer.parseInt(depttime.substring(k+1,l));
				
				int s=Integer.parseInt(depttime.substring(l+1));
				
				java.sql.Time dept_tm=new Time(h, m, s);
				
				pstmt.setTime(3, dept_tm);
				
				
				
				pstmt.setString(4, tdistance1.getText());
				pstmt.setString(5, tfare1.getText());
				
				
				pstmt.executeUpdate();
				
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
	

	}

		
	}

	@Override
	public void itemStateChanged(ItemEvent IE) {
		if(IE.getStateChange()==ItemEvent.DESELECTED)
		{
		String train_id=cbtrnid.getSelectedItem().toString();
try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306","root","");
			Statement stml=con.createStatement();
			
			
			stml.execute("use RailwayDb");
			PreparedStatement pstmt=con.prepareStatement("Select train_name from TrainMasterTb where train_id=?");
			pstmt.setString(1,cbtrnid.getSelectedItem().toString());
			ResultSet rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				
				String train_name=rs.getString(1);
				ttrnnm.setText(train_name);
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


