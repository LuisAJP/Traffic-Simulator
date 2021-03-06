package es.ucm.fdi.eventos;

import es.ucm.fdi.ini.IniSection;

public abstract class ConstructorEventos {
	
	//cada clase dar? los valores correspondientes a estos atributos
	// en la constructora
	protected String etiqueta; // etiqueta de la entrada (?new_road?, etc..)
	protected String[] claves; // campos de la entrada (?time?, ?vehicles?, etc.)
	protected String[] valoresPorDefecto; 
	
	ConstructorEventos() {
		this.etiqueta = null;
		this.claves = null;
	}
	
	protected static String identificadorValido(IniSection seccion, String clave) {
		String s = seccion.getValue(clave);
		if (!esIdentificadorValido(s))
			throw new IllegalArgumentException("El valor " + s + " para " + clave + " no es un ID valido");
		else 
			return s;
	}
	
	protected static String[] identificadorValido01(IniSection seccion, String clave) {
		String s = seccion.getValue(clave);
		String [] cadena=s.split(",");
//		if (!esIdentificadorValido(s))
//			throw new IllegalArgumentException("El valor " + s + " para " + clave + " no es un ID valido");
//		else 
			return cadena;
	}
	
	// identificadores v?lidos
	// s?lo pueden contener letras, n?meros y subrayados
	private static boolean esIdentificadorValido(String id) {
		return id != null && id.matches("[a-z0-9_]+");
	}
		
	protected static int parseaInt(IniSection seccion, String clave) {
		String v = seccion.getValue(clave);
		if (v == null)
			throw new IllegalArgumentException("Valor inexistente para la clave: " + clave);
		else 
			return Integer.parseInt(seccion.getValue(clave));
	}
	
			
	protected static int parseaInt(IniSection seccion,String clave, int valorPorDefecto) {
		String v = seccion.getValue(clave);
		return 
			(v != null) ? Integer.parseInt(seccion.getValue(clave)) :valorPorDefecto;
	}
		
	
	protected static int parseaIntNoNegativo(IniSection seccion,String clave, int valorPorDefecto) {
		int i = ConstructorEventos.parseaInt(seccion, clave, valorPorDefecto);
		if (i < 0)
			throw new IllegalArgumentException("El valor " + i + " para " + clave +" no es un ID valido");
		else return i;
	}
	
	protected static int parseaIntNoNegativo01(IniSection seccion,String clave) {
		int i = ConstructorEventos.parseaInt(seccion, clave);
		if (i < 0)
			throw new IllegalArgumentException("El valor " + i + " para " + clave +" no es un ID valido");
		else return i;
	}
	
	public abstract Evento parser(IniSection section);
}
