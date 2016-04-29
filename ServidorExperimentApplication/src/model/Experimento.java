package model;

import java.util.Date;

public class Experimento {

	//Atributos
	private int id;
	private String nombre;
	private Date fechaInicio;
	private Date fechaFin;
	private int maxRondas;
	private boolean grupal;
	private boolean gruposVariables;
	private int tamanoGrupos;
	private TipoExperimento tipo;
	
	//Metodos
	public Experimento() {}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public int getMaxRondas() {
		return maxRondas;
	}
	public void setMaxRondas(int maxRondas) {
		this.maxRondas = maxRondas;
	}

	public boolean isGrupal() {
		return grupal;
	}
	public void setGrupal(boolean grupal) {
		this.grupal = grupal;
	}

	public boolean isGruposVariables() {
		return gruposVariables;
	}
	public void setGruposVariables(boolean gruposVariables) {
		this.gruposVariables = gruposVariables;
	}

	public int getTamanoGrupos() {
		return tamanoGrupos;
	}
	public void setTamanoGrupos(int tamanoGrupos) {
		this.tamanoGrupos = tamanoGrupos;
	}

	public TipoExperimento getTipo() {
		return tipo;
	}
	public void setTipo(TipoExperimento tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
}
