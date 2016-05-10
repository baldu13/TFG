package presentation;

import model.*;

public interface IUsuario {

	void enviaResultadoFondoPublico(ResultadoFondoPublico r);
	
	void enviaResultadoBeautyContest(ResultadoBeautyContest r);
	
	boolean isRoundFinish(int idExperimento, int ronda, int idTipoExperimento);
	
	/**
	 * Solo retorna el id del experimento y el tipo
	 */
	Experimento logeaExperimento(String usuario, String clave);
}
