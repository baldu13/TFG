package mainMultithread;

import java.io.IOException;

public class Launcher {

	private Launcher(){}
	
	public static void main(String[] args){
		new Thread(){
			@Override
			public void run(){
				try {
					TCPAdmin.main(new String[0]);
				} catch (Exception e) {
					System.err.println("Error en el servidor de administracion");
				}
			}
		}.start();
		
		new Thread(){
			@Override
			public void run(){
				try {
					TCPClient.main(new String[0]);
				} catch (IOException e) {
					System.err.println("Error en el servidor de cliente");
				}
			}
		}.start();
		try{
			while(true){
				System.in.read();
			}
		}catch(Exception e){}
	}
}
