package presentation;

import model.*;

import java.util.List;

import business.*;

public class AdminFacade implements IAdministracion{
	
	private ExperimentosBusiness experimentos;
	private ResultadosBusiness resultados;

	public AdminFacade(){
		experimentos = new ExperimentosBusiness();
		resultados = new ResultadosBusiness();
	}
	
	@Override
	public CrearExperimentoResponseDTO crearExperimento(String nombre, TipoExperimento tipo, int numUsuarios, int tamanoGrupos, int numRondas) {
		Experimento e = new Experimento();
		e.setMaxRondas(numRondas);
		e.setNombre(nombre);
		e.setNumGrupos(tamanoGrupos);
		e.setTipo(tipo);
		if(tamanoGrupos!=0){
			e.setGrupal(true);
		}
		return experimentos.creaExperimento(e,numUsuarios);
	}

	@Override
	public Informe informeExperimento(int idExperimento) {
		return resultados.resultadosExperimento(idExperimento);
	}

	@Override 
	public List<Experimento> getExperimentos(){
		return experimentos.getExperimentos();
	}
	
	@Override
	public List<Usuario> getUsuariosExperimento(int idExperimento){
		return experimentos.getUsuariosExperimento(idExperimento);
	}
}
