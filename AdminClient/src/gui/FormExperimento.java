package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FormExperimento extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	
	private JFrame main;

	/**
	 * Create the frame.
	 */
	public FormExperimento(JFrame main) {
		this.main = main;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombreDelExperimento = new JLabel("Nombre del experimento:");
		lblNombreDelExperimento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombreDelExperimento.setBounds(46, 46, 177, 22);
		contentPane.add(lblNombreDelExperimento);
		
		JLabel lblNmeroDeParticipantes = new JLabel("N\u00FAmero de participantes:");
		lblNmeroDeParticipantes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNmeroDeParticipantes.setBounds(46, 79, 177, 22);
		contentPane.add(lblNmeroDeParticipantes);
		
		JLabel lblNmeroDeRondas = new JLabel("N\u00FAmero de rondas:");
		lblNmeroDeRondas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNmeroDeRondas.setBounds(46, 114, 177, 22);
		contentPane.add(lblNmeroDeRondas);
		
		JLabel lblTamaoDeLos = new JLabel("Tama\u00F1o de los grupos:");
		lblTamaoDeLos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTamaoDeLos.setBounds(46, 149, 177, 22);
		contentPane.add(lblTamaoDeLos);
		
		JLabel lblTipoDeExperimento = new JLabel("Tipo de experimento:");
		lblTipoDeExperimento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTipoDeExperimento.setBounds(46, 182, 177, 22);
		contentPane.add(lblTipoDeExperimento);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField.setBounds(221, 49, 177, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_1.setBounds(221, 82, 31, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_2.setBounds(221, 117, 31, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_3.setBounds(221, 152, 31, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: crear el experimento
				main.setVisible(true);
				setVisible(false);
				new MensajeUsuario("Experimento creado correctamente");
			}
		});
		btnCrear.setBounds(322, 227, 89, 23);
		contentPane.add(btnCrear);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Volver al main
				main.setVisible(true);
				setVisible(false);
			}
		});
		btnCancelar.setBounds(221, 227, 89, 23);
		contentPane.add(btnCancelar);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(221, 182, 187, 20);
		comboBox.addItem("Beauty Contest");
		comboBox.addItem("Fondo público y privado");
		contentPane.add(comboBox);
		
		JLabel lblTitulo = new JLabel("Introduzca los datos para la creaci\u00F3n del experimento:");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTitulo.setBounds(22, 11, 389, 14);
		contentPane.add(lblTitulo);
	}
}
