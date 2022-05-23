package es.ucm.fdi.vistas.tablas;

import java.util.List;

import javax.swing.SwingUtilities;

import es.ucm.fdi.controlador.Controlador;
import es.ucm.fdi.events.Evento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.mapaCarreteras.MapaCarreteras;
import es.ucm.fdi.objetoSimulacion.cruces.CruceGenerico;

public class ModeloTablaCruces extends ModeloTabla<CruceGenerico<?>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModeloTablaCruces(String[] columnIdEventos, Controlador ctrl) {
		super(columnIdEventos, ctrl);
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Override // necesario para que se visualicen los datos
	public Object getValueAt(int indiceFil, int indiceCol) {
	Object s = null;
		switch (indiceCol) {
		case 0:
			s = this.lista.get(indiceFil).getId() ;
			break;
		 case 1:
			 for(int i=0;!this.lista.get(indiceFil).getCarreteras().isEmpty()&&   i<this.lista.get(indiceFil).getCarreteras().size();i++) {
				 if(this.lista.get(indiceFil).getCarreteras().get(i).tieneSemaforoVerde()) {
					 s= this.lista.get(indiceFil).getCarreteras().get(i);
				 }
			 }
		
		
		 break;
		case 2:
			 for(int i=0;!this.lista.get(indiceFil).getCarreteras().isEmpty()&&   i<this.lista.get(indiceFil).getCarreteras().size();i++) {
				 if(!this.lista.get(indiceFil).getCarreteras().get(i).tieneSemaforoVerde()) {
					 s= this.lista.get(indiceFil).getCarreteras().get(i);
				 }
			 }
		default:
			assert (false);
	}
	return s;
	}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				lista = mapa.getCruces();
				fireTableStructureChanged();

			}

		});

	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				lista=null;
				fireTableStructureChanged();

			}

		});


	}

}
