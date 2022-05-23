package es.ucm.fdi.eventos;

import es.ucm.fdi.ini.IniSection;

public class ConstructorEventoNuevoVehiculo extends ConstructorEventos {
	public ConstructorEventoNuevoVehiculo() {
		
		this.etiqueta = "new_vehicle";
		this.claves = new String[] { "time", "id", "itinerary" , "max_speed" };
		this.valoresPorDefecto = new String[] { "", "", };
	}
		
	@Override
	public Evento parser(IniSection section) {
		if (!section.getTag().equals(this.etiqueta) ||section.getValue("type") != null) 
			return null;
		else
			return new EventoNuevoVehiculo(ConstructorEventos.parseaIntNoNegativo01(section, "time"),
					ConstructorEventos.identificadorValido(section, "id"),
					ConstructorEventos.parseaIntNoNegativo(section, "max_speed", 0),ConstructorEventos.identificadorValido01(section,"itinerary"));
	}
		
	@Override
	public String toString() { 
		return "Constructor New Vechicle";
	}
}
