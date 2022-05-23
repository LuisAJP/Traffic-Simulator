package es.ucm.fdi.events.builders;

import es.ucm.fdi.events.Evento;
import es.ucm.fdi.events.EventoNuevaAutopista;
import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevaAutopista extends ConstructorEventos {

	public ConstructorEventoNuevaAutopista() {
		this.etiqueta = "new_road";
		this.claves = new String[] { "time", "id", "src", "dest", "max_speed", "length", "lanes", "type" };
		this.valoresPorDefecto = new String[] { "", "", "","","","","","lanes" };
	}

	@Override
	public Evento parser(IniSection section) {
		
		
		if (section.getTag().equals(this.etiqueta) && section.getValue("type").equals("lanes")) {

			return new EventoNuevaAutopista(ConstructorEventos.parseaIntNoNegativo01(section, "time"),
					ConstructorEventos.identificadorValido(section, "id"),
					ConstructorEventos.identificadorValido(section, "src"),
					ConstructorEventos.identificadorValido(section, "dest"),
					ConstructorEventos.parseaIntNoNegativo01(section, "max_speed"),
					ConstructorEventos.parseaIntNoNegativo01(section, "length"),
					ConstructorEventos.parseaIntNoNegativo01(section, "lanes"),
					ConstructorEventos.identificadorValido(section, "type"));
		} else
			return null;

	}

	@Override
	public String toString() {
		return "Constructor Nueva Autopista";
	}
}
