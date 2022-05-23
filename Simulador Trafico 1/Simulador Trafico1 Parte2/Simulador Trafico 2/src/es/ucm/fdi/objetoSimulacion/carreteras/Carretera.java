package es.ucm.fdi.objetoSimulacion.carreteras;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.objetoSimulacion.ObjetoSimulacion;
import es.ucm.fdi.objetoSimulacion.cruces.CruceGenerico;
import es.ucm.fdi.objetoSimulacion.vehiculos.Vehiculo;

import java.util.Comparator;
import java.util.LinkedList;



public class Carretera extends ObjetoSimulacion {
	protected int longitud; // longitud de la carretera
	protected int velocidadMaxima; // velocidad máxima
	protected CruceGenerico<?> cruceOrigen; // cruce del que parte la carretera
	protected CruceGenerico<?> cruceDestino; // cruce al que llega la carretera
	protected LinkedList<Vehiculo> vehiculos; // lista ordenada de vehículos en la carretera (ordenada por localización)
	protected Comparator<Vehiculo> comparadorVehiculo; // orden entre vehículos
	protected String tipo;

	
	class Cmp implements Comparator<Vehiculo>{

		@Override
		public int compare(Vehiculo o1, Vehiculo o2) {
			if (o1.getLocalizacion() == o2.getLocalizacion()) 
				return 0;
			else if (o1.getLocalizacion() > o2.getLocalizacion()) 
				return -1;
			else 
				return 1;
		}
		
	}
	
	public Carretera(String id, int length, int maxSpeed, CruceGenerico<?> src, CruceGenerico<?> dest, String type) {
		super(id);
		this.longitud=length;
		this.velocidadMaxima=maxSpeed;
		this.cruceOrigen=src;
		this.cruceDestino=dest;
		vehiculos= new LinkedList<Vehiculo>();
		this.comparadorVehiculo = new Cmp();// se fija el orden entre los vehículos: (inicia comparadorVehiculo)
		this.tipo = type;
	}
	
	public int numeroObstaculos() {
		int obstaculos=0;
		if(!vehiculos.isEmpty()) {
			if(vehiculos.get(0).isEstaAveriado()) {
				obstaculos++;
			}
		}
		return obstaculos;
	}
	public void avanza() throws ErrorDeSimulacion {
		int velocidadCarretera=0;
		int obstaculos=0;
		//velocidadCarretera=calcularVelocidadCarretera();
		for (int i=0;i<vehiculos.size()&&!vehiculos.isEmpty();i++) {
			if(vehiculos.get(i).getTiempoAveria()>0) {
				obstaculos++;
			}
			velocidadCarretera=calcularVelocidadCarretera(obstaculos);
			vehiculos.get(i).setVelocidadActual(velocidadCarretera);
			vehiculos.get(i).avanza();	//3. Se pide al vehículo que avance.
		}
		this.vehiculos.sort(comparadorVehiculo);// ordenar la lista de vehículos

	}
	
	public int calcularVelocidadCarretera(int obstaculos) {
		int velocidadBase=calculaVelocidadBase();// calcular velocidad base de la carretera
		//int obstaculos=numeroObstaculos();//1. Si el vehículo está averiado se incrementa el número de obstaculos.
		int factorReduccion=calculaFactorReduccion(obstaculos);
		return velocidadBase/factorReduccion;//2. Se fija la velocidad actual del vehículo
	}
	
	public void entraVehiculo(Vehiculo vehiculo) {	
		if(!vehiculos.contains(vehiculo)) {// Si el vehículo no existe en la carretera, se añade a la lista de vehículos
			vehiculos.add(vehiculo);
		}
		// ordenar la lista de vehículos
		this.vehiculos.sort(comparadorVehiculo);
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
		
	protected int calculaFactorReduccion(int obstaculos) {
		int factorReduccion;
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
		return this.longitud;
	}

	public String toString() {
		return  getId();
	}
	
	public CruceGenerico<?> getCruceOrigen(){
		return this.cruceOrigen;
	}
	
	public CruceGenerico<?> getCruceDestino(){
		return this.cruceDestino;
	}
	
}
