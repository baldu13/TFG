package model;

import java.util.LinkedList;
import java.util.List;

public class CrearExperimentoResponseDTO {

	private int idExperimento;
	private List<Usuario> usuarios;
	
	public CrearExperimentoResponseDTO() {
		usuarios = new LinkedList<Usuario>();
	}

	public int getIdExperimento() {
		return idExperimento;
	}
	public void setIdExperimento(int idExperimento) {
		this.idExperimento = idExperimento;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	
}
