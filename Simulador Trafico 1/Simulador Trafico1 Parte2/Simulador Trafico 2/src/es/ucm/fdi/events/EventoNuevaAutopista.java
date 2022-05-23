package es.ucm.fdi.events;

import es.ucm.fdi.objetoSimulacion.carreteras.Autopista;
import es.ucm.fdi.objetoSimulacion.carreteras.Carretera;
import es.ucm.fdi.objetoSimulacion.cruces.CruceGenerico;

public class EventoNuevaAutopista extends EventoNuevaCarretera{
	protected Integer numCarriles;
	protected String type;
	
	public EventoNuevaAutopista(int tiempo, String id, String origen, String destino, int velocidadMaxima,int longitud,int numCarriles, String type) {
		super(tiempo, id, origen, destino, velocidadMaxima, longitud);
		// TODO Auto-generated constructor stub
		this.numCarriles=numCarriles;
		this.type =type;
	}
	
	@Override
	protected Carretera creaCarretera(CruceGenerico<?> cruceOrigen,CruceGenerico<?> cruceDestino) {
		return new Autopista(this.id, this.longitud, this.velocidadMaxima,cruceOrigen, cruceDestino, numCarriles,this.type);
	}
}
