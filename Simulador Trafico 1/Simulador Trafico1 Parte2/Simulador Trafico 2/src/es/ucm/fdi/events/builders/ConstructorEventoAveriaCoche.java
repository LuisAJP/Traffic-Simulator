package es.ucm.fdi.events.builders;

import es.ucm.fdi.events.Evento;
import es.ucm.fdi.events.EventoAveriaCoche;
import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoAveriaCoche extends ConstructorEventos {

	public ConstructorEventoAveriaCoche() {
		this.etiqueta = "make_vehicle_faulty";
		this.claves = new String[] { "time", "vehicles", "duration" };
		this.valoresPorDefecto = new String[] { "", "", };
	}
		
	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) ||section.getValue("type") != null) 
			return null;
		else
			return new EventoAveriaCoche(ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
					ConstructorEventos.identificadorValido01(section, "vehicles"), 
					ConstructorEventos.parseaIntNoNegativo01(section, "duration"));
	}
		
	@Override
	public String toString() { 
		return "Constructor Vehicle_Faulty";
	}

}
