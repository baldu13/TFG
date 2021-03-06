package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MensajeUsuario extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public MensajeUsuario(String mensaje) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 468, 134);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Cerrar el mensaje
				dispose();
			}
		});
		btnOk.setBounds(353, 61, 89, 23);
		contentPane.add(btnOk);
		
		JLabel lblNewLabel = new JLabel(mensaje);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 11, 398, 50);
		contentPane.add(lblNewLabel);
		setVisible(true);
	}

}
