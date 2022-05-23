package es.ucm.fdi.eventos;

import java.util.List;

import es.ucm.fdi.excepciones.ErrorDeSimulacion;
import es.ucm.fdi.mapaCarreteras.MapaCarreteras;
import es.ucm.fdi.objetoSimulacion.Cruce;
import es.ucm.fdi.objetoSimulacion.Vehiculo;

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
		List<Cruce> iti = ParserCarreteras.parseaListaCruces(this.itinerario,mapa);
		// si iti es null o tiene menos de dos cruces lanzar excepción
		if(iti.isEmpty() || iti.size()<2) {
			throw new ErrorDeSimulacion(" Error!! lista de vehiculos es nulo o es menor que 2");	
		}
		// en otro caso crear el vehículo y añadirlo al mapa.
		vehiculo= new Vehiculo(this.id,velocidadMaxima,iti);
		mapa.addVehiculo(this.id, vehiculo);
	}
	
	public String getId() {
		return this.id;
	}
	
	
	
	@Override
	public String toString() {
		return "Nuevo Vehiculo "+ getId();
		
	}


	
}
