package es.ucm.fdi.objetoSimulacion.cruces;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.exceptions.RelacionCruceNoEncotrada;
import es.ucm.fdi.objetoSimulacion.ObjetoSimulacion;
import es.ucm.fdi.objetoSimulacion.carreteras.Carretera;
import es.ucm.fdi.objetoSimulacion.cruces.carreterasEntrantes.CarreteraEntrante;
import es.ucm.fdi.objetoSimulacion.vehiculos.Vehiculo;

public abstract class CruceGenerico<T extends CarreteraEntrante> extends ObjetoSimulacion {

	protected int indiceSemaforoVerde;
	protected LinkedList<T> carreterasEntrantes;
	protected Map<String, T> mapaCarreterasEntrantes;
	protected Map<CruceGenerico<?>, Carretera> carreterasSalientes;

	public CruceGenerico(String id) {
		super(id);
		carreterasEntrantes = new LinkedList<T>();
		mapaCarreterasEntrantes = new HashMap<String, T>();
		carreterasSalientes = new HashMap<CruceGenerico<?>, Carretera>();
		indiceSemaforoVerde = 0;
	}

	public Carretera carreteraHaciaCruce(CruceGenerico<?> cruce) throws RelacionCruceNoEncotrada {
		if (carreterasSalientes.containsKey(cruce)) {
			Carretera retorno = carreterasSalientes.get(cruce);
			return retorno;
		}
		throw new RelacionCruceNoEncotrada("Los cruces no se relacionan");
	}

	public void addCarreteraEntranteAlCruce(String idCarretera, Carretera carretera) {
		// T ri = creaCarreteraEntrante(carretera);
		if (!mapaCarreterasEntrantes.containsKey(idCarretera)) {
			T nueva = creaCarreteraEntrante(carretera);
			mapaCarreterasEntrantes.put(idCarretera, nueva);
			carreterasEntrantes.add(nueva);
		}

	}

	abstract protected T creaCarreteraEntrante(Carretera carretera);

	public void addCarreteraSalienteAlCruce(CruceGenerico<?> destino, Carretera carr) {
		if (!carreterasSalientes.containsKey(destino)) {
			carreterasSalientes.put(destino, carr);
		}
	}

	public void entraVehiculoAlCruce(String idCarretera, Vehiculo vehiculo) {
		if (mapaCarreterasEntrantes.containsKey(idCarretera)) {
			CarreteraEntrante aux = mapaCarreterasEntrantes.get(idCarretera);
			aux.getColaVehiculos().add(vehiculo);
		}
	}

	@Override
	public void avanza() throws RelacionCruceNoEncotrada, ErrorDeSimulacion {
		if (!carreterasEntrantes.isEmpty()) {
			carreterasEntrantes.get(indiceSemaforoVerde).avanzaPrimerVehiculo();
			actualizaSemaforos();
		}
	}
	
	public Map<String, T>  getMapaCarreterasEntrantes(){
		
		return this.mapaCarreterasEntrantes;
	}
	abstract protected void actualizaSemaforos();

	public LinkedList<T> getCarreteras(){
		return this.carreterasEntrantes;
	}

}
