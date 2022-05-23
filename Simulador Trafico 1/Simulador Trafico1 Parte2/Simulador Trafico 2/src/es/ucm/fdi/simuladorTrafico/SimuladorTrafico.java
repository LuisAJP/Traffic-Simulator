package es.ucm.fdi.simuladorTrafico;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Comparator;

import es.ucm.fdi.events.Evento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.exceptions.RelacionCruceNoEncotrada;
import es.ucm.fdi.mapaCarreteras.MapaCarreteras;
import es.ucm.fdi.objetoSimulacion.carreteras.Carretera;
import es.ucm.fdi.objetoSimulacion.cruces.CruceGenerico;


public class SimuladorTrafico {
	private MapaCarreteras mapa;
	private SortedArrayList<Evento> eventos;
	private int contadorTiempo;
	private  String report;
	
	
	public SimuladorTrafico() {
		this.mapa = new MapaCarreteras();
		this.contadorTiempo = 0;
		this.report= "";
		Comparator<Evento> cmp = new Comparator<Evento>() {

			@Override
			public int compare(Evento arg0, Evento arg1) {
				if (arg0.getTiempo() < arg1.getTiempo())
					return -1;
				if (arg0.getTiempo() > arg1.getTiempo())
					return 1;
				return 0;

			}
		};
		this.eventos = new SortedArrayList<>(cmp); // estructura ordenada por “tiempo”
	}
	
	public void ejecuta(int pasosSimulacion, OutputStream ficheroSalida) throws ErrorDeSimulacion {
		
		int limiteTiempo = this.contadorTiempo + pasosSimulacion - 1;
		while (this.contadorTiempo <= limiteTiempo) {
			// 1. ejecutar los eventos correspondientes a ese tiempo
			for (Evento e : eventos) {
				if(getContadorTiempo()==e.getTiempo()) {
					e.ejecuta(mapa);
				}
			}
		// actualizar “mapa”
			// 2. invocar al método avanzar de las carreteras
			for (Carretera c : mapa.getListaCarreteras()) {
				c.avanza();
			}
			// 3. invocar al método avanzar de los cruces
			for (CruceGenerico<?> cr : mapa.getListaCruces()) {
				try {
					cr.avanza();
				} catch (RelacionCruceNoEncotrada e1) {
					throw new ErrorDeSimulacion(e1.getMessage());
				}
			}
			// 4. this.contadorTiempo++;
			this.contadorTiempo++;
		// 5. escribir un informe en OutputStream(fichero salida) en caso de que no sea null
			report=report +mapa.generateReport(getContadorTiempo());	
		}
		
		byte[] bytes = report.getBytes();
		try {
			ficheroSalida.write(bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void insertaEvento(Evento e) {
		// inserta un evento en “eventos”, controlando que el tiempo de
		// ejecución del evento sea menor que “contadorTiempo”
		eventos.add(e);
	}
	
	public int getContadorTiempo() {
		
		return this.contadorTiempo;
	}
}