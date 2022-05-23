package es.ucm.fdi.excepciones;

public class ErrorDeSimulacion extends Exception {
	private static final long serialVersionUID = 1L;

	public ErrorDeSimulacion() {

		super();
	}

	public ErrorDeSimulacion(String argumento) {

		super(argumento);
	}

	public ErrorDeSimulacion(Throwable argumento) {

		super(argumento);
	}

}
