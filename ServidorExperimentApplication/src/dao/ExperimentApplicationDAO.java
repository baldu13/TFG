package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import model.*;

public class ExperimentApplicationDAO {

	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/experimentsApplication";

	// Database credentials
	private static final String USER = "admin";
	private static final String PASS = "admin";

	private Connection conn = null;
	private Statement stmt = null;
	private PreparedStatement pstmt = null;

	private String sql;
	
	private String errorConexion = "Error al cerrar la conexion";

	public ExperimentApplicationDAO() {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (Exception e) {
			System.err.println("Error al abrir la conexion con la base de datos");
		}
	}

	/**
	 * Metodo que crea un usuario
	 * 
	 * @param u
	 *            Usuario a crear
	 * @return Usuario creado con el id actualizado
	 */
	public Usuario creaUsuario(Usuario u) {
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			sql = "INSERT INTO usuario(usuario, clave) VALUES (?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, u.getUsuario());
			pstmt.setString(2, u.getClave());
			pstmt.execute();

			sql = "SELECT id, usuario, clave FROM usuario WHERE usuario=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, u.getUsuario());

			ResultSet rs = pstmt.executeQuery(); // Consulta para retornar el
													// usuario actualizado
			if (rs.next()) {
				u.setUsuario(rs.getString("usuario"));
				u.setClave(rs.getString("clave"));
				u.setId(rs.getInt("id"));
			}
			return u;
		} catch (Exception e) {
			System.err.println("Error al crear el usuario");
			return null;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				System.err.println(errorConexion);
			}
		}
	}

	/**
	 * Metodo que crea un experimento
	 * 
	 * @param e Experimento a crear
	 * @return id del experimento creado
	 */
	public int creaExperimento(Experimento e) throws SQLException{
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			sql = "INSERT INTO experimento(fecha, maxRondas, grupal, numGrupos, tipo, nombre, fpublico, fprivado) VALUES (?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, getActualSQLDate());
			pstmt.setInt(2, e.getMaxRondas());
			pstmt.setBoolean(3, e.isGrupal());
			pstmt.setInt(4, e.getNumGrupos());
			pstmt.setInt(5, e.getTipo().getId());
			pstmt.setString(6, e.getNombre());
			pstmt.setFloat(7, e.getfPublico());
			pstmt.setFloat(8, e.getfPrivado());
			pstmt.execute();

			sql = "SELECT id FROM experimento WHERE nombre=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, e.getNombre());

			ResultSet rs = pstmt.executeQuery(); // Consulta para retornar el
													// usuario actualizado

			// Creamos las rondas del experimento
			if (rs.next()) {
				Ronda r;
				for (int i = 1; i <= e.getMaxRondas(); i++) {
					r = new Ronda();
					e.setId(rs.getInt("id"));
					r.setExperimento(e);
					r.setNumRonda(i);
					creaRonda(r);
				}
				return e.getId();
			}
			return -1;
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				System.err.println(errorConexion);
			}
		}
	}

	public Experimento getExperimentoUsuario(Usuario u) {
		Experimento e = null;
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			// Consultamos el id del usuario (si existe)
			sql = "SELECT id FROM usuario WHERE usuario=? AND clave=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, u.getUsuario());
			pstmt.setString(2, u.getClave());
			ResultSet rs = pstmt.executeQuery();

			int idUsuario = -1;
			if (!rs.next()) {
				return e;
			} else {
				idUsuario = rs.getInt("id");
			}

			// Buscamos el id del experimento en el que participa
			int idExperimento = -1;
			sql = "SELECT experimento FROM participacion WHERE usuario=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idUsuario);
			rs = pstmt.executeQuery();

			if (!rs.next()) {
				return e;
			} else {
				idExperimento = rs.getInt("experimento");
			}

			// Buscamos los datos de ese experimento
			sql = "SELECT id, tipo, maxRondas FROM experimento WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idExperimento);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				e = new Experimento();
				e.setMaxRondas(rs.getInt("maxRondas"));
				e.setId(rs.getInt("id"));
				int tipo = rs.getInt("tipo");
				sql = "SELECT tipo FROM tipoexperimento WHERE id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, tipo);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					TipoExperimento te = new TipoExperimento();
					te.setId(tipo);
					te.setTipo(rs.getString("tipo"));
					e.setTipo(te);
				}
			}
		} catch (Exception ex) {
			System.err.println("Error al obtener el experimento de un usuario");
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				System.err.println(errorConexion);
			}
		}
		return e;
	}

	public void registraResultado(Resultado r) {
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			int idUsuario = getIdParticipacionByUser(r.getParticipante().getUsuario().getUsuario(),r.getParticipante().getRonda().getNumRonda());
			sql = "INSERT INTO resultado(tipoResultado, participacion, valorTexto, valorNumerico) VALUES (?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, r.getTipo().getId());
			pstmt.setInt(2, idUsuario);
			pstmt.setString(3, r.getValorTexto());
			pstmt.setFloat(4, r.getValorNumerico());
			pstmt.execute();
		} catch (Exception e) {
			System.err.println("Error al crear el resultado");
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				System.err.println(errorConexion);
			}
		}
	}
	
	public int participacionesExperimento(int idExperimento) {
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			sql = "SELECT COUNT(u.id) as num FROM usuario u, participacion p WHERE u.id=p.usuario AND p.experimento=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idExperimento);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				return rs.getInt("num");
			}else{
				return 0;
			}
		} catch (Exception e) {
			System.err.println("Error al consultar el numero de participantes");
			return 0;
		}
	}
	
	/**
	 * Metodo que registra un usuario en una ronda-experimento-grupo
	 * 
	 * @param p
	 *            Participacion del usuario
	 */
	public void creaParticipacion(Participacion p) {
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			sql = "INSERT INTO participacion(usuario, experimento, ronda, grupo, entrado) VALUES (?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, p.getUsuario().getId());
			pstmt.setInt(2, p.getRonda().getExperimento().getId());
			pstmt.setInt(3, p.getRonda().getNumRonda());
			pstmt.setInt(4, p.getNumGrupo());
			pstmt.setBoolean(5, false);
			pstmt.execute();
		} catch (Exception ex) {
			System.err.println("Error al crear la participacion");
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				System.err.println(errorConexion);
			}
		}
	}

	public Informe getResultadosExperimento(int idExperimento) {
		Informe informe = new Informe();
		informe.setExperimento(getExperimentoId(idExperimento));
		List<Resultado> resultados = new LinkedList<Resultado>();
		
		try {
			//Obtenemos el id de las participaciones en el experimento
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			sql = "SELECT id FROM participacion WHERE experimento=? ORDER BY id";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idExperimento);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				sql = "SELECT id, tipoResultado, participacion, valorTexto, valorNumerico FROM resultado WHERE participacion=?";
				int idParticipacion = rs.getInt("id");
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, idParticipacion);
				ResultSet rs2 = pstmt.executeQuery();
				while(rs2.next()){
					Resultado r = new Resultado();
					r.setId(rs2.getInt("id"));
					r.setValorNumerico(rs2.getFloat("valorNumerico"));
					r.setValorTexto(rs2.getString("valorTexto"));
					r.setTipo(getTipoId(rs2.getInt("tipoResultado")));
					r.setParticipante(getParticipanteId(rs2.getInt("participacion")));
					resultados.add(r);
				}
			}
			informe.setResultados(resultados);
			return informe;
			
		} catch (Exception ex) {
			System.err.println("Error al obtener los resultados");
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
			} catch (Exception ex) {
				System.err.println(errorConexion);
			}
		}
		
		informe.setResultados(resultados);
		return informe;
	}

	/**
	 * Retorna lista con todos los experimentos
	 */
	public List<Experimento> getExperimentos() {
		try{
			List<Experimento> lista = new ArrayList<Experimento>();
			Experimento ex;
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			sql = "SELECT id, nombre, fecha, maxRondas, grupal, tipo, numGrupos FROM experimento";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				ex = new Experimento();
				ex.setId(rs.getInt("id"));
				ex.setFecha(rs.getDate("fecha"));
				ex.setGrupal(rs.getBoolean("grupal"));
				ex.setMaxRondas(rs.getInt("maxRondas"));
				ex.setNombre(rs.getString("nombre"));
				ex.setNumGrupos(rs.getInt("numGrupos"));
				ex.setTipo(getTipoExId(rs.getInt("tipo")));
				ex.setNumParticipantes(participacionesExperimento(rs.getInt("id"))/ex.getMaxRondas());
				lista.add(ex);
			}
			return lista;
		}catch(Exception e){
			System.err.println("Error al obtener loss experimentos");
		}
		return new ArrayList<Experimento>();
	}

	/**
	 * Metodo para especificar si el usuario esta participando o no
	 * @param idUsuario
	 * @param estado
	 */
	public void setParticipando(String usuario, boolean estado){
		try{
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			sql = "UPDATE usuario SET participando=? WHERE usuario=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setBoolean(1, estado);
			pstmt.setString(2, usuario);
			pstmt.execute();
		}catch(Exception e){
			System.err.println("Error al cambiar el estado del usuario");
		}finally{
			try{
				if(pstmt!=null)
					pstmt.close();
				if(conn!=null)
					conn.close();
			}catch(Exception e){
				System.err.println(errorConexion);
			}
		}
	}
	
	public boolean isRoundFinish(int idExperimento, int ronda, int tipoExperimento){
		try{
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			sql = "SELECT COUNT(r.id) FROM resultado r, participacion p WHERE r.participacion=p.id AND p.experimento=? AND p.ronda=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idExperimento);
			pstmt.setInt(2, ronda);
			ResultSet rs = pstmt.executeQuery();
			int numResultados = 0;
			if(rs.next()){
				numResultados = rs.getInt(1);
			}
			sql = "SELECT COUNT(u.id) FROM usuario u, participacion p, resultado r WHERE r.participacion=p.id AND u.id=p.usuario AND p.experimento=? AND p.ronda=? AND u.participando=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idExperimento);
			pstmt.setInt(2, ronda);
			pstmt.setBoolean(3, true);
			rs = pstmt.executeQuery();
			int numUsuarios = 0;
			if(rs.next()){
				numUsuarios = rs.getInt(1);
			}
			System.out.println("Resultados: "+numResultados+"\nUsuarios: "+numUsuarios);
			switch(tipoExperimento){
			case 1:
				//Beauty contest, 1 resultado por usuario
				return numResultados>=numUsuarios;
			case 2:
				//Fondo publico privado, 2 resultados por usuario
				return numResultados>=(2*numUsuarios);
			default:
				return false; //Caso incorrecto	
			}
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("Error al ver los resultados");
		}finally{
			try{
				if(pstmt!=null)
					pstmt.close();
				if(conn!=null)
					conn.close();
			}catch(Exception e){
				System.err.println(errorConexion);
			}
		}
		return false;//cualquier otro caso
	}

	/**
	 * Metodo para crear una ronda de un experimento
	 */
	private void creaRonda(Ronda r) {
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			sql = "INSERT INTO ronda(numRonda, experimento) VALUES (?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, r.getNumRonda());
			pstmt.setInt(2, r.getExperimento().getId());
			pstmt.execute();
		} catch (Exception ex) {
			System.err.println("Error al crear la ronda");
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				System.err.println(errorConexion);
			}
		}
	}

	private Participacion getParticipanteId(int idParticipante) {
		try{
			Participacion p = null;
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			sql = "SELECT usuario, experimento, ronda FROM participacion WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idParticipante);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				p = new Participacion();
				p.setId(idParticipante);
				p.setUsuario(getUsuarioById(rs.getInt("usuario")));
				Ronda r = new Ronda();
				r.setExperimento(getExperimentoId(rs.getInt("experimento")));
				r.setNumRonda(rs.getInt("ronda"));
				p.setRonda(r);
			}
			return p;
		}catch(Exception e){
			System.err.println("Error al obtener la participacion");
		}
		return null;
	}

	private Usuario getUsuarioById(int int1) {
		try{
			Usuario u = null;
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			sql = "SELECT usuario FROM usuario WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, int1);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				u = new Usuario();
				u.setUsuario(rs.getString("usuario"));
			}
			return u;
		}catch(Exception e){
			System.err.println("Error al obtener el usuario");
		}finally {
			try {
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				System.err.println(errorConexion);
			}
		}
		return null;
	}

	private TipoResultado getTipoId(int idTipo) {
		try{
			TipoResultado tr = null;
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			sql = "SELECT etiqueta FROM tiporesultado WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idTipo);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				tr = new TipoResultado();
				tr.setId(idTipo);
				tr.setEtiqueta(rs.getString("etiqueta"));
			}
			return tr;
		}catch(Exception e){
			System.err.println("Error al obtener el tipo de experimento");
		}finally {
			try {
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				System.err.println(errorConexion);
			}
		}
		return null;
	}

	/**
	 * Metodo auxiliar para obtener un experimento por id
	 */
	private Experimento getExperimentoId(int id) {
		Experimento e = null;
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			sql = "SELECT id, nombre, fecha, maxRondas, grupal, numGrupos, tipo FROM experimento WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				e = new Experimento();
				e.setId(rs.getInt("id"));
				e.setFecha(rs.getDate("fecha"));
				e.setGrupal(rs.getBoolean("grupal"));
				e.setMaxRondas(rs.getInt("maxRondas"));
				e.setNumGrupos(rs.getInt("numGrupos"));
				int tipo = rs.getInt("tipo");
				sql = "SELECT tipo FROM tipoexperimento WHERE id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, tipo);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					TipoExperimento te = new TipoExperimento();
					te.setId(tipo);
					te.setTipo(rs.getString("tipo"));
					e.setTipo(te);
				}
				e.setNumParticipantes(participacionesExperimento(e.getId()/e.getMaxRondas()));
			}
		} catch (Exception ex) {
			System.err.println("Error al obtener el experimento");
		}
		return e;
	}

	/**
	 * Metodo que devuelve la fecha actual en tipo de dato SQL
	 * 
	 * @return Date SQL actual
	 */
	private java.sql.Date getActualSQLDate() {
		return new java.sql.Date(new java.util.Date().getTime());
	}

	public TipoExperimento getTipoExId(int id) {
		TipoExperimento te = null;
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			sql = "SELECT tipo FROM tipoexperimento WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				te = new TipoExperimento();
				te.setId(id);
				te.setTipo(rs.getString("tipo"));
			}
		} catch (Exception ex) {
			System.err.println("Error al obtener el experimento");
		}
		return te;
	}
	
	private int getIdParticipacionByUser(String username, int ronda) {
		int id = -1;
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			sql = "SELECT p.id FROM participacion p, usuario u WHERE p.usuario=u.id AND u.usuario=? AND p.ronda=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setInt(2, ronda);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				id =rs.getInt("id");
			}
		} catch (Exception ex) {
			System.err.println("Error al obtener la participacion");
		}
		return id;
	}
	
	public List<Usuario> getUsuariosExperimento(int idExperimento){
		List<Usuario> lista = new LinkedList<Usuario>();
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			sql = "SELECT u.usuario, u.clave FROM usuario u, participacion p WHERE p.usuario=u.id AND p.experimento=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idExperimento);
			ResultSet rs = pstmt.executeQuery();
			Usuario u;
			while (rs.next()) {
				u = new Usuario();
				u.setUsuario(rs.getString("usuario"));
				u.setClave(rs.getString("clave"));
				lista.add(u);
			}
		} catch (Exception ex){
			System.err.println("Error al obtener el experimento");
		}
		return lista;
	}
	
	public int getidExperimentoUsuario(String user) {
		int idExperimento = -1;
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			// Consultamos el id del usuario (si existe)
			sql = "SELECT id FROM usuario WHERE usuario=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user);
			ResultSet rs = pstmt.executeQuery();

			int idUsuario = -1;
			if (!rs.next()) {
				return idExperimento;
			} else {
				idUsuario = rs.getInt("id");
			}

			// Buscamos el id del experimento en el que participa
			sql = "SELECT experimento FROM participacion WHERE usuario=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idUsuario);
			rs = pstmt.executeQuery();

			if (!rs.next()) {
				return idExperimento;
			} else {
				idExperimento = rs.getInt("experimento");
			}
		} catch (Exception ex) {
			System.err.println("Error al obtener el experimento de un usuario");
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				System.err.println(errorConexion);
			}
		}
		return idExperimento;
	}
	
	public float[] getRatiosExperimento(int idExperimento){
		float[] ratios = new float[2];
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			// Consultamos el id del usuario (si existe)
			sql = "SELECT fpublico, fprivado FROM experimento WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idExperimento);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				ratios[0] = rs.getFloat("fpublico");
				ratios[1] = rs.getFloat("fprivado");
			}
		} catch (Exception ex){
			System.err.println("Error al obtener el experimento");
		}
		return ratios;
	}
}
