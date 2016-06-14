package gui;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import model.*;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

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
			writer.println("INFORME DEL EXPERIMENTO "+toPrint.getExperimento().getId());
			writer.println("---------------------------------------------------------");
			writer.println("Tipo: "+toPrint.getExperimento().getTipo().getTipo());
			writer.println("Nombre: "+toPrint.getExperimento().getNombre());
			writer.println("Numero de rondas: "+toPrint.getExperimento().getMaxRondas());
			writer.println("Numero de grupos: "+toPrint.getExperimento().getNumGrupos());
			writer.println("\n-------------------------------------------------------\n");
			writer.println("RESULTADOS\n");
			switch(toPrint.getExperimento().getTipo().getId()){
			case 1:
				printResultadosBeautyContest(writer,toPrint.getResultados());
				break;
			case 2:
				printResultadosFondoPublicoPrivado(writer,toPrint.getResultados());
				break;
			default:
				break;
			}
			writer.close();
		} catch (Exception e) {
			new MensajeUsuario("No se pudo generar el fichero");
			e.printStackTrace();
		}
	}

	private void printResultadosFondoPublicoPrivado(PrintWriter writer, List<Resultado> resultados) {
		//TODO calcular invertido en cada fondo y repartir
		for(Resultado r: resultados){
			writer.println("Usuario: "+r.getParticipante().getUsuario().getUsuario());
			writer.println("Grupo: "+r.getParticipante().getNumGrupo());
			writer.println("Ronda: "+r.getParticipante().getRonda());
			writer.println("Fondo destinado: "+r.getTipo().getEtiqueta());
			writer.println("Valor seleccionado: "+r.getValorNumerico());
		}
	}

	private void printResultadosBeautyContest(PrintWriter writer, List<Resultado> resultados) {
		float total = 0;
		for(Resultado r: resultados){
			writer.println("Usuario: "+r.getParticipante().getUsuario().getUsuario());
			writer.println("Grupo: "+r.getParticipante().getNumGrupo());
			writer.println("Ronda: "+r.getParticipante().getRonda().getNumRonda());
			writer.println("Valor seleccionado: "+r.getValorNumerico());
			total += r.getValorNumerico();
		}
		total /= resultados.size();
		total *= 0.75;
		writer.println("3/4 de la media: "+total);
	}
}
