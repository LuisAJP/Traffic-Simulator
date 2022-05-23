package es.ucm.fdi.events.builders;

import es.ucm.fdi.events.Evento;
import es.ucm.fdi.events.EventoNuevoCamino;
import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoCamino extends ConstructorEventos {
	
	public ConstructorEventoNuevoCamino() {
		this.etiqueta = "new_road";
		this.claves = new String[] { "time", "id", "src", "dest", "max_speed", "length", "type" };
		this.valoresPorDefecto = new String[] { "", "","", "", "", "", "dirt" };
	}
		
	@Override
	public Evento parser(IniSection section) {
		if (section.getTag().equals(this.etiqueta) && section.getValue("type").equals("dirt")) {
			
			return new EventoNuevoCamino(ConstructorEventos.parseaIntNoNegativo01(section, "time"),
					ConstructorEventos.identificadorValido(section, "id"), ConstructorEventos.identificadorValido(section, "src"),
					ConstructorEventos.identificadorValido(section, "dest"), ConstructorEventos.parseaIntNoNegativo01(section, "max_speed"), 
					ConstructorEventos.parseaIntNoNegativo01(section, "length"), 
					ConstructorEventos.identificadorValido(section, "type"));
		}
		else 
			return null;
		
	}
	@Override
	public String toString() { 
		return "Constructor Nuevo Camino";
	}
}
