package mainMultithread;

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.*;

import dao.ExperimentApplicationDAO;
import model.*;
import presentation.*;

public class TCPAdmin {

	private static final int SERVER_PORT = 12002;
	private static final IAdministracion admin = new AdminFacade();
	private static final IUsuario usuario = new UserFacade();
	
	private TCPAdmin(){}
	
	public static void main(String[] argv) throws Exception {
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
				float fPublico = inFromClient.readFloat();
				float fPrivado = inFromClient.readFloat();
				// Procesamos
				CrearExperimentoResponseDTO response = admin.crearExperimento(nombre, te, numUsuarios, tamGrupos,
						numRondas,fPublico,fPrivado);
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
			case 83886080: //Informe de experimento (para móvil)
				InputStream is = connectionSocket.getInputStream();
				OutputStream os = connectionSocket.getOutputStream();
				//Leemos USUARIO
				byte[] var = new byte[4];
				is.read(var, 0, 4);
				int l = bytesToInteger(var);
				var = new byte[l];
				is.read(var, 0, l);
				String user = new String(var, StandardCharsets.UTF_8);
				//Procesamos
				Informe in = admin.informeExperimento(new ExperimentApplicationDAO().getidExperimentoUsuario(user));
				//Retornamos informacion
				os.write(intToBytes(in.getResultados().size()));
				for(int j=0; j<in.getResultados().size();j++){
					Resultado r = in.getResultados().get(j);
					os.write(intToBytes(r.getParticipante().getNumGrupo()));
					os.write(intToBytes(r.getParticipante().getRonda().getNumRonda()));
					os.write(intToBytes(r.getParticipante().getUsuario().getUsuario().length()));
					os.write(r.getParticipante().getUsuario().getUsuario().getBytes());
					os.write(intToBytes(r.getTipo().getEtiqueta().length()));
					os.write(r.getTipo().getEtiqueta().getBytes());
					if(r.getValorTexto() == null){
						r.setValorTexto("");
					}
					os.write(intToBytes(r.getValorTexto().length()));
					os.write(r.getValorTexto().getBytes());
					os.write(floatToBytes(r.getValorNumerico()));
				}
				break;
			default:
				// No hacer nada, peticion erronea
			}
			connectionSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		} //Peticion erronea, ignorar
	}
	
	public static int bytesToInteger(byte[] bytes) {
		return ((bytes[3] & 0xff) << 24) | ((bytes[2] & 0xff) << 16) | ((bytes[1] & 0xff) << 8) | (bytes[0] & 0xff);
	}

	public static float bytesToFloat(byte[] bytes) {
		return ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();
	}

	public static byte[] intToBytes(int value) {
		return new byte[] { (byte) (value >>> 24), (byte) (value >>> 16), (byte) (value >>> 8), (byte) value };
	}
	
	public static byte[] floatToBytes(float value){
		int bits = Float.floatToIntBits(value);
		byte[] bytes = new byte[4];
		bytes[0] = (byte)(bits & 0xff);
		bytes[1] = (byte) ((bits >> 8) & 0xff);
		bytes[2] = (byte) ((bits >> 16) & 0xff);
		bytes[3] = (byte) ((bits >> 24) & 0xff);
		return bytes;
	}
}
