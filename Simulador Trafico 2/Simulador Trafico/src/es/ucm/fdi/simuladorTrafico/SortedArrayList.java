package es.ucm.fdi.simuladorTrafico;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class SortedArrayList<E> extends ArrayList<E> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Comparator<E> cmp;

	public SortedArrayList() {

	}

	public SortedArrayList(Comparator<E> cmp) {
		this.cmp = cmp;
	}

	// hay que buscar la posición en la que va el elemento
	// y luego llamar a super.add(i,e)

	@Override
	public boolean add(E e) {
		// programar la inserción ordenada
		//
		int pos = buscaPos(e, 0, this.size() - 1);
		super.add(pos, e);
		boolean r = (pos >= 0 && pos < this.size());

//		 boolean r = super.add(e);
//		 sort(cmp);
		return r;

	}

	public int buscaPos(E e, int ini, int fin) {

		if (ini > fin) {
			return ini;
		}
		else {
			int m = (ini + fin) / 2;
			if (cmp.compare(e, this.get(m)) == -1)
				return buscaPos(e, ini, m -1 );
			else
				return buscaPos(e, m + 1 , fin);
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean retorno = true;
		for (E e : c) {
			retorno = super.add(e);
		}
		sort(cmp);
		return retorno;
		// programar inserción ordenada (invocando a add)
	}

	@Override
	public void add(int index, E element) {
		throw new IndexOutOfBoundsException("No se puede insertar en una posicion determinada ");
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		throw new IndexOutOfBoundsException("No se puede insertar en una posicion determinada ");
	}

	public E set(int index, E element) {
		throw new IndexOutOfBoundsException("No se puede modificar una posicion determinada ");
	}
}
