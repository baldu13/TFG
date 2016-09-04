package mainMultithread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

import model.Experimento;
import model.ResultadoBeautyContest;
import model.ResultadoFondoPublico;
import model.Usuario;
import presentation.IUsuario;
import presentation.UserFacade;

public class TCPClient {

	private static final int SERVER_PORT = 12003;
	private static final IUsuario client = new UserFacade();

	private TCPClient(){}
	
	public static void main(String[] args) throws IOException {

		System.out.println("Servidor de clientes INICIADO");
		ServerSocket serverSocket = new ServerSocket(SERVER_PORT, 10);

		while (true) {
			Socket socket = serverSocket.accept();
			new Thread(){
				@Override
				public void run(){
					try {
						processCall(socket);
					} catch (Exception e) {}
				}
			}.start();
		}
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

	private static void processCall(Socket socket) {
		try {
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			// Leemos el codigo de operacion
			byte[] codOperacion = new byte[4];
			is.read(codOperacion, 0, 4);
			int op = bytesToInteger(codOperacion);

			System.out.println("CLIENTE Recibida peticion. Codigo de operacion " + op);

			// Variables
			byte[] var;
			int l;
			String user;
			Usuario u;
			float f;

			switch (op) {
			case 1: // logeaExperimento
				// Usuario
				var = new byte[4];
				is.read(var, 0, 4);
				l = bytesToInteger(var);
				var = new byte[l];
				is.read(var, 0, l);
				user = new String(var, StandardCharsets.UTF_8);

				// Pass
				var = new byte[4];
				is.read(var, 0, 4);
				l = bytesToInteger(var);
				var = new byte[l];
				is.read(var, 0, l);
				String pass = new String(var, StandardCharsets.UTF_8);

				Experimento e = client.logeaExperimento(user, pass);

				// Enviamos id, tipo del experimento y rondas
				if (e != null) {
					// Identificacion correcta
					os.write(intToBytes(e.getId()));
					os.write(intToBytes(e.getTipo().getId()));
					os.write(intToBytes(e.getMaxRondas()));
				} else {
					// Identificacion invalida
					os.write(intToBytes(0));
					os.write(intToBytes(0));
					os.write(intToBytes(0));
				}
				break;
			case 2: // Enviar resultado
				var = new byte[4];
				is.read(var, 0, 4);
				l = bytesToInteger(var); // Tipo de resultado
				switch (l) {
				case 1: // Beauty contest
					var = new byte[4];
					is.read(var, 0, 4);
					f = bytesToFloat(var); // Resultado

					ResultadoBeautyContest rbc = new ResultadoBeautyContest();
					rbc.setNumElegido(f);

					var = new byte[4];
					is.read(var, 0, 4);
					l = bytesToInteger(var);
					var = new byte[l];
					is.read(var, 0, l);
					user = new String(var, StandardCharsets.UTF_8);

					u = new Usuario();
					u.setUsuario(user);
					rbc.setUsuario(u);

					var = new byte[4];
					is.read(var, 0, 4);
					l = bytesToInteger(var); // Ronda

					client.enviaResultadoBeautyContest(rbc, l);
					break;
				case 2: // FondoPublicoPrivado
					var = new byte[4];
					is.read(var, 0, 4);
					f = bytesToFloat(var); // Resultado publico

					ResultadoFondoPublico rfp = new ResultadoFondoPublico();
					rfp.setCantidadPublico(f);

					var = new byte[4];
					is.read(var, 0, 4);
					f = bytesToFloat(var); // Resultado privado

					rfp.setCantidadPrivado(f);

					var = new byte[4];
					is.read(var, 0, 4);
					l = bytesToInteger(var);
					var = new byte[l];
					is.read(var, 0, l);
					user = new String(var, StandardCharsets.UTF_8);

					u = new Usuario();
					u.setUsuario(user);
					rfp.setUsuario(u);

					var = new byte[4];
					is.read(var, 0, 4);
					l = bytesToInteger(var); // Ronda

					client.enviaResultadoFondoPublico(rfp, l);
					break;
				}
				break;
			default:
			}
			socket.close();
		} catch (Exception e) {
		}
	}

}
