package business;

import model.*;
import dao.*;

public class ExperimentosBusiness {
	
	private static final int LONGITUD_CONTRASENA = 6;
	private ExperimentApplicationDAO dao;
	
	public ExperimentosBusiness(){
		dao = new ExperimentApplicationDAO();
	}
	
	public CrearExperimentoResponseDTO creaExperimento(Experimento e, int numUsuarios){
		CrearExperimentoResponseDTO response = new CrearExperimentoResponseDTO();
		//Creamos el experimento con las rondas
		response.setIdExperimento(dao.creaExperimento(e));
		//Creamos los usuarios necesarios y los anadimos al experimento
		Usuario u;
		for(int i=0; i<numUsuarios;i++){
			//Creamos un nuevo usuario
			u = new Usuario();
			u.setUsuario(generateUsername(e.getId(),i));
			u.setClave(generatePassword(LONGITUD_CONTRASENA));
			u = dao.creaUsuario(u);
			response.getUsuarios().add(u);
			//Lo añadimos al experimento
			Participacion p = new Participacion();
			p.setUsuario(u);
			p.setNumGrupo(i%e.getTamanoGrupos()); //va añadiendo a todos los grupos por igual
			for(int j=1; j<=e.getMaxRondas();j++){ //TODO: Para rondas variables falta
				//Se le añade en todas las rondas
				Ronda r = new Ronda();
				r.setExperimento(e);
				r.setNumRonda(j);
				p.setRonda(r);
				dao.creaParticipacion(p);
			}
		}
		return response;
	}
	
	public TipoExperimento getExperimentoUsuario(Usuario u){
		//TODO
		return null;
	}
	
	/**
	 * Metodo que genera un nombre de usuario en funcion del experimento
	 * y un numero de "usuario"
	 * @param idExperimento Experimento
	 * @param iter Numero de usuario dentro del experimento
	 * @return El nombre de usuario
	 */
	private String generateUsername(int idExperimento, int iter){
		return String.format("ex%du%d", idExperimento,iter);
	}
	
	/**
	 * Metodo que genera una contraseña aleatoria
	 * @param longitud Longitud de la contraseña
	 * @return Contraseña de la longitud deseada
	 */
	private String generatePassword(int longitud){
		String cadena ="abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int random;
		String password = "";
		for(int i=0;i<longitud;i++){
			random = (int) (Math.random()*cadena.length());
			password += cadena.charAt(random);
		}
		return password;
	}
		
}
