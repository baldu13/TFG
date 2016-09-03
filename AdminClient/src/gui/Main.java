package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utilities.ServerConnection;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

	private JFrame actual = this;
	private JPanel contentPane;
	
	private static Main frame;
	private static FormExperimento formularioExperimento;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					frame = new Main();
					formularioExperimento = new FormExperimento(frame);
					frame.setVisible(true);
				} catch (Exception e) {}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblBienvenidoALa = new JLabel("Bienvenido a la aplicaci\u00F3n de gesti\u00F3n");
		lblBienvenidoALa.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel.add(lblBienvenidoALa);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnCrearNuevoExperimento = new JButton("Crear Nuevo Experimento");
		btnCrearNuevoExperimento.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnCrearNuevoExperimento.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Se muestra el formulario para crear un experimento
				formularioExperimento.setVisible(true);
				frame.setVisible(false);
			}
		});
		panel_1.add(btnCrearNuevoExperimento);
		
		JButton btnVerExperimentos = new JButton("Ver Experimentos");
		btnVerExperimentos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new ListExperimentos(ServerConnection.getExperimentos(),actual);
				setVisible(false);
			}
		});
		btnVerExperimentos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		panel_1.add(btnVerExperimentos);
	}
}
