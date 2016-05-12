package model;

import java.util.List;

/**
 * Clase que contiene la información necesaria para crear un informe
 * de un experimento
 * @author Baldu
 *
 */
public class Informe {

	private Experimento experimento;
	private List<Resultado> resultados;
	
	public Informe() {}

	public Experimento getExperimento() {
		return experimento;
	}
	public void setExperimento(Experimento experimento) {
		this.experimento = experimento;
	}

	public List<Resultado> getResultados() {
		return resultados;
	}
	public void setResultados(List<Resultado> resultados) {
		this.resultados = resultados;
	}
}
