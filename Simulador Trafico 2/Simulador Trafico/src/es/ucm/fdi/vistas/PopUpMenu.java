package es.ucm.fdi.vistas;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import es.ucm.fdi.events.ParserEventos;
import es.ucm.fdi.events.builders.ConstructorEventos;


public class PopUpMenu extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PopUpMenu(VentanaPrincipal mainWindow) {
		//JMenu plantillas = new JMenu("Nueva plantilla");
		 JMenuItem plantillas = new  JMenuItem("Nueva plantilla");
		 plantillas.addActionListener(this);
		 this.add(plantillas);
		
//		for (ConstructorEventos ce : ParserEventos.getConstrutoresEventos()) {
//			JMenuItem mi = new JMenuItem(ce.toString());
//			mi.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				mainWindow.inserta(ce.template() + System.lineSeparator());
//			}
//			});
//			plantillas.add(mi);
		
	}

	public void show(Component component, int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
