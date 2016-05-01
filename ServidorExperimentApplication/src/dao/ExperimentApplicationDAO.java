package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
			e.printStackTrace();
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
				System.err.println("Error al cerrar la conexion");
			}
		}
	}

	/**
	 * Metodo que crea un experimento
	 * 
	 * @param e
	 *            Experimento a crear
	 * @return id del experimento creado
	 */
	public int creaExperimento(Experimento e) {
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			sql = "INSERT INTO experimento(fechaInicio, maxRondas, grupal, numGrupos, tipo, nombre) VALUES (?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setDate(1, getActualSQLDate());
			pstmt.setInt(2, e.getMaxRondas());
			pstmt.setBoolean(3, e.isGrupal());
			pstmt.setInt(5, e.getNumGrupos());
			pstmt.setInt(6, e.getTipo().getId());
			pstmt.setString(7, e.getNombre());
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

		} catch (Exception ex) {
			System.err.println("Error al crear el experimento");
			ex.printStackTrace();
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
				System.err.println("Error al cerrar la conexion");
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

			// Buscamos los datros de ese experimento
			sql = "SELECT id, tipo FROM experimento WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idExperimento);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				e = new Experimento();
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
			ex.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				System.err.println("Error al cerrar la conexion");
			}
		}
		return e;
	}

	public void registraResultado(Resultado r) {
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			sql = "INSERT INTO resultado(tipoResultado, participacion, valorTexto, valorNumerico) VALUES (?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, r.getTipo().getId());
			pstmt.setInt(2, r.getParticipante().getId());
			pstmt.setString(3, r.getValorTexto());
			pstmt.setFloat(4, r.getValorNumerico());
			pstmt.execute();
		} catch (Exception e) {
			System.err.println("Error al crear el resultado");
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				System.err.println("Error al cerrar la conexion");
			}
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
			ex.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				System.err.println("Error al cerrar la conexion");
			}
		}
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
			System.out.println("Ronda: " + r.getNumRonda() + " Experimento: " + r.getExperimento().getId());
			pstmt.setInt(2, r.getExperimento().getId());
			pstmt.execute();
		} catch (Exception ex) {
			System.err.println("Error al crear la ronda");
			ex.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				System.err.println("Error al cerrar la conexion");
			}
		}
	}

	public Informe getResultadosExperimento(int idExperimento) {
		// TODO
		Informe informe = new Informe();
		informe.setExperimento(getExperimentoId(idExperimento));
		List<Resultado> resultados = new LinkedList<Resultado>();
		Resultado r;
		
		try {
			//TODO
			/*
			sql = "SELECT id, nombre, fechaInicio, fechaFin, maxRondas, grupal, numGrupos, tipo FROM experimento WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				e = new Experimento();
				e.setId(rs.getInt("id"));
				e.setFechaInicio(rs.getDate("fechaInicio"));
				e.setFechaFin(rs.getDate("fechaFin"));
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
			}
			*/
		} catch (Exception ex) {
			System.err.println("Error al obtener los resultados");
			ex.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				System.err.println("Error al cerrar la conexion");
			}
		}
		
		informe.setResultados(resultados);
		return informe;
	}

	/**
	 * Metodo auxiliar para obtener un experimento por id
	 */
	private Experimento getExperimentoId(int id) {
		Experimento e = null;
		try {
			sql = "SELECT id, nombre, fechaInicio, fechaFin, maxRondas, grupal, numGrupos, tipo FROM experimento WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				e = new Experimento();
				e.setId(rs.getInt("id"));
				e.setFechaInicio(rs.getDate("fechaInicio"));
				e.setFechaFin(rs.getDate("fechaFin"));
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
			}
		} catch (Exception ex) {
			System.err.println("Error al obtener el experimento");
			ex.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
				System.err.println("Error al cerrar la conexion");
			}
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
}
