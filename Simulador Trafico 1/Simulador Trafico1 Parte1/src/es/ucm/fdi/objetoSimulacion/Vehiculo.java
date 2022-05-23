package es.ucm.fdi.objetoSimulacion;

import java.util.List;

import es.ucm.fdi.excepciones.ErrorDeSimulacion;
import es.ucm.fdi.ini.IniSection;

public class Vehiculo extends ObjetoSimulacion {
	
	protected Carretera carretera; // carretera en la que está el vehículo
	protected int velocidadMaxima; // velocidad máxima
	protected int velocidadActual; // velocidad actual
	protected int kilometraje; // distancia recorrida
	protected int localizacion; // localización en la carretera
	protected int tiempoAveria; // tiempo que estará averiado o duracion
	protected boolean estaAveriado; /**vehiculo averiado*/
	protected List<Cruce> itinerario; // itinerario a recorrer (mínimo 2)
	private boolean haLlegado;
	private boolean estaEnCruce;
	/***/
	int contadorItinerario;
	int longitud;
	
	
	public Vehiculo(String id, int velocidadMaxima, List<Cruce> iti) throws ErrorDeSimulacion {
		super(id);
		// comprobar que la velocidadMaxima es mayor o igual que 0, y
		// que el itinerario tiene al menos dos cruces.
		// En caso de no cumplirse lo anterior, lanzar una excepción.
		if(velocidadMaxima<=0 ) {
			throw new ErrorDeSimulacion(" Error!! la velocidadMaxima es menor o igual a 0");	
		}
		else {
			// inicializar los atributos teniendo en cuenta los parámetros.
			// al crear un vehículo su “carretera” será inicalmene “null”.
			this.velocidadMaxima=velocidadMaxima;
			this.itinerario=iti;
			this.carretera=null;
			this.localizacion= 0;
			this.velocidadActual=0;
			this.kilometraje=0;
			this.haLlegado=false;
			this.tiempoAveria=0;
			this.estaAveriado=false;
			this.estaEnCruce=true;
			this.contadorItinerario=0;
			this.longitud=0;
		}
	}

	
	@Override
	public void avanza() {
		if(estaEnCruce) {
			this.velocidadActual=0;
		}
		// si el coche está averiado, decrementar tiempoAveria
		if(getTiempoAveria()>0) {
			this.tiempoAveria= this.tiempoAveria -1;
			this.velocidadActual=0;
			if(this.tiempoAveria==0) {
				this.estaAveriado=false;
				
			}
		}
		// si el coche está esperando en un cruce, no se hace nada.
		// en otro caso:
		else if (!estaEnCruce && this.localizacion<carretera.getLength()&&!haLlegado) {
			//	1. Actualizar su “localizacion”
			localizacion= velocidadActual + localizacion;
			//	2. Actualizar su “kilometraje”
			kilometraje = kilometraje + velocidadActual;
			
			//3. Si el coche ha llegado a un cruce (localizacion >= carretera.getLength())
			if (localizacion >= carretera.getLength()&&!haLlegado) {	
				this.contadorItinerario++;
				
				//si ha llegado al final del recorrido
				if(contadorItinerario==itinerario.size()-1) {
					this.haLlegado=true;
					this.velocidadActual=0;
					localizacion=0;
					
					
					Cruce cruce = itinerario.get(this.contadorItinerario);
					cruce.entraVehiculoAlCruce(carretera.getId(), this);
					kilometraje =this.longitud;
				}
				//si no ha llegado al final, pero ha llegado a un cruce
				else {
				//3.1. Poner la localización igual a la longitud de la carretera.
				localizacion=carretera.getLength();
					//localizacion=0;
				//3.2. Corregir el kilometraje.
					kilometraje=carretera.getLength();
				//3.3. Indicar a la carretera que el vehículo entra al cruce.  tengo que pasarle el cruce j2 cruce=itinerario.get(this.contador)
					
					this.velocidadActual=0;
				/**obtengo el cruce siguiente del itinerario*/
					Cruce cruce = itinerario.get(this.contadorItinerario);
				/**añado vehiculo a la cola de vehiculos de la carretera entrante de la lista de carreteras entrantes del cruce*/
					cruce.entraVehiculoAlCruce(carretera.getId(), this);
				//3.4. Marcar que éste vehículo está en un cruce (this.estaEnCruce = true)
					this.estaEnCruce = true;
				}	
			}	
		}	
		
	}	
	
		
	
	
	public void moverASiguienteCarretera() throws ErrorDeSimulacion {
		// Si la carretera no es null, sacar el vehículo de la carretera.
		if(carretera !=null) {
			carretera.saleVehiculo(this);
		}
		// Si hemos llegado al último cruce del itinerario, entonces:
//		1. Se marca que el vehículo ha llegado (this.haLlegado = true).
//		2. Se pone su carretera a null.
//		3. Se pone su “velocidadActual” y “localizacion” a 0.
//		// y se marca que el vehículo está en un cruce (this.estaEnCruce = true).
		 if(contadorItinerario==itinerario.size()-1) {
			this.haLlegado=true;
			this.carretera=null;
			this.velocidadActual=0;
			this.localizacion=0;
			this.estaEnCruce=true;
		}
//		// En otro caso:
//		1. Se calcula la siguiente carretera a la que tiene que ir.
//		2. Si dicha carretera no existe, se lanza excepción.
//		3. En otro caso, se introduce el vehículo en la carretera.
//		4. Se inicializa su localización.
		else if (estaEnCruce){
			
			this.localizacion=0;
			//this.velocidadActual=0;
			Cruce cruce1=itinerario.get(this.contadorItinerario);
			Cruce cruce2=itinerario.get(this.contadorItinerario+1);
			//me devuelva la carretera que conecta entre los dos cruces
			carretera=cruce1.carreteraHaciaCruce(cruce2);
			if(carretera!=null){
				carretera.entraVehiculo(this);
				this.localizacion=0;
				this.velocidadActual=0;
				this.estaEnCruce=false;
				//contador de las longitudes de las carreteras
				this.longitud=this.longitud+carretera.getLength();
				
			}
			else {
				throw new ErrorDeSimulacion("error!! carretera es null");
			}
			
		}
	}
	
	
	protected void completaDetallesSeccion(IniSection is) {
		is.setValue("speed", this.velocidadActual);
		is.setValue("kilometrage", this.kilometraje);
		is.setValue("faulty", this.tiempoAveria);
		is.setValue("location", this.haLlegado ? "arrived" : "(" + this.carretera + "," + this.getLocalizacion()+")");
	}
	
	public int getLocalizacion() {
		return this.localizacion;
	}
	
	public int getTiempoDeInfraccion() {
		return this.tiempoAveria;
	}
	
	public void setVelocidadActual(int velocidad) {
		
	// Si “velocidad” es negativa, entonces la “velocidadActual” es 0.
		if(velocidad <0) {
			velocidadActual=0;
		}
	// Si “velocidad” excede a “velocidadMaxima”, entonces la “velocidadActual” es “velocidadMaxima”
		else if(velocidad>velocidadMaxima) {
			velocidadActual=velocidadMaxima;
		}
		else {
			// En otro caso, “velocidadActual” es “velocidad”
			velocidadActual=velocidad;
		}
	
	}
	
	public void setTiempoAveria(int duracionAveria) {
	// Comprobar que “carretera” no es null.
	// Se fija el tiempo de avería de acuerdo con el enunciado.
		if(carretera!=null) {
			this.tiempoAveria=duracionAveria;
		}
	// Si el tiempo de avería es finalmente positivo, entonces
	// la “velocidadActual” se pone a 0
		if(tiempoAveria>0) {
			velocidadActual=0;
		}
	}
	
		
	public int getTiempoAveria() {
		return this.tiempoAveria;
	}
	
	protected String getNombreSeccion() {
		return "vehicle_report";
	}
	
	/***/

	/***/
	public void setAveriado(boolean a) {
		this.estaAveriado=a;
	}
	
	public Carretera getCarretera() {
		return this.carretera;
	}
	
	/***/
	public String toString() {
		//return getId()+ "," + this.localizacion;
		return getId();
	}
	
}
