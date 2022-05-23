package es.ucm.fdi.events;

import es.ucm.fdi.events.builders.ConstructorEventoAveriaCoche;
import es.ucm.fdi.events.builders.ConstructorEventoNuevaAutopista;
import es.ucm.fdi.events.builders.ConstructorEventoNuevaBicicleta;
import es.ucm.fdi.events.builders.ConstructorEventoNuevaCarretera;
import es.ucm.fdi.events.builders.ConstructorEventoNuevoCamino;
import es.ucm.fdi.events.builders.ConstructorEventoNuevoCoche;
import es.ucm.fdi.events.builders.ConstructorEventoNuevoCruce;
import es.ucm.fdi.events.builders.ConstructorEventoNuevoVehiculo;
import es.ucm.fdi.events.builders.ConstructorEventos;
import es.ucm.fdi.ini.IniSection;

public class ParserEventos {

	private static ConstructorEventos[] eventos = { new ConstructorEventoNuevoCruce(),
			new ConstructorEventoNuevaCarretera(), new ConstructorEventoNuevoVehiculo(),
			new ConstructorEventoNuevoCoche(),new ConstructorEventoNuevaBicicleta(),
			new ConstructorEventoAveriaCoche(), new ConstructorEventoNuevoCamino(),
			new ConstructorEventoNuevaAutopista() };

	// bucle de prueba y error
	public static Evento parseaEvento(IniSection sec) {
		int i = 0;
		boolean seguir = true;
		Evento e = null;
		while (i < ParserEventos.eventos.length && seguir) {
			// ConstructorEventos contiene el método parse(sec)
			e = ParserEventos.eventos[i].parser(sec);
			if (e != null)
				seguir = false;
			else
				i++;
		}
		return e;
	}

	public static Object getConstrutoresEventos() {
		// TODO Auto-generated method stub
		
		return eventos;
	}
}
