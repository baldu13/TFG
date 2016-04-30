package presentation;

import model.*;

public interface IAdministracion {

	CrearExperimentoResponseDTO crearExperimento(String nombre, TipoExperimento tipo, int numUsuarios, int tamanoGrupos, int numRondas);
	
	Informe informeExperimento(int idExperimento);
}
