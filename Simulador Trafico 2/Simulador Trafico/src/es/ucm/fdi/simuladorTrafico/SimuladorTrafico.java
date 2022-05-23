package es.ucm.fdi.simuladorTrafico;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import es.ucm.fdi.events.Evento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.exceptions.RelacionCruceNoEncotrada;
import es.ucm.fdi.mapaCarreteras.MapaCarreteras;
import es.ucm.fdi.modelo.ObservadorSimuladorTrafico;
import es.ucm.fdi.objetoSimulacion.carreteras.Carretera;
import es.ucm.fdi.objetoSimulacion.cruces.CruceGenerico;

public class SimuladorTrafico implements Observador<ObservadorSimuladorTrafico> {
	private MapaCarreteras mapa;
	private SortedArrayList<Evento> eventos;
	private int contadorTiempo;
	private String report;
	private List<ObservadorSimuladorTrafico> observadores;

	public SimuladorTrafico() {
		this.mapa = new MapaCarreteras();
		this.contadorTiempo = 0;
		this.report = "";
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
		this.eventos = new SortedArrayList<>(cmp); // estructura ordenada por
													// “tiempo”

		this.observadores = new ArrayList<>();
	}

	public String getInforme() {
		return this.report;
	}

	// falta implementar
	private void notificaError(ErrorDeSimulacion err) {
		for (ObservadorSimuladorTrafico o : this.observadores) {
			o.errorSimulador(this.contadorTiempo, this.mapa, this.eventos, err);
		}

	}

	// falta implementar
	private void notificaReinicia() {
		// TODO Auto-generated method stub
		for (ObservadorSimuladorTrafico o : this.observadores) {
			o.reinicia(this.contadorTiempo, this.mapa, this.eventos);
		}
	}

	private void notificaNuevoEvento() {
		for (ObservadorSimuladorTrafico o : this.observadores) {
			o.addEvento(this.contadorTiempo, this.mapa, this.eventos);
		}
	}

	private void notificaOnExecute() {// Esto lo he puesto
		for (ObservadorSimuladorTrafico o : this.observadores) {
			o.avanza(this.contadorTiempo, this.mapa, this.eventos);
		}
	}

	public void insertaEvento(Evento e) throws ErrorDeSimulacion {
		if (e != null) {
			if (e.getTiempo() < this.contadorTiempo) {
				ErrorDeSimulacion err = new ErrorDeSimulacion();
				this.notificaError(err);
				throw err;
			} else {
				this.eventos.add(e);
				this.notificaNuevoEvento(); // se notifica a los observadores
			}
		} else {
			ErrorDeSimulacion err = new ErrorDeSimulacion("lista de eventos vacia");
			this.notificaError(err); // se notifica a los observadores
			throw err;
		}
	}

	public void ejecuta(int pasosSimulacion, OutputStream ficheroSalida) throws ErrorDeSimulacion {
		if (eventos.isEmpty()) {
			ErrorDeSimulacion err = new ErrorDeSimulacion("No se puede ejecutar, no hay eventos en el simulador");
			this.notificaError(err); // se notifica a los observadores
			throw err;
		}

		// this.mapa.setInformeString();

		int limiteTiempo = this.contadorTiempo + pasosSimulacion - 1;
		while (this.contadorTiempo <= limiteTiempo) {
			// 1. ejecutar los eventos correspondientes a ese tiempo
			for (Evento e : eventos) {
				if (getContadorTiempo() == e.getTiempo()) {
					e.ejecuta(mapa);
				}
			}
			// actualizar “mapa”
			// 2. invocar al método avanzar de las carreteras
			for (Carretera c : mapa.getCarreteras()) {
				c.avanza();
			}
			// 3. invocar al método avanzar de los cruces
			for (CruceGenerico<?> cr : mapa.getCruces()) {
				try {
					cr.avanza();
				} catch (RelacionCruceNoEncotrada e1) {
					throw new ErrorDeSimulacion(e1.getMessage());
				}
			}
			// 4. this.contadorTiempo++;
			this.contadorTiempo++;
			// 5. escribir un informe en OutputStream(fichero salida) en caso de
			// que no sea null
			report = report + mapa.generateReport(getContadorTiempo());
		}

		// this.notificaOnExecute(); // se notifica a los observadores
		guardaInforme(ficheroSalida);

		this.notificaOnExecute(); // se notifica a los observadores
	}

	public void guardaInforme(OutputStream ficheroSalida) {
		byte[] bytes = report.getBytes();
		try {
			ficheroSalida.write(bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** Este metodo se usaba en la practica 4 */
	// public void insertaEvento(Evento e) {
	// // inserta un evento en “eventos”, controlando que el tiempo de
	// // ejecución del evento sea menor que “contadorTiempo”
	// eventos.add(e);
	// }

	public int getContadorTiempo() {

		return this.contadorTiempo;
	}

	@Override
	public void addObservador(ObservadorSimuladorTrafico o) {
		// TODO Auto-generated method stub
		if (o != null && !this.observadores.contains(o)) {
			this.observadores.add(o);
		}
	}

	@Override
	public void removeObservador(ObservadorSimuladorTrafico o) {
		// TODO Auto-generated method stub
		if (o != null && this.observadores.contains(o)) {
			this.observadores.remove(o);
		}

	}

	public void reinicia() {
		// TODO Auto-generated method stub
		this.mapa = new MapaCarreteras();
		this.contadorTiempo = 0;
		this.report = "";
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
		this.eventos = new SortedArrayList<>(cmp); // estructura ordenada por
													// “tiempo”

		// this.observadores = new ArrayList<>();
		notificaReinicia();
	}

	public MapaCarreteras getMapa() {
		return this.mapa;
	}

}