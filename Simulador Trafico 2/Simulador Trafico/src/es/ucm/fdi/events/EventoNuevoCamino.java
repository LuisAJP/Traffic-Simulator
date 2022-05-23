package es.ucm.fdi.events;


import es.ucm.fdi.objetoSimulacion.carreteras.Camino;
import es.ucm.fdi.objetoSimulacion.carreteras.Carretera;
import es.ucm.fdi.objetoSimulacion.cruces.CruceGenerico;

public class EventoNuevoCamino extends EventoNuevaCarretera{
	
	protected String type;
	
	public EventoNuevoCamino(int tiempo, String id, String origen, String destino, int velocidadMaxima, int longitud, String type) {
		super(tiempo, id, origen, destino, velocidadMaxima, longitud);
		// TODO Auto-generated constructor stub
		this.type=type;
	}
	
	@Override
	protected Carretera creaCarretera(CruceGenerico<?> cruceOrigen,CruceGenerico<?> cruceDestino) {
		return new Camino(this.id, this.longitud, this.velocidadMaxima,cruceOrigen, cruceDestino, type);
	}
}
