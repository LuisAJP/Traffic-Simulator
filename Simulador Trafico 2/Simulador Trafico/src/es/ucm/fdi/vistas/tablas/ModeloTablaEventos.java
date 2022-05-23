package es.ucm.fdi.vistas.tablas;

import java.util.List;

import javax.swing.SwingUtilities;

import es.ucm.fdi.controlador.Controlador;
import es.ucm.fdi.events.Evento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.mapaCarreteras.MapaCarreteras;

public class ModeloTablaEventos extends ModeloTabla<Evento> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModeloTablaEventos(String[] columnIdEventos, Controlador ctrl) {

		super(columnIdEventos, ctrl);
	}

	@Override
	// necesario para que se visualicen los datos
	public Object getValueAt(int indiceFil, int indiceCol) {
		Object s = null;
		switch (indiceCol) {
		case 0:
			s = indiceFil;
			break;
		case 1:
			s = this.lista.get(indiceFil).getTiempo();
			break;
		case 2:
			s = this.lista.get(indiceFil).toString();
			break;
		default:
			assert (false);
		}
		return s;
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				lista = eventos;
				fireTableStructureChanged();
			}

		});

	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				lista = eventos;
				fireTableStructureChanged();

			}

		});

	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				lista = null;
				fireTableStructureChanged();

			}

		});

	}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map,
			List<Evento> eventos, ErrorDeSimulacion e) {
		// TODO Auto-generated method stub

	}
}
