package es.ucm.fdi.events;

import es.ucm.fdi.mapaCarreteras.MapaCarreteras;
import es.ucm.fdi.objetoSimulacion.vehiculos.Vehiculo;

public class EventoAveriaCoche extends Evento {
	protected String[] vehiculos;
	protected int duracion;
	
	public EventoAveriaCoche(int tiempo, String[] vehiculos, int duracion) {
		super(tiempo);
		this.vehiculos=vehiculos;
		this.duracion=duracion;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void ejecuta(MapaCarreteras mapa) {
		// TODO Auto-generated method stub
		for(String v: vehiculos) {
			Vehiculo vehiculo=mapa.getVehiculo(v);
			vehiculo.setTiempoAveria(duracion);
			vehiculo.setAveriado(true);
		}
	}
	
	
	
	public String toString() {	
		return "Averia Coche";
	}
}
