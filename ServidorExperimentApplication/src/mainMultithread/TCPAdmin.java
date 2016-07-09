package mainMultithread;

import java.net.*;
import java.io.*;
import java.util.*;

import dao.ExperimentApplicationDAO;
import model.*;
import presentation.*;

public class TCPAdmin {

	private static final int SERVER_PORT = 12002;
	private static final IAdministracion admin = new AdminFacade();

	public static void main(String argv[]) throws Exception {
		System.out.println("Servidor de administracion INICIADO");
		ServerSocket welcomeSocket = new ServerSocket(SERVER_PORT);
		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			new Thread(){
				@Override
				public void run(){
					try {
						processCall(connectionSocket);
					} catch (Exception e) {}
				}
			}.start();
		}
	}
	
	private static void processCall(Socket connectionSocket){
		try {
			DataInputStream inFromClient = new DataInputStream(connectionSocket.getInputStream());
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			int codOperacion = inFromClient.readInt();
			System.out.println("ADMIN Recibida peticion. Codigo de operacion " + codOperacion + ".");
			switch (codOperacion) {
			case 1: // getExperimentos
				List<Experimento> lista = admin.getExperimentos();
				// Mandamos los resultados
				outToClient.writeInt(lista.size()); // Mandamos el tamano de
													// la lista seguido de
													// todos los campos en
													// orden
				for (Experimento e : lista) {
					outToClient.writeInt(e.getId());
					outToClient.writeBytes(e.getNombre());
					outToClient.writeBytes("\n"); // Mandamos linea
					outToClient.writeInt(e.getMaxRondas());
					outToClient.writeInt(e.getNumGrupos());
					outToClient.writeInt(e.getNumParticipantes());
					outToClient.writeInt(e.getTipo().getId());
					outToClient.writeLong(e.getFecha().getTime());
				}
				break;
			case 2: // crearExperimento
				// Leemos datos
				String nombre = inFromClient.readLine();
				TipoExperimento te = new ExperimentApplicationDAO().getTipoExId(inFromClient.readInt());
				int numUsuarios = inFromClient.readInt();
				int tamGrupos = inFromClient.readInt();
				int numRondas = inFromClient.readInt();
				// Procesamos
				CrearExperimentoResponseDTO response = admin.crearExperimento(nombre, te, numUsuarios, tamGrupos,
						numRondas);
				// Enviamos respuesta
				outToClient.writeInt(response.getIdExperimento());
				outToClient.writeInt(response.getUsuarios().size());
				for (Usuario u : response.getUsuarios()) {
					outToClient.writeBytes(u.getUsuario() + "\n");
					outToClient.writeBytes(u.getClave() + "\n");
				}
				break;
			case 3: //Lista de usuarios de experimento
				//Leemos idExperimento
				int idExperimento = inFromClient.readInt();
				//Procesamos
				List<Usuario> listaUsuarios = admin.getUsuariosExperimento(idExperimento);
				//Retornamos
				outToClient.writeInt(listaUsuarios.size());
				for(Usuario u: listaUsuarios){
					outToClient.writeBytes(u.getUsuario()+"\n");
					outToClient.writeBytes(u.getClave()+"\n");
				}
				break;
			case 4: //Informe de experimento
				//Leemos idExperimento
				int idExInforme = inFromClient.readInt();
				//Procesamos
				Informe i = admin.informeExperimento(idExInforme);
				//Retornamos informacion
				outToClient.writeInt(i.getResultados().size());
				for(int j=0; j<i.getResultados().size();j++){
					Resultado r = i.getResultados().get(j);
					outToClient.writeInt(r.getParticipante().getNumGrupo());
					outToClient.writeInt(r.getParticipante().getRonda().getNumRonda());
					outToClient.writeBytes(r.getParticipante().getUsuario().getUsuario()+"\n");
					outToClient.writeBytes(r.getTipo().getEtiqueta()+"\n");
					outToClient.writeBytes(r.getValorTexto()+"\n");
					outToClient.writeFloat(r.getValorNumerico());
				}
				break;
			default:
				// No hacer nada, peticion erronea
			}
			connectionSocket.close();
		} catch (Exception e) {} //Peticion erronea, ignorar
	}
}
