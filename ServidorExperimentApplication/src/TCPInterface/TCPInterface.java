package TCPInterface;

import java.net.*;
import java.io.*;
import java.util.*;

import dao.ExperimentApplicationDAO;
import model.*;
import presentation.*;

public class TCPInterface {

	private static final int SERVER_PORT = 12002;
	private static final IAdministracion admin = new AdminFacade();

	public static void main(String argv[]) throws Exception {
		ServerSocket welcomeSocket = new ServerSocket(SERVER_PORT);
		while (true) {
			try {
				Socket connectionSocket = welcomeSocket.accept();
				DataInputStream inFromClient = new DataInputStream(connectionSocket.getInputStream());
				DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
				int codOperacion = inFromClient.readInt();
				System.out.println("Recibida peticion. Codigo de operacion " + codOperacion + ".");
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
				default:
					// No hacer nada, peticion erronea
				}
			} catch (Exception e) {} //Peticion erronea
		}
	}
}
