package es.ucm.fdi.objetoSimulacion.cruces;

import java.util.List;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.objetoSimulacion.carreteras.Carretera;
import es.ucm.fdi.objetoSimulacion.cruces.carreterasEntrantes.CarreteraEntranteConIntervalo;
import es.ucm.fdi.objetoSimulacion.vehiculos.Vehiculo;

public class CruceCongestionado extends CruceGenerico<CarreteraEntranteConIntervalo> {

	public CruceCongestionado(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected CarreteraEntranteConIntervalo creaCarreteraEntrante(Carretera carretera) {
		return new CarreteraEntranteConIntervalo(carretera, 0, 0);
	}

	// no tiene atributos
	
	@Override
	protected void actualizaSemaforos() {
//	- Si no hay carretera con semáforo en verde (indiceSemaforoVerde == -1) entonces se
//		selecciona la carretera que tenga más vehículos en su cola de vehículos.
//	- Si hay carretera entrante "ri" con su semáforo en verde, (indiceSemaforoVerde !=
//	-1) entonces:
//		1. Si ha consumido su intervalo de tiempo en verde ("ri.tiempoConsumido()"):
//		1.1. Se pone el semáforo de "ri" a rojo.
//		1.2. Se inicializan los atributos de "ri".
//		1.3. Se busca la posición "max" que ocupa la primera carretera entrante
//			distinta de "ri" con el mayor número de vehículos en su cola.
//		1.4. "indiceSemaforoVerde" se pone a "max".
//		1.5. Se pone el semáforo de la carretera entrante en la posición "max" ("rj")
//			a verde y se inicializan los atributos de "rj", entre ellos el
//			"intervaloTiempo" a Math.max(rj.numVehiculosEnCola()/2,1).
		int maxPos;
		
		if (this.indiceSemaforoVerde == 0 && !carreterasEntrantes.get(this.indiceSemaforoVerde).getSemaforo()) {
			carreterasEntrantes.get(posMaxCoches()).ponSemaforo(true);
			this.carreterasEntrantes.get(indiceSemaforoVerde).actualizaIntervaloCongestionado();
			
		} else {
			if (this.carreterasEntrantes.get(this.indiceSemaforoVerde).tiempoConsumido()) {
				this.carreterasEntrantes.get(this.indiceSemaforoVerde).ponSemaforo(false);
				this.carreterasEntrantes.get(indiceSemaforoVerde).actualizaIntervaloCongestionado();
				this.carreterasEntrantes.get(indiceSemaforoVerde).setUsoCompleto(true);
				this.carreterasEntrantes.get(indiceSemaforoVerde).setUsadaPorUnVehiculo(false);
				this.carreterasEntrantes.get(indiceSemaforoVerde).setUnidadesDeTiempoUsadas(0);
				// 1.2. Se inicializan los atributos de "ri". Revisar la
				// sentencia anterior

				maxPos = posMaxCoches();
				if (this.carreterasEntrantes.size() > 1 && this.indiceSemaforoVerde == maxPos)
					maxPos++;
				this.indiceSemaforoVerde = maxPos;
				this.carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(true);
				this.carreterasEntrantes.get(indiceSemaforoVerde).actualizaIntervaloCongestionado();
			}
		}
	}

	public int posMaxCoches(){
		int maxPos = 0, max = 0;
		for(int i = 0; i < this.carreterasEntrantes.size(); i++){
			if(this.carreterasEntrantes.get(i).getColaVehiculos().size() > max && i != this.indiceSemaforoVerde){
				maxPos = i;
				max = this.carreterasEntrantes.get(i).getColaVehiculos().size();
			}
		}
		
		return maxPos;
		
	}
	@Override
	protected void completaDetallesSeccion(IniSection is) {
		String cadena = cruceCongestionadoDetallesSeccion();
		is.setValue("queues", cadena);
		is.setValue("type", "mc");
		
	}
	
	public String cruceCongestionadoDetallesSeccion() {
		String cadena = "";

		if (!carreterasEntrantes.isEmpty()) {
			String color = "";
			//for (CarreteraEntrante c : carreterasEntrantes) {
				for(int i = 0; i < this.carreterasEntrantes.size(); i++){
				if (carreterasEntrantes.get(i).getSemaforo()){
					color = "green";
					cadena = cadena + "(" + carreterasEntrantes.get(i).getCarretera() + "," + color + ":" + carreterasEntrantes.get(i).getUnidadesDeTiempo()+ "," + "["
							+ colaVehiculosDetallesSeccion(carreterasEntrantes.get(i).getColaVehiculos()) + "]" + ")";
				}
					
				else{
					color = "red";
					cadena = cadena + "(" + carreterasEntrantes.get(i).getCarretera() + "," + color + "," + "["
							+ colaVehiculosDetallesSeccion(carreterasEntrantes.get(i).getColaVehiculos()) + "]" + ")";
				}
					
				// cadena= cadena +"("+c.getCarretera()+","+ color +","+ c.colaVehiculos +")";
				
				
				if (i < carreterasEntrantes.size() -1) {
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

	@Override
	protected String getNombreSeccion() {
		return "junction_report";
	}
	
	public String toString() {
		//return "Cruce " + getId();
		return getId();
	}
}
