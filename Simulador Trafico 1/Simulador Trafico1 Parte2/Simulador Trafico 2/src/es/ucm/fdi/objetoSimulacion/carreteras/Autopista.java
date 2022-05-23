package es.ucm.fdi.objetoSimulacion.carreteras;

import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.objetoSimulacion.cruces.CruceGenerico;

public class Autopista extends Carretera {
	protected String type;
	protected int  carriles;

	
	public Autopista(String id, int length, int maxSpeed, CruceGenerico<?> src, CruceGenerico<?> dest,int carriles,String type) {
		super(id, length, maxSpeed, src, dest, "lanes");
		this.carriles=carriles;
		this.type=type;
	}
	
	@Override
	protected int calculaVelocidadBase() {
		int m1=velocidadMaxima;
		int m2=velocidadMaxima;
		
		//Si en la carretera hay vehiculos
		if(vehiculos.size()>1) {
			m2=m2*carriles/vehiculos.size()+1;
		}
		//Si en la carretera no hay vehiculos
		else {
			m2=m2*carriles/1 + 1;
		}
		
		if(m1<m2) 
			return m1;
		
		else
			return m2;
		
	}
	
	@Override
	protected int calculaFactorReduccion(int obstaculos) {
		int factorReduccion;
		//si el primer vehiculo esta averiado
		if(obstaculos<this.carriles)
			factorReduccion=1;
		else
			factorReduccion=2;
		return factorReduccion;
		
	}
	
	@Override
	protected void completaDetallesSeccion(IniSection is) {
		//is.setValue("type", this.type);
		String cadena=vehiculosDetallesSeccion();
		is.setValue("state", cadena);
		is.setValue("type", this.type);
	}
	
}
