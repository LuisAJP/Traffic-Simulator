package es.ucm.fdi.objetoSimulacion.vehiculos;

import java.util.LinkedList;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.exceptions.RelacionCruceNoEncotrada;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.objetoSimulacion.ObjetoSimulacion;
import es.ucm.fdi.objetoSimulacion.carreteras.Carretera;
import es.ucm.fdi.objetoSimulacion.cruces.CruceGenerico;

public class Vehiculo extends ObjetoSimulacion {

	protected Carretera carretera; // carretera en la que está el vehículo
	protected int velocidadMaxima; // velocidad máxima
	protected int velocidadActual; // velocidad actual
	protected int kilometraje; // distancia recorrida
	protected int localizacion; // localización en la carretera
	protected int tiempoAveria; // tiempo que estará averiado o duracion
	private boolean estaAveriado;
	protected LinkedList<CruceGenerico<?>> itinerario; // itinerario a recorrer (mínimo 2)
	protected String tipo;
	private boolean haLlegado;
	private boolean estaEnCruce;
	/***/
	int contadorItinerario;
	int longitud;

	public Vehiculo(String id, int velocidadMaxima, LinkedList<CruceGenerico<?>> iti, String type) {
		super(id);
		this.velocidadMaxima = velocidadMaxima;
		this.itinerario = iti;
		this.carretera = null;
		this.localizacion = 0;
		this.velocidadActual = 0;
		this.kilometraje = 0;
		this.haLlegado = false;
		this.tiempoAveria = 0;
		this.setEstaAveriado(false);
		this.estaEnCruce = true;
		this.contadorItinerario = 0;
		this.longitud = 0;
		this.tipo = type;
	}

	public void actualizarTiempoAveria() {
		this.tiempoAveria = this.tiempoAveria - 1;//decrementar tiempoAveria
		this.velocidadActual = 0;
		if (this.tiempoAveria == 0) {
			this.setEstaAveriado(false);
		}
	}

	@Override
	public void avanza() throws ErrorDeSimulacion {
		if (getTiempoAveria() > 0) {// si tiempoAveria >0 significa que está averiado
			actualizarTiempoAveria();
		}
		
		if (estaEnCruce) {// si el coche está esperando en un cruce, no se hace nada.
			this.velocidadActual = 0;
		}
		else if (!estaEnCruce && this.localizacion < carretera.getLongitud() && !haLlegado) {	// si no esta en cruce y no ha llegado al final de la carretera y no ha llegado al final del itinerario
			actualizarVehiculoRecorrido();
			if (localizacion >= carretera.getLongitud() && !haLlegado) {//3. Si el coche ha llegado a un cruce 
				this.contadorItinerario++;
				
				
				if (contadorItinerario == itinerario.size()- 1 ) {// si ha llegado al final del itinerario, pero todavia no se sabe si hay otros vehiculos en el cruce para llegar al destino
					actualizarVehiculoFinalRecorrido();
				}
				else {// si no ha llegado al final, pero ha llegado a un cruce
					actualizarVehiculoLlegadoACruce();
				}
			}
		}
	}
	
	
	public void actualizarVehiculoRecorrido() {
		localizacion = velocidadActual + localizacion;	// 1. Actualizar su “localizacion”
		kilometraje = kilometraje + velocidadActual;	// 2. Actualizar su “kilometraje”
	}
	
	public void actualizarVehiculoLlegadoACruce() {
		int aux = localizacion - velocidadActual;
		// 3.1. Poner la localización igual a la longitud de la carretera.
		localizacion = carretera.getLongitud();
		// 3.2. Corregir el kilometraje.
		kilometraje -= velocidadActual;
		kilometraje += carretera.getLongitud() - aux;
		// 3.3. Indicar a la carretera que el vehículo entra al cruce. tengo que pasarle
		// el cruce j2 cruce=itinerario.get(this.contador)
		localizacion = carretera.getLongitud();

		this.velocidadActual = 0;
		/** obtengo el cruce siguiente del itinerario */
		CruceGenerico<?> cruce = itinerario.get(this.contadorItinerario);
		/**
		 * añado vehiculo a la cola de vehiculos de la carretera entrante de la lista de
		 * carreteras entrantes del cruce
		 */
		cruce.entraVehiculoAlCruce(carretera.getId(), this);
		// 3.4. Marcar que éste vehículo está en un cruce (this.estaEnCruce = true)
		this.estaEnCruce = true;
	}
	
	public void actualizarVehiculoFinalRecorrido() {
		//this.haLlegado = true;
		this.velocidadActual = 0;
		localizacion = carretera.getLongitud();
		CruceGenerico<?> cruce = itinerario.get(this.contadorItinerario);
		cruce.entraVehiculoAlCruce(carretera.getId(), this);
		kilometraje = this.longitud;
		
		this.estaEnCruce = true;
		
	}
	
	void actualizarVehiculoUltimoCruceItinerario() {
		this.haLlegado = true;// 1. Se marca que el vehículo ha llegado (this.haLlegado = true).
		this.carretera = null;// 2. Se pone su carretera a null.
		this.velocidadActual = 0;// 3. Se pone su “velocidadActual” y “localizacion” a 0.
		this.localizacion = 0;
		this.estaEnCruce = true; //se marca que el vehículo está en un cruce (this.estaEnCruce = true).
	}
	
	public void moverASiguienteCarretera() throws ErrorDeSimulacion {
		
		if (carretera != null) {// Si la carretera no es null, sacar el vehículo de la carretera.
			carretera.saleVehiculo(this);
		}
		if (contadorItinerario == itinerario.size() - 1) {// Si hemos llegado al último cruce del itinerario, entonces:
			actualizarVehiculoUltimoCruceItinerario();
		}
		else if (estaEnCruce) {
			this.localizacion = 0;
			this.kilometraje=this.longitud;
			CruceGenerico<?> cruce1 = itinerario.get(this.contadorItinerario);
			CruceGenerico<?> cruce2 = itinerario.get(this.contadorItinerario + 1);
			try {
				carretera = cruce1.carreteraHaciaCruce(cruce2);// me devuelva la carretera que conecta entre los dos cruces
			} catch (RelacionCruceNoEncotrada e) {
				throw new ErrorDeSimulacion(e.getMessage());// 2. Si dicha carretera no existe, se lanza excepción.
			}
			if (carretera != null) {
				actualizarVehiculoEntraNuevaCarretera();

			} else {
				throw new ErrorDeSimulacion("error!! carretera es null");
			}
		}
	}
	
	public void actualizarVehiculoEntraNuevaCarretera() {
		carretera.entraVehiculo(this);// 3. En otro caso, se introduce el vehículo en la carretera.
		this.localizacion = 0;// 4. Se inicializa su localización.
		this.velocidadActual = 0;
		this.estaEnCruce = false;
		this.longitud = this.longitud + carretera.getLongitud();// contador de las longitudes de las carreteras
	}

	protected void completaDetallesSeccion(IniSection is) {
		is.setValue("speed", this.velocidadActual);
		is.setValue("kilometrage", this.kilometraje);
		is.setValue("faulty", this.tiempoAveria);
		is.setValue("location", this.haLlegado ? "arrived" : "(" + this.carretera + "," + this.getLocalizacion() + ")");
		if (this.tipo != null)
			is.setValue("type", this.tipo);
	}

	public int getLocalizacion() {
		return this.localizacion;
	}

	public int getTiempoDeInfraccion() {
		return this.tiempoAveria;
	}

	public void setVelocidadActual(int velocidad) {
		if (velocidad < 0) {//Si “velocidad” es negativa, entonces la “velocidadActual” es 0.
			velocidadActual = 0;
		} else if (velocidad > velocidadMaxima) {
			velocidadActual = velocidadMaxima;//“velocidad” excede a “velocidadMaxima”, entonces la “velocidadActual” es“velocidadMaxima” 
		} else {
			velocidadActual = velocidad; //En otro caso, “velocidadActual” es “velocidad”
		}
	}

	public void setTiempoAveria(int duracionAveria) {
		/*
		 * Comprobar que “carretera” no es null. Se fija el tiempo de avería de acuerdo
		 * con el enunciado. Si el tiempo de avería es finalmente positivo, entonces la
		 * “velocidadActual” se pone a 0
		 */
		if (carretera != null) {
			this.tiempoAveria = duracionAveria;
		}
		if (tiempoAveria > 0) {
			velocidadActual = 0;
		}
	}

	public int getTiempoAveria() {
		return this.tiempoAveria;
	}

	protected String getNombreSeccion() {
		return "vehicle_report";
	}

	public void setAveriado(boolean a) {
		this.setEstaAveriado(a);
	}

	public Carretera getCarretera() {
		return this.carretera;
	}

	/***/
	public String toString() {
		// return getId()+ "," + this.localizacion;
		return getId();
	}

	public boolean isEstaAveriado() {
		return estaAveriado;
	}

	public void setEstaAveriado(boolean estaAveriado) {
		this.estaAveriado = estaAveriado;
	}
	
	public int getVelocidadActual() {
		return this.velocidadActual;
	}
	
	public int getKilometraje() {
		return this.kilometraje;
	}
	
	public LinkedList<CruceGenerico<?>> getItinerario(){
		
		return this.itinerario;
	}
	

}
