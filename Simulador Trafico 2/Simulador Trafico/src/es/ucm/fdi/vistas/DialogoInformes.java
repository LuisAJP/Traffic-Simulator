package es.ucm.fdi.vistas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.ucm.fdi.controlador.Controlador;
import es.ucm.fdi.events.Evento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.mapaCarreteras.MapaCarreteras;
import es.ucm.fdi.modelo.ObservadorSimuladorTrafico;
import es.ucm.fdi.objetoSimulacion.carreteras.Carretera;
import es.ucm.fdi.objetoSimulacion.cruces.CruceGenerico;
import es.ucm.fdi.objetoSimulacion.vehiculos.Vehiculo;

public class DialogoInformes extends JDialog implements ObservadorSimuladorTrafico {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static final char TECLALIMPIAR = 0;

	private PanelBotones panelBotones;
	private PanelObjSim<Vehiculo> panelVehiculos;
	private PanelObjSim<Carretera> panelCarreteras;
	private PanelObjSim<CruceGenerico<?>> panelCruces;

	private VentanaPrincipal ventanaPrincipal;
	private Controlador controlador;

	public DialogoInformes(VentanaPrincipal ventanaPrincipal, Controlador controlador) {
		// TODO Auto-generated constructor stub
		this.setTitle("Generar Informes");
		this.ventanaPrincipal = ventanaPrincipal;
		this.controlador = controlador;
		this.initGUI();
	}

	private void initGUI() {

		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.PAGE_AXIS));

		// creo 3 paneles
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
		JLabel textoEtiqueta1 = new JLabel("Selecciona objetos para generar Informes.");
		JLabel textoEtiqueta2 = new JLabel("Usa 'c' para deseleccionar todos. ");
		JLabel textoEtiqueta3 = new JLabel("Usa Ctrl + A para seleccionar todos. ");
		panel1.add(textoEtiqueta1);
		panel1.add(textoEtiqueta2);
		panel1.add(textoEtiqueta3);

		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));

		this.panelVehiculos = new PanelObjSim<Vehiculo>("Vehiculos");

		this.panelCarreteras = new PanelObjSim<Carretera>("Carreteras");
		this.panelCruces = new PanelObjSim<CruceGenerico<?>>("Cruces");

		panel2.add(this.panelVehiculos);
		panel2.add(this.panelCarreteras);
		panel2.add(this.panelCruces);

		JPanel panel3 = new JPanel();
		panel3.setLayout(new BorderLayout());
		// this.panelBotones = new PanelBotones(this);
		panel3.add(new PanelBotones(this), BorderLayout.CENTER);

		panelPrincipal.add(panel1);
		panelPrincipal.add(panel2);
		panelPrincipal.add(panel3);

		// InformationPanel panelInfo = new InformationPanel();

		// panelPrincipal.add(panelInfo, BorderLayout.PAGE_START);

		this.add(panelPrincipal);
		this.setVisible(false);
		this.pack();

	}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.setMapa(mapa);

	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		this.setMapa(mapa);
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		this.setMapa(mapa);

	}

	public void mostrar() {
		this.setVisible(true);
	}

	private void setMapa(MapaCarreteras mapa) {
		this.panelVehiculos.setList(mapa.getVehiculos());
		this.panelCarreteras.setList(mapa.getCarreteras());
		this.panelCruces.setList(mapa.getCruces());
	}

	public List<Vehiculo> getVehiculosSeleccionados() {
		return this.panelVehiculos.getSelectedItems();
	}

	public List<Carretera> getCarreterasSeleccionadas() {
		return this.panelCarreteras.getSelectedItems();
	}

	public List<CruceGenerico<?>> getCrucesSeleccionados() {
		return this.panelCruces.getSelectedItems();
	}

	public PanelObjSim<Vehiculo> getPanelObjVehiculo() {
		return this.panelVehiculos;
	}
}
