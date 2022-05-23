package es.ucm.fdi.objetoSimulacion;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.excepciones.ErrorDeSimulacion;

public class CarreteraEntrante {
	protected Carretera carretera;
	protected List<Vehiculo> colaVehiculos;
	protected boolean semaforo; // true=verde, false=rojo
	
	public CarreteraEntrante(Carretera carretera) {
	// inicia los atributos.
	// el semáforo a rojo
		this.carretera =carretera;
		this.colaVehiculos = new ArrayList<Vehiculo>();
		/**al principio todos los semaforos estaran en rojo*/
		
		semaforo=false;
	}
	
	void ponSemaforo(boolean color) {
		this.semaforo=color;
	}
	
	public boolean getSemaforo() {
		return this.semaforo;
	}
	
	public void avanzaPrimerVehiculo()  {
		if(!colaVehiculos.isEmpty()) {
			
			// coge el primer vehiculo de la cola, lo elimina,
			Vehiculo v=colaVehiculos.get(0);
			colaVehiculos.remove(0);
		// y le manda que se mueva a su siguiente carretera.
			try {
				v.moverASiguienteCarretera();
			} catch (ErrorDeSimulacion e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
	public List<Vehiculo> getColaVehiculos(){
		return this.colaVehiculos;
	}
}
