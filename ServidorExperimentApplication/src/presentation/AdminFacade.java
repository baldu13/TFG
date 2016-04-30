package presentation;

import model.*;

import java.util.Calendar;

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
		e.setTamanoGrupos(tamanoGrupos);
		e.setTipo(tipo);
		e.setFechaInicio(Calendar.getInstance().getTime());
		return experimentos.creaExperimento(e,numUsuarios);
	}

	@Override
	public Informe informeExperimento(int idExperimento) {
		return resultados.resultadosExperimento(idExperimento);
	}

}
