package es.ucm.fdi.simuladorTrafico;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import es.ucm.fdi.eventos.Evento;
import es.ucm.fdi.excepciones.ErrorDeSimulacion;
import es.ucm.fdi.mapaCarreteras.MapaCarreteras;
import es.ucm.fdi.objetoSimulacion.Carretera;
import es.ucm.fdi.objetoSimulacion.Cruce;


public class SimuladorTrafico {
	private MapaCarreteras mapa;
	private List<Evento> eventos;
	private int contadorTiempo;
	private  String report;
	
	
	public SimuladorTrafico() {
		this.mapa = new MapaCarreteras();
		this.contadorTiempo = 0;
		this.report= "";
//		Comparator<Evento> cmp = new Comparator<Evento>() {
//			@Override
//			public int compare(Evento o1, Evento o2) {
//				return o1.getTiempo() > o2.getTiempo() ? 1 :
//					o1.getTiempo() < o2.getTiempo() ? -1 :
//					0;
//			}};
		this.eventos = new ArrayList<Evento>(); // estructura ordenada por “tiempo”
	}
	
	
	/**metodo sort que ordena los eventos segun el tiempo*/
	public void sort() {
			Collections.sort(eventos, new Comparator<Evento>() {
			
			public int compare(Evento o1, Evento o2) {
				
				if (o1.getTiempo() == o2.getTiempo()) 
					return 0;
				else if (o1.getTiempo() < o2.getTiempo()) 
					return -1;
				else 
					return 1;
			}	
		});
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
			for (Cruce cr : mapa.getListaCruces()) {
				cr.avanza();
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