package es.ucm.fdi.vistas;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public abstract class PanelAreaTexto extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JTextArea areatexto;
	
	public PanelAreaTexto(String titulo, boolean editable) {
		this.setLayout(new GridLayout(1, 1));
		this.areatexto = new JTextArea(40, 30);
		this.areatexto.setEditable(editable);
		this.add(new JScrollPane(areatexto,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));
		this.setBorde(titulo);
	}
	
	public void setBorde(String titulo) {
		this.setBorder(BorderFactory.createTitledBorder(titulo));
	}
	
	
	public String getTexto() {
		return this.areatexto.getText();
		
	}
	
	public void limpiar() {
		this.setTexto("");
	}
	
	public void inserta(String valor) {
		this.areatexto.setText(valor);
		
	}

	public void setTexto(String s) {
		this.inserta(s);
	}
	
	public void setInforme(String s) {
		limpiar();
		setTexto(s);
	}

	public String getContenido() {
		return this.areatexto.getText();
	}
	
}
