package juanhg.eog.machine;

import java.awt.Color;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;

import juanhg.eog.machine.listener.IEOG;


public class EOGPanelApplication extends JApplet implements IEOG, Runnable {
	
	EOGModel model;
	public final String rightFile = "D:/rFile.txt";
	public final String leftFile = "D:/lFile.txt";
	public final String upFile = "D:/uFile.txt";
	public final String downFile = "D:/dFile.txt";
	
	private JPanel rPanel, lPanel, uPanel, dPanel, bPanel;
	
	//Thread that executed the simlation
    private Thread flujo = null;
	
	public void init(){
		try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    initComponents();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        repaint();
	}

	
	public void initComponents(){
		setSize(1000,610);
		autogeneratedCode();
		try {
			model = new EOGModel(rightFile, leftFile, upFile, downFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		flujo = new Thread(this);
		flujo.start();
		
	}

	
	private void autogeneratedCode(){
		lPanel = new JPanel();
		lPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		uPanel = new JPanel();
		uPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		rPanel = new JPanel();
		rPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		dPanel = new JPanel();
		dPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		bPanel = new JPanel();
		bPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lPanel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(dPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)
						.addComponent(uPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)
						.addComponent(bPanel, GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rPanel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(lPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(rPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(uPanel, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(bPanel, GroupLayout.PREFERRED_SIZE, 360, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(dPanel, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)))
					.addGap(16))
		);
		getContentPane().setLayout(groupLayout);
	}
	
	@Override
	public void rightEvent() {
		if(rPanel.getBackground() == Color.red){
			rPanel.setBackground(Color.white);
		}
		else{
			rPanel.setBackground(Color.red);
		}
		repaint();
		System.out.println("Derecha");

	}

	@Override
	public void leftEvent() {
		if(lPanel.getBackground() == Color.red){
			lPanel.setBackground(Color.white);
		}
		else{
			lPanel.setBackground(Color.red);
		}
		repaint();
		System.out.println("Izquierda");

	}

	@Override
	public void upEvent() {
		uPanel.setBackground(Color.red);// TODO Auto-generated method stub
		repaint();
	}

	@Override
	public void downEvent() {
		dPanel.setBackground(Color.red);
		repaint();
	}

	@Override
	public void blinkEvent() {
		bPanel.setBackground(Color.red);
	}


	@Override
	public void run() {
		while(true){
			try {
				model.simulate(this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
