package es.ucm.fdi.events;

import java.util.LinkedList;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
//import es.ucm.fdi.exceptions.ElementoYaAñadido;
import es.ucm.fdi.exceptions.RelacionCruceNoEncotrada;
import es.ucm.fdi.events.builders.ConstructorEventos;
import es.ucm.fdi.mapaCarreteras.MapaCarreteras;
import es.ucm.fdi.objetoSimulacion.cruces.CruceGenerico;
import es.ucm.fdi.objetoSimulacion.vehiculos.Coche;

public class EventoNuevoCoche extends Evento {
	protected String id;
	protected int velMax;
	protected String[] itinerario;
	protected String type;
	protected int resistencia;
	protected double probabilidadAveria;
	protected int duracionMaximaAveria;
	protected long seed;
	 

	public EventoNuevoCoche(int tiempo, String id,String[] iti, int velocidadMaxima, int res,
			double fault_probability, int duracionMax, long semilla, String type) {
		super(tiempo);
		this.id = id;
		this.velMax = velocidadMaxima;
		itinerario = iti;
		
		this.resistencia = res;
		this.probabilidadAveria = fault_probability;
		this.duracionMaximaAveria = duracionMax;
		this.seed = semilla;
		this.type = type;
		
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion {
		LinkedList<CruceGenerico<?>> iti = ConstructorEventos.parseaListaCruces(this.itinerario, mapa);
		if (iti.size() <= 1)
			throw new ErrorDeSimulacion("No hay suficientes cruces para generar la ruta");
		// String id, int velocidadMaxima, int resistencia, double probabilidad, long
		// semilla, int duracionMaximaInfraccion, LinkedList<Cruce/*Generico<?>*/>
		// itinerario
		Coche v = new Coche(this.id, velMax, resistencia, probabilidadAveria, seed, duracionMaximaAveria, iti);
		try {
			mapa.addVehiculo(this.id, v);
			// v.moverASiguienteCarretera();
			// } catch (ElementoYaAñadido e) {
			// throw new ErrorDeSimulacion(e.getMessage());
		} catch (RelacionCruceNoEncotrada e1) {
			throw new ErrorDeSimulacion(e1.getMessage());
		}

	}

}
