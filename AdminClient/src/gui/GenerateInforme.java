package gui;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import model.*;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class GenerateInforme extends JFrame {

	/**
	 * Create the frame.
	 */
	public GenerateInforme(JFrame main, Informe informe) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.showSaveDialog(null);
		
		generateInforme(informe, fileChooser.getSelectedFile());
		main.setVisible(true);
		dispose();
	}
	
	private void generateInforme(Informe toPrint,File f){
		try {
			String path = f+"\\informeExperimento"+toPrint.getExperimento().getId()+".txt";
			File fichero = new File(path);
			PrintWriter writer = new PrintWriter(fichero,"UTF-8");
			//TODO: Escribir el fichero
			writer.print("WIP");
			writer.close();
			f.setReadable(true);
		} catch (Exception e) {
			new MensajeUsuario("No se pudo generar el fichero");
			e.printStackTrace();
		}
	}
}
