package presentation;

import java.util.List;

import model.*;

public interface IAdministracion {

	CrearExperimentoResponseDTO crearExperimento(String nombre, TipoExperimento tipo, int numUsuarios, int tamanoGrupos, int numRondas, float fPublico, float fPrivado);
	
	Informe informeExperimento(int idExperimento);
	
	List<Experimento> getExperimentos();
	
	List<Usuario> getUsuariosExperimento(int idExperimento);
}
