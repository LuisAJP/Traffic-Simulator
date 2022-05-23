package es.ucm.fdi.eventos;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.mapaCarreteras.MapaCarreteras;
import es.ucm.fdi.objetoSimulacion.Cruce;


public class ParserCarreteras {

	public static List<Cruce> parseaListaCruces(String[] itinerario, MapaCarreteras mapa) {
		
		List<Cruce> listaItinerario = new ArrayList<Cruce>();
		String cadena="";
		for(int i=0; i<itinerario.length ;i++){
			cadena=itinerario[i];
			for(Cruce c : mapa.getListaCruces() ) {
				if(cadena.equals(c.getId())){
					listaItinerario.add(c);
				}
			}	
		}
		return listaItinerario;
	}
	
}
