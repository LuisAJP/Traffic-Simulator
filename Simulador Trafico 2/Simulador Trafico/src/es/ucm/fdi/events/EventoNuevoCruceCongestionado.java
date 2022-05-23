package es.ucm.fdi.events;


import es.ucm.fdi.objetoSimulacion.cruces.CruceCongestionado;
import es.ucm.fdi.objetoSimulacion.cruces.CruceGenerico;
//import es.ucm.fdi.objetoSimulacion.CruceCongestionado;
//import es.ucm.fdi.objetoSimulacion.CruceGenerico;

public class EventoNuevoCruceCongestionado extends EventoNuevoCruce {

	public EventoNuevoCruceCongestionado(int time, String id) {
		super(time, id);
	}

//	@Override
//	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion {
//		// TODO Auto-generated method stub
//
//	}

	@Override
	protected CruceGenerico<?> creaCruce() {
		return new CruceCongestionado(this.id);
	}
	
	@Override
	public String toString() {
		return "Nuevo Cruce Congestionado "+ getId();
		
	}

}
