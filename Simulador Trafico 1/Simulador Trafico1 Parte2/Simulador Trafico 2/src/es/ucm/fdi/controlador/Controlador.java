package es.ucm.fdi.controlador;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import es.ucm.fdi.events.Evento;
import es.ucm.fdi.events.ParserEventos;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.ini.Ini;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.simuladorTrafico.SimuladorTrafico;

public class Controlador {

	private SimuladorTrafico simulador;
	private OutputStream ficheroSalida;
	private InputStream ficheroEntrada;
	private int pasosSimulacion;

	public Controlador(SimuladorTrafico sim, Integer limiteTiempo, InputStream is, OutputStream os) {

		this.simulador = sim;
		this.pasosSimulacion = limiteTiempo;
		this.ficheroEntrada = is;
		this.ficheroSalida = os;
	}

	public void ejecuta() throws ErrorDeSimulacion {

		try {
			this.cargaEventos(this.ficheroEntrada);
			this.simulador.ejecuta(pasosSimulacion, this.ficheroSalida);

		}

		catch (ErrorDeSimulacion e) {
			System.out.println(e.getMessage());
		}
	}

	private void cargaEventos(InputStream inStream) throws ErrorDeSimulacion {
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

}
