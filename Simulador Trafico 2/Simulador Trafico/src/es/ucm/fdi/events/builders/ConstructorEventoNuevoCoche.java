package es.ucm.fdi.events.builders;

import es.ucm.fdi.events.Evento;
import es.ucm.fdi.events.EventoNuevoCoche;
import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoCoche extends ConstructorEventos{
	
	public ConstructorEventoNuevoCoche() {

		this.etiqueta = "new_vehicle";
		this.claves = new String[] { "time", "id", "itinerary", "max_speed","resistance",
									"fault_probability","max_fault_duration","seed","type"};
		this.valoresPorDefecto = new String[] { "", "", "", "", "", "", "", "", "car" };
	}
	@Override
	public Evento parser(IniSection section) {
		if (section.getTag().equals(this.etiqueta) && section.getValue("type").equals("car")) {
			
			return new EventoNuevoCoche
					(ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
					ConstructorEventos.identificadorValido(section, "id"),
					section.getValue("itinerary").split(","),
					ConstructorEventos.parseaIntNoNegativo(section, "max_speed", 1),
					 ConstructorEventos.parseaIntNoNegativo(section, "resistance", 1),
					ConstructorEventos.parseaDouble(section, "fault_probability", 0),
					ConstructorEventos.parseaInt(section, "max_fault_duration"),
					ConstructorEventos.parseaLong(section, "seed", System.currentTimeMillis()),
					ConstructorEventos.identificadorValido(section, "type"));
		}
		else
			return null;
	}
	
	@Override
	public String toString() { 
		return "Constructor Nuevo Coche";
	}
}
