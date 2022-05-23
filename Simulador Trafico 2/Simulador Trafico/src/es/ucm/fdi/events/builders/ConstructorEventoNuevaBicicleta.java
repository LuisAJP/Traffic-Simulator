package es.ucm.fdi.events.builders;

import es.ucm.fdi.events.Evento;
import es.ucm.fdi.events.EventoNuevaBicicleta;
import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevaBicicleta  extends ConstructorEventos {
	public ConstructorEventoNuevaBicicleta() {

		this.etiqueta = "new_vehicle";
		this.claves = new String[] { "time", "id", "itinerary", "max_speed","type"};
		this.valoresPorDefecto = new String[] { "", "","","","bike" };
	}


	@Override
	public Evento parser(IniSection section) {
		// TODO Auto-generated method stub
		if (section.getTag().equals(this.etiqueta) && section.getValue("type").equals("bike")) {
			return new EventoNuevaBicicleta( 
					ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
					ConstructorEventos.identificadorValido(section, "id"),
					ConstructorEventos.parseaIntNoNegativo(section, "max_speed", 1),
					section.getValue("itinerary").split(","),ConstructorEventos.identificadorValido(section, "type")
			);
			
		}
		else
		 return null;
	}
	@Override
	public String toString() { 
		return "Constructor Nueva Bicicleta";
	}
}
