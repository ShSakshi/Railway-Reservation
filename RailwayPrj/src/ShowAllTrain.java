import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JApplet;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ShowAllTrain extends JPanel implements ItemListener{

	JLabel lsource, ldestination;
	JComboBox cbsource, cbdestination;
	JTable table, subTable;
	JScrollPane jsp, jsp1;
	
	//public void init()
	public ShowAllTrain()
	{
		lsource=new JLabel("Source: ");
		ldestination=new JLabel("Destination: ");
		
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
	
		
		setLayout(new FlowLayout());
		add(lsource);
		add(cbsource);
	
		add(ldestination);
		add(cbdestination);
	
		cbdestination.addItemListener(this);
		
		cbsource.addItemListener(this);
		
	}

	@Override
	public void itemStateChanged(ItemEvent IE)
	{
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
			Object[]colhead= {"ttrnid", "ttrnnm", "source","dest","arrival_time","dept_time"};
			Object[][]rowdata=null;
				
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
			//return;
		}
		else
		{
		rowdata=new Object[c][6];
		PreparedStatement pstmt1=con.prepareStatement("Select * from TrainMasterTb where source=? and dest=? order by arrival_time asc");
		pstmt1.setString(1,cbsource.getSelectedItem().toString());
		pstmt1.setString(2,cbdestination.getSelectedItem().toString());
		 rs=pstmt1.executeQuery();
			
		int i=0;
		while(rs.next())
		{
			rowdata[i][0]=rs.getString(1);
			rowdata[i][1]=rs.getString(2);
			rowdata[i][2]=rs.getString(3);
			rowdata[i][3]=rs.getString(4);
			rowdata[i][4]=rs.getString(5);
			rowdata[i][5]=rs.getString(6);
			i++;
			
		}
		if(table !=null)
		{
			jsp.remove(table);
			remove(jsp);
			validate();
		}

		table=new JTable(rowdata,colhead);
		jsp=new JScrollPane(table);

		add(jsp);

		}
		

		Object[]colhead1= {"train_name","st_name","arrival_time","dept_time"};
		Object[][]rowdata1=null;
	
		PreparedStatement pstmt2=con.prepareStatement("Select train_id,train_name from TrainMasterTb where dest=?");
		pstmt2.setString(1,cbdestination.getSelectedItem().toString());

		ResultSet rs2=pstmt2.executeQuery();
		while(rs2.next())
		{
		String trName=rs2.getString(2);
		String trainid=rs2.getString(1);
		String table1=trainid+"Tb";
				
		PreparedStatement pstmt3=con.prepareStatement("Select count(*) from "+table1 + " where st_name=?");
		pstmt3.setString(1,cbsource.getSelectedItem().toString());

		ResultSet rs1=pstmt3.executeQuery();
		int c1=0;
		while(rs1.next())
			c1=rs1.getInt(1);
		
		
		//JOptionPane.showMessageDialog(null, c+"");
		if(c1!=0)
		{
			
			rowdata1=new Object[c1][4];
			PreparedStatement pstmt5=con.prepareStatement("Select * from "+ table1 + " where st_name=? order by arrival_time asc");
			pstmt5.setString(1,cbsource.getSelectedItem().toString());
			ResultSet rs11=pstmt5.executeQuery();
			
			int i1=0;
			while(rs11.next())
			{
				rowdata1[i1][0]=trName;
				rowdata1[i1][1]=rs11.getString(1);
				rowdata1[i1][2]=rs11.getString(2);
				rowdata1[i1][3]=rs11.getString(3);
				i1++;
				
			}
	
		if(subTable!=null)
		{
			jsp1.remove(subTable);
			remove(jsp1);
			validate();
		}
		subTable=new JTable(rowdata1,colhead1);
		jsp1=new JScrollPane(subTable);
		add(jsp1);
		validate();
		}
		}
		con.close();
		

		}
	catch (ClassNotFoundException e)
	{
		e.printStackTrace();
	}
	catch (SQLException e1) 
	{
		e1.printStackTrace();
	}	
	
		}
	}
}
