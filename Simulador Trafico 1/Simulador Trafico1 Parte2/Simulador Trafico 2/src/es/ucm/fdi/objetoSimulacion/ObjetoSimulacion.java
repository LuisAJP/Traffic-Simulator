package es.ucm.fdi.objetoSimulacion;

import es.ucm.fdi.exceptions.ErrorDeSimulacion;
import es.ucm.fdi.ini.IniSection;
import es.ucm.fdi.exceptions.RelacionCruceNoEncotrada;

public abstract class ObjetoSimulacion {
	
	protected String id;
	
	public ObjetoSimulacion(String id) {
		this.id=id;
	}
	public String getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return ("");
	}

	
	public abstract void avanza() throws RelacionCruceNoEncotrada, ErrorDeSimulacion;
	
	public String generaInforme(int tiempo) {
		IniSection is = new IniSection(this.getNombreSeccion());
		is.setValue("id", this.id);
		is.setValue("time", tiempo);
		this.completaDetallesSeccion(is);
		return is.toString();
	}
	
	// los métodos getNombreSeccion y completaDetallesSeccion
	// tendrán que implementarlos cada subclase de ObjetoSimulacion
	protected abstract void completaDetallesSeccion(IniSection is);
		// TODO Auto-generated method stub
		
	
	
	// los métodos getNombreSeccion y completaDetallesSeccion
	// tendrán que implementarlos cada subclase de ObjetoSimulacion
	protected abstract String getNombreSeccion();
		// TODO Auto-generated method stub
		
	

}
