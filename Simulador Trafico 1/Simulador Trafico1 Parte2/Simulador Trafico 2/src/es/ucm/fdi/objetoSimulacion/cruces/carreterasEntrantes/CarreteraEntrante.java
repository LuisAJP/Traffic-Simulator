package es.ucm.fdi.objetoSimulacion.cruces.carreterasEntrantes;
import java.util.LinkedList;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.objetoSimulacion.carreteras.Carretera;
import es.ucm.fdi.objetoSimulacion.vehiculos.Vehiculo;

public class CarreteraEntrante {
	protected Carretera carretera;
	protected LinkedList<Vehiculo> colaVehiculos;
	protected boolean semaforo; // true=verde, false=rojo
	
	public CarreteraEntrante(Carretera carretera) {
	// inicia los atributos.
	// el semáforo a rojo
		this.carretera =carretera;
		this.colaVehiculos = new LinkedList<Vehiculo>();
		/**al principio todos los semaforos estaran en rojo*/
		
		semaforo=false;
	}
	
	public void ponSemaforo(boolean color) {
		this.semaforo=color;
	}
	
	public boolean getSemaforo() {
		return this.semaforo;
	}
	
	public void avanzaPrimerVehiculo() throws ErrorDeSimulacion  {
		if(!colaVehiculos.isEmpty()) {
			
			// coge el primer vehiculo de la cola, lo elimina,
			Vehiculo v=colaVehiculos.get(0);
			colaVehiculos.remove(0);
		// y le manda que se mueva a su siguiente carretera.
				v.moverASiguienteCarretera();
		}
	
	}
	
	@Override
	public String toString() {
		String color;
		if(this.semaforo) {
			color="green";
		}
		else {
			color="red";
		}
		return carretera.getId() +", " + color + "," + colaVehiculos ;
	}
	
	/***/
	public Carretera getCarretera() {
		return this.carretera;
	}
	
	public LinkedList<Vehiculo> getColaVehiculos(){
		return this.colaVehiculos;
	}
}
