package es.ucm.fdi.events;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.mapaCarreteras.MapaCarreteras;
import es.ucm.fdi.objetoSimulacion.carreteras.Carretera;
import es.ucm.fdi.objetoSimulacion.cruces.CruceGenerico;

public class EventoNuevaCarretera extends Evento {
	protected Carretera carretera;
	protected String id;
	protected Integer velocidadMaxima;
	protected Integer longitud;
	protected String cruceOrigenId;
	protected String cruceDestinoId;
	protected String tipo;

	public EventoNuevaCarretera(int tiempo, String id, String origen, String destino, int velocidadMaxima,int longitud) {
		super(tiempo);
		this.id = id;
		this.cruceOrigenId = origen;
		this.cruceDestinoId = destino;
		this.velocidadMaxima = velocidadMaxima;
		this.longitud = longitud;

	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion {
		// obten cruce origen y cruce destino utilizando el mapa
		CruceGenerico<?> cruceO = mapa.getCruce(cruceOrigenId);
		CruceGenerico<?> cruceD = mapa.getCruce(cruceDestinoId);
		// crea la carretera
		carretera= creaCarretera( cruceO, cruceD);//crea el tipo de carretera Autopista/camino

		// añade al mapa la carretera
		mapa.addCarretera(id, carretera.getCruceOrigen(), carretera, carretera.getCruceDestino());
	}

	protected Carretera creaCarretera(CruceGenerico<?> cruceOrigen, CruceGenerico<?> cruceDestino) {
		return new Carretera(this.id, this.longitud, this.velocidadMaxima, cruceOrigen, cruceDestino,null);
	}

	public String getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Nueva Carretera " + getId();
	}

}
