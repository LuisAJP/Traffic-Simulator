package es.ucm.fdi.vistas.tablas;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import es.ucm.fdi.controlador.Controlador;
import es.ucm.fdi.modelo.ObservadorSimuladorTrafico;

public abstract class ModeloTabla<T> extends DefaultTableModel
implements ObservadorSimuladorTrafico {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String[] columnIds;
	protected List<T> lista;

	public ModeloTabla(String[] columnIdEventos, Controlador ctrl) {
		this.lista = null;
		this.columnIds=columnIdEventos;
		
		
		ctrl.addObserver(this);
	}

	@Override
	public String getColumnName(int col) {
		return this.columnIds[col];
	}

	@Override
	public int getColumnCount() {
		return this.columnIds.length;
	}

	@Override
	public int getRowCount() {
		return this.lista == null ? 0 : this.lista.size();
	}
	
	//metodo añadido por luis
	public List<T> getLista(){
		return this.lista;
	}
}
