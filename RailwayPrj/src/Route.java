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

public class Route extends JPanel implements ItemListener, ActionListener{

	JLabel lsource, ldestination;
	JComboBox cbsource, cbdestination;
	JButton btshow;
	JTable table;
	JScrollPane jsp;
	
	//public void init()
	public Route()
	{
		lsource=new JLabel("Source: ");
		ldestination=new JLabel("Destination: ");
		
		cbsource=new JComboBox();
		cbsource.addItem("Select Source Station");
		cbdestination=new JComboBox();
		cbdestination.addItem("Select Destination Station");
		
		btshow=new JButton("Show Route");
		
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
	
		setLayout(new FlowLayout());
 		add(ldestination);
		add(cbdestination);
		add(btshow);
		cbdestination.addItemListener(this);
		
		cbsource.addItemListener(this);
		btshow.addActionListener(this);
	
		
		
		}
	@Override
	public void itemStateChanged(ItemEvent IE) {
//		String source=cbsource.getSelectedItem().toString();
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
	public void actionPerformed(ActionEvent arg0) {
		Object[]colhead= {"st_name"};
		Object[][]rowdata=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306","root","");
				Statement stmt=con.createStatement();
				stmt.execute("Use RailwayDb"); 
				
				PreparedStatement pstmt=con.prepareStatement("Select train_id from TrainMasterTb where source=? and dest=? ");
				pstmt.setString(1,cbsource.getSelectedItem().toString());
				pstmt.setString(2,cbdestination.getSelectedItem().toString());
				
				ResultSet rs=pstmt.executeQuery();
				
				while(rs.next())
				{
						String trainid=rs.getString(1);
						String table1=trainid+"Tb";
				
						PreparedStatement pstmt1=con.prepareStatement("Select count(*) from "+ table1  );
						ResultSet rs1=pstmt1.executeQuery();
				 
						int c=0;
						while(rs1.next())
							c=rs1.getInt(1);
					
						if(c==0)
						{
							JOptionPane.showMessageDialog(null, "No Mid Stations in Route...");
							return;
						}
					
						rowdata=new Object[c][1];
					
						PreparedStatement pstmt2=con.prepareStatement("Select st_name from "+table1);
					
						ResultSet rs2=pstmt2.executeQuery();
						int i=0;
				
						while(rs2.next())
						{
							rowdata[i][0]=rs2.getString(1);
							i++;
						}
				
						if(jsp!=null)
						{
							jsp.remove(table);
							remove(jsp);
							validate();
						}
						
						table=new JTable(rowdata,colhead);
						jsp=new JScrollPane(table);
						add(jsp);
				}
					con.close();
					validate();
				
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
			

		
	}
	}