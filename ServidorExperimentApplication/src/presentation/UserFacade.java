package presentation;

import business.*;
import model.*;

public class UserFacade implements IUsuario{

	private ExperimentosBusiness experimentos;
	private ResultadosBusiness resultados;

	public UserFacade(){
		experimentos = new ExperimentosBusiness();
		resultados = new ResultadosBusiness();
	}
	
	@Override
	public void enviaResultadoFondoPublico(ResultadoFondoPublico rfp, int ronda) {
		Resultado r = new Resultado();
		Participacion p = new Participacion();
		p.setUsuario(rfp.getUsuario());
		Ronda ro = new Ronda();
		ro.setNumRonda(ronda);
		p.setRonda(ro);
		r.setParticipante(p);
		TipoResultado tr = new TipoResultado();
		tr.setId(3); //Tipo fondo privado
		r.setTipo(tr);
		r.setValorNumerico(rfp.getCantidadPrivado());
		resultados.registraResultado(r);
		
		r = new Resultado();
		p = new Participacion();
		p.setUsuario(rfp.getUsuario());
		p.setRonda(ro);
		r.setParticipante(p);
		tr = new TipoResultado();
		tr.setId(2); //Tipo fondo publico
		r.setTipo(tr);
		r.setValorNumerico(rfp.getCantidadPublico());
		resultados.registraResultado(r);
	}
	
	@Override
	public void enviaResultadoBeautyContest(ResultadoBeautyContest rbc, int ronda) {
		Resultado r = new Resultado();
		Participacion p = new Participacion();
		p.setUsuario(rbc.getUsuario());
		Ronda ro = new Ronda();
		ro.setNumRonda(ronda);
		p.setRonda(ro);
		r.setParticipante(p);
		TipoResultado tr = new TipoResultado();
		tr.setId(1); //Tipo beauty contest
		r.setTipo(tr);
		r.setValorNumerico(rbc.getNumElegido());
		resultados.registraResultado(r);
	}

	@Override
	public Experimento logeaExperimento(String usuario, String clave) {
		Usuario u = new Usuario();
		u.setUsuario(usuario);
		u.setClave(clave);
		return experimentos.getExperimentoUsuario(u);
	}

	@Override
	public boolean isRoundFinish(int idExperimento, int ronda, int tipoExperimento){
		return resultados.isRoundFinish(idExperimento, ronda, tipoExperimento);
	}
	
	@Override
	public float[] getRatiosExperimento(int idExperimento){
		return experimentos.getRatiosExperimento(idExperimento);
	}
	
	@Override
	public int getResultadosFondos(int idExperimento){
		return experimentos.getResultadosFondos(idExperimento);
	}
}
