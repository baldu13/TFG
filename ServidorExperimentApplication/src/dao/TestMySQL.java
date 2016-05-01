package dao;

import business.ExperimentosBusiness;
import model.CrearExperimentoResponseDTO;
import model.Experimento;
import model.TipoExperimento;
import model.Usuario;

public class TestMySQL {
	   
	public static void main(String[] args) {
		
		ExperimentosBusiness eb = new ExperimentosBusiness();
		/*
		 * Experimento e = new Experimento();
		e.setNombre("Primer experimento");
		e.setNumGrupos(2);
		e.setMaxRondas(3);
		e.setGrupal(true);
		TipoExperimento te = new TipoExperimento();
		te.setId(1);
		e.setTipo(te);
		CrearExperimentoResponseDTO response = eb.creaExperimento(e, 4);
		for(Usuario u: response.getUsuarios()){
			System.out.println("Usuario: "+u.getUsuario()+
					"\nClave: "+u.getClave()+"\n");
		}
		*/
		Usuario u = new Usuario();
		u.setUsuario("ex9u0");
		u.setClave("a5I0y5");
		System.out.println(eb.getExperimentoUsuario(u).getTipo().getTipo());
	}

}
