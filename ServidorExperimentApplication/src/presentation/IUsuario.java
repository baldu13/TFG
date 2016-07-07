package presentation;

import model.*;

public interface IUsuario {

	void enviaResultadoFondoPublico(ResultadoFondoPublico r, int ronda);
	
	void enviaResultadoBeautyContest(ResultadoBeautyContest r, int ronda);
	
	boolean isRoundFinish(int idExperimento, int ronda, int idTipoExperimento);
	
	/**
	 * Solo retorna el id del experimento y el tipo
	 */
	Experimento logeaExperimento(String usuario, String clave);
}
