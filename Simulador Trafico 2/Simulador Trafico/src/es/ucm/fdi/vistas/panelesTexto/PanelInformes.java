package es.ucm.fdi.vistas.panelesTexto;

import java.util.List;

import es.ucm.fdi.controlador.Controlador;
import es.ucm.fdi.events.Evento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.mapaCarreteras.MapaCarreteras;
import es.ucm.fdi.modelo.ObservadorSimuladorTrafico;
import es.ucm.fdi.vistas.PanelAreaTexto;

public class PanelInformes extends PanelAreaTexto implements ObservadorSimuladorTrafico {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PanelInformes(String titulo, boolean editable, Controlador ctrl) {
		super(titulo, editable);
		ctrl.addObserver(this); // se añade como observador
	}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map,
			List<Evento> eventos, ErrorDeSimulacion e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		
	}

}
