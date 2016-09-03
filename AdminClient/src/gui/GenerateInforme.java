package gui;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import model.*;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class GenerateInforme extends JFrame {

	/**
	 * Create the frame.
	 */
	public GenerateInforme(JFrame main, Informe informe) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.showSaveDialog(null);
		if(fileChooser.getSelectedFile()!=null)
			generateInforme(informe, fileChooser.getSelectedFile());
		main.setVisible(true);
		dispose();
	}
	
	private void generateInforme(Informe toPrint,File f){
		try {
			String path = f+"\\informeExperimento"+toPrint.getExperimento().getId()+".txt";
			File fichero = new File(path);
			PrintWriter writer = new PrintWriter(fichero,"UTF-8");
			//TODO: Falta clasificar por rondas
			writer.println("INFORME DEL EXPERIMENTO "+toPrint.getExperimento().getId());
			writer.println("---------------------------------------------------------");
			writer.println("Tipo: "+toPrint.getExperimento().getTipo().getTipo());
			writer.println("Nombre: "+toPrint.getExperimento().getNombre());
			writer.println("Numero de participantes: "+toPrint.getExperimento().getNumParticipantes());
			writer.println("Numero de rondas: "+toPrint.getExperimento().getMaxRondas());
			writer.println("Numero de grupos: "+toPrint.getExperimento().getNumGrupos());
			writer.println("\n-------------------------------------------------------\n");
			writer.println("RESULTADOS\n");
			writer.println();
			List<Resultado>[] resultadosRonda = resultadosPorRonda(toPrint.getResultados(), toPrint.getExperimento().getMaxRondas());
			switch(toPrint.getExperimento().getTipo().getId()){
			case 1:
				for(int i=0;i<resultadosRonda.length;i++){
					writer.println("   RONDA "+(i+1));
					writer.println();
					printResultadosBeautyContest(writer,resultadosRonda[i]);
					writer.println();
				}
				break;
			case 2:
				for(int i=0;i<resultadosRonda.length;i++){
					writer.println("   RONDA "+(i+1));
					writer.println();
					printResultadosFondoPublicoPrivado(writer,resultadosRonda[i]);
					writer.println();
				}
				break;
			default:
				break;
			}
			writer.close();
		} catch (Exception e) {
			new MensajeUsuario("No se pudo generar el fichero");
		}
	}

	private void printResultadosFondoPublicoPrivado(PrintWriter writer, List<Resultado> resultados) {
		//TODO calcular invertido en cada fondo y repartir
		for(Resultado r: resultados){
			writer.println("         Usuario: "+r.getParticipante().getUsuario().getUsuario());
			writer.println("         Grupo: "+r.getParticipante().getNumGrupo());
			writer.println("         Fondo destinado: "+r.getTipo().getEtiqueta());
			writer.println("         Valor seleccionado: "+r.getValorNumerico());
			writer.println();
		}
	}

	private void printResultadosBeautyContest(PrintWriter writer, List<Resultado> resultados) {
		if(!resultados.isEmpty()){
			float total = 0;
			
			for(Resultado r: resultados){
				writer.println("         Usuario: "+r.getParticipante().getUsuario().getUsuario());
				writer.println("         Grupo: "+r.getParticipante().getNumGrupo());
				writer.println("         Valor seleccionado: "+r.getValorNumerico());
				total += r.getValorNumerico();
				writer.println();
			}
			writer.println();
			total /= resultados.size();
			total *= 0.75;
			writer.println("      3/4 de la media: "+total);
			writer.println();
	
			Resultado winner = resultados.get(0);
			float diferencia;
			float distancia;
			for(Resultado r: resultados){
				diferencia = Math.abs(total-winner.getValorNumerico());
				distancia = Math.abs(total-r.getValorNumerico());
				if(distancia<diferencia){
					winner = r; //Está mas cerca del resultado
				}
			}
			writer.println("      Ganador "+winner.getParticipante().getUsuario().getUsuario()+" con el número "+winner.getValorNumerico());
			writer.println();
		}
	}
	
	private List<Resultado>[] resultadosPorRonda(List<Resultado> resuls, int maxRonda){
		if(maxRonda!=0){
			List<Resultado>[] resultados = new List[maxRonda];
			
			//Inicializacion
			for(int i=0;i<resultados.length;i++){
				resultados[i] = new ArrayList<Resultado>();
			}
			
			for(Resultado r: resuls){
				resultados[r.getParticipante().getRonda().getNumRonda()-1].add(r);
			}
		
			return resultados;
		}
		return new ArrayList[0];
	}
}
