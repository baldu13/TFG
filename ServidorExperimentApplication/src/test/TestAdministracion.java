package test;

import presentation.*;

import java.util.List;

import model.*;

public class TestAdministracion {

	private IAdministracion admin;
	
	public TestAdministracion() {
		admin = new AdminFacade();
		//testCrearExperimento();
		testGetExperimentos();
		testGetInformeExperimento();
	}
	
	public static void main(String[] args){
		new TestAdministracion();
	}
	
	public void testCrearExperimento(){
		System.out.println("TEST CREAR EXPERIMENTO\n");
		//Creamos un experimento
		TipoExperimento te = new TipoExperimento();
		te.setId(1);
		//Verificamos que los datos devueltos son correctos
		CrearExperimentoResponseDTO response = admin.crearExperimento("Primer experimento", te, 6, 3, 4,1.5f,1f);
		for(Usuario u: response.getUsuarios()){
			System.out.println("Usuario: "+u.getUsuario()+
					"\nClave: "+u.getClave()+"\n");
		}
		System.out.println("\n------------------------------------------\n\n");
	}
	
	public void testGetExperimentos(){
		System.out.println("TEST OBTENER EXPERIMENTOS\n");
		//Creamos otro experimento para mostrar mas de uno
		TipoExperimento te = new TipoExperimento();
		te.setId(2);
		admin.crearExperimento("Segundo experimento", te, 9, 1, 3, 1.5f, 1f);
		
		//Comprobamos la lista de experimentos
		List<Experimento> lista = admin.getExperimentos();
		for(Experimento exp: lista){
			System.out.println("ID: "+exp.getId());
			System.out.println("Nombre: "+exp.getNombre());
			System.out.println("Tipo: "+exp.getTipo().getTipo());
		}
		System.out.println("\n------------------------------------------\n\n");
	}
	
	public void testGetInformeExperimento(){
		System.out.println("TEST INFORME EXPERIMENTO\n");
		Informe i = admin.informeExperimento(10);
		System.out.println("Informe: "+i.getExperimento().getMaxRondas());
		for(Resultado re: i.getResultados())
			System.out.println("Resultado: "+re.getValorNumerico());
		System.out.println("\n------------------------------------------\n\n");
	}
}
