package test;

import presentation.*;

import java.util.List;

import model.*;

public class TestUsuario {

	private IUsuario user;
	
	public TestUsuario() {
		user = new UserFacade();
		testLogeaExperimento();
		//testEnviarResultado();
		testisRoundFinish();
	}
	
	public static void main(String[] args){
		new TestUsuario();
	}
	
	public void testLogeaExperimento(){
		System.out.println("TEST LOGEAR EXPERIMENTO\n");
		//Creamos un experimento
		Experimento response = user.logeaExperimento("ex10u0", "VBTSm4");
		System.out.println("Experimento obtenido: "+response.getTipo().getTipo());
		System.out.println("\n------------------------------------------\n\n");
	}
	
	public void testisRoundFinish(){
		System.out.println("TEST IS ROUND FINISH\n");
		System.out.println(user.isRoundFinish(10, 1, 1));
		System.out.println("\n------------------------------------------\n\n");
	}
	
	public void testEnviarResultado(){
		System.out.println("TEST ENVIAR RESULTADO\n");
		ResultadoBeautyContest r = new ResultadoBeautyContest();
		r.setNumElegido(2);
		Usuario u = new Usuario();
		u.setUsuario("ex10u2");
		r.setUsuario(u);
		user.enviaResultadoBeautyContest(r,1);
		System.out.println("\n------------------------------------------\n\n");
	}
}
