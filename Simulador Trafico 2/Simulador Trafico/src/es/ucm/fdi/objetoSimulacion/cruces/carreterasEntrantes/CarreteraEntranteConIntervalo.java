package es.ucm.fdi.objetoSimulacion.cruces.carreterasEntrantes;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.objetoSimulacion.carreteras.Carretera;
import es.ucm.fdi.objetoSimulacion.vehiculos.Vehiculo;

public class CarreteraEntranteConIntervalo extends CarreteraEntrante {
	private int intervaloDeTiempo;
	// Tiempo que ha de transcurrir para poner
	// el semáforo de la carretera en rojo

	private int unidadesDeTiempoUsadas;
	// Se incrementa cada vez que
	// avanza un vehículo

	private boolean usoCompleto;
	// Controla que en cada paso con el semáforo
	// en verde, ha pasado un vehículo

	private boolean usadaPorUnVehiculo;
	// Controla que al menos ha pasado un
	// vehículo mientras el semáforo estaba en verde.

	public CarreteraEntranteConIntervalo(Carretera carretera, int intervalo, int uso) {
		super(carretera);
		this.intervaloDeTiempo = intervalo;
		this.unidadesDeTiempoUsadas = uso;
		// revisar por si es necesario introducir un valor inicial a las
		// variables booleanas

	}

	@Override
	public void avanzaPrimerVehiculo() throws ErrorDeSimulacion {
		// Incrementa unidadesDeTiempoUsadas
		// Actualiza usoCompleto:
		// - Si “colaVehiculos” es vacía, entonces “usoCompleto=false”
		// - En otro caso saca el primer vehículo “v” de la “colaVehiculos”,
		// y le mueve a la siguiente carretera (“v.moverASiguienteCarretera()”)
		// Pone “usadaPorUnVehiculo” a true.

		this.unidadesDeTiempoUsadas++;
		if (this.colaVehiculos.isEmpty())
			usoCompleto = false;
		else {
			
			Vehiculo v = colaVehiculos.get(0);
			colaVehiculos.remove(0);
			v.moverASiguienteCarretera();
			this.usadaPorUnVehiculo = true;
		}

	}
	
	public void setUsoCompleto(boolean uso){
		this.usoCompleto = uso;
	}
	
	public void setUsadaPorUnVehiculo(boolean usada){
		this.usadaPorUnVehiculo = usada;
	}
	
	public void actualizarIntervaloCircular(int max, int min) {
		if (usoCompleto())
			this.intervaloDeTiempo = Math.min(intervaloDeTiempo + 1, max);
		else if(!usada())
			this.intervaloDeTiempo = Math.max(intervaloDeTiempo - 1, min);
	}

	public boolean tiempoConsumido() {
		// comprueba si se ha agotado el intervalo de tiempo.
		// “unidadesDeTiempoUsadas >= “intervaloDeTiempo”
		return this.unidadesDeTiempoUsadas >= this.intervaloDeTiempo;
	}

	public boolean usoCompleto() {
		return this.usoCompleto;
	} // método get

	public boolean usada() {
		return this.usadaPorUnVehiculo;
	} // método get

	public int getUnidadesDeTiempo() {
		return Math.max(this.intervaloDeTiempo - this.unidadesDeTiempoUsadas, 1);

	}

	public void actualizaIntervaloCongestionado() {
		this.intervaloDeTiempo = Math.max(this.colaVehiculos.size()/2, 1);
	}

	public void setUnidadesDeTiempoUsadas(int time) {
		this.unidadesDeTiempoUsadas = 0;

	}
}
