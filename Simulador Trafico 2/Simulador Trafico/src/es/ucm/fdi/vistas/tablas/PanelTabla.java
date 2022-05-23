package es.ucm.fdi.vistas.tablas;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import es.ucm.fdi.events.Evento;
import es.ucm.fdi.mapaCarreteras.MapaCarreteras;

public class PanelTabla<T> extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ModeloTabla<T> modelo;
	
	public PanelTabla(String bordeId, ModeloTabla<T> modelo){
		this.setLayout(new GridLayout(1,1));
		this.setBorder(getBorder());//lo he puesto asi no se si estara bien
		this.modelo = modelo;
		JTable tabla = new JTable(this.modelo);
		this.add(new JScrollPane(tabla));
		this.setBorde(bordeId);
		tabla.setShowGrid(false);
		tabla.setFillsViewportHeight(true);
	}

	public void setBorde(String titulo) {
		this.setBorder(BorderFactory.createTitledBorder(titulo));
	}
	
	public ModeloTabla<T> getModeloTabla() {
		return this.modelo;
	}
	
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos){
		this.modelo.avanza(tiempo, mapa, eventos);
	}
	
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos){
		this.modelo.addEvento(tiempo, mapa, eventos);
	}
	
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos){
		this.modelo.reinicia(tiempo, mapa, eventos);
	}
	
}
