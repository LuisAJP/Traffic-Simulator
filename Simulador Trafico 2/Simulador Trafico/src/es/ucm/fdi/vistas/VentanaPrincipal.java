package es.ucm.fdi.vistas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import es.ucm.fdi.controlador.Controlador;
import es.ucm.fdi.events.Evento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.mapaCarreteras.MapaCarreteras;
import es.ucm.fdi.modelo.ObservadorSimuladorTrafico;
import es.ucm.fdi.objetoSimulacion.carreteras.Carretera;
import es.ucm.fdi.objetoSimulacion.cruces.CruceGenerico;
import es.ucm.fdi.objetoSimulacion.vehiculos.Vehiculo;
import es.ucm.fdi.vistas.panelesTexto.PanelEditorEventos;
import es.ucm.fdi.vistas.panelesTexto.PanelInformes;
import es.ucm.fdi.vistas.tablas.ModeloTablaCarreteras;
import es.ucm.fdi.vistas.tablas.ModeloTablaCruces;
import es.ucm.fdi.vistas.tablas.ModeloTablaEventos;
import es.ucm.fdi.vistas.tablas.ModeloTablaVehiculos;
import es.ucm.fdi.vistas.tablas.PanelTabla;

public class VentanaPrincipal extends JFrame implements ObservadorSimuladorTrafico {

	private static final long serialVersionUID = 1L;

	private Thread myGUIThread;
	private Controlador controlador;
	public static Border bordePorDefecto = BorderFactory.createLineBorder(Color.black, 2);

	// SUPERIOR PANEL
	static private final String[] columnIdEventos = { "#", "Tiempo", "Tipo" };
	private PanelAreaTexto panelEditorEventos;
	private PanelTabla<Evento> panelColaEventos;
	private PanelAreaTexto panelInformes;

	// MENU AND TOOL BAR
	private JFileChooser fc;
	private ToolBar toolbar;

	// GRAPHIC PANEL
	private ComponenteMapa componenteMapa;

	// STATUS BAR (INFO AT THE BOTTOM OF THE WINDOW)
	private PanelBarraEstado panelBarraEstado;

	// INFERIOR PANEL
	static private final String[] columnIdVehiculo = { "ID", "Carretera", "Localizacion", "Vel.", "Km", "Tiempo. Ave.",
			"Itinerario" };
	static private final String[] columnIdCarretera = { "ID", "Origen", "Destino", "Longitud", "Vel. Max",
			"Vehiculos" };
	static private final String[] columnIdCruce = { "ID", "Verde", "Rojo" };
	private PanelTabla<Vehiculo> panelVehiculos;
	private PanelTabla<Carretera> panelCarreteras;
	private PanelTabla<CruceGenerico<?>> panelCruces;

	// opcional // REPORT DIALOG
	private DialogoInformes dialogoInformes;

	// MODEL PART - VIEW CONTROLLER MODEL
	private File ficheroActual;

	// PopMenu
	// private PopUpMenu popUpMenu;

	public VentanaPrincipal(String ficheroEntrada, Controlador ctrl) throws FileNotFoundException {
		super("Simulador de Trafico");
		this.controlador = ctrl;
		this.ficheroActual = ficheroEntrada != null ? new File(ficheroEntrada) : null;
		this.initGUI();
		ctrl.addObserver(this);// añadimos la ventana principal como observadora
	}

	private void initGUI() {
		// TODO Auto-generated method stub
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}
			// al salir pide confirmación
		});

		// Panel Principal
		JPanel panelPrincipal = this.creaPanelPrincipal();
		panelPrincipal.setLayout(new BorderLayout());

		// BARRA DE ESTADO INFERIOR (contiene una JLabel para mostrar el estado del
		// simulador)
		this.addBarraEstado(panelPrincipal);

		// PANEL QUE CONTIENE EL RESTO DE COMPONENTES
		// (Lo dividimos en dos paneles (superior e inferior)
		JPanel panelCentral = this.createPanelCentral();
		panelPrincipal.add(panelCentral, BorderLayout.CENTER);

		// PANEL SUPERIOR
		this.createPanelSuperior(panelCentral);

		// MENU
		BarraMenu menubar = new BarraMenu(this, this.controlador);
		this.setJMenuBar(menubar);

		// PANEL INFERIOR
		this.createPanelInferior(panelCentral);

		// BARRA DE HERRAMIENTAS
		this.addToolBar(panelPrincipal);

		// FILE CHOOSER
		this.fc = new JFileChooser();
		// REPORT DIALOG (OPCIONAL)
		this.dialogoInformes = new DialogoInformes(this, this.controlador);

		// this.setContentPane(panelPrincipal);
		this.add(panelPrincipal);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	private void addToolBar(JPanel panelPrincipal) {
		// TODO Auto-generated method stub
		this.toolbar = new ToolBar(this, this.controlador);
		panelPrincipal.add(this.toolbar, BorderLayout.PAGE_START);

	}

	private void createPanelSuperior(JPanel panelCentral) {
		// TODO Auto-generated method stub
		JPanel panelSuperior = new JPanel();
		panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.X_AXIS));// Creamos un panel con layout
																				// “BoxLayout” alineado en el eje de las
																				// X
		this.panelEditorEventos = new PanelEditorEventos("Eventos", "", true, this);
		// Opcional
		PopUpMenu popUpMenu = new PopUpMenu(this);
		// this.panelEditorEventos.getTextoArea().add(popUpMenu);
		this.panelEditorEventos.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if (e.isPopupTrigger() && panelEditorEventos.isEnabled()) {
					popUpMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		this.panelColaEventos = new PanelTabla<Evento>("Cola Eventos: ",
				new ModeloTablaEventos(VentanaPrincipal.columnIdEventos, this.controlador));
		this.panelInformes = new PanelInformes("Informes: ", false, this.controlador);

		panelSuperior.add(this.panelEditorEventos);
		panelSuperior.add(this.panelColaEventos);
		panelSuperior.add(this.panelInformes);

		panelCentral.add(panelSuperior);

	}

	private void createPanelInferior(JPanel panelCentral) {
		// TODO Auto-generated method stub
		JPanel panelInferior = new JPanel();
		panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.X_AXIS));// Creamos un panel con layout
																				// “BoxLayout” alineado en el eje de las
																				// X

		JPanel panelInferiorIzquierda = new JPanel(new GridLayout(3, 1));
		JPanel panelInferiorDerecha = new JPanel();

		panelInferiorDerecha.setLayout(new GridLayout(1, 1));

		this.panelVehiculos = new PanelTabla<Vehiculo>("Vehiculos",
				new ModeloTablaVehiculos(VentanaPrincipal.columnIdVehiculo, this.controlador));

		this.panelCarreteras = new PanelTabla<Carretera>("Carretras",
				new ModeloTablaCarreteras(VentanaPrincipal.columnIdCarretera, this.controlador));

		this.panelCruces = new PanelTabla<CruceGenerico<?>>("Cruces",
				new ModeloTablaCruces(VentanaPrincipal.columnIdCruce, this.controlador));

		this.componenteMapa = new ComponenteMapa(this.controlador);
		// añadir un ScroolPane al panel inferior donde se coloca la componente.

		panelInferiorIzquierda.add(this.panelVehiculos);
		panelInferiorIzquierda.add(this.panelCarreteras);
		panelInferiorIzquierda.add(this.panelCruces);
		panelInferiorDerecha.add(new JScrollPane(componenteMapa));
		// panelInferiorDerecha.add(this.componenteMapa);

		panelInferior.add(panelInferiorIzquierda);
		panelInferior.add(panelInferiorDerecha);
		panelCentral.add(panelInferior, BorderLayout.SOUTH);

	}

	private void addBarraEstado(JPanel panelPrincipal) {
		this.panelBarraEstado = new PanelBarraEstado("Bienvenido al simulador !", this.controlador);
		// se añade al panel principal (el que contiene al panel
		// superior y al inferior)
		panelPrincipal.add(this.panelBarraEstado, BorderLayout.PAGE_END);

	}

	private JPanel createPanelCentral() {
		JPanel panelCentral = new JPanel();
		// para colocar el panel superior e inferior
		panelCentral.setLayout(new GridLayout(2, 1));
		return panelCentral;
	}

	private JPanel creaPanelPrincipal() {
		// TODO Auto-generated method stub
		return new JPanel();
	}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.panelVehiculos.avanza(tiempo, mapa, eventos);
		this.panelCarreteras.avanza(tiempo, mapa, eventos);
		this.panelCruces.avanza(tiempo, mapa, eventos);

	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		this.panelColaEventos.addEvento(tiempo, mapa, eventos);
	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		this.panelVehiculos.reinicia(tiempo, mapa, eventos);
		this.panelCarreteras.reinicia(tiempo, mapa, eventos);
		this.panelCruces.reinicia(tiempo, mapa, eventos);
		this.panelColaEventos.reinicia(tiempo, mapa, eventos);
	}

	public void cargaFichero() {
		int returnVal = this.fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File fichero = this.fc.getSelectedFile();
			String s = leeFichero(fichero);
			this.controlador.reinicia();
			this.ficheroActual = fichero;
			this.panelEditorEventos.setTexto(s);
			this.panelEditorEventos.setBorde(this.ficheroActual.getName());
			this.panelBarraEstado.setMensaje("Fichero " + fichero.getName() + " de eventos cargado into the editor");
		}
	}

	public void guardarFichero() {
		controlador.guardaInforme();
	}

	public void clear() {
		this.panelEditorEventos.setTexto("");
	}

	public void limpiarInforme() {
		this.panelInformes.setTexto("");
	}

	private static String leeFichero(File file) {
		String s = "";
		try {
			s = new Scanner(file).useDelimiter("\\A").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return s;
	}

	protected void muestraDialogoError(String error) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(null, error, "alert", JOptionPane.ERROR_MESSAGE);

	}

	public int getSteps() {
		return (int) this.toolbar.getSteps().getValue();
	}

	public void generaInformes() {
		this.dialogoInformes.setVisible(true);
	}

	public void guardarInforme() throws FileNotFoundException {
		int returnVal = fc.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			OutputStream nuevo = new FileOutputStream(file);
			controlador.cambiarFicheroSalida(nuevo);
			controlador.guardaInforme();
		}
	}

	public void salir() {
		int n = JOptionPane.showOptionDialog(new JFrame(), "¿Estás seguro de que quieres Salir?", "Quit",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (n == 0) {
			setVisible(false);
			System.exit(0);
		}
	}

	public String getContenidoFicheroActual() {
		return leeFichero(this.ficheroActual);
	}

	public void updateInformes(String s) {
		this.panelInformes.setTexto(s);
	}

	public void dormir() {
		this.toolbar.dormir();
	}

	public void despertar() {
		this.toolbar.despertar();
	}

	public int getDelay() {
		return this.toolbar.getDelay();
	}

	public int steps() {
		return (int) this.toolbar.getSteps().getValue();
	}

	// public String getTextoEditorEventos() {
	// return this.panelEditorEventos.getTexto();
	// }

	public void startHilo() {
		this.myGUIThread.start();
	}
	
	public void interruptThread() {
		if (this.myGUIThread!=null){
		this.myGUIThread.interrupt();
		}
	}

	public void newThread() {
		if (this.myGUIThread == null){
		this.myGUIThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				dormir();
				int pasos = steps();
				while (!Thread.interrupted() && pasos > 0) {
					pasos--;
					controlador.ejecuta(1);
					updateInformes(controlador.getInformeModelo());

					try {
						Thread.sleep(getDelay());
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
				despertar();
				myGUIThread = null;
				
			}
		});
		}
	}

}
