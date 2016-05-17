package utilities;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import model.*;

public class ServerConnection {
	
	private static final String SERVER_IP = "localhost";
	private static final int SERVER_PORT = 12002;

	public static CrearExperimentoResponseDTO crearExperimento(String nombre, TipoExperimento tipo, int numUsuarios, int tamanoGrupos, int numRondas){
		CrearExperimentoResponseDTO response = new CrearExperimentoResponseDTO();
		try{
			Socket clientSocket = new Socket(SERVER_IP, SERVER_PORT);   
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());   
			DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());   
			
			//Enviamos la informacion
			outToServer.writeInt(2); //Codigo 2 = crearExperimento
			outToServer.writeBytes(nombre+"\n");
			outToServer.writeInt(tipo.getId());
			outToServer.writeInt(numUsuarios);
			outToServer.writeInt(tamanoGrupos);
			outToServer.writeInt(numRondas);
			//Leemos la respuesta
			response.setIdExperimento(inFromServer.readInt());
			numUsuarios = inFromServer.readInt();
			Usuario u;
			for(int i=0;i<numUsuarios;i++){
				u = new Usuario();
				u.setUsuario(inFromServer.readLine());
				u.setClave(inFromServer.readLine());
				response.getUsuarios().add(u);
			}
			//Cerramos la conexion
			clientSocket.close();
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("Error al conectar con el servidor");
		}
		
		return response;
	}
	
	public static Informe informeExperimento(int idExperimento){
		//TODO
		return null;
	}
	
	public static List<Experimento> getExperimentos(){
		List<Experimento> lista = new LinkedList<Experimento>();
		//Conexion con el servidor
		try{
			Socket clientSocket = new Socket(SERVER_IP, SERVER_PORT);   
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());   
			DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());   
			
			//Enviamos la informacion
			outToServer.writeInt(1); //Codigo 1 = getExperimentos
			//Leemos la respuesta
			int numElementos = inFromServer.readInt();
			Experimento e;
			for(int i=0;i<numElementos;i++){
				e = new Experimento();
				e.setId(inFromServer.readInt());
				e.setNombre(inFromServer.readLine());
				e.setMaxRondas(inFromServer.readInt());
				e.setNumGrupos(inFromServer.readInt());
				
				int tipo = inFromServer.readInt();
				TipoExperimento te = new TipoExperimento();
				te.setId(tipo);
				switch(tipo){
				case 1:
					te.setTipo("Beauty Contest");
					break;
				case 2:
					te.setTipo("Fondo publico y privado");
					break;
				default:
				}
				e.setTipo(te);
				e.setFecha(new Date(inFromServer.readLong()));
				lista.add(e);
			}
			//Cerramos la conexion
			clientSocket.close();
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("Error al conectar con el servidor");
		}
		return lista;
	}
	
	public static List<Usuario> getListaUsuariosExperimento(int idExperimento){
		List<Usuario> listaUsuarios = new LinkedList<Usuario>();
		try{
			Socket clientSocket = new Socket(SERVER_IP, SERVER_PORT);   
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());   
			DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());   
			
			//Enviamos la informacion
			outToServer.writeInt(3); //Codigo 3 = getUsuariosExperimento
			outToServer.writeInt(idExperimento);
			//Leemos la respuesta
			int numUsuarios = inFromServer.readInt();
			Usuario u;
			for(int i=0;i<numUsuarios;i++){
				u = new Usuario();
				u.setUsuario(inFromServer.readLine());
				u.setClave(inFromServer.readLine());
				listaUsuarios.add(u);
			}
			//Cerramos la conexion
			clientSocket.close();
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("Error al conectar con el servidor");
		}
		return listaUsuarios;
	}
	
	public static Informe geInformeExperimento(int idExperimento){
		Informe i = new Informe();
		try{
			Socket clientSocket = new Socket(SERVER_IP, SERVER_PORT);   
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());   
			DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());   
			
			//Enviamos la informacion
			outToServer.writeInt(4); //Codigo 4 = getInforme
			outToServer.writeInt(idExperimento);
			//Leemos la respuesta
			//TODO
			//Cerramos la conexion
			clientSocket.close();
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("Error al conectar con el servidor");
		}
		
		return i;
	}
}