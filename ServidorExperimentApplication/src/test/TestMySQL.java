package test;

import presentation.*;

import java.util.List;

import dao.ExperimentApplicationDAO;
import model.*;

public class TestMySQL {
	
	private TestMySQL(){}
	
	public static void main(String[] args) {	
		IAdministracion admin = new AdminFacade();
		IUsuario user = new UserFacade();
		ExperimentApplicationDAO dao = new ExperimentApplicationDAO();
		
		/*
		//Test crear experimento
		TipoExperimento te = new TipoExperimento();
		te.setId(1);
		CrearExperimentoResponseDTO response = admin.crearExperimento("Primero experimento", te, 6, 3, 4);
		for(Usuario u: response.getUsuarios()){
			System.out.println("Usuario: "+u.getUsuario()+
					"\nClave: "+u.getClave()+"\n");
		}
		*/
		
		/*
		//Test lista experimentos
		List<Experimento> lista = admin.getExperimentos();
		for(Experimento exp: lista){
			System.out.println("ID: "+exp.getId());
			System.out.println("Nombre: "+exp.getNombre());
			System.out.println("Tipo: "+exp.getTipo().getTipo());
		}
		*/
		
		/*
		//Test enviar resultado
		Usuario u = new Usuario();
		u.setUsuario("ex10u0");
		u.setClave("VBTSm4");
		ResultadoBeautyContest r = new ResultadoBeautyContest(u,4);
		user.enviaResultadoBeautyContest(r);
		*/
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
		*/
		/*
		Informe i = admin.informeExperimento(10);
		
		System.out.println("Informe: "+i.getExperimento().getMaxRondas());
		for(Resultado re: i.getResultados())
			System.out.println("Resultado: "+re.getValorNumerico());
		*/
		user.logeaExperimento("ex10u0", "VBTSm4");
		System.out.println(user.isRoundFinish(10, 1, 1));
	}

}
