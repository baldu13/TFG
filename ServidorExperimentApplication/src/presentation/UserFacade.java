package presentation;

import business.ExperimentosBusiness;
import business.ResultadosBusiness;
import model.Resultado;
import model.TipoExperimento;
import model.Usuario;

public class UserFacade implements IUsuario{

	private ExperimentosBusiness experimentos;
	private ResultadosBusiness resultados;

	public UserFacade(){
		experimentos = new ExperimentosBusiness();
		resultados = new ResultadosBusiness();
	}
	
	@Override
	public void enviaResultado(Resultado r) {
		resultados.registraResultado(r);
	}

	@Override
	public TipoExperimento logeaExperimento(String usuario, String clave) {
		Usuario u = new Usuario();
		u.setUsuario(usuario);
		u.setClave(clave);
		return experimentos.getExperimentoUsuario(u);
	}

}
