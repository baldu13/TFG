package main;

import java.io.IOException;

import TCPInterface.TCPAdmin;
import TCPInterface.TCPClient;

public class Launcher {

	public static void main(String[] args){
		new Thread(){
			@Override
			public void run(){
				try {
					TCPAdmin.main(new String[0]);
				} catch (Exception e) {
					System.err.println("Error en el servidor de adminstracion");
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
	}
}
