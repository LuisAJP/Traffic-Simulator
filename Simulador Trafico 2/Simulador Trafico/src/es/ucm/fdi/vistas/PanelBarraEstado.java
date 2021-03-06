package es.ucm.fdi.vistas;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.ucm.fdi.controlador.Controlador;
import es.ucm.fdi.events.Evento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.mapaCarreteras.MapaCarreteras;
import es.ucm.fdi.modelo.ObservadorSimuladorTrafico;

public class PanelBarraEstado extends JPanel implements ObservadorSimuladorTrafico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel infoEjecucion;
	
	public PanelBarraEstado(String mensaje, Controlador controlador) {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.infoEjecucion = new JLabel(mensaje);
		this.add(this.infoEjecucion);
		this.setBorder(BorderFactory.createBevelBorder(1));
		controlador.addObserver(this);
	}
	
	// la ventana principal se comunica con el panel
	public void setMensaje(String mensaje) { 
												

	}
	
	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.infoEjecucion.setText("Paso: " + tiempo + " del Simulador");
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.infoEjecucion.setText("Evento a?adido al simulador");
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {

	}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map,
			List<Evento> eventos, ErrorDeSimulacion e) {
		// TODO Auto-generated method stub
		
	}

}
