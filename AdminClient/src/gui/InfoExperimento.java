package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.*;
import utilities.ServerConnection;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.awt.event.ActionEvent;

public class InfoExperimento extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public InfoExperimento(Experimento e, JFrame previous) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 310);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Font fuente = new Font("Tahoma", Font.BOLD, 11);
		
		JLabel lblExperimento = new JLabel("Experimento:");
		lblExperimento.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblExperimento.setBounds(25, 11, 131, 26);
		contentPane.add(lblExperimento);
		
		JLabel lblId = new JLabel("ID:");
		lblId.setFont(fuente);
		lblId.setBounds(52, 48, 46, 14);
		contentPane.add(lblId);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(fuente);
		lblNombre.setBounds(52, 73, 66, 14);
		contentPane.add(lblNombre);

		JLabel lblParticipantes = new JLabel("Participantes:");
		lblParticipantes.setFont(fuente);
		lblParticipantes.setBounds(52, 123, 89, 14);
		contentPane.add(lblParticipantes);
		
		JLabel lblNRondas = new JLabel("N\u00BA Rondas:");
		lblNRondas.setFont(fuente);
		lblNRondas.setBounds(52, 151, 66, 14);
		contentPane.add(lblNRondas);
		
		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setFont(fuente);
		lblTipo.setBounds(52, 98, 46, 14);
		contentPane.add(lblTipo);
		
		JLabel lblTamaoDeLos = new JLabel("Tama\u00F1o de los grupos:");
		lblTamaoDeLos.setFont(fuente);
		lblTamaoDeLos.setBounds(52, 176, 131, 14);
		contentPane.add(lblTamaoDeLos);
		
		JLabel lblFechaDeCreacin = new JLabel("Fecha de creaci\u00F3n:");
		lblFechaDeCreacin.setFont(fuente);
		lblFechaDeCreacin.setBounds(52, 202, 104, 14);
		contentPane.add(lblFechaDeCreacin);
		
		JButton btnAtrs = new JButton("Atr\u00E1s");
		btnAtrs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				previous.setVisible(true);
				dispose();
			}
		});
		btnAtrs.setBounds(25, 237, 89, 23);
		contentPane.add(btnAtrs);
		
		JButton btnGenerarInforme = new JButton("Generar informe");
		btnGenerarInforme.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Informe i = ServerConnection.informeExperimento(e.getId());
				i.setExperimento(e);
				new GenerateInforme(previous, i);
				dispose();
			}
		});
		btnGenerarInforme.setBounds(274, 237, 131, 23);
		contentPane.add(btnGenerarInforme);
		
		JLabel lblId_1 = new JLabel(String.valueOf(e.getId()));
		lblId_1.setBounds(75, 48, 46, 14);
		contentPane.add(lblId_1);
		
		JLabel lblNombre_1 = new JLabel(e.getNombre());
		lblNombre_1.setBounds(110, 73, 246, 14);
		contentPane.add(lblNombre_1);
		
		JLabel lblParticipantes_1 = new JLabel(String.valueOf(e.getNumParticipantes()));
		lblParticipantes_1.setBounds(139, 123, 46, 14);
		contentPane.add(lblParticipantes_1);
		
		JLabel lblTipo_1 = new JLabel(e.getTipo().getTipo());
		lblTipo_1.setBounds(88, 98, 293, 14);
		contentPane.add(lblTipo_1);
		
		JLabel lblRondas = new JLabel(String.valueOf(e.getMaxRondas()));
		lblRondas.setBounds(128, 151, 46, 14);
		contentPane.add(lblRondas);
		
		JLabel lblTamgrupos = new JLabel(String.valueOf(e.getNumGrupos()));
		lblTamgrupos.setBounds(193, 176, 46, 14);
		contentPane.add(lblTamgrupos);
		
		SimpleDateFormat formatterDate = new SimpleDateFormat("dd/MM/yyyy");
		JLabel lblFecha = new JLabel(formatterDate.format(e.getFecha()));
		lblFecha.setBounds(166, 202, 200, 14);
		contentPane.add(lblFecha);
		
		JButton btnGenerarUsuarios = new JButton("Generar usuarios");
		btnGenerarUsuarios.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ex) {
				List<Usuario> listaUsuarios = ServerConnection.getListaUsuariosExperimento(e.getId());
				CrearExperimentoResponseDTO print = new CrearExperimentoResponseDTO();
				print.setIdExperimento(e.getId());
				print.setUsuarios(listaUsuarios);
				new SaveUsuarios(previous,print);
				dispose();
			}
		});
		btnGenerarUsuarios.setBounds(124, 237, 142, 23);
		contentPane.add(btnGenerarUsuarios);
		
		setVisible(true);
	}
}
