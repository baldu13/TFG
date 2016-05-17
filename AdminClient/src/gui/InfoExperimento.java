package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.*;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InfoExperimento extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public InfoExperimento(Experimento e, JFrame previous) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblExperimento = new JLabel("Experimento:");
		lblExperimento.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblExperimento.setBounds(25, 11, 131, 26);
		contentPane.add(lblExperimento);
		
		JLabel lblId = new JLabel("ID:");
		lblId.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblId.setBounds(52, 48, 46, 14);
		contentPane.add(lblId);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNombre.setBounds(52, 73, 66, 14);
		contentPane.add(lblNombre);
		
		JLabel lblNRondas = new JLabel("N\u00BA Rondas:");
		lblNRondas.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNRondas.setBounds(52, 124, 66, 14);
		contentPane.add(lblNRondas);
		
		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTipo.setBounds(52, 98, 46, 14);
		contentPane.add(lblTipo);
		
		JLabel lblTamaoDeLos = new JLabel("Tama\u00F1o de los grupos:");
		lblTamaoDeLos.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTamaoDeLos.setBounds(52, 149, 131, 14);
		contentPane.add(lblTamaoDeLos);
		
		JLabel lblFechaDeCreacin = new JLabel("Fecha de creaci\u00F3n:");
		lblFechaDeCreacin.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFechaDeCreacin.setBounds(52, 174, 104, 14);
		contentPane.add(lblFechaDeCreacin);
		
		JButton btnAtrs = new JButton("Atr\u00E1s");
		btnAtrs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previous.setVisible(true);
				dispose();
			}
		});
		btnAtrs.setBounds(62, 227, 89, 23);
		contentPane.add(btnAtrs);
		
		JButton btnGenerarInforme = new JButton("Generar informe");
		btnGenerarInforme.setBounds(296, 227, 111, 23);
		contentPane.add(btnGenerarInforme);
		
		JLabel lblId_1 = new JLabel(String.valueOf(e.getId()));
		lblId_1.setBounds(75, 48, 46, 14);
		contentPane.add(lblId_1);
		
		JLabel lblNombre_1 = new JLabel(e.getNombre());
		lblNombre_1.setBounds(110, 73, 246, 14);
		contentPane.add(lblNombre_1);
		
		JLabel lblTipo_1 = new JLabel(e.getTipo().getTipo());
		lblTipo_1.setBounds(88, 99, 293, 14);
		contentPane.add(lblTipo_1);
		
		JLabel lblRondas = new JLabel(String.valueOf(e.getMaxRondas()));
		lblRondas.setBounds(126, 124, 46, 14);
		contentPane.add(lblRondas);
		
		JLabel lblTamgrupos = new JLabel(String.valueOf(e.getNumGrupos()));
		lblTamgrupos.setBounds(185, 149, 46, 14);
		contentPane.add(lblTamgrupos);
		
		JLabel lblFecha = new JLabel(e.getFecha().toString());
		lblFecha.setBounds(169, 174, 62, 14);
		contentPane.add(lblFecha);
		
		JButton btnGenerarUsuarios = new JButton("Generar usuarios");
		btnGenerarUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ex) {
				//TODO: get lista usuarios
			}
		});
		btnGenerarUsuarios.setBounds(161, 227, 125, 23);
		contentPane.add(btnGenerarUsuarios);
		
		setVisible(true);
	}
}
