package es.ucm.fdi.vistas.tablas;

import java.util.List;

import javax.swing.SwingUtilities;

import es.ucm.fdi.controlador.Controlador;
import es.ucm.fdi.events.Evento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.mapaCarreteras.MapaCarreteras;
import es.ucm.fdi.objetoSimulacion.vehiculos.Vehiculo;

public class ModeloTablaVehiculos extends ModeloTabla<Vehiculo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModeloTablaVehiculos(String[] columnIdEventos, Controlador ctrl) {
		super(columnIdEventos, ctrl);
		// TODO Auto-generated constructor stub
	}

	@Override
	// necesario para que se visualicen los datos
	public Object getValueAt(int indiceFil, int indiceCol) {
		Object s = null;
		switch (indiceCol) {
		case 0:
			s = this.lista.get(indiceFil).getId();
			break;
		case 1:
			s = this.lista.get(indiceFil).getCarretera();
			break;
		case 2:
			s = this.lista.get(indiceFil).getLocalizacion();
			break;
		case 3:
			s = this.lista.get(indiceFil).getVelocidadActual();
			break;
		case 4:
			s = this.lista.get(indiceFil).getKilometraje();
			break;
		case 5:
			s = this.lista.get(indiceFil).getTiempoAveria();
			break;
		case 6:
			s = this.lista.get(indiceFil).getItinerario();
			break;
		default:
			assert (false);
		}
		return s;
	}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map,
			List<Evento> eventos, ErrorDeSimulacion e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				lista = mapa.getVehiculos();
				fireTableStructureChanged();

			}

		});

	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				lista = null;
				fireTableStructureChanged();

			}

		});

	}

}
