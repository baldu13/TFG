package presentation;

import java.util.List;

import model.*;

public interface IAdministracion {

	CrearExperimentoResponseDTO crearExperimento(String nombre, TipoExperimento tipo, int numUsuarios, int tamanoGrupos, int numRondas);
	
	Informe informeExperimento(int idExperimento);
	
	List<Experimento> getExperimentos();
}
