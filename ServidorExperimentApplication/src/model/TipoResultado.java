package model;

public class TipoResultado {

	//Atributos
	private int id;
	private String etiqueta;
	private String tipoDato;
	private TipoExperimento tipoExperimento;
	
	//Metodos
	public TipoResultado() {}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getEtiqueta() {
		return etiqueta;
	}
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public String getTipoDato() {
		return tipoDato;
	}
	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}

	public TipoExperimento getTipoExperimento() {
		return tipoExperimento;
	}
	public void setTipoExperimento(TipoExperimento tipoExperimento) {
		this.tipoExperimento = tipoExperimento;
	}
}
