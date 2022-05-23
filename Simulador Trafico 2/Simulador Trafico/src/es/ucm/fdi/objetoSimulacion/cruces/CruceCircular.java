package es.ucm.fdi.objetoSimulacion.cruces;

import java.util.List;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.objetoSimulacion.carreteras.Carretera;
import es.ucm.fdi.objetoSimulacion.cruces.carreterasEntrantes.CarreteraEntranteConIntervalo;
import es.ucm.fdi.objetoSimulacion.vehiculos.Vehiculo;

public class CruceCircular extends CruceGenerico<CarreteraEntranteConIntervalo> {
	
	protected Integer maxValorIntervalo;
	protected Integer minValorIntervalo;

	public CruceCircular(String id, Integer max, Integer min) {
		super(id);
		this.maxValorIntervalo = max;
		this.minValorIntervalo = min;
	}

	@Override
	protected CarreteraEntranteConIntervalo creaCarreteraEntrante(Carretera carretera) {
		return new CarreteraEntranteConIntervalo(carretera, this.maxValorIntervalo, -1);
	}

	protected void actualizaSemaforos() {
//		- Si no hay carretera con semáforo en verde (indiceSemaforoVerde == -1) entonces se
//			selecciona la primera carretera entrante (la de la posición 0) y se pone su
//			semáforo en verde.
//		- Si hay carretera entrante "ri" con su semáforo en verde, (indiceSemaforoVerde !=-1)
//			entonces:
//			1. Si ha consumido su intervalo de tiempo en verde ("ri.tiempoConsumido()"):
//			1.1. Se pone el semáforo de "ri" a rojo.
//			1.2. Si ha sido usada en todos los pasos (“ri.usoCompleto()”), se fija
//				el intervalo de tiempo a ... Sino, si no ha sido usada
//				(“!ri.usada()”) se fija el intervalo de tiempo a ...
//			1.3. Se coge como nueva carretera con semáforo a verde la inmediatamente
//				Posterior a “ri”.

		if (this.indiceSemaforoVerde == 0 && !carreterasEntrantes.get(this.indiceSemaforoVerde).getSemaforo()) {
			carreterasEntrantes.get(0).ponSemaforo(true);
		} else {
			if (carreterasEntrantes.get(this.indiceSemaforoVerde).tiempoConsumido()) {
				carreterasEntrantes.get(this.indiceSemaforoVerde).ponSemaforo(false);
					this.carreterasEntrantes.get(indiceSemaforoVerde)
							.actualizarIntervaloCircular(this.maxValorIntervalo, this.minValorIntervalo);
					this.carreterasEntrantes.get(indiceSemaforoVerde).setUnidadesDeTiempoUsadas(0);
					siguienteSemaforo();
			}
			

		}
	}
	
	public void siguienteSemaforo(){
		if(this.indiceSemaforoVerde + 1 >= this.carreterasEntrantes.size()) this.indiceSemaforoVerde = 0;
		else this.indiceSemaforoVerde++;
		this.carreterasEntrantes.get(indiceSemaforoVerde).ponSemaforo(true);
	}
		

	@Override
	protected void completaDetallesSeccion(IniSection is) {
		
		String cadena = cruceCircularDetallesSeccion();
		is.setValue("queues", cadena);
		is.setValue("type", "rr");
		
	}
	
	
	public String cruceCircularDetallesSeccion() {
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
				
				
				if (i < carreterasEntrantes.size() - 1) {
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
