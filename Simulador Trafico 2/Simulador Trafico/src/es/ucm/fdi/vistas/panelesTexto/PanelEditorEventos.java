package es.ucm.fdi.vistas.panelesTexto;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import es.ucm.fdi.vistas.PanelAreaTexto;
import es.ucm.fdi.vistas.PopUpMenu;
import es.ucm.fdi.vistas.VentanaPrincipal;

public class PanelEditorEventos extends PanelAreaTexto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7177695547855492770L;

	public PanelEditorEventos(String titulo, String texto, boolean editable, VentanaPrincipal mainWindow) {
			
		super(titulo,editable);
		this.setTexto(texto);
		
		// OPCIONAL
		PopUpMenu popUp = new PopUpMenu(mainWindow);
		
		this.areatexto.add(popUp);
		
		this.areatexto.addMouseListener(new MouseListener() {
		
		@Override
		public void mousePressed(MouseEvent e) {
		
			if (e.isPopupTrigger() && areatexto.isEnabled())
			popUp.show(e.getComponent(), e.getX(), e.getY());
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
