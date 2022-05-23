package es.ucm.fdi.events.builders;

import java.util.LinkedList;

import es.ucm.fdi.events.Evento;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.mapaCarreteras.MapaCarreteras;
import es.ucm.fdi.objetoSimulacion.cruces.CruceGenerico;

public abstract class ConstructorEventos {
	
	//cada clase dará los valores correspondientes a estos atributos
	// en la constructora
	protected String etiqueta; // etiqueta de la entrada (“new_road”, etc..)
	protected String[] claves; // campos de la entrada (“time”, “vehicles”, etc.)
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
	
	// identificadores válidos
	// sólo pueden contener letras, números y subrayados
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
	
	protected static double parseaDouble(IniSection seccion,String clave, int valorPorDefecto) {
		String v = seccion.getValue(clave);
		return 
			(v != null) ? Double.parseDouble(seccion.getValue(clave)) :valorPorDefecto;
	}
	
	
	protected static long parseaLong(IniSection seccion,String clave, long valorPorDefecto) {
		String v = seccion.getValue(clave);
		return 
			(v != null) ? Long.parseLong(seccion.getValue(clave)) :valorPorDefecto;
	}
		
	
	protected static int parseaIntNoNegativo(IniSection seccion, String clave, int valorPorDefecto) {
		int i = ConstructorEventos.parseaInt(seccion, clave, valorPorDefecto);
		if (i < 0)
			throw new IllegalArgumentException("El valor " + i + " para " + clave + " no es un ID valido");
		else
			return i;
	}

	protected static int parseaIntNoNegativo01(IniSection seccion,String clave) {
		int i = ConstructorEventos.parseaInt(seccion, clave);
		if (i < 0)
			throw new IllegalArgumentException("El valor " + i + " para " + clave +" no es un ID valido");
		else return i;
	}
	
	public abstract Evento parser(IniSection section);

	public static LinkedList<CruceGenerico<?>> parseaListaCruces(String[] itinerario, MapaCarreteras mapa) {
		LinkedList<CruceGenerico<?>> listaItinerario = new LinkedList<CruceGenerico<?>>();
		String cadena="";
		for(int i=0; i<itinerario.length ;i++){
			cadena=itinerario[i];
			for(CruceGenerico<?> c : mapa.getCruces() ) {
				if(cadena.equals(c.getId())){
					listaItinerario.add(c);
				}
			}	
		}
		return listaItinerario;
	}
}
