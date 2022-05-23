package es.ucm.fdi.events;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.mapaCarreteras.MapaCarreteras;

public abstract class Evento  {
	protected Integer tiempo;
	
	public Evento(int tiempo) { 
		this.tiempo=tiempo;
	}
	
	public int getTiempo() {
		return this.tiempo;
	}
	
	public abstract void ejecuta(MapaCarreteras mapa) throws ErrorDeSimulacion;
}
