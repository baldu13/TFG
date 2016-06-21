package TCPInterface;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import model.Experimento;
import presentation.IUsuario;
import presentation.UserFacade;

public class TCPClient {
	
	private static final int SERVER_PORT = 12003;
	private static final IUsuario client = new UserFacade();
	
    public static void main(String[] args) throws IOException {

    	System.out.println("Servidor de clientes INICIADO");
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT, 10);
        
        while(true){
	        Socket socket = serverSocket.accept();
	        InputStream is = socket.getInputStream();
	        OutputStream os = socket.getOutputStream();
	
	        //Leemos el codigo de operacion
	        byte[] codOperacion = new byte[4];
	        is.read(codOperacion, 0, 4);
	        int op = bytesToInteger(codOperacion);
	
	        System.out.println("CLIENTE Recibida peticion. Codigo de operacion " + op);
	        
	        switch(op){
	        case 1: //logeaExperimento
	        	//Usuario
	        	byte[] var = new byte[4];
	        	is.read(var,0, 4);
	        	int l = bytesToInteger(var);
	        	var = new byte[l];
	        	is.read(var, 0, l);
	        	String user = new String(var,StandardCharsets.UTF_8);
	        	
	        	//Pass
	        	var = new byte[4];
	        	is.read(var,0, 4);
	        	l = bytesToInteger(var);
	        	var = new byte[l];
	        	is.read(var, 0, l);
	        	String pass = new String(var,StandardCharsets.UTF_8);
	        	
	        	Experimento e = client.logeaExperimento(user, pass);
	        	
	        	//Enviamos id y tipo del experimento
	        	if(e!=null){
	        		//Identificacion correcta
	        		os.write(intToBytes(e.getId()));
	        		os.write(intToBytes(e.getTipo().getId()));
	        	}else{
	        		//Identificacion invalida
	        		os.write(intToBytes(0));
	        		os.write(intToBytes(0));
	        	}
	        	break;
	        default:
	        }
	        socket.close();
        }
    }
    
    public static int bytesToInteger(byte[] bytes){
    	return (((bytes[3] & 0xff) << 24) | ((bytes[2] & 0xff) << 16) |
                ((bytes[1] & 0xff) << 8) | (bytes[0] & 0xff));
    }
    
    public static byte[] intToBytes(int value){
    	return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
    }
}
