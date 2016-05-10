package presentation;

import model.*;

public interface IUsuario {

	void enviaResultadoFondoPublico(ResultadoFondoPublico r);
	
	void enviaResultadoBeautyContest(ResultadoBeautyContest r);
	
	boolean isRoundFinish(int idExperimento, int ronda, int idTipoExperimento);
	
	Experimento logeaExperimento(String usuario, String clave);
}
