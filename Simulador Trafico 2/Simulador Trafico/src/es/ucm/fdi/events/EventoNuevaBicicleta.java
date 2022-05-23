package es.ucm.fdi.events;

import java.util.LinkedList;

import es.ucm.fdi.events.builders.ConstructorEventos;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.exceptions.RelacionCruceNoEncotrada;
import es.ucm.fdi.mapaCarreteras.MapaCarreteras;
import es.ucm.fdi.objetoSimulacion.cruces.CruceGenerico;
import es.ucm.fdi.objetoSimulacion.vehiculos.Bicicleta;

public class EventoNuevaBicicleta extends Evento {
	protected Bicicleta bici;
	protected String id;
	protected Integer velocidadMaxima;
	protected String[] itinerario;
	protected String type;

	public EventoNuevaBicicleta(int tiempo, String id, int velocidadMaxima, String[] itinerario,String type1) {
		super(tiempo);
		this.id = id;
		this.velocidadMaxima = velocidadMaxima;
		this.itinerario = itinerario;
		this.type=type1;
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion {
		// duda de si siempre sigue ese orden de creacion cruce,carretera y vehiculo
		LinkedList<CruceGenerico<?>> iti = ConstructorEventos.parseaListaCruces(this.itinerario, mapa);
		// si iti es null o tiene menos de dos cruces lanzar excepción
		if (iti.isEmpty() || iti.size() < 2 || velocidadMaxima <= 0) {
			throw new ErrorDeSimulacion(" Error!!");
		}
		// en otro caso crear el vehículo y añadirlo al mapa.
		bici = new Bicicleta(this.id, velocidadMaxima, iti);
		try {
			mapa.addVehiculo(this.id, bici);
		} catch (RelacionCruceNoEncotrada e) {
			throw new ErrorDeSimulacion(e.getMessage());
		}
	}
	
	public String getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return "Nueva Bicicleta "+ getId();
		
	}

}
