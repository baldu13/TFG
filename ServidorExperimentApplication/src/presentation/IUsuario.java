package presentation;

import model.*;

public interface IUsuario {

	void enviaResultado(Usuario u, TipoResultado tr, Object valor);
	
	boolean autentificar(String usuario, String clave);
	
	Ronda getRondaExperimento(Usuario u);
}
