package es.ucm.fdi.objetoSimulacion.cruces;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.objetoSimulacion.carreteras.Carretera;
import es.ucm.fdi.objetoSimulacion.cruces.carreterasEntrantes.CarreteraEntranteConIntervalo;

public class CruceCongestionado extends CruceGenerico<CarreteraEntranteConIntervalo> {

	public CruceCongestionado(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected CarreteraEntranteConIntervalo creaCarreteraEntrante(Carretera carretera) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void actualizaSemaforos() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void completaDetallesSeccion(IniSection is) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String getNombreSeccion() {
		// TODO Auto-generated method stub
		return null;
	}

}
