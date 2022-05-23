package es.ucm.fdi.objetoSimulacion.carreteras;

import es.ucm.fdi.objetoSimulacion.cruces.CruceGenerico;
import es.ucm.fdi.ini.IniSection;

public class Camino extends Carretera{
	protected String type;
	
	public Camino(String id, int length, int maxSpeed, CruceGenerico<?> src, CruceGenerico<?> dest,String type) {
		super(id, length, maxSpeed, src, dest, "dirt");
		// TODO Auto-generated constructor stub
		this.type=type;
	}
	
	@Override
	protected int calculaVelocidadBase() { 
		return this.velocidadMaxima; 
	}
	
	@Override
	protected int calculaFactorReduccion(int obstaculos) {
		//si el primer vehiculo esta averiado
		return obstaculos + 1;
	}
	
	@Override
	protected void completaDetallesSeccion(IniSection is) {
		//is.setValue("type", this.type);
		String cadena=vehiculosDetallesSeccion();
		is.setValue("state", cadena);
		is.setValue("type", this.type);
	}

}
