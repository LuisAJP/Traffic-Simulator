package es.ucm.fdi.vistas;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelBotones extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton botonCancelar,botonGenerar;
	
	public PanelBotones(DialogoInformes dialogoInformes) {
		// TODO Auto-generated constructor stub
		botonCancelar= new JButton("Cancelar");
		botonGenerar= new JButton("Generar");
		this.add(botonCancelar);
		this.add(botonGenerar);
	}

}
