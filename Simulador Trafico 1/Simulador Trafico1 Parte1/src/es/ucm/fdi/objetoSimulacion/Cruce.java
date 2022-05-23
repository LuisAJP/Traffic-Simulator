package es.ucm.fdi.objetoSimulacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import es.ucm.fdi.ini.IniSection;

public class Cruce extends ObjetoSimulacion{
	
	
	protected int indiceSemaforoVerde; // lleva el índice de la carretera entrante
	// con el semáforo en verde
	protected List<CarreteraEntrante> carreterasEntrantes;
	// para optimizar las búsquedas de las carreterasEntrantes
	// (IdCarretera, CarreteraEntrante)
	protected Map<String,CarreteraEntrante> mapaCarreterasEntrantes;
	protected Map<Cruce, Carretera> mapaCarreterasSalientes;
	
	public Cruce(String id) {
		super(id);
		this.carreterasEntrantes= new ArrayList<CarreteraEntrante>();
		this.mapaCarreterasEntrantes= new HashMap <String,CarreteraEntrante>();
		this.mapaCarreterasSalientes= new HashMap <Cruce,Carretera>();
		this.indiceSemaforoVerde=0;
		
	}
	
	public Carretera carreteraHaciaCruce(Cruce cruce) {
		boolean encontrado=false;
		Iterator<Entry<Cruce, Carretera>> it = mapaCarreterasSalientes.entrySet().iterator();
		Entry<Cruce, Carretera> entry= null;
		Carretera c=null;
		Cruce cr=null;
		while (it.hasNext()&& !encontrado) {
			entry= it.next();
			cr=entry.getKey();
			if(cr==cruce) {
				encontrado=true;
				c=entry.getValue(); //valor
			}
		}
		// devuelve la carretera que llega a ese cruce desde “this”
		return c;

	}
	
	public void addCarreteraEntranteAlCruce(String idCarretera, Carretera carretera) {
	// añade una carretera entrante al “mapaCarreterasEntrantes” y
	// a las “carreterasEntrantes”
		CarreteraEntrante carreteraE= new CarreteraEntrante(carretera);
		
		carreterasEntrantes.add(carreteraE);
		actualizaSemaforos1(carreteraE);
		mapaCarreterasEntrantes.put(idCarretera, carreteraE);
	}
	
	public void addCarreteraSalienteAlCruce(Cruce destino, Carretera road) {
	// añade una carretera saliente
		mapaCarreterasSalientes.put(destino, road);
	}
	
	
	
	
	public void entraVehiculoAlCruce(String idCarretera, Vehiculo vehiculo){
	// añade el “vehiculo” a la carretera entrante “idCarretera”
	//busco en mapa de carreteras entrantes mediante el idCarretera
	
		for(int i=0;i<carreterasEntrantes.size();i++) {
			/** si la carretera entrante de la lista de carreteras entrantes para insertar el vehiculo a la cola de Vehiculos*/
			if(carreterasEntrantes.get(i).getCarretera().getId().equals(idCarretera)) {
				/**una vez que lo tengo inserto el vehiculo en cola de vehiculos de la carretera entrante*/
				carreterasEntrantes.get(i).colaVehiculos.add(vehiculo);
			}
		}
	}
	
	
	protected void actualizaSemaforos1(CarreteraEntrante carreteraE){
		//Si es la primera carretera entrante, pongo el semaforo a verde
			if(carreteraE==carreterasEntrantes.get(0)) {
				carreteraE.ponSemaforo(true);
			}
			else {
				carreteraE.ponSemaforo(false);
			}
			
		}
	
	protected void actualizaSemaforos(CarreteraEntrante ce){
		//lo uso para que en el time=1 no cambie el semaforo, se mantenga green y red al principio
		if(this.indiceSemaforoVerde<2){
			indiceSemaforoVerde++;
		}
//		else {
//			if(carreteraE.getSemaforo()==true) {
//				carreteraE.ponSemaforo(false);
//			}
//			else {
//				carreteraE.ponSemaforo(true);
//			}	
//		}
//		
		else {
			if(ce.getSemaforo()==true) {
				ce.ponSemaforo(false);
			}
			else {
				ce.ponSemaforo(true);
			}	
		}
			
	}
	
	
	@Override
	public void avanza() {
	// Si “carreterasEntrantes” es vacío, no hace nada.
		if(!carreterasEntrantes.isEmpty()) {
			// en otro caso “avanzaPrimerVehiculo” de la carretera con el semáforo verde.
			// Posteriormente actualiza los semáforos.
		
			for(CarreteraEntrante ce : carreterasEntrantes) {
			
				if(carreterasEntrantes.size()==1) {
					ce.avanzaPrimerVehiculo();
				}
				//Actualiza semaforos
				if(carreterasEntrantes.size()==2) {	
					this.actualizaSemaforos(ce);
					if(!ce.getSemaforo()) {
						ce.avanzaPrimerVehiculo();
					}		
				}
	
			}
		}
	
	}
	protected String getNombreSeccion() {
		return "junction_report";
	}
	
	protected void completaDetallesSeccion(IniSection is) {
		String cadena=cruceDetallesSeccion();
	// genera la sección queues = (r2,green,[]),...
		is.setValue("queues", cadena);
		
	}
	
	public String cruceDetallesSeccion() {
		String cadena="";
		
		if(!carreterasEntrantes.isEmpty()) {
			int i=0;
			String color="";
			for (CarreteraEntrante c :carreterasEntrantes) {
				if(c.getSemaforo()) color="green";
				else color="red";
				//cadena= cadena +"("+c.getCarretera()+","+ color +","+  c.colaVehiculos +")";
				cadena= cadena +"("+c.getCarretera()+","+ color +","+ "[" +colaVehiculosDetallesSeccion(c.colaVehiculos)+"]" +")";
				i++;
				if(i<carreterasEntrantes.size()) {
					cadena=cadena +",";
				}
				
			}
		}
		return cadena;
	}
	
	public String colaVehiculosDetallesSeccion(List<Vehiculo> colaVehiculos1) {
		String cadena="";
		int i=0;
		for (Vehiculo  v :colaVehiculos1) {
			cadena=cadena + v.getId();
			i++;
			if(i<colaVehiculos1.size()) {
				cadena=cadena +",";
			}
		}
		return cadena;
	}
	
	
	
	
	
	/***/
	public String toString() {
		return "Cruce " + getId();
	}
	
	public Map<Cruce, Carretera> getMapaCarreterasSalientes() {
		
		return this.mapaCarreterasSalientes;
	}
	
}
