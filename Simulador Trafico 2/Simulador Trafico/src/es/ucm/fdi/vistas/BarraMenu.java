package es.ucm.fdi.vistas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import es.ucm.fdi.controlador.Controlador;

public class BarraMenu extends JMenuBar{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BarraMenu(VentanaPrincipal mainWindow, Controlador controlador) {

		super();
		// MANEJO DE FICHEROS
		JMenu menuFicheros = new JMenu("Ficheros");
		this.add(menuFicheros);
		this.creaMenuFicheros(menuFicheros, mainWindow);

		// SIMULADOR
		JMenu menuSimulador = new JMenu("Simulador");
		this.add(menuSimulador);
		this.creaMenuSimulador(menuSimulador, controlador, mainWindow);

		// INFORMES
		JMenu menuReport = new JMenu("Informes");
		this.add(menuReport);
		this.creaMenuInformes(menuReport, mainWindow);

	}

	private void creaMenuFicheros(JMenu menu, VentanaPrincipal mainWindow) {
		JMenuItem cargar = new JMenuItem("Carga Eventos");
		cargar.setMnemonic(KeyEvent.VK_L);
		cargar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				ActionEvent.ALT_MASK));
		cargar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.cargaFichero();
			}
		});

		JMenuItem salvar = new JMenuItem("Salvar Eventos");
		salvar.setMnemonic(KeyEvent.VK_L);
		salvar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				ActionEvent.ALT_MASK));
		salvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.guardarFichero();
			}
		});

		JMenuItem salvarInformes = new JMenuItem("Salva Informes");
		salvarInformes.setMnemonic(KeyEvent.VK_L);
		salvarInformes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				ActionEvent.ALT_MASK));
		salvarInformes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					mainWindow.guardarInforme();
				} catch (FileNotFoundException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
					mainWindow.muestraDialogoError("Error al abrir el fichero");
				}
			}
		});

		JMenuItem salir = new JMenuItem("salir");
		salir.setMnemonic(KeyEvent.VK_L);
		salir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				ActionEvent.ALT_MASK));
		salir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.salir();
			}
		});

		menu.add(cargar);
		menu.add(salvar);
		menu.addSeparator();
		menu.add(salvarInformes);
		menu.addSeparator();
		menu.add(salir);
	}
	
	private void creaMenuSimulador(JMenu menuSimulador,Controlador controlador, VentanaPrincipal mainWindow) {
		// TODO Auto-generated method stub
		JMenuItem ejecuta = new JMenuItem("Ejecuta");
		ejecuta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int pasos = mainWindow.getSteps();
				controlador.ejecuta(pasos);
			}
		});
		JMenuItem reinicia = new JMenuItem("Reinicia");
		reinicia.setMnemonic(KeyEvent.VK_L);
		reinicia.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				ActionEvent.ALT_MASK));
		reinicia.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.reinicia();
			}
		});
		
		menuSimulador.add(ejecuta);
		menuSimulador.add(reinicia);
	}
	
	
	private void creaMenuInformes(JMenu menuReport, VentanaPrincipal mainWindow) {
		// TODO Auto-generated method stub
		JMenuItem generaInformes = new JMenuItem("Generar");
		generaInformes.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		// OPCIONAL
			mainWindow.generaInformes();
		}
		});
		menuReport.add(generaInformes);
		
		JMenuItem limpiaAreaInformes = new JMenuItem("Clear");
		limpiaAreaInformes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			// OPCIONAL
				mainWindow.limpiarInforme();
			}
			});
		
		menuReport.add(limpiaAreaInformes);
		
	}
}
