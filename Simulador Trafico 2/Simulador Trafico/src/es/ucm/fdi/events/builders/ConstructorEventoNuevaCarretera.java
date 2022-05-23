package es.ucm.fdi.events.builders;

import es.ucm.fdi.events.Evento;
import es.ucm.fdi.events.EventoNuevaCarretera;
import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevaCarretera extends ConstructorEventos {

	public ConstructorEventoNuevaCarretera() {
		this.etiqueta = "new_road";
		this.claves = new String[] { "time", "id", "src", "dest", "max_speed", "length" };
		this.valoresPorDefecto = new String[] { "", "", };
	}
		
	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) ||section.getValue("type") != null) 
			return null;
		else
			return new EventoNuevaCarretera(ConstructorEventos.parseaIntNoNegativo01(section, "time"),
					ConstructorEventos.identificadorValido(section, "id"), ConstructorEventos.identificadorValido(section, "src"),
					ConstructorEventos.identificadorValido(section, "dest"), ConstructorEventos.parseaIntNoNegativo01(section, "max_speed"), 
					ConstructorEventos.parseaIntNoNegativo01(section, "length"));
	}
		
	@Override
	public String toString() { 
		return "Constructor New Road";
	}

}
