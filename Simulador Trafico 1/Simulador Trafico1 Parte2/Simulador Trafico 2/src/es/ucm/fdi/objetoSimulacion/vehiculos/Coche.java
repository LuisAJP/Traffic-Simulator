package es.ucm.fdi.objetoSimulacion.vehiculos;

import java.util.LinkedList;
import java.util.Random;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.objetoSimulacion.cruces.CruceGenerico;

public class Coche extends Vehiculo {

	protected int kmUltimaAveria;
	protected int resistenciaKm;
	protected int duracionMaximaAveria;
	protected double probabilidadDeAveria;
	protected Random numAleatorio;
	protected long semilla;

	public Coche(String id, int velocidadMaxima, int resistencia, double probabilidad, long semilla,
			int duracionMaximaInfraccion, LinkedList<CruceGenerico<?>> itinerario) throws ErrorDeSimulacion {
		super(id, velocidadMaxima, itinerario, "car");
		this.numAleatorio = new Random(semilla);
		this.semilla=semilla;
		this.resistenciaKm = resistencia;
		this.probabilidadDeAveria = probabilidad;
		this.duracionMaximaAveria = duracionMaximaInfraccion;
		this.kmUltimaAveria = 0;

	}

	@Override
	public void avanza() throws ErrorDeSimulacion {
		/*
		 * - Si el coche está averiado poner “kmUltimaAveria” a “kilometraje”. 
		 * - Sino el coche no está averiado y ha recorrido “resistenciakm”, y además
		 * 		“numAleatorio”.nextDouble() < “probabilidadDeAveria”, entonces incrementar
		 * 		“tiempoAveria” con “numAleatorio.nextInt(“duracionMaximaAveria”)+1 
		 * - Llamar a super.avanza();
		 */
		//Random numeroAleatorio1=null;
		if (super.tiempoAveria != 0) {
			this.kmUltimaAveria = kilometraje;
			actualizarTiempoAveria();
		}
		else {
			if (super.kilometraje - this.kmUltimaAveria > this.resistenciaKm) {
				
				double a=numAleatorio.nextDouble();
				//numeroAleatorio1=new Random(this.semilla);
				//double a=numeroAleatorio1.nextDouble();
				if (a < probabilidadDeAveria) {
					//tiempoAveria = numeroAleatorio1.nextInt(this.duracionMaximaAveria) + 1;
					tiempoAveria = numAleatorio.nextInt(this.duracionMaximaAveria) + 1;
					
				}
					
			}
			super.avanza();
		}
	}
	/*
	 * [new_vehicle] time = <NONEG-INTEGER> 
	 * id = <VEHICLE-ID>
	 * itinerary =<JUNC-ID>,<JUNC-ID>(,<JUNC-ID>)* 
	 * max_speed = <POSITIVE-INTEGER> 
	 * type = car
	 * resistance = <POSITIVE-INTEGER> 
	 * fault_probability = <NONEG-DOUBLE>
	 * max_fault_duration = <POSITIVE-INTEGER> 
	 * seed = <POSITIVE-LONG>
	 */

//	@Override
//	protected void completaDetallesSeccion(IniSection is) {
//		super.completaDetallesSeccion(is);
//		is.setValue("type", "car");
//		is.setValue("fault_probability", this.probabilidadDeAveria);
//		is.setValue("seed", this.numAleatorio);
//	}
//	

			
}
