package es.ucm.fdi.events;

import es.ucm.fdi.objetoSimulacion.cruces.CruceCircular;
import es.ucm.fdi.objetoSimulacion.cruces.CruceGenerico;

public class EventoNuevoCruceCircular extends EventoNuevoCruce {
	protected Integer maxValorIntervalo;
	protected Integer minValorIntervalo;

	public EventoNuevoCruceCircular(int time, String id, int minValorIntervalo, int maxValorIntervalo) {
		super(time, id);
		this.maxValorIntervalo = maxValorIntervalo;
		this.minValorIntervalo = minValorIntervalo;
	}

	@Override
	protected CruceGenerico<?> creaCruce() {
		return new CruceCircular(this.id, this.minValorIntervalo, this.maxValorIntervalo);
	}
	
	@Override
	public String toString() {
		return "Nuevo Cruce Circular "+ getId();
		
	}
}