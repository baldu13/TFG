package business;

import model.*;

import java.sql.SQLException;
import java.util.List;

import dao.*;

public class ExperimentosBusiness {
	
	private static final int LONGITUD_CONTRASENA = 6;
	private ExperimentApplicationDAO dao;
	
	public ExperimentosBusiness(){
		dao = new ExperimentApplicationDAO();
	}
	
	/**
	 * Metodo que crea un experimento con los datos proporcionados
	 * Crea las rondas especificadas y da de alta usuarios para ese experimento
	 * @param e Experimento a dar de alta
	 * @param numUsuarios Numero de usuarios que participaran en el experimento
	 * @return
	 */
	public CrearExperimentoResponseDTO creaExperimento(Experimento e, int numUsuarios){
		CrearExperimentoResponseDTO response = new CrearExperimentoResponseDTO();
		//Creamos el experimento con las rondas
		try{
			response.setIdExperimento(dao.creaExperimento(e));
		}catch(SQLException ex){
			//No se pudo crear el experimento ya que ya existe uno con ese nombre
			return null;
		}
		//Creamos los usuarios necesarios y los anadimos al experimento
		Usuario u;
		for(int i=0; i<numUsuarios;i++){
			//Creamos un nuevo usuario
			u = new Usuario();
			u.setUsuario(generateUsername(e.getId(),i));
			u.setClave(generatePassword(LONGITUD_CONTRASENA));
			u = dao.creaUsuario(u);
			response.getUsuarios().add(u);
		}
		//Creamos los grupos para las rondas segun se pida
		if(!e.isGrupal()){
			insertaUsuariosSinGrupos(response, e);
		}else{
			insertaUsuariosGruposFijos(response, e);
		}
		return response;
	}
	
	public Experimento getExperimentoUsuario(Usuario u){
		Experimento e = dao.getExperimentoUsuario(u);
		if(e!=null){
			//El usuario esta participando
			dao.setParticipando(u.getUsuario(), true);
		}
		return e;
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
	
	private void insertaUsuariosGruposFijos(CrearExperimentoResponseDTO response, Experimento e){
		//Lo añadimos al experimento
		int i = 1;
		Participacion p;
		Ronda r;
		for(Usuario u : response.getUsuarios()){
			p = new Participacion();
			p.setUsuario(u);
			p.setNumGrupo(i%e.getNumGrupos()); //va añadiendo a todos los grupos por igual
			for(int j=1; j<=e.getMaxRondas();j++){
				//Se le añade en todas las rondas
				r = new Ronda();
				r.setExperimento(e);
				r.setNumRonda(j);
				p.setRonda(r);
				dao.creaParticipacion(p);
			}
			i++;
		}
	}
	
	private void insertaUsuariosSinGrupos(CrearExperimentoResponseDTO response, Experimento e){
		//Lo añadimos al experimento
		int i = 1;
		Participacion p;
		Ronda r;
		for(Usuario u : response.getUsuarios()){
			p = new Participacion();
			p.setUsuario(u);
			p.setNumGrupo(i); //Cada usuario un grupo
			for(int j=1; j<=e.getMaxRondas();j++){
				//Se le añade en todas las rondas
				r = new Ronda();
				r.setExperimento(e);
				r.setNumRonda(j);
				p.setRonda(r);
				dao.creaParticipacion(p);
			}
			i++;
		}
	}

	public List<Experimento> getExperimentos() {
		return dao.getExperimentos();
	}	
	
	public List<Usuario> getUsuariosExperimento(int id){
		return dao.getUsuariosExperimento(id);
	}
	
	public float[] getRatiosExperimento(int idExperimento){
		return dao.getRatiosExperimento(idExperimento);
	}
	
	public int getResultadosFondos(int idExperimento){
		return dao.participacionesExperimento(idExperimento);
	}
}
