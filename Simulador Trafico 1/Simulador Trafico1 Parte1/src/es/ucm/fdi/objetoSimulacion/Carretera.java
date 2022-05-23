package es.ucm.fdi.objetoSimulacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import es.ucm.fdi.ini.IniSection;

public class Carretera extends ObjetoSimulacion {
	
	protected int longitud; // longitud de la carretera
	protected int velocidadMaxima; // velocidad máxima
	protected Cruce cruceOrigen; // cruce del que parte la carretera
	protected Cruce cruceDestino; // cruce al que llega la carretera
	protected List<Vehiculo> vehiculos; // lista ordenada de vehículos en la carretera (ordenada por localización)
	protected Comparator<Vehiculo> comparadorVehiculo; // orden entre vehículos
	
	
	public Carretera(String id, int length, int maxSpeed, Cruce src, Cruce dest) {
	// se inicializan los atributos de acuerdo con los parámetros.
	// se fija el orden entre los vehículos: (inicia comparadorVehiculo)
	//- la localización 0 es la menor
		super(id);
		this.longitud=length;
		this.velocidadMaxima=maxSpeed;
		this.cruceOrigen=src;
		this.cruceDestino=dest;
		vehiculos= new ArrayList<Vehiculo>();
	}
	
	public void avanza() {
		int factorReduccion;
		int velocidad=0;
		// calcular velocidad base de la carretera
		int velocidadBase=calculaVelocidadBase();
		// inicializar obstáculos a 0
		
		// Para cada vehículo de la lista “vehiculos”:
		//1. Si el vehículo está averiado se incrementa el número de obstaculos.
		
		factorReduccion=calculaFactorReduccion();
		velocidad=velocidadBase/factorReduccion;
		//2. Se fija la velocidad actual del vehículo
		//3. Se pide al vehículo que avance.
		int tam=vehiculos.size();
		for (int i=0;i<tam;i++) {
			
			vehiculos.get(i).setVelocidadActual(velocidad);
			vehiculos.get(i).avanza();
		}
		// ordenar la lista de vehículos
		this.sort();

	}
	
	public void sort() {
		Collections.sort(vehiculos, new Comparator<Vehiculo>() {
			@Override
			public int compare(Vehiculo o1, Vehiculo o2) {
				if (o1.getLocalizacion() == o2.getLocalizacion()) 
					return 0;
				else if (o1.getLocalizacion() > o2.getLocalizacion()) 
					return -1;
				else 
					return 1;
			}
		});
	}
	
	public void entraVehiculo(Vehiculo vehiculo) {
		// Si el vehículo no existe en la carretera, se añade a la lista de vehículos y
		// se ordena la lista.
		// Si existe no se hace nada.
		
		if(!vehiculos.contains(vehiculo)) {
			vehiculos.add(vehiculo);
		}
		// ordenar la lista de vehículos
		this.sort();
	}
		
	public void saleVehiculo(Vehiculo vehiculo) {
		// elimina el vehículo de la lista de vehículos
		for(int i=0; i<vehiculos.size();i++) {
			if(vehiculos.get(i).getId().equals(vehiculo.getId())) {
				vehiculos.remove(vehiculos.get(i));
			}
		}
	}
	
	
	public void entraVehiculoAlCruce(Vehiculo v) {
	// añade el vehículo al “cruceDestino” de la carretera”
	}
		
	
	protected int calculaVelocidadBase() {
		int m1=velocidadMaxima;
		int m2=velocidadMaxima;
		if(vehiculos.size()>1) {
			m2=m2/vehiculos.size()+1;
		}
		else {
			m2=m2/1;
		}
		if(m1<m2) 
			return m1;
		
		else
			return m2;
		
	}
		
	protected int calculaFactorReduccion() {
		int obstaculos=0;
		int factorReduccion;
		//si el primer vehiculo esta averiado
		if(!vehiculos.isEmpty()) {
			if(vehiculos.get(0).estaAveriado) {
				obstaculos++;
			}
		}
		
		
		if(obstaculos>0) {
			factorReduccion=2;
		}
		else {
			factorReduccion=1;
		}
		return factorReduccion;
		
	}
	
	protected String getNombreSeccion() {
		return "road_report";
		
	}
		
	protected void completaDetallesSeccion(IniSection is) {
		String cadena=vehiculosDetallesSeccion();
	// crea “vehicles = (v1,10),(v2,10) ”
		is.setValue("state", cadena);
	}

	public String vehiculosDetallesSeccion() {
		String cadena="";
		int i=0;
		for (Vehiculo v :vehiculos) {
			
			cadena= cadena + "("+v.getId()+","+v.getLocalizacion()+")";
			i++;
			if(i<vehiculos.size()) {
				cadena=cadena +",";
			}
		}
		
		return cadena;
	}
	
	public int getLength() {
		// TODO Auto-generated method stub
		return this.longitud;
	}
	/***/
	public String toString() {
		return  getId();
	}
	
	public Cruce getCruceOrigen(){
		return this.cruceOrigen;
	}
	
	public Cruce getCruceDestino(){
		return this.cruceDestino;
	}
	
	
}
