package presentation;

import model.*;

public interface IUsuario {

	void enviaResultado(Resultado r);
	
	TipoExperimento logeaExperimento(String usuario, String clave);
}
