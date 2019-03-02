import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Enquiry extends JPanel implements ActionListener{
	
	JLabel lenquiry;
	JButton btpnrst, btfare, btsummary;
	JPanel pn1, pn2, Pn, pmain;
	CardLayout clo;
	
	public Enquiry()
	//public void init()
	{
		lenquiry=new  JLabel("Enquiry Section");
		
		btpnrst=new JButton("PNR Status");
		btfare=new JButton("Fare Enquiry");
		btsummary=new JButton("Summary Chart");
		
		pn1=new JPanel();
		pn1.setBackground(Color.blue);
		pn1.add(lenquiry);
		
		pn2=new JPanel();
		pn2.setLayout(new GridLayout(1, 3));
		pn2.add(btpnrst);
		pn2.add(btfare);
		pn2.add(btsummary);
		
		Pn=new JPanel();
		Pn.setLayout(new GridLayout(2,1));
		Pn.add(pn1);
		Pn.add(pn2);
		
		clo=new CardLayout();
		
		pmain=new JPanel();
		pmain.setLayout(clo);
		pmain.add("PNR Status", new GetPnrst());
		pmain.add("Fare Enquiry", new FareEnqiury());
		pmain.add("Summary Chart", new SummaryChart());

		
		setLayout(new BorderLayout());
		add(Pn,BorderLayout.NORTH);
		add(pmain,BorderLayout.CENTER);
		
		btpnrst.addActionListener(this);
		btfare.addActionListener(this);
		btsummary.addActionListener(this);
		
	
	}

	@Override
	public void actionPerformed(ActionEvent AE) {
		
		JButton src=(JButton) AE.getSource();
		clo.show(pmain, src.getLabel());
		validate();
		
	}

}
