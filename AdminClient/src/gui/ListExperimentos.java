package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JList;

import model.*;

import javax.swing.JButton;

public class ListExperimentos extends JFrame {

	private JFrame actual = this;
	private JPanel contentPane;
	
	private List<Experimento> experimentos;

	/**
	 * Create the frame.
	 */
	public ListExperimentos(List<Experimento> lista, JFrame previous) {
		experimentos = lista;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblListaDeLos = new JLabel("Lista de los experimentos creados actualmente:");
		lblListaDeLos.setBounds(5, 5, 424, 20);
		lblListaDeLos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		contentPane.add(lblListaDeLos);
		
		JScrollPane scrollPane = new JScrollPane();
		JList list = new JList(experimentos.toArray());
		scrollPane.setBounds(5, 25, 424, 208);
		scrollPane.setViewportView(list);
		contentPane.add(scrollPane);
		
		JButton btnAtrs = new JButton("Atr\u00E1s");
		btnAtrs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				previous.setVisible(true);
				dispose();
			}
		});
		btnAtrs.setBounds(5, 233, 81, 23);
		contentPane.add(btnAtrs);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Experimento selectedItem = (Experimento) list.getSelectedValue();
				new InfoExperimento(selectedItem,actual);
				setVisible(false);
			}
		});
		
		setVisible(true);
	}

}
