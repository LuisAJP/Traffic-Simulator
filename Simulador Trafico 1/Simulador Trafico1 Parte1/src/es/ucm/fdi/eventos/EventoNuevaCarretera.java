package es.ucm.fdi.eventos;

import es.ucm.fdi.excepciones.ErrorDeSimulacion;
import es.ucm.fdi.mapaCarreteras.MapaCarreteras;
import es.ucm.fdi.objetoSimulacion.Carretera;
import es.ucm.fdi.objetoSimulacion.Cruce;

public class EventoNuevaCarretera extends Evento{
	protected Carretera carretera;
	protected String id;
	protected Integer velocidadMaxima;
	protected Integer longitud;
	protected String cruceOrigenId;
	protected String cruceDestinoId;
	
	
	public EventoNuevaCarretera(int tiempo, String id, String origen,String destino, int velocidadMaxima, int longitud) {
		super(tiempo);
		this.id=id;
		this.cruceOrigenId=origen;
		this.cruceDestinoId=destino;
		this.velocidadMaxima=velocidadMaxima;
		this.longitud=longitud;
	}
	
	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion {
		// obten cruce origen y cruce destino utilizando el mapa
		Cruce cruceO = mapa.getCruce(cruceOrigenId);
		Cruce cruceD = mapa.getCruce(cruceDestinoId);
		// crea la carretera
		carretera= new Carretera(this.id,this.longitud,this.velocidadMaxima,cruceO,cruceD);
		
		// añade al mapa la carretera
		mapa.addCarretera(id, carretera.getCruceOrigen(), carretera, carretera.getCruceDestino());
	}
	

	public String getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return "Nueva Carretera " + getId();
	}
	
}
