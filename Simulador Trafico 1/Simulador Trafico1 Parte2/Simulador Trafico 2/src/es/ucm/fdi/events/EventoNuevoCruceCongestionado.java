package es.ucm.fdi.events;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.mapaCarreteras.MapaCarreteras;
//import es.ucm.fdi.objetoSimulacion.CruceCongestionado;
//import es.ucm.fdi.objetoSimulacion.CruceGenerico;

public class EventoNuevoCruceCongestionado extends EventoNuevoCruce {



	public EventoNuevoCruceCongestionado(int time, String id) {
		super(time, id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion {
		// TODO Auto-generated method stub

	}
	
//	@Override
//	protected CruceGenerico<?> creaCruce() {
//		return new CruceCongestionado(this.id);
//	}

}
