package es.ucm.fdi.events;

import java.util.LinkedList;

import es.ucm.fdi.events.builders.ConstructorEventos;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.exceptions.RelacionCruceNoEncotrada;
import es.ucm.fdi.mapaCarreteras.MapaCarreteras;
import es.ucm.fdi.objetoSimulacion.cruces.CruceGenerico;
import es.ucm.fdi.objetoSimulacion.vehiculos.Vehiculo;

public class EventoNuevoVehiculo extends Evento {
	protected Vehiculo vehiculo;
	protected String id;
	protected Integer velocidadMaxima;
	protected String[] itinerario;
	
	public EventoNuevoVehiculo(int tiempo, String id, int velocidadMaxima, String[] itinerario) {
		super(tiempo);
		this.id=id;
		this.velocidadMaxima=velocidadMaxima;
		this.itinerario=itinerario;
	}
	
	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion {
		//duda de si siempre sigue ese orden de creacion cruce,carretera y vehiculo
		LinkedList<CruceGenerico<?>> iti = ConstructorEventos.parseaListaCruces(this.itinerario,mapa);
		// si iti es null o tiene menos de dos cruces lanzar excepción
		if(iti.isEmpty() || iti.size()<2|| velocidadMaxima <= 0) {
			throw new ErrorDeSimulacion(" Error!!");	
		}
		// en otro caso crear el vehículo y añadirlo al mapa.
		vehiculo= new Vehiculo(this.id,velocidadMaxima,iti, null);
		try {
			mapa.addVehiculo(this.id, vehiculo);
		} catch (RelacionCruceNoEncotrada e) {
			throw new ErrorDeSimulacion(e.getMessage());		}
	}
	
	public String getId() {
		return this.id;
	}
	
	
	
	@Override
	public String toString() {
		return "Nuevo Vehiculo "+ getId();
		
	}


	
}
