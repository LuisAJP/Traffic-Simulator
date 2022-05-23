package es.ucm.fdi.simuladorTrafico;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class SortedArrayList<E> extends ArrayList<E>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Comparator<E> cmp;
	
	public SortedArrayList() {
		
	}
	
	public SortedArrayList(Comparator<E> cmp) {
		this.cmp=cmp;
	}

	@Override
	public boolean add(E e) {
	// programar la inserci�n ordenada
		boolean r = super.add(e);
		sort(cmp);
		return r;
		
	}
	

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean retorno = true;
		for(E e: c) {
			retorno = super.add(e);
		}
		sort(cmp);
		return retorno;
	// programar inserci�n ordenada (invocando a add)
	}
	// sobreescribir los m�todos que realizan operaciones de
	// inserci�n basados en un �ndice para que lancen excepcion.
	// Ten en cuenta que esta operaci�n romper�a la ordenaci�n.
	// estos m�todos son add(int index, E element),
	// addAll(int index, Colection<? Extends E>) y E set(int index, E element).
	

}
