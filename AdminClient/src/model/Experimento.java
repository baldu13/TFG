package model;

import java.util.Date;

public class Experimento {

	//Atributos
	private int id;
	private String nombre;
	private Date fecha;
	private int maxRondas;
	private boolean grupal;
	private int numGrupos;
	private TipoExperimento tipo;
	
	//Metodos
	public Experimento() {}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
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

	public int getNumGrupos() {
		return numGrupos;
	}
	public void setNumGrupos(int numGrupos) {
		this.numGrupos = numGrupos;
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
	
	@Override
	public String toString(){
		//Para mostrarlo en la lista
		return nombre;
	}
}
