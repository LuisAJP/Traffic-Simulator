package es.ucm.fdi.objetoSimulacion.cruces;

import java.util.List;
import java.util.Map;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.exceptions.RelacionCruceNoEncotrada;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.objetoSimulacion.carreteras.Carretera;
import es.ucm.fdi.objetoSimulacion.cruces.carreterasEntrantes.CarreteraEntrante;
import es.ucm.fdi.objetoSimulacion.vehiculos.Vehiculo;

public class Cruce extends CruceGenerico<CarreteraEntrante> {


	public Cruce(String id) {
		super(id);
	}

	public Carretera carreteraHaciaCruce(Cruce cruce) throws RelacionCruceNoEncotrada {
		if (carreterasSalientes.containsKey(cruce)) {
			Carretera retorno = carreterasSalientes.get(cruce);
			return retorno;
		}
		throw new RelacionCruceNoEncotrada("Los cruces no se relacionan");
	}

	public void addCarreteraEntranteAlCruce(String idCarretera, Carretera carretera) {
		if (!mapaCarreterasEntrantes.containsKey(idCarretera)) {
			CarreteraEntrante carreteraE = new CarreteraEntrante(carretera);
			carreterasEntrantes.add(carreteraE);
//			actualizaSemaforos1(carreteraE);
			actualizaSemaforos();
			mapaCarreterasEntrantes.put(idCarretera, carreteraE);
		}
	}

	public void addCarreteraSalienteAlCruce(Cruce destino, Carretera road) {
		if (!carreterasSalientes.containsKey(destino)) {
			carreterasSalientes.put(destino, road);
		}
	}

	public void entraVehiculoAlCruce(String idCarretera, Vehiculo vehiculo) {
		if (mapaCarreterasEntrantes.containsKey(idCarretera)) {
			CarreteraEntrante aux = mapaCarreterasEntrantes.get(idCarretera);
			aux.getColaVehiculos().add(vehiculo);
		}

	}


	@Override
	public void avanza() throws ErrorDeSimulacion {
		if (!carreterasEntrantes.isEmpty()) {
			carreterasEntrantes.get(indiceSemaforoVerde).avanzaPrimerVehiculo();
			actualizaSemaforos();
		}

	}

	protected String getNombreSeccion() {
		return "junction_report";
	}

	protected void completaDetallesSeccion(IniSection is) {
		String cadena = cruceDetallesSeccion();
		is.setValue("queues", cadena);

	}

	public String cruceDetallesSeccion() {
		String cadena = "";

		if (!carreterasEntrantes.isEmpty()) {
			int i = 0;
			String color = "";
			for (CarreteraEntrante c : carreterasEntrantes) {
				if (c.getSemaforo())
					color = "green";
				else
					color = "red";
				// cadena= cadena +"("+c.getCarretera()+","+ color +","+ c.colaVehiculos +")";
				cadena = cadena + "(" + c.getCarretera() + "," + color + "," + "["
						+ colaVehiculosDetallesSeccion(c.getColaVehiculos()) + "]" + ")";
				i++;
				if (i < carreterasEntrantes.size()) {
					cadena = cadena + ",";
				}

			}
		}
		return cadena;
	}

	public String colaVehiculosDetallesSeccion(List<Vehiculo> colaVehiculos1) {
		String cadena = "";
		int i = 0;
		for (Vehiculo v : colaVehiculos1) {
			cadena = cadena + v.getId();
			i++;
			if (i < colaVehiculos1.size()) {
				cadena = cadena + ",";
			}
		}
		return cadena;
	}

	/***/
	public String toString() {
		//return "Cruce " + getId();
		return getId();
	}

	public Map<CruceGenerico<?>, Carretera> getMapaCarreterasSalientes() {

		return this.carreterasSalientes;
	}

	@Override
	protected CarreteraEntrante creaCarreteraEntrante(Carretera carretera) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void actualizaSemaforos() {
		if (indiceSemaforoVerde == 0 && !carreterasEntrantes.get(indiceSemaforoVerde).getSemaforo())//si semaforo es verde y 
			carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(true);
		else {
			carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(false);
			indiceSemaforoVerde = (indiceSemaforoVerde + 1) % carreterasEntrantes.size();
			carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(true);
		}

	}

}
