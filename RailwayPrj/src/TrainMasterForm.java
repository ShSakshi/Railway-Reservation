import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TrainMasterForm extends JApplet implements ActionListener{
	
	JLabel ltrnid, ltrnnm, lsource, ldestination, larrtym, ldeptym, lfare, ldistance;
	JTextField ttrnid, ttrnnm, tsource, tdestination, tarrtym, tdeptym, tfare, tdistance;
	JButton btinsert;
	JPanel p1, p2;
	
	
	public void init()
	{
		ltrnid=new JLabel("Train Id: ");
		ltrnnm=new JLabel("Train Name: ");
		lsource=new JLabel("Source: ");
		ldestination=new JLabel("Destination: ");
		larrtym=new JLabel("Arrival Time: ");
		ldeptym=new JLabel("Departure Time: ");
		ldistance=new JLabel("Distance Travelled: ");
		lfare=new JLabel("Fare: ");
		
		ttrnid=new JTextField(20);
		ttrnnm=new JTextField(20);
		tsource=new JTextField(20);
		tdestination=new JTextField(20);
		tarrtym=new JTextField(20);
		tdeptym=new JTextField(20);
		tdistance=new JTextField(20);
		tfare=new JTextField(20);
		
		btinsert=new JButton("Insert");
		
		
		p1=new JPanel();
		p1.setLayout(new GridLayout(8, 2));
		p1.add(ltrnid);
		p1.add(ttrnid);
		
		p1.add(ltrnnm);
		p1.add(ttrnnm);
		
		p1.add(lsource);
		p1.add(tsource);
		
		p1.add(ldestination);
		p1.add(tdestination);
		
		p1.add(larrtym);
		p1.add(tarrtym);
		
		p1.add(ldeptym);
		p1.add(tdeptym);
		
		p1.add(ldistance);
		p1.add(tdistance);
		
		p1.add(lfare);
		p1.add(tfare);
		
		p2=new JPanel();
		p2.add(btinsert);
		
		
		setLayout(new GridLayout(2,1));
		add(p1);
		add(p2);

		btinsert.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent AE) {
		Object src=AE.getSource();
		if(src==btinsert)
		{
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306","root","");
				Statement stmt=con.createStatement();
				stmt.executeUpdate("Create database if not exists RailwayDb");
				stmt.execute("Use RailwayDb");   //database selection or open
				stmt.executeUpdate("create table if not exists TrainMasterTb("
						+"train_id varchar(100),train_name varchar(100),source varchar(255), dest varchar(200),"
						+"arrival_time time, dept_time time,distance int, fare int, primary key(train_id))");
				
				
				PreparedStatement pstmt=con.prepareStatement("Insert into TrainMasterTb(train_id,train_name,source, dest,arrival_time, dept_time,distance,fare) values(?,?,?,?,?,?,?,?)");
				
				pstmt.setString(1, ttrnid.getText());
				pstmt.setString(2, ttrnnm.getText());
				pstmt.setString(3,tsource.getText());
				pstmt.setString(4, tdestination.getText());
				
				String arrtime=tarrtym.getText();
				int i= arrtime.indexOf(':');
				int hh=Integer.parseInt(arrtime.substring(0,i));
				
				int j= arrtime.indexOf(':',i+1);
				int mm=Integer.parseInt(arrtime.substring(i+1,j));
				
				int ss=Integer.parseInt(arrtime.substring(j+1));
				
				//JOptionPane.showMessageDialog(null, hh+" "+mm+" "+ss);
				
				
				java.sql.Time arr_tm=new Time(hh, mm, ss);
				
				pstmt.setTime(5, arr_tm);
				
				String depttime=tdeptym.getText();
				int k=depttime.indexOf(':');
				int h=Integer.parseInt(depttime.substring(0,k));
				
				int l= arrtime.indexOf(':',k+1);
				int m=Integer.parseInt(depttime.substring(k+1,l));
				
				int s=Integer.parseInt(depttime.substring(l+1));
				
				java.sql.Time dept_tm=new Time(h, m, s);
				
				pstmt.setTime(6, dept_tm);
				
				
				
				pstmt.setString(7, tdistance.getText());
				pstmt.setString(8, tfare.getText());
				pstmt.executeUpdate();
				
				String tbname=ttrnid.getText()+"Tb";
				stmt.executeUpdate("create table if not exists "+tbname
						+"(st_name varchar(255), arrival_time time,dept_time time,distance int, fare int,primary key(st_name))");
				
				con.close();
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
	