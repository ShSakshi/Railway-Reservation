 
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import javax.swing.ButtonGroup;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Reservation extends JPanel implements ItemListener, ActionListener{
	
	JLabel ltrnid, ltrnnm, ldatjour, ldatbook, lsource, ldestination, lclass, lnoseat, lspseat, lnrseat, lphn;
	JComboBox cbtrnm, cbdd,cbmm,cbyy,cbdd1,cbmm1,cbyy1, cbnoseat,cbspseat, cbnrseat, cbclass, cbsource, cbdestination;
	JButton btcheck, btsubmit, btreceipt;
	JPanel p1, p2,p3,p4,p5,p6,p7,p8,p9,pa,pb,pc,pd,pe;
	int i;
	JRadioButton rdmale[]=new JRadioButton[6], rdfemale[]=new JRadioButton[6];
	ButtonGroup bg[]=new ButtonGroup[6];
	JTextField ttrnid,tpnrno,tfare,tname[]=new JTextField[6], tage[]=new JTextField[6], tcategory[]=new JTextField[6];
	
	public Reservation()
	//public void init()
	{
		ldatbook=new JLabel("Date Of Booking: ");
		lsource=new JLabel("Source: ");
		ldestination=new JLabel("Destination: ");
		ldatjour=new JLabel("Date Of Journey: ");
		ltrnnm=new JLabel("Train Name: ");
		lclass=new JLabel("Class: ");
		ltrnid=new JLabel("Train Id: ");
		lnoseat=new JLabel("Total No Of Seats: ");
		lspseat=new JLabel("No Of Special Seats: ");
		lnrseat=new JLabel("No Of Normal Seats: ");
		lphn=new JLabel("Mobile No: ");
	
		ttrnid=new JTextField(20);
		
		//Date Of Booking
		cbdd=new JComboBox();
		cbdd.addItem("dd");
		for(int i=1; i<=31;i++)
			cbdd.addItem(i+"");	
	
		cbmm=new JComboBox();
		cbmm.addItem("mm");
		cbmm.addItem("January");
		cbmm.addItem("Febuary");
		cbmm.addItem("March");
		cbmm.addItem("April");
		cbmm.addItem("May");
		cbmm.addItem("June");
		cbmm.addItem("July");
		cbmm.addItem("August");
		cbmm.addItem("September");
		cbmm.addItem("October");
		cbmm.addItem("November");
		cbmm.addItem("December");
	
	
		cbyy=new JComboBox();
		cbyy.addItem("yy");
		for(int i=2017; i<=2020;i++)
			cbyy.addItem(i+"");
		
		for(i=0;i<=5;i++)
		{
			tname[i]=new JTextField(20);
			tage[i]=new JTextField(20);
			tcategory[i]=new JTextField(20);
			rdmale[i]=new JRadioButton("Male",false);
			rdfemale[i]=new JRadioButton("Female",false);
			
			
			bg[i]=new ButtonGroup();
			bg[i].add(rdmale[i]);
			bg[i].add(rdfemale[i]);
			
			
		
		}
		
		
		
		//Source and Destination
		
		cbsource=new JComboBox();
		cbsource.addItem("Select Source Station");
		cbdestination=new JComboBox();
		cbdestination.addItem("Select Destination Station");
		
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
				
				rs=stmt.executeQuery("Select train_id from TrainMasterTb");
				while(rs.next())
				{
					String table=rs.getString(1)+"tb";
					Statement stmt1=con.createStatement();
					ResultSet rs1=stmt1.executeQuery("Select distinct st_name from " +table);
				
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
	
	
		
		
	//Date Of Journey	
		cbdd1=new JComboBox();
		cbdd1.addItem("dd");
		for(int i=1; i<=31;i++)
			cbdd1.addItem(i+"");	
	
		cbmm1=new JComboBox();
		cbmm1.addItem("mm");
		cbmm1.addItem("January");
		cbmm1.addItem("Febuary");
		cbmm1.addItem("March");
		cbmm1.addItem("April");
		cbmm1.addItem("May");
		cbmm1.addItem("June");
		cbmm1.addItem("July");
		cbmm1.addItem("August");
		cbmm1.addItem("September");
		cbmm1.addItem("October");
		cbmm1.addItem("November");
		cbmm1.addItem("December");
	
	
		cbyy1=new JComboBox();
		cbyy1.addItem("yy");
		for(int i=2017; i<=2020;i++)
			cbyy1.addItem(i+"");
		
		//Train Name
		cbtrnm=new JComboBox();
		cbtrnm.addItem("Select Train Name");
		
			//Class
		cbclass=new JComboBox();
		cbclass.addItem("Select class: ");
		cbclass.addItem("Ac1");
		cbclass.addItem("Ac2");
		cbclass.addItem("Ac3");
		cbclass.addItem("General");
		cbclass.addItem("Sleeper");
		
		
//Total seats
		cbnoseat=new JComboBox();
		cbnoseat.addItem(" Select no of seats");  
		cbnoseat.addItem("1");
		cbnoseat.addItem("2");
		cbnoseat.addItem("3");
		cbnoseat.addItem("4");
		cbnoseat.addItem("5");
		cbnoseat.addItem("6");
		
		cbspseat=new JComboBox();
		cbspseat.addItem(" Select no of seats");  
		cbspseat.addItem("1");
		cbspseat.addItem("2");
		cbspseat.addItem("3");
		cbspseat.addItem("4");
		cbspseat.addItem("5");
		cbspseat.addItem("6");
		
		cbnrseat=new JComboBox();
		cbnrseat.addItem(" Select no of seats");  
		cbnrseat.addItem("1");
		cbnrseat.addItem("2");
		cbnrseat.addItem("3");
		cbnrseat.addItem("4");
		cbnrseat.addItem("5");
		cbnrseat.addItem("6");
		
				btcheck=new JButton("Check");
		btsubmit=new JButton("Submit");
		btreceipt=new JButton("Receipt");
		
			
		p1=new JPanel();
		p1.setLayout(new GridLayout(1,4));
		p1.add(ldatbook);
		p1.add(cbdd);
		p1.add(cbmm);
		p1.add(cbyy);
		
		p2=new JPanel();
		p2.setLayout(new GridLayout(2,2));
		p2.add(lsource);
		p2.add(cbsource);
		
		p2.add(ldestination);
		p2.add(cbdestination);
		
		
		p3=new JPanel();
		p3.setLayout(new GridLayout(1,4));
		p3.add(ldatjour);
		p3.add(cbdd1);
		p3.add(cbmm1);
		p3.add(cbyy1);
		
		p4=new JPanel();
		p4.setLayout(new GridLayout(6,2));
		p4.add(ltrnnm);
		p4.add(cbtrnm);
		
		p4.add(ltrnid);
		p4.add(ttrnid);
		
		p4.add(lclass);
		p4.add(cbclass);
		
		p4.add(lnoseat);
		p4.add(cbnoseat);
		
		p4.add(lspseat);
		p4.add(cbspseat);
		
		p4.add(lnrseat);
		p4.add(cbnrseat);
		
		
		p5=new JPanel();
		p5.add(btcheck);
		
		p6=new JPanel();
		p7=new JPanel();
		
		p6.setPreferredSize(new Dimension(1200,400));
		p6.setLayout(new GridLayout(5,1));
		p6.add(p1);
		p6.add(p2);
		p6.add(p3);
		//p6.add(p4);
		//p6.add(p5);
		setLayout(new GridLayout(3,1));
		add(p6);
		add(p4);
		add(p5);
		
		cbdestination.addItemListener(this);
		cbsource.addItemListener(this);
		cbtrnm.addItemListener(this);
		cbclass.addItemListener(this);
		cbnoseat.addItemListener(this);
		btcheck.addActionListener(this);
		btsubmit.addActionListener(this);
	}
	
	
		
	
	@Override
	public void itemStateChanged(ItemEvent IE) {
		

	//String source=cbsource.getSelectedItem().toString();
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
	
	

	if(IE.getSource()==cbdestination)
	{
		
	if(cbdestination.getSelectedIndex() ==0 || IE.getStateChange()==ItemEvent.DESELECTED)return;
	
	String dest=cbdestination.getSelectedItem().toString();
	
	try
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306","root","");
		Statement stmt=con.createStatement();
		
		stmt.execute("use RailwayDb");
		PreparedStatement pstmt=con.prepareStatement("Select count(*) from TrainMasterTb where source=? and dest = ?");
		pstmt.setString(1,cbsource.getSelectedItem().toString());
		pstmt.setString(2,cbdestination.getSelectedItem().toString());
		ResultSet rs=pstmt.executeQuery();

		int c=0;
		while(rs.next())
			c=rs.getInt(1);
	
		if(c==0)
		{
			JOptionPane.showMessageDialog(null, "No Direct Train ");
			//	return;
		}
		else
		{
			PreparedStatement pstmt1=con.prepareStatement("Select train_name from TrainMasterTb where source=? and dest=? ");
			pstmt1.setString(1,cbsource.getSelectedItem().toString());
			pstmt1.setString(2,cbdestination.getSelectedItem().toString());
			rs=pstmt1.executeQuery();
//			ResultSet rs=pstmt.executeQuery();
				
			while(rs.next())
			{
				cbtrnm.addItem(rs.getString(1));
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
	if(IE.getSource()==cbtrnm)
	{
try
	{
		Class.forName("com.mysql.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306","root","");
		Statement stml=con.createStatement();
		
		
		stml.execute("use RailwayDb");
		PreparedStatement pstmt=con.prepareStatement("Select train_id from TrainMasterTb where train_name=?");
		pstmt.setString(1,cbtrnm.getSelectedItem().toString());
		ResultSet rs=pstmt.executeQuery();
		
		while(rs.next())
		{
			
			ttrnid.setText(rs.getString(1));
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

	@Override
	public void actionPerformed(ActionEvent AE) {
		Object src=AE.getSource();
		
		int pnr=0;
		float genfare=0.0f;
		float fare=0.0f;
		Date dt=new Date(Integer.parseInt(cbyy.getSelectedItem().toString())-1900,cbmm.getSelectedIndex()+1,Integer.parseInt(cbdd.getSelectedItem().toString()));
		Date bdt=new Date(Integer.parseInt(cbyy1.getSelectedItem().toString())-1900,cbmm1.getSelectedIndex()+1,Integer.parseInt(cbdd1.getSelectedItem().toString()));
		String tickettype=cbclass.getSelectedItem().toString();
		String trainno=ttrnid.getText();
		int passengerno=Integer.parseInt(cbnoseat.getSelectedItem().toString());
		int nosc=Integer.parseInt(cbspseat.getSelectedItem().toString());
		int noa=Integer.parseInt(cbnrseat.getSelectedItem().toString());
		String userid=Login.username;
	
		if (src==btcheck)
		{

			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306","root","");
				Statement stmt=con.createStatement();

				stmt.execute("Use RailwayDb");   //database selection or open

				PreparedStatement pstmt1=con.prepareStatement("Select count(*) from TrainIdBookingTb where train_id=? and JourneyDate=?");
				pstmt1.setString(1, trainno);
				pstmt1.setDate(2, dt);
				
				ResultSet rs=pstmt1.executeQuery();
				
				int c=0;
				while(rs.next())
					c=rs.getInt(1);
				
				if(c==0)
					
				{
					PreparedStatement pstmt=con.prepareStatement("Insert into TrainidBookingTb(train_id,JourneyDate, Ac1,Ac2,Ac3,General, Sleeper) values(?,?,?,?,?,?,?)");
			
					pstmt.setString(1, trainno);
					pstmt.setDate(2, dt);
			
					int ac1=0;
					int ac2=0;
					int ac3=0;
					int general=0;
					int sleeper=0;
					pstmt.setInt(3,ac1);
					pstmt.setInt(4, ac2);
					pstmt.setInt(5, ac3);
					pstmt.setInt(6, general);
					pstmt.setInt(7, sleeper);
		
					pstmt.executeUpdate();
//					JOptionPane.showMessageDialog(null, "Insertionin TrainIdBookingTb...");
				}
				
					
				PreparedStatement pstmt2=con.prepareStatement("Select "+tickettype+" from TrainidBookingTb where train_id=? and JourneyDate=? ");
				pstmt2.setString(1, trainno);
				pstmt2.setDate(2, dt);
				
				ResultSet rs1=pstmt2.executeQuery();
			
			while(rs1.next())
			{
				int ticketall=rs1.getInt(1)+cbnoseat.getSelectedIndex();
				String coach="No"+tickettype;
				
				//JOptionPane.showMessageDialog(null, "Booked "+rs1.getInt(1));
				
				PreparedStatement pstmt3=con.prepareStatement("Select "+coach+" from TrainSeatsMasterTb where train_id=?");
				pstmt3.setString(1, trainno);
				ResultSet rs2=pstmt3.executeQuery();
				while(rs2.next())
				{
					int tickettt=rs2.getInt(1);
						
				//JOptionPane.showMessageDialog(null, "Total "+rs2.getInt(1));
				
				if(ticketall>tickettt)
							JOptionPane.showMessageDialog(null, "Seats Not available for this coach on selected Train and Journey Date");
				else
							{
								//if(pa!=null)
								//remove(pa);
					//JOptionPane.showMessageDialog(null, "Seatss available for this coach on selected Train and Journey Date");
			
								pa=new JPanel();
								pa.setLayout(new GridLayout(7,1));
								pa.add(new JLabel("Name: "));
								for(i=0;i<=5;i++)
								{
									//if(tname[i].getText().equals("")) break;
									pa.add(tname[i]);
								}
								
								pb=new JPanel();
								pb.setLayout(new GridLayout(7,2));
								pb.add(new JLabel("Gender: "));
								pb.add(new JLabel(""));
								
								for(i=0;i<=5;i++)
								{
									pb.add(rdmale[i]);
									pb.add(rdfemale[i]);	
								}
			
								
								pd=new JPanel();
								pd.setLayout(new GridLayout(7,1));
								pd.add(new JLabel("Age: "));
								for(i=0;i<=5;i++)
								{
									pd.add(tage[i]);
								}
			
								pe=new JPanel();
								pe.setLayout(new GridLayout(7,1));
								pe.add(new JLabel("Category: "));
								for(i=0;i<=5;i++)
								{
									pe.add(tcategory[i]);
								}
								
								p7.setLayout(new GridLayout(1,4));
								p7.add(pa);
								p7.add(pb);
								p7.add(pd);
								p7.add(pe);
								
								p8=new JPanel();
								p8.setLayout(new GridLayout(1,1));
								p8.add(btsubmit);
								
								p9=new JPanel();
								p9.setLayout(new GridLayout(2,1));
								p9.add(p7);
								p9.add(p8);
								
								add(p9);
								validate();
							}
							}
				}
			
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
	
		else
			
			if(src==btsubmit)
			{		
						try
						{
							Class.forName("com.mysql.jdbc.Driver");
							Connection con;
							try
							{
								con = DriverManager.getConnection("jdbc:mysql://localhost:3306","root","");
								Statement stmt=con.createStatement();
								stmt.execute("use RailwayDb");
								
							
									PreparedStatement pstmt1=con.prepareStatement("Select * from TrainidBookingTb where train_id=? and journeyDate=?");
									pstmt1.setString(1, trainno);
									pstmt1.setDate(2, dt);
									ResultSet rs1=pstmt1.executeQuery();
								JOptionPane.showMessageDialog(null, "select...");
									while(rs1.next())
									{
										
										int ac1=rs1.getInt(3);
										int ac2=rs1.getInt(4);
										int ac3=rs1.getInt(5);
										int sl=rs1.getInt(6);
										int gen=rs1.getInt(7);
										
										JOptionPane.showMessageDialog(null, ac1+"  "+ac2+" "+ac3+" "+sl+" "+gen);
										
										PreparedStatement pstmt11=con.prepareStatement("update TrainidBookingTb set Ac1=?,Ac2=?,Ac3=?,Sleeper=?,General=? where train_id=? and journeyDate=?");
										pstmt11.setString(6, trainno);
										pstmt11.setDate(7, dt);
										
										
										//ResultSet rs2=pstmt11.executeQuery();
										//while(rs2.next())
										
									
										
										if(tickettype.equalsIgnoreCase("ac1"))
											{
												pstmt11.setInt(1, passengerno+ac1);
												pstmt11.setInt(2, ac2);
												pstmt11.setInt(3, ac3);
												pstmt11.setInt(4, sl);
												pstmt11.setInt(5, gen);
											}
										else if(tickettype.equalsIgnoreCase("ac2"))
										{

											pstmt11.setInt(1, ac1);
											pstmt11.setInt(2, passengerno+ac2);
											pstmt11.setInt(3, ac3);
											pstmt11.setInt(4, sl);
											pstmt11.setInt(5, gen);
										}
										else if(tickettype.equalsIgnoreCase("ac3"))
										{

											pstmt11.setInt(1, ac1);
											pstmt11.setInt(2, ac2);
											pstmt11.setInt(3, passengerno+ac3);
											pstmt11.setInt(4, sl);
											pstmt11.setInt(5, gen);
										}
										else if(tickettype.equalsIgnoreCase("sl"))
										{
											pstmt11.setInt(1, ac1);
											pstmt11.setInt(2, ac2);
											pstmt11.setInt(3, ac3);
											pstmt11.setInt(4, passengerno+sl);
											pstmt11.setInt(5, gen);
										}
										else if(tickettype.equalsIgnoreCase("gen"))
										{
											pstmt11.setInt(1, ac1);
											pstmt11.setInt(2, ac2);
											pstmt11.setInt(3, ac3);
											pstmt11.setInt(4, sl);
											pstmt11.setInt(5, passengerno+gen);
											
										}
										//pstmt11.setString(8, trainno);
										
										
										pstmt11.executeUpdate();
										JOptionPane.showMessageDialog(null, "Update...");
										
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
						
						
						try 
						{
							Class.forName("com.mysql.jdbc.Driver");
							try 
							{
								Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306","root","");
								Statement stmt=con.createStatement();
								stmt.executeUpdate("Create database if not exists RailwayDb");
								stmt.execute("use RailwayDb");
								
								PreparedStatement pstmt=con.prepareStatement("select count(*) from TrainMasterTb where source=? and dest=? and train_name=?");
								pstmt.setString(1, cbsource.getSelectedItem().toString());
								pstmt.setString(2, cbdestination.getSelectedItem().toString());
								pstmt.setString(3,cbtrnm.getSelectedItem().toString());
								ResultSet rs=pstmt.executeQuery();
								
								int c=0;
								while(rs.next())
								{
									c=rs.getInt(1);
								}
								
								if(c==0)
								{
									PreparedStatement pstmt1=con.prepareStatement("select train_id from TrainMasterTb where source=? and dest=? and train_name=?");
									
									pstmt1.setString(1, cbsource.getSelectedItem().toString());
									pstmt1.setString(2, cbdestination.getSelectedItem().toString());
									pstmt1.setString(3,cbtrnm.getSelectedItem().toString());
									ResultSet rs1=pstmt1.executeQuery();
									
									while(rs1.next())
									{
										String table1=rs1.getString(1)+"Tb";
										
										PreparedStatement pstmt11=con.prepareStatement("select fare from "+table1+" where st_name=? ");
										
										pstmt11.setString(1, cbsource.getSelectedItem().toString());
								
										ResultSet rs11=pstmt11.executeQuery();
										
										while(rs11.next())
										{
											genfare=rs11.getInt(1);
										}
										
									}
									
								}
								else
								{
									PreparedStatement pstmt11=con.prepareStatement("select fare from TrainMasterTb where source=? and dest=? and train_name=?");
									
									pstmt11.setString(1, cbsource.getSelectedItem().toString());
									pstmt11.setString(2, cbdestination.getSelectedItem().toString());
									pstmt11.setString(3,cbtrnm.getSelectedItem().toString());
									
									ResultSet rs11=pstmt11.executeQuery();
									
									while(rs11.next())
									{
										genfare=rs11.getInt(1);
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
						
						if(tickettype.equalsIgnoreCase("ac1"))
						{
							fare=(genfare*(noa+nosc)-(genfare*nosc)*33/100)*10.0f;
						}
						else if(tickettype.equalsIgnoreCase("ac2"))
						{
							fare=(genfare*(noa+nosc)-(genfare*nosc)*33/100)*7.0f;
						}
						else if(tickettype.equalsIgnoreCase("ac3"))
						{
							fare=(genfare*(noa+nosc)-(genfare*nosc)*33/100)*5.0f;
						}
						else if(tickettype.equalsIgnoreCase("sl"))
						{
							fare=(genfare*(noa+nosc)-(genfare*nosc)*33/100)*3.0f;
						}
						else if(tickettype.equalsIgnoreCase("gen"))
						{
							fare=(genfare*(noa+nosc)-(genfare*nosc)*33/100)*1.0f;
						}
						
					try {
							Class.forName("com.mysql.jdbc.Driver");
							Connection con1=DriverManager.getConnection("jdbc:mysql://localhost:3306","root","");
							Statement stmt1=con1.createStatement();
							stmt1.executeUpdate("Create database if not exists RailwayDb");
							stmt1.execute("Use RailwayDb");   //database selection or open										
							
							PreparedStatement pstmt=con1.prepareStatement("select count(*) from UseridbookingTb");
							ResultSet rs=pstmt.executeQuery();
							
							int c=0;
							while(rs.next())
								c=rs.getInt(1);
		            
							if(c==0)
								pnr=1234567890;
							else
							{
								pstmt=con1.prepareStatement("select Pnr_no from UseridbookingTb");
								rs=pstmt.executeQuery();
								while(rs.next())
									pnr=rs.getInt(1);
								
								pnr++;
							}	
					
						
							
							userid=Login.username;
							PreparedStatement pstmt4=con1.prepareStatement("Insert into UseridBookingTb(username,PNR_No,DateOfBooking,source,destination,DateOfJourney, train_id,train_name,class,Totalseats, fare) values(?,?,?,?,?,?,?,?,?,?,?)");
							pstmt4.setString(1, userid);
							pstmt4.setInt(2, pnr);
							pstmt4.setDate(3, dt);
							pstmt4.setString(4, cbsource.getSelectedItem().toString());
							pstmt4.setString(5, cbdestination.getSelectedItem().toString());
							pstmt4.setDate(6,bdt);
							pstmt4.setString(7, cbtrnm.getSelectedItem().toString());
							pstmt4.setString(8, trainno);
							pstmt4.setString(9, cbclass.getSelectedItem().toString());
							pstmt4.setString(10, cbnoseat.getSelectedItem().toString());
							pstmt4.setFloat(11, fare);
							pstmt4.executeUpdate();
							
							JOptionPane.showMessageDialog(null, pnr+"hhhh");
							
							String usrid=userid+"Tb";
							for(int i=0;i<6;i++)
							{
								if(tname[i].getText().toString().equals(""))
									break;
								
								int age=Integer.parseInt(tage[i].getText().toString());
							
							PreparedStatement pstmt11=con1.prepareStatement("insert into "+usrid+"(PnrNo,name,age,gender,category)values(?,?,?,?,?)");
							
							JOptionPane.showMessageDialog(null, pnr+"h");
									
							pstmt11.setInt(1, pnr);
							pstmt11.setString(2, tname[i].getText().toString());
							pstmt11.setInt(3, age);
							
							if(rdmale[i].isSelected())
							pstmt11.setString(4, "male");
							else
								pstmt11.setString(4, "female");
							
							pstmt11.setString(5, tcategory[i].getText().toString());
							 
							pstmt11.executeUpdate();
							}
						
							JOptionPane.showMessageDialog(null, "Ticket Successfully Reserved,your Pnr is "+pnr+" ");

							con1.close();		
						
						}
						catch (SQLException e) 
						{
							e.printStackTrace();
						}
					 
					catch (ClassNotFoundException e)
					{
						e.printStackTrace();
						
					}
					validate();				
					
			}
	}
}
		
