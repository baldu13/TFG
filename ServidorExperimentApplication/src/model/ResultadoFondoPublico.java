package model;

public class ResultadoFondoPublico {

	//Atributos
	private Usuario usuario;
	private float cantidadPrivado;
	private float cantidadPublico;
	
	public ResultadoFondoPublico(){}

	public ResultadoFondoPublico(Usuario usuario, float cantidadPrivado, float cantidadPublico) {
		this.usuario = usuario;
		this.cantidadPrivado = cantidadPrivado;
		this.cantidadPublico = cantidadPublico;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public float getCantidadPrivado() {
		return cantidadPrivado;
	}

	public void setCantidadPrivado(float cantidadPrivado) {
		this.cantidadPrivado = cantidadPrivado;
	}

	public float getCantidadPublico() {
		return cantidadPublico;
	}

	public void setCantidadPublico(float cantidadPublico) {
		this.cantidadPublico = cantidadPublico;
	}
}
