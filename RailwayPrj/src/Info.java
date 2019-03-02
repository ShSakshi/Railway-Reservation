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

public class Info extends JPanel implements ActionListener{

	JLabel linfo;
	JButton btroute, btroutesch, btalltrain;
	JPanel pn1, pn2, Pn, pmain;
	CardLayout clo;
	
	public Info()
	//public void init()
	{
		linfo=new JLabel("Information Section");
		btroute=new JButton("Route");
		btroutesch=new JButton("Route Schedule");
		btalltrain=new JButton("Show All Train");
		
		pn1=new JPanel();
		pn1.setBackground(Color.magenta);
		pn1.add(linfo);
		
		pn2=new JPanel();
		pn2.setLayout(new GridLayout(1, 3));
		pn2.add(btroute);
		pn2.add(btroutesch);
		pn2.add(btalltrain);
		
		Pn=new JPanel();
		Pn.setLayout(new GridLayout(2,1));
		Pn.add(pn1);
		Pn.add(pn2);
		
		clo=new CardLayout();
		
		pmain=new JPanel();
		pmain.setLayout(clo);
		pmain.add("Route", new Route());
		pmain.add("Route Schedule", new RouteSchedule());
		pmain.add("Show All Train", new ShowAllTrain());
		
		
		
		setLayout(new BorderLayout());
		add(Pn,BorderLayout.NORTH);
		add(pmain,BorderLayout.CENTER);
		
		btroute.addActionListener(this);
		btroutesch.addActionListener(this);
		btalltrain.addActionListener(this);
		
	}
	@Override
	public void actionPerformed(ActionEvent AE) {
		
		JButton src=(JButton) AE.getSource();
		clo.show(pmain, src.getLabel());
		validate();
		
	}

}
