package es.ucm.fdi.vistas;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;

import es.ucm.fdi.controlador.Controlador;
import es.ucm.fdi.events.Evento;
import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.ini.IniError;
import es.ucm.fdi.mapaCarreteras.MapaCarreteras;
import es.ucm.fdi.modelo.ObservadorSimuladorTrafico;

public class ToolBar extends JToolBar implements ObservadorSimuladorTrafico {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JSpinner steps;
	private JTextField time;
	private JSpinner delay;

	private JButton botonGuardar;
	private JButton botonPlay;
	private JButton botonCargar;
	private JButton botonClear;
	private JButton botonCheckIn;
	private JButton botonReiniciar;
	private JButton botonGeneraReports;
	private JButton botonStop;

	public ToolBar(VentanaPrincipal mainWindow, Controlador controlador) {
		super();
		this.iniGUI(mainWindow, controlador);
		controlador.addObserver(this);
	}

	private void iniGUI(VentanaPrincipal mainWindow, Controlador controlador) {
		// TODO Auto-generated method stub
		this.cargarEventos(mainWindow);
		this.guardarEventos(mainWindow);
		this.limpiarEventos(mainWindow);
		this.checkIn(controlador, mainWindow);
		this.detenerEjecucion(mainWindow);
		this.playSimulador(mainWindow, controlador);
		this.reiniciar(mainWindow, controlador);
		this.delaySpinner();
		this.barraSpinner();
		this.barraTiempo();
		this.barraInformes(mainWindow);
		this.limpiarInformes(mainWindow);
		this.guardarInformes(mainWindow);
		this.salir(mainWindow);

	}

	public void detenerEjecucion(VentanaPrincipal mainWindow) {
		botonStop = new JButton();
		botonStop.setToolTipText("Detiene la ejecución actual");
		botonStop.setIcon(new ImageIcon("resources\\icons\\stop.png"));
		botonStop.setEnabled(false);
		botonStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				despertar();
				mainWindow.interruptThread();
			}

		});

		this.add(botonStop);
	}

	// Carga un fichero de eventos
	public void cargarEventos(VentanaPrincipal mainWindow) {
		botonCargar = new JButton();
		botonCargar.setToolTipText("Carga un fichero de ventos");
		botonCargar.setIcon(new ImageIcon("resources\\icons\\open.png"));
		botonCargar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.cargaFichero();
			}
		});
		this.add(botonCargar);
	}

	// salva un fichero de eventos
	public void guardarEventos(VentanaPrincipal mainWindow) {
		botonGuardar = new JButton();
		botonGuardar.setToolTipText("guarda un fichero de eventos");
		botonGuardar.setIcon(new ImageIcon("resources\\icons\\save.png"));
		botonGuardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.guardarFichero();
			}
		});
		this.add(botonGuardar);
	}

	// limpia zona de Eventos
	public void limpiarEventos(VentanaPrincipal mainWindow) {
		botonClear = new JButton();
		botonClear.setToolTipText("clear");
		botonClear.setIcon(new ImageIcon("resources\\icons\\clear.png"));
		botonClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.clear();
			}
		});
		this.add(botonClear);
	}

	// Insertar eventos en el simulador
	public void checkIn(Controlador controlador, VentanaPrincipal mainWindow) {
		botonCheckIn = new JButton();
		botonCheckIn.setToolTipText("Carga los eventos al simulador");
		// botonCheckIn.setIcon(new
		// ImageIcon(Utils.loadImage("resources/icons/events.png")));
		botonCheckIn.setIcon(new ImageIcon("resources\\icons\\events.png"));
		botonCheckIn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					controlador.reinicia();
					byte[] contenido = mainWindow.getContenidoFicheroActual().getBytes();
					// byte[] contenido = mainWindow.getTextoEditorEventos().getBytes();
					controlador.cargaEventos(new ByteArrayInputStream(contenido));

				} catch (ErrorDeSimulacion err) {
					mainWindow.muestraDialogoError("Error al cargar los eventos en el simulador");
				} catch (IniError e1) {
					// System.out.println(e1.getMessage());
					mainWindow.muestraDialogoError("Error al cargar los eventos en el simulador");
				}

			}
		});
		this.add(botonCheckIn);

	}

	public void playSimulador(VentanaPrincipal mainWindow, Controlador controlador) {
		botonPlay = new JButton();
		botonPlay.setToolTipText("ejecutar");
		botonPlay.setIcon(new ImageIcon("resources\\icons\\play.png"));
		botonPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.newThread();
				mainWindow.startHilo();
			}
		});
		this.add(botonPlay);
	}

	public void reiniciar(VentanaPrincipal mainWindow, Controlador controlador) {
		botonReiniciar = new JButton();
		botonReiniciar.setToolTipText("reiniciar");
		botonReiniciar.setIcon(new ImageIcon("resources\\icons\\reset.png"));
		botonReiniciar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.reinicia();
				// FALTA PONER LLAMADA
			}
		});
		this.add(botonReiniciar);
	}

	// generar Informes
	public void barraInformes(VentanaPrincipal mainWindow) {

		// OPCIONAL
		botonGeneraReports = new JButton();
		botonGeneraReports.setToolTipText("Generar informes");
		botonGeneraReports.setIcon(new ImageIcon("resources\\icons\\report.png"));
		botonGeneraReports.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.generaInformes();
			}
		});
		this.add(botonGeneraReports);

	}

	public void barraSpinner() {

		this.add(new JLabel(" Pasos: "));
		this.steps = new JSpinner(new SpinnerNumberModel(5, 1, 1000, 1));
		this.steps.setToolTipText("pasos a ejecutar: 1-1000");
		this.steps.setMaximumSize(new Dimension(70, 70));
		this.steps.setMinimumSize(new Dimension(70, 70));
		this.steps.setValue(1);
		this.add(steps);
	}

	public void delaySpinner() {

		this.add(new JLabel(" Delay: "));
		this.delay = new JSpinner(new SpinnerNumberModel(5, 1, 10000, 1));
		this.delay.setToolTipText("tiempo de sueño (ms)");
		this.delay.setMaximumSize(new Dimension(70, 70));
		this.delay.setMinimumSize(new Dimension(70, 70));
		this.delay.setValue(1000);
		this.add(delay);
	}

	public void barraTiempo() {
		this.add(new JLabel(" Tiempo: "));
		this.time = new JTextField("0", 5);
		this.time.setToolTipText("Tiempo actual");
		this.time.setMaximumSize(new Dimension(70, 70));
		this.time.setMinimumSize(new Dimension(70, 70));
		this.time.setEditable(false);
		this.add(this.time);
	}

	// limpiar Area de Informes
	public void limpiarInformes(VentanaPrincipal mainWindow) {
		JButton botonLimpiar = new JButton();
		botonLimpiar.setToolTipText("limpia Area de Informes");
		botonLimpiar.setIcon(new ImageIcon("resources\\icons\\delete_report.png"));
		botonLimpiar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.limpiarInforme();
			}
		});
		this.add(botonLimpiar);
	}

	// Guardar Informes
	public void guardarInformes(VentanaPrincipal mainWindow) {
		JButton botonGuardar = new JButton();
		botonGuardar.setToolTipText("Guardar Informes");
		botonGuardar.setIcon(new ImageIcon("resources\\icons\\save_report.png"));
		botonGuardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					mainWindow.guardarInforme();
				} catch (FileNotFoundException e1) {
					mainWindow.muestraDialogoError("Error al abrir el archivo");
				}
			}
		});
		this.add(botonGuardar);
	}

	// Salir
	public void salir(VentanaPrincipal mainWindow) {
		JButton botonSalir = new JButton();
		botonSalir.setToolTipText("Salir");
		botonSalir.setIcon(new ImageIcon("resources\\icons\\exit.png"));
		botonSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				mainWindow.salir();
			}

		});
		this.add(botonSalir);
	}

	@Override
	public void errorSimulador(int tiempo, MapaCarreteras map, List<Evento> eventos, ErrorDeSimulacion e) {

	}

	@Override
	public void avanza(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		this.time.setText("" + tiempo);
	}

	@Override
	public void addEvento(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reinicia(int tiempo, MapaCarreteras mapa, List<Evento> eventos) {
		// TODO Auto-generated method stub
		this.steps.setValue(1);
		this.time.setText("0");

	}

	public JSpinner getSteps() {
		return this.steps;
	}

	public int getDelay() {
		return (int) this.delay.getValue();
	}

	public void dormir() {
		this.botonGuardar.setEnabled(false);
		this.botonCargar.setEnabled(false);
		this.botonCheckIn.setEnabled(false);
		this.botonGeneraReports.setEnabled(false);
		this.botonPlay.setEnabled(false);
		this.botonReiniciar.setEnabled(false);
		this.botonClear.setEnabled(false);
		this.botonStop.setEnabled(true);
	}

	public void despertar() {
		this.botonGuardar.setEnabled(true);
		this.botonCargar.setEnabled(true);
		this.botonCheckIn.setEnabled(true);
		this.botonGeneraReports.setEnabled(true);
		this.botonPlay.setEnabled(true);
		this.botonReiniciar.setEnabled(true);
		this.botonClear.setEnabled(true);
		this.botonStop.setEnabled(false);
	}

}
