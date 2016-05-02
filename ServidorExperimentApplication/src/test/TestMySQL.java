package test;

import presentation.*;

import java.util.List;

import model.*;

public class TestMySQL {
	   
	public static void main(String[] args) {
		
		IAdministracion admin = new AdminFacade();
		IUsuario user = new UserFacade();
		
		/*
		//Test crear experimento
		Experimento e = new Experimento();
		e.setNombre("Primer experimento");
		e.setNumGrupos(2);
		e.setMaxRondas(3);
		e.setGrupal(true);
		TipoExperimento te = new TipoExperimento();
		te.setId(1);
		e.setTipo(te);
		CrearExperimentoResponseDTO response = admin.crearExperimento("Primer experimento", te, 6, 3, 4);
		for(Usuario u: response.getUsuarios()){
			System.out.println("Usuario: "+u.getUsuario()+
					"\nClave: "+u.getClave()+"\n");
		}
		*/
		
		
		
		//Test lista experimentos
		List<Experimento> lista = admin.getExperimentos();
		for(Experimento exp: lista){
			System.out.println("ID: "+exp.getId());
			System.out.println("Nombre: "+exp.getNombre());
			System.out.println("Tipo: "+exp.getTipo().getTipo());
		}
		
		
		/*
		//Test logearExperimento
		System.out.println("Tipo experiento: "+user.logeaExperimento("ex1u5", "ebVeB7").getTipo().getTipo());
		//Verificar en BBDD que el valor de participando ha cambiado a 1
		//y que el tipo de experimento es correcto
		*/
		
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
		
		Informe i = rb.resultadosExperimento(1);
		
		System.out.println("Informe: "+i.getExperimento().getMaxRondas());
		for(Resultado re: i.getResultados())
			System.out.println("Resultado: "+re.getValorNumerico());
		*/
	}

}
