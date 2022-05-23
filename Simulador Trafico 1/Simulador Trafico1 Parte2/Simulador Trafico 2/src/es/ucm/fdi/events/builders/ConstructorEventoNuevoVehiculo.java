package es.ucm.fdi.events.builders;

import es.ucm.fdi.events.Evento;
import es.ucm.fdi.events.EventoNuevoVehiculo;
import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoVehiculo extends ConstructorEventos {
	public ConstructorEventoNuevoVehiculo() {

		this.etiqueta = "new_vehicle";
		this.claves = new String[] { "time", "id", "itinerary", "max_speed" };
		this.valoresPorDefecto = new String[] { "", "", };
	}

	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta)||section.getValue("type") != null) {
			return null;
		} 
		else
			return new EventoNuevoVehiculo(
					ConstructorEventos.parseaIntNoNegativo(section, "time", 0),
					ConstructorEventos.identificadorValido(section, "id"),
					ConstructorEventos.parseaIntNoNegativo(section, "max_speed", 1),
					section.getValue("itinerary").split(",")

			);
	}

	@Override
	public String toString() {
		return "Constructor New Vechicle";
	}
}
