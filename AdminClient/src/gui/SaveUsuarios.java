package gui;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import model.*;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class SaveUsuarios extends JFrame {

	/**
	 * Create the frame.
	 */
	public SaveUsuarios(JFrame main, CrearExperimentoResponseDTO response) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.showSaveDialog(null);
		if(fileChooser.getSelectedFile()!=null)
			generateUsuarios(response, fileChooser.getSelectedFile());
		main.setVisible(true);
		dispose();
	}
	
	private void generateUsuarios(CrearExperimentoResponseDTO toPrint,File f){
		try {
			String path = f+"\\usersExp"+toPrint.getIdExperimento()+".txt";
			File fichero = new File(path);
			PrintWriter writer = new PrintWriter(fichero,"UTF-8");
			writer.print("Usuarios para el experimento "+toPrint.getIdExperimento()+"\n\n");
			for(Usuario u: toPrint.getUsuarios()){
				writer.print("Usuario: "+u.getUsuario()+"\n");
				writer.print("Contraseña: "+u.getClave()+"\n\n");
			}
			writer.close();
			f.setReadable(true);
		} catch (Exception e) {
			new MensajeUsuario("No se pudo generar el fichero");
			e.printStackTrace();
		}
	}
}
