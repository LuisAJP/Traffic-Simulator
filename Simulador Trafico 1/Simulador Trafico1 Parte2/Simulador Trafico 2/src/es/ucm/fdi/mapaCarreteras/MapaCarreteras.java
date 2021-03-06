package es.ucm.fdi.mapaCarreteras;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.exceptions.RelacionCruceNoEncotrada;
import es.ucm.fdi.objetoSimulacion.carreteras.Carretera;
import es.ucm.fdi.objetoSimulacion.cruces.CruceGenerico;
import es.ucm.fdi.objetoSimulacion.vehiculos.Vehiculo;

public class MapaCarreteras {
	private LinkedList<Carretera> carreteras;
	private LinkedList<CruceGenerico<?>> cruces;
	private LinkedList<Vehiculo> vehiculos;

	// estructuras para agilizar la b?squeda (id,valor)
	private Map<String, Carretera> mapaDeCarreteras;
	private Map<String, CruceGenerico<?>> mapaDeCruces;
	private Map<String, Vehiculo> mapaDeVehiculos;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MapaCarreteras() {
		// inicializa los atributos a sus constructoras por defecto.
		// Para carreteras, cruces y veh?culos puede usarse ArrayList.

		this.carreteras = new LinkedList();
		this.cruces = new LinkedList();
		this.vehiculos = new LinkedList();
		// Para los mapas puede usarse HashMap
		this.mapaDeCarreteras = new HashMap<String, Carretera>();
		this.mapaDeCruces = new HashMap<String, CruceGenerico<?>>();
		this.mapaDeVehiculos = new HashMap<String, Vehiculo>();

	}

	/**
	 * Solo se ejecuta una vez por Cruce. Cuando se procesa su evento
	 * 
	 * @throws ErrorDeSimulacion
	 */
	public void addCruce(String idCruce, CruceGenerico<?> cruce) throws ErrorDeSimulacion {
		boolean existe = false;
		// comprueba que ?idCruce? no existe en el mapa.
		for (String key : mapaDeCruces.keySet()) {
			if (key.equals(idCruce)) {
				existe = true;
			}
		}
		// Si no existe, lo a?ade a ?cruces? y a ?mapaDeCruces?.
		if (!existe) {
			cruces.add(cruce);
			mapaDeCruces.put(idCruce, cruce);
		}
		// Si existe lanza una excepci?n.
		else {
			throw new ErrorDeSimulacion("Error!! Ya existe idCruce");
		}
	}

	/**
	 * Solo se ejecuta una vez por veh?culo. Cuando se procesa el evento
	 * 
	 * @throws ErrorDeSimulacion
	 * @throws RelacionCruceNoEncotrada
	 */
	public void addVehiculo(String idVehiculo, Vehiculo vehiculo) throws ErrorDeSimulacion, RelacionCruceNoEncotrada {
		// comprueba que ?idVehiculo? no existe en el mapa.
		boolean existe = false;
		for (String key : mapaDeVehiculos.keySet()) {
			if (key.equals(idVehiculo)) {
				existe = true;
			}
		}
		// Si no existe, lo a?ade a ?vehiculos? y a ?mapaDeVehiculos?,
		if (!existe) {
			vehiculos.add(vehiculo);
			mapaDeVehiculos.put(idVehiculo, vehiculo);
		}

		else {
			throw new ErrorDeSimulacion("Error!! Ya existe idVehiculo");
		}

		// y posteriormente solicita al vehiculo que se mueva a la siguiente carretera
		// de su itinerario (moverASiguienteCarretera).
		vehiculo.moverASiguienteCarretera();

		// Si existe lanza una excepci?n.
	}

	public void addCarretera(String idCarretera, CruceGenerico<?> origen, Carretera carretera, CruceGenerico<?> destino)
			throws ErrorDeSimulacion {
		// comprueba que ?idCarretera? no existe en el mapa.
		boolean existe = false;
		for (String key : mapaDeCarreteras.keySet()) {
			if (key.equals(idCarretera)) {
				existe = true;
			}
		}
		// Si no existe, lo a?ade a ?carreteras? y a ?mapaDeCarreteras?,
		if (!existe) {
			carreteras.add(carretera);
			mapaDeCarreteras.put(idCarretera, carretera);
		}
		// Si existe lanza una excepci?n.
		else {
			throw new ErrorDeSimulacion("Error!! Ya existe idCarretera");
		}
		// y posteriormente actualiza los cruces origen y destino como sigue:
		// - A?ade al cruce origen la carretera, como ?carretera saliente?
		origen.addCarreteraSalienteAlCruce(destino, carretera);
		// - A?ade al crude destino la carretera, como ?carretera entrante?
		destino.addCarreteraEntranteAlCruce(idCarretera, carretera);
		// Si existe lanza una excepci?n.

	}

	public String generateReport(int time) {
		String report = "";
		// genera informe para cruces

		for (CruceGenerico<?> cr : cruces) {
			report += cr.generaInforme(time) + "\n";
		}
		// genera informe para carreteras

		for (Carretera c : carreteras) {
			report += c.generaInforme(time) + "\n";
		}

		// genera informe para vehiculos
		for (Vehiculo v : vehiculos) {
			report += v.generaInforme(time) + "\n";
		}

		return report;
	}

	public void actualizar() {
		// llama al m?todo avanza de cada cruce
		// llama al m?todo avanza de cada carretera
	}

	public CruceGenerico<?> getCruce(String id) throws ErrorDeSimulacion {
		// devuelve el cruce con ese ?id? utilizando el mapaDeCruces.
		CruceGenerico<?> cruce = null;
		boolean encontrado = false;
		int i = 0;
		while (i < cruces.size() && !encontrado) {

			if (id.equals(cruces.get(i).getId())) {
				encontrado = true;
				cruce = cruces.get(i);
			} else {
				i++;
			}
		}
		// sino existe el cruce lanza excepci?n.
		if (!encontrado) {
			throw new ErrorDeSimulacion("Error!! no existe cruce");

		}

		return cruce;
	}

	public Vehiculo getVehiculo(String id) {

		// devuelve el veh?culo con ese ?id? utilizando el mapaDeVehiculos.
		// sino existe el veh?culo lanza excepci?n.
		Vehiculo vehiculo = null;
		boolean encontrado = false;
		int i = 0;
		while (i < vehiculos.size() && !encontrado) {
			if (id.equals(vehiculos.get(i).getId())) {
				encontrado = true;
				vehiculo = vehiculos.get(i);
			} else {
				i++;
			}
		}
		// sino existe el cruce lanza excepci?n.
		if (!encontrado) {
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
		// devuelve la carretera con ese ?id? utilizando el mapaDeCarreteras.
		// sino existe la carretra lanza excepci?n.
	}

	public LinkedList<Carretera> getListaCarreteras() {
		return this.carreteras;
	}

	public LinkedList<CruceGenerico<?>> getListaCruces() {
		return this.cruces;
	}
}
