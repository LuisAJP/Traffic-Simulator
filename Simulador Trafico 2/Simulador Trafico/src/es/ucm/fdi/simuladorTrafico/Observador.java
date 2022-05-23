package es.ucm.fdi.simuladorTrafico;

public interface Observador<T> {
	public void addObservador(T o);
	public void removeObservador(T o);
}
