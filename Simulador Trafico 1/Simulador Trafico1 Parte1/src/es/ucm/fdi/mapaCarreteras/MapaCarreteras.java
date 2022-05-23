package es.ucm.fdi.mapaCarreteras;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import es.ucm.fdi.excepciones.ErrorDeSimulacion;
import es.ucm.fdi.objetoSimulacion.Carretera;
import es.ucm.fdi.objetoSimulacion.Cruce;
import es.ucm.fdi.objetoSimulacion.Vehiculo;


public class MapaCarreteras {
	private List<Carretera> carreteras;
	private List<Cruce> cruces;
	private List<Vehiculo> vehiculos;
	
	// estructuras para agilizar la búsqueda (id,valor)
	private Map<String, Carretera> mapaDeCarreteras;
	private Map<String, Cruce> mapaDeCruces;
	private Map<String, Vehiculo> mapaDeVehiculos;
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MapaCarreteras() {
		// inicializa los atributos a sus constructoras por defecto.
		// Para carreteras, cruces y vehículos puede usarse ArrayList.
		
		this.carreteras= new ArrayList();
		this.cruces= new ArrayList();
		this.vehiculos= new ArrayList();
		// Para los mapas puede usarse HashMap
		this.mapaDeCarreteras = new HashMap<String,Carretera>();
		this.mapaDeCruces = new HashMap<String,Cruce>();
		this.mapaDeVehiculos = new HashMap<String,Vehiculo>();
		
	}
	
	/**Solo se ejecuta una vez por Cruce. Cuando se procesa su evento
	 * @throws ErrorDeSimulacion */
	public void addCruce(String idCruce, Cruce cruce) throws ErrorDeSimulacion {
		boolean existe=false;
		// comprueba que “idCruce” no existe en el mapa.
		for (String key : mapaDeCruces.keySet()) {
			if(key.equals(idCruce)) {
				existe=true;
			}
		}
		// Si no existe, lo añade a “cruces” y a “mapaDeCruces”.
		if(!existe) {
				cruces.add(cruce);	
				mapaDeCruces.put(idCruce, cruce);
		}
		// Si existe lanza una excepción.
		else {
			throw new ErrorDeSimulacion("Error!! Ya existe idCruce");	
		}
	}
	
	
	/**Solo se ejecuta una vez por vehículo. Cuando se procesa el evento
	 * @throws ErrorDeSimulacion */
	public void addVehiculo(String idVehiculo,Vehiculo vehiculo) throws ErrorDeSimulacion {
		// comprueba que “idVehiculo” no existe en el mapa.
		boolean existe=false;
		for (String key : mapaDeVehiculos.keySet()) {
			if(key.equals(idVehiculo)) {
				existe=true;
			}
		}
		// Si no existe, lo añade a “vehiculos” y a “mapaDeVehiculos”,
		if(!existe) {
			vehiculos.add(vehiculo);	
			mapaDeVehiculos.put(idVehiculo, vehiculo);
		}
		
		else {
			throw new ErrorDeSimulacion("Error!! Ya existe idVehiculo");	
		}
		
		// y posteriormente solicita al vehiculo que se mueva a la siguiente carretera de su itinerario (moverASiguienteCarretera).
		vehiculo.moverASiguienteCarretera();
	
		// Si existe lanza una excepción.
	}
	
	public void addCarretera(String idCarretera,Cruce origen,Carretera carretera,Cruce destino) throws ErrorDeSimulacion {
		// comprueba que “idCarretera” no existe en el mapa.
		boolean existe=false;
		for (String key : mapaDeCarreteras.keySet()) {
			if(key.equals(idCarretera)) {
				existe=true;
			}
		}
		// Si no existe, lo añade a “carreteras” y a “mapaDeCarreteras”,
		if(!existe) {
			carreteras.add(carretera);	
			mapaDeCarreteras.put(idCarretera, carretera);
		}
		// Si existe lanza una excepción.
		else {
			throw new ErrorDeSimulacion("Error!! Ya existe idCarretera");	
		}
		// y posteriormente actualiza los cruces origen y destino como sigue:
		// 		- Añade al cruce origen la carretera, como “carretera saliente”
		origen.addCarreteraSalienteAlCruce(destino, carretera);
		// 		- Añade al crude destino la carretera, como “carretera entrante”
		destino.addCarreteraEntranteAlCruce(idCarretera, carretera);
		// Si existe lanza una excepción.
		
	}
	
	
	
	public String generateReport(int time) {
		String report = "";
		// genera informe para cruces
		
		for(Cruce cr : cruces) {
			 report+= cr.generaInforme(time) + "\n";
		}
		// genera informe para carreteras
		
		for(Carretera c : carreteras) {
			 report+= c.generaInforme(time) + "\n";
		}
		
		// genera informe para vehiculos
		for(Vehiculo v : vehiculos) {
			 report+= v.generaInforme(time) + "\n";
		}
		
		return report;
	}
		
	public void actualizar() {
	// llama al método avanza de cada cruce
	// llama al método avanza de cada carretera
	}
	
	public Cruce getCruce(String id) {
		// devuelve el cruce con ese “id” utilizando el mapaDeCruces.
		Cruce cruce=null;
		boolean encontrado=false;
		int i=0;
		while(i<cruces.size()&&!encontrado) {
			
			if(id.equals(cruces.get(i).getId())) {
				encontrado=true;
				cruce=cruces.get(i);
			}
			else{
				i++;
			}
		}
		// sino existe el cruce lanza excepción.
		if(!encontrado) {
			try {
				throw new ErrorDeSimulacion("Error!! no existe cruce");
			} catch (ErrorDeSimulacion e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return cruce;
	}
	
	public Vehiculo getVehiculo(String id) {
		
	// devuelve el vehículo con ese “id” utilizando el mapaDeVehiculos.
	// sino existe el vehículo lanza excepción.
		Vehiculo vehiculo=null;
		boolean encontrado=false;
		int i=0;
		while(i<vehiculos.size()&&!encontrado) {
			if(id.equals(vehiculos.get(i).getId())) {
				encontrado=true;
				vehiculo=vehiculos.get(i);
			}
			else {
				i++;
			}
		}
		// sino existe el cruce lanza excepción.
		if(!encontrado) {
			try {
				throw new ErrorDeSimulacion("Error!! no existe vehiculo");
			} catch (ErrorDeSimulacion e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return vehiculo;
	}
	
		
	public Carretera getCarretera(String id) {
		return null;
	// devuelve la carretera con ese “id” utilizando el mapaDeCarreteras.
	// sino existe la carretra lanza excepción.
	}
	
	public List<Carretera> getListaCarreteras() {
		return this.carreteras;
	}	
	
	public List<Cruce> getListaCruces() {
		return this.cruces;
	}	
}
