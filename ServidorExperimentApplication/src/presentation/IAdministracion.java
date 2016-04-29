package presentation;

import model.*;

public interface IAdministracion {

	int crearExperimento(TipoExperimento tipo, Experimento e);
	
	String generarInforme(int idExperimento);
}
