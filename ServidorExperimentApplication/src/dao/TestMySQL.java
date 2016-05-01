package dao;

import business.*;
import model.*;

public class TestMySQL {
	   
	public static void main(String[] args) {
		
		ExperimentosBusiness eb = new ExperimentosBusiness();
		ResultadosBusiness rb = new ResultadosBusiness();
		/*
		Experimento e = new Experimento();
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
		u.setUsuario("ex1u3");
		u.setClave("f7FRKO");
		System.out.println("Tipo experiento: "+eb.getExperimentoUsuario(u).getTipo().getTipo());
		
		/*
		Resultado r = new Resultado();
		TipoResultado tr = new TipoResultado();
		tr.setId(1);
		r.setTipo(tr);
		Participacion p = new Participacion();
		p.setId(47);
		r.setParticipante(p);
		r.setValorNumerico(1.1f);
		rb.registraResultado(r);
		*/
		
		Informe i = rb.resultadosExperimento(1);
		
		System.out.println("Informe: "+i.getExperimento().getMaxRondas());
		for(Resultado re: i.getResultados())
			System.out.println("Resultado: "+re.getValorNumerico());
	}

}
