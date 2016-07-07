package model;

public class ResultadoBeautyContest {

	//Atributos
	private Usuario usuario;
	private float numElegido;
	
	public ResultadoBeautyContest(){}
	
	public ResultadoBeautyContest(Usuario usuario, int numElegido){
		this.usuario = usuario;
		this.numElegido = numElegido;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public float getNumElegido() {
		return numElegido;
	}

	public void setNumElegido(float numElegido) {
		this.numElegido = numElegido;
	}
	
}
