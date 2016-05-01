package presentation;

import model.*;

public interface IUsuario {

	void enviaResultado(Resultado r);
	
	Experimento logeaExperimento(String usuario, String clave);
}
