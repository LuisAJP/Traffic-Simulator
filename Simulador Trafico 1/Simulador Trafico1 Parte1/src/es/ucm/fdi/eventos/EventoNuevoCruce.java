package es.ucm.fdi.eventos;

import es.ucm.fdi.excepciones.ErrorDeSimulacion;
import es.ucm.fdi.mapaCarreteras.MapaCarreteras;
import es.ucm.fdi.objetoSimulacion.Cruce;

public class EventoNuevoCruce extends Evento {
	protected Cruce cruce;
	protected String id;
	
	public EventoNuevoCruce(int time, String id) {
		super(time);
		this.id=id;
	}
	
	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion {
		// crea el cruce y se lo añade al mapa
		cruce=new Cruce(this.id);
		mapa.addCruce(this.id, cruce);
	}
	
	public String getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return "Nuevo Cruce " + getId();
	}
	
	
//	public String toString() {
//		return "[new_junction]" + "\n" + "time = "+ this.tiempo + "\n"
//				+ "id: " + this.id + "\n";
//	}

}
