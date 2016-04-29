package model;

public class Participacion {

	//Atributos
	private int id;
	private Usuario usuario;
	private Ronda ronda;
	private int numGrupo;
	
	//Metodos
	public Participacion() {}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Ronda getRonda() {
		return ronda;
	}
	public void setRonda(Ronda ronda) {
		this.ronda = ronda;
	}

	public int getNumGrupo() {
		return numGrupo;
	}
	public void setNumGrupo(int numGrupo) {
		this.numGrupo = numGrupo;
	}
}
