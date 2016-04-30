package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import model.*;

public class ExperimentApplicationDAO {
	
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private static final String DB_URL = "jdbc:mysql://localhost:3306/experimentsApplication";

	//  Database credentials
	private static final String USER = "admin";
	private static final String PASS = "admin";
	
	private Connection conn = null;
	private Statement stmt = null;
	private PreparedStatement pstmt = null;

	private String sql;
	
	public ExperimentApplicationDAO(){
		try{
			Class.forName(JDBC_DRIVER);
		}catch(Exception e){
			System.err.println("Error al abrir la conexion con la base de datos");
		}
	}
	
	/**
	 * Metodo que crea un usuario
	 * @param u Usuario a crear
	 * @return Usuario creado con el id actualizado
	 */
	public Usuario creaUsuario(Usuario u){
		try{
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
		    sql = "INSERT INTO usuario(usuario, clave) VALUES (?,?)";
		    pstmt = conn.prepareStatement(sql);
		    pstmt.setString(1, u.getUsuario());
		    pstmt.setString(2, u.getClave());
		    pstmt.execute();
		    
		    sql = "SELECT id, usuario, clave FROM usuario WHERE usuario=?";
		    pstmt = conn.prepareStatement(sql);
		    pstmt.setString(1, u.getUsuario());
		    
		    ResultSet rs = pstmt.executeQuery(); //Consulta para retornar el usuario actualizado
		    if(rs.next()){
		    	u.setUsuario(rs.getString("usuario"));
		    	u.setClave(rs.getString("clave"));
		    	u.setId(rs.getInt("id"));
		    }
		    return u;
		}catch(Exception e){
			System.err.println("Error al crear el usuario");
			e.printStackTrace();
			return null;
		}finally{
			try{
				if(stmt!=null)
		            stmt.close();
				if(pstmt!=null)
		            pstmt.close();
				if(conn!=null)
		            conn.close();
			}catch(Exception e){
				System.err.println("Error al cerrar la conexion");
			}
		}
	}
	
	/**
	 * Metodo que crea un experimento
	 * @param e Experimento a crear
	 * @return id del experimento creado
	 */
	public int creaExperimento(Experimento e){
		try{
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
		    sql = "INSERT INTO experimento(fechaInicio, maxRondas, grupal, gruposVariables, tamGrupos, tipo, nombre) VALUES (?,?,?,?,?,?,?)";
		    pstmt = conn.prepareStatement(sql);
		    pstmt.setDate(1, getActualSQLDate());
		    pstmt.setInt(2, e.getMaxRondas());
		    pstmt.setBoolean(3, e.isGrupal());
		    pstmt.setBoolean(4, e.isGruposVariables());
		    pstmt.setInt(5, e.getTamanoGrupos());
		    pstmt.setInt(6, e.getTipo().getId());
		    pstmt.setString(7, e.getNombre());
		    pstmt.execute();
		    
		    sql = "SELECT id FROM experimento WHERE nombre=?";
		    pstmt = conn.prepareStatement(sql);
		    pstmt.setString(1, e.getNombre());
		    
		    ResultSet rs = pstmt.executeQuery(); //Consulta para retornar el usuario actualizado
		    
		  //Creamos las rondas del experimento
		    
		    
		    if(rs.next()){
		    	Ronda r;
			    for(int i=1;i<=e.getMaxRondas();i++){
			    	r = new Ronda();
			    	e.setId(rs.getInt("id"));
			    	r.setExperimento(e);
			    	r.setNumRonda(i);
			    	creaRonda(r);
			    }
		    	return e.getId();
		    }
		    return -1;
		    
		    
		}catch(Exception ex){
			System.err.println("Error al crear el experimento");
			ex.printStackTrace();
			return -1;
		}finally{
			try{
				if(stmt!=null)
		            stmt.close();
				if(pstmt!=null)
		            pstmt.close();
				if(conn!=null)
		            conn.close();
			}catch(Exception ex){
				System.err.println("Error al cerrar la conexion");
			}
		}
	}
	
	public TipoExperimento getExperimentoUsuario(Usuario u){
		//TODO
		return null;
	}
	
	public void registraResultado(Resultado r){
		//TODO
	}
	
	/**
	 * Metodo que registra un usuario en una ronda-experimento-grupo
	 * @param p Participacion del usuario
	 */
	public void creaParticipacion(Participacion p){
		try{
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
		    sql = "INSERT INTO participacion(usuario, experimento, ronda, grupo, entrado) VALUES (?,?,?,?,?)";
		    pstmt = conn.prepareStatement(sql);
		    pstmt.setInt(1, p.getUsuario().getId());
		    pstmt.setInt(2, p.getRonda().getExperimento().getId());
		    pstmt.setInt(3, p.getRonda().getNumRonda());
		    pstmt.setInt(4, p.getNumGrupo());
		    pstmt.setBoolean(5, false);
		    pstmt.execute();
		}catch(Exception ex){
			System.err.println("Error al crear la participacion");
			ex.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
		            stmt.close();
				if(pstmt!=null)
		            pstmt.close();
				if(conn!=null)
		            conn.close();
			}catch(Exception ex){
				System.err.println("Error al cerrar la conexion");
			}
		}
	}

	/**
	 * Metodo para crear una ronda de un experimento
	 */
	private void creaRonda(Ronda r){
		try{
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
		    sql = "INSERT INTO ronda(numRonda, experimento) VALUES (?,?)";
		    pstmt = conn.prepareStatement(sql);
		    pstmt.setInt(1, r.getNumRonda());
		    System.out.println("Ronda: "+r.getNumRonda()+" Experimento: "+r.getExperimento().getId());
		    pstmt.setInt(2, r.getExperimento().getId());
		    pstmt.execute();
		}catch(Exception ex){
			System.err.println("Error al crear la ronda");
			ex.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
		            stmt.close();
				if(pstmt!=null)
		            pstmt.close();
				if(conn!=null)
		            conn.close();
			}catch(Exception ex){
				System.err.println("Error al cerrar la conexion");
			}
		}
	}
	
	/**
	 * Metodo que devuelve la fecha actual en tipo de dato SQL
	 * @return Date SQL actual
	 */
	private java.sql.Date getActualSQLDate(){
		return new java.sql.Date(new java.util.Date().getTime());
	}
}
