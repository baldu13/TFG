package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.*;
import utilities.ServerConnection;

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
	private JTextField textFieldNombre;
	private JTextField textFieldUsuarios;
	private JTextField textFieldRondas;
	private JTextField textFieldGrupos;
	
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
		
		textFieldNombre = new JTextField();
		textFieldNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldNombre.setBounds(221, 49, 177, 20);
		contentPane.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		textFieldUsuarios = new JTextField();
		textFieldUsuarios.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldUsuarios.setBounds(221, 82, 31, 20);
		contentPane.add(textFieldUsuarios);
		textFieldUsuarios.setColumns(10);
		
		textFieldRondas = new JTextField();
		textFieldRondas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldRondas.setBounds(221, 117, 31, 20);
		contentPane.add(textFieldRondas);
		textFieldRondas.setColumns(10);
		
		textFieldGrupos = new JTextField();
		textFieldGrupos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldGrupos.setBounds(221, 152, 31, 20);
		contentPane.add(textFieldGrupos);
		textFieldGrupos.setColumns(10);
		
		JComboBox<String> comboBoxTipo = new JComboBox<String>();
		comboBoxTipo.setBounds(221, 182, 187, 20);
		comboBoxTipo.addItem("Beauty Contest");
		comboBoxTipo.addItem("Fondo p�blico y privado");
		contentPane.add(comboBoxTipo);
		
		JLabel lblTitulo = new JLabel("Introduzca los datos para la creaci\u00F3n del experimento:");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTitulo.setBounds(22, 11, 389, 24);
		contentPane.add(lblTitulo);
		
		JButton btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: crear el experimento
				TipoExperimento te = new TipoExperimento();
				if("Beauty Contest".equals((String)comboBoxTipo.getSelectedItem())){
					te.setId(1);
					te.setTipo((String)comboBoxTipo.getSelectedItem());
				}
				CrearExperimentoResponseDTO response = ServerConnection.crearExperimento(textFieldNombre.getText(), te, Integer.parseInt(textFieldUsuarios.getText()), Integer.parseInt(textFieldGrupos.getText()), Integer.parseInt(textFieldRondas.getText()));
				if(response!=null){
					setVisible(false);
					new SaveUsuarios(main,response);
				}else{
					main.setVisible(true);
					setVisible(false);
					new MensajeUsuario("Se produjo un error al intentar crear el experimento.\nPor favor, revisa que la informaci�n es correcta.");
				}
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
	}
}
