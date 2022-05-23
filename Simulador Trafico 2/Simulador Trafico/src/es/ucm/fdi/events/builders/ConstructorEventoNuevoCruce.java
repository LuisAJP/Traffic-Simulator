package es.ucm.fdi.events.builders;

import es.ucm.fdi.events.Evento;
import es.ucm.fdi.events.EventoNuevoCruce;
import es.ucm.fdi.events.EventoNuevoCruceCircular;
import es.ucm.fdi.events.EventoNuevoCruceCongestionado;
import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoCruce extends ConstructorEventos {

	public ConstructorEventoNuevoCruce() {
		this.etiqueta = "new_junction";
		this.claves = new String[] { "time", "id" };
		this.valoresPorDefecto = new String[] { "", "", };
	}

	@Override
	public Evento parser(IniSection section) {
		if (section.getTag().equals(this.etiqueta)) {
			if (section.getValue("type") != null) {
				if (section.getValue("type").equalsIgnoreCase("rr"))
					return new EventoNuevoCruceCircular(ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
							ConstructorEventos.identificadorValido(section, "id"),
							ConstructorEventos.parseaIntNoNegativo(section, "max_time_slice", 0),
							ConstructorEventos.parseaIntNoNegativo(section, "min_time_slice", 0));
				else if (section.getValue("type").equalsIgnoreCase("mc"))
					return new EventoNuevoCruceCongestionado(ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
							ConstructorEventos.identificadorValido(section, "id"));
			}

			else return new EventoNuevoCruce(ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
						ConstructorEventos.identificadorValido(section, "id"));
		} 
		return null;
	}

	@Override
	public String toString() {
		return "Constructor New Junction";
	}

}
