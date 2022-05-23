package es.ucm.fdi.controlador;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import es.ucm.fdi.events.Evento;
import es.ucm.fdi.events.ParserEventos;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.modelo.ObservadorSimuladorTrafico;
import es.ucm.fdi.simuladorTrafico.SimuladorTrafico;

public class Controlador {

	private SimuladorTrafico simulador;
	private OutputStream ficheroSalida;
	
	//private InputStream ficheroEntrada;
	
	//private int pasosSimulacion;

	public Controlador(SimuladorTrafico sim, Integer limiteTiempo, InputStream is, OutputStream os) {

		this.simulador = sim;
		//this.pasosSimulacion = limiteTiempo;
		//this.ficheroEntrada = is;
		this.ficheroSalida = os;
	}
	
	/**Metodo usado en la practica 4 
	 * @throws ErrorDeSimulacion */
//	public void ejecuta() throws ErrorDeSimulacion {
//
//		try {
//			this.cargaEventos(this.ficheroEntrada);
//			this.simulador.ejecuta(pasosSimulacion, this.ficheroSalida);
//
//		}
//
//		catch (ErrorDeSimulacion e) {
//			System.out.println(e.getMessage());
//		}
//	}
	
	public String getInformeModelo() {
		return this.simulador.getInforme();
	}
	
	public void ejecuta(int pasos)  {
		
		try {
//			this.cargaEventos(this.ficheroEntrada);//seria correcto ponerlo aquii?
			this.simulador.ejecuta(pasos,this.ficheroSalida);
		} catch (ErrorDeSimulacion e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		
		
	}

	public void cargaEventos(InputStream inStream) throws ErrorDeSimulacion {
		Ini ini;
		try {
			// lee el fichero y carga su atributo iniSections
			ini = new Ini(inStream);
		}

		catch (IOException e) {
			throw new ErrorDeSimulacion("Error en la lectura de eventos: " + e);
		}

		// recorremos todas los elementos de iniSections para generar el evento
		// correspondiente
		for (IniSection sec : ini.getSections()) {
			// parseamos la sección para ver a que evento corresponde
			Evento e = ParserEventos.parseaEvento(sec);
			if (e != null)
				this.simulador.insertaEvento(e);
			else
				throw new ErrorDeSimulacion("Evento desconocido: " + sec.getTag());
		}
	}
	
	public void reinicia() { 
		this.simulador.reinicia();
	}
	
	public void cambiarFicheroSalida(OutputStream nuevo) {
		this.ficheroSalida = nuevo;
	}
	
	public void guardaInforme() {
		this.simulador.guardaInforme(ficheroSalida);
	}
	
	public void addObserver(ObservadorSimuladorTrafico o) {
		this.simulador.addObservador(o);
	}
	
	public void removeObserver(ObservadorSimuladorTrafico o) {
		this.simulador.removeObservador(o);
	}
	
	
	
	

}
