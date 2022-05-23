package es.ucm.fdi.objetoSimulacion.vehiculos;

import java.util.LinkedList;

import es.ucm.fdi.objetoSimulacion.cruces.CruceGenerico;

public class Bicicleta extends Vehiculo {

	public Bicicleta(String id, int velocidadMaxima, LinkedList<CruceGenerico<?>> iti) {
		super(id, velocidadMaxima, iti, "bike");
		// TODO Auto-generated constructor stub
	}
	
	// ----------------------------------------------------------------------------------------
	
	@Override
	public void setTiempoAveria(int duracion) {
		if (this.velocidadActual >= this.velocidadMaxima / 2) {
			super.setTiempoAveria(duracion);
		}
	}
}
	