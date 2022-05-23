package es.ucm.fdi.events;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.mapaCarreteras.MapaCarreteras;
import es.ucm.fdi.objetoSimulacion.cruces.Cruce;

public class EventoNuevoCruce extends Evento {
	protected String id;
	protected Cruce cruce;

	public EventoNuevoCruce(int time, String id) {
		super(time);
		this.id = id;
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion {
		// crea el cruce y se lo añade al mapa
		cruce = new Cruce(this.id);
		mapa.addCruce(this.id, cruce);
	}

	public String getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Nuevo Cruce " + getId();
	}

	// public String toString() {
	// return "[new_junction]" + "\n" + "time = "+ this.tiempo + "\n"
	// + "id: " + this.id + "\n";
	// }

}
