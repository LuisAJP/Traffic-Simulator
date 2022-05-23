package es.ucm.fdi.vistas;

import java.util.List;

import javax.swing.DefaultListModel;

public class ListModel<T> extends DefaultListModel<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<T> lista;
	
	ListModel() { 
		this.lista = null;
	}
	
	public void setList(List<T> lista) {
		this.lista = lista;
		fireContentsChanged(this, 0, this.lista.size());
	}
	
	@Override
	public T getElementAt(int index) {
		return null; 
		
	}
	@Override
	public int getSize() {
		return this.lista == null ? 0 : this.lista.size();
	}
	
	
	
	
	
	
	
	
//	public void addElement(T e){
//		this.lista.add(e);
//		fireContentsChanged(this,lista.size()-1,lista.size()-1);
//	}
	
	
}
