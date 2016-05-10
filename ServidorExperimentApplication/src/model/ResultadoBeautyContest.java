package model;

public class ResultadoBeautyContest {

	//Atributos
	private Usuario usuario;
	private int numElegido;
	
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

	public int getNumElegido() {
		return numElegido;
	}

	public void setNumElegido(int numElegido) {
		this.numElegido = numElegido;
	}
	
}
