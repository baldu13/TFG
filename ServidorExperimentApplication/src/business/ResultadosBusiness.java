package business;

import model.*;

import dao.ExperimentApplicationDAO;

public class ResultadosBusiness {
	
	private ExperimentApplicationDAO dao;
	
	public ResultadosBusiness() {
		dao = new ExperimentApplicationDAO();
	}
	
	public void registraResultado(Resultado r){
		dao.registraResultado(r);
	}
	
	public Informe resultadosExperimento(int idExperimento){
		return dao.getResultadosExperimento(idExperimento);
	}
}
