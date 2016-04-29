package model;

public class Ronda {

	//Atributos
	private Experimento experimento;
	private int numRonda;
	
	//Metodos
	public Ronda() {}

	public Experimento getExperimento() {
		return experimento;
	}
	public void setExperimento(Experimento experimento) {
		this.experimento = experimento;
	}

	public int getNumRonda() {
		return numRonda;
	}
	public void setNumRonda(int numRonda) {
		this.numRonda = numRonda;
	}
}
