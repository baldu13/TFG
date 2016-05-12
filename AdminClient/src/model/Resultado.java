package model;

public class Resultado {

	//Atributos
	private int id;
	private TipoResultado tipo;
	private Participacion participante;
	private String valorTexto;
	private float valorNumerico;
	
	//Metodos
	public Resultado() {}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public TipoResultado getTipo() {
		return tipo;
	}
	public void setTipo(TipoResultado tipo) {
		this.tipo = tipo;
	}

	public Participacion getParticipante() {
		return participante;
	}
	public void setParticipante(Participacion participante) {
		this.participante = participante;
	}

	public String getValorTexto() {
		return valorTexto;
	}
	public void setValorTexto(String valorTexto) {
		this.valorTexto = valorTexto;
	}

	public float getValorNumerico() {
		return valorNumerico;
	}
	public void setValorNumerico(float valorNumerico) {
		this.valorNumerico = valorNumerico;
	}
}
