package view;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import controller.AlgorithmController;
import controller.MapDataLoader;
import model.Edge;
import model.Station;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MapViewer 
{

	private JFrame frame;
	private JPanel mapPanel;
	private JPanel controlPanel;
	private JMapViewer _map;
	private JTextField txtTimer;
	private JButton primButton;
	private JButton kruskalButton;
	private JButton clearButton;
	private boolean arePathsDrawn;
	
	private AlgorithmController controller;
	private MapDrawer drawer;
	private JTextField textField;
	private JTextField textField_1;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() {
				try {
					MapViewer window = new MapViewer();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MapViewer() 
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		//controller.loadPark();
		
		frame = new JFrame("Generador de senderos con mínimo impacto ambiental");
		frame.setBounds(100, 100, 725, 506);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		mapPanel = new JPanel();
		mapPanel.setBounds(10, 11, 437, 446);
		frame.getContentPane().add(mapPanel);
		
		controlPanel = new JPanel();
		controlPanel.setBounds(457, 11, 242, 446);
		frame.getContentPane().add(controlPanel);		
		controlPanel.setLayout(null);
		
		
		//Mapa y su foco.
		_map = new JMapViewer();
		_map.setDisplayPosition(new Coordinate(-40.83333333,-71.61666667), 15);
		mapPanel.add(_map);
		drawer = new MapDrawer(_map);
		
		//Boton para hacer AGM con Kruskal
		
		kruskalButton = new JButton("Opción 1");
		kruskalButton.setBounds(20, 273, 195, 23);
		controlPanel.add(kruskalButton);
		kruskalButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				if(!arePathsDrawn) {
					
					List<Edge> aux = controller.doMSTWithKruskal();
					double time = controller.getKruskalTime();
					
					drawer.placeStationsOnMap(aux);
					drawer.placePathsOnMap(aux);
					
					arePathsDrawn = true;
					placeAlgorithmTimeOnScreen(time);
					
				} else {
					JOptionPane.showMessageDialog(_map, "Caminos ya estan dibujados. Borrar primero.", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		//Boton para hacer AGM con Prim
		
		primButton = new JButton("Opción 2");
		primButton.setBounds(20, 307, 195, 23);
		controlPanel.add(primButton);
		primButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				if(!arePathsDrawn) {
					
					List<Edge> aux = controller.doMSTWithPrim();
					double time = controller.getPrimTime();
					
					drawer.placeStationsOnMap(aux);
					drawer.placePathsOnMap(aux);
					
					arePathsDrawn = true;
					placeAlgorithmTimeOnScreen(time);
					
				} else {
					JOptionPane.showMessageDialog(_map, "Caminos ya estan dibujados. Borrar primero.", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		//Boton para Borrar mapa.
		
		clearButton = new JButton("Borrar Mapa");
		clearButton.setBounds(20, 341, 195, 23);
		controlPanel.add(clearButton);
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!arePathsDrawn) {
					JOptionPane.showMessageDialog(_map, "No existe camino para borrar.", "ERROR", JOptionPane.ERROR_MESSAGE);
				} else {
					drawer.clearMap();
					arePathsDrawn = false;
					txtTimer.setVisible(false);
				}
			}
		});
		
		// Etiqueta de texto del tiempo
		txtTimer = new JTextField();
		txtTimer.setEditable(false);
		txtTimer.setFont(new Font("Tahoma", Font.BOLD, 9));
		txtTimer.setText("Tiempo Total Algoritmo: x sec");
		txtTimer.setBounds(20, 375, 185, 42);
		controlPanel.add(txtTimer);
		txtTimer.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Generar Senderos con \r\nminimo impacto ambiental:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(20, 239, 195, 23);
		controlPanel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(20, 29, 67, 20);
		controlPanel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(131, 29, 86, 20);
		controlPanel.add(textField_1);
		textField_1.setColumns(10);
		txtTimer.setVisible(false);
		
		/*
		//Hacemos Kruskal y Prim de antemano por temas del compilador de Java.
		doMSTwithKruskal();
		clearMap();
		doMSTwithPrim();
		clearMap();
		*/
	}
	

	
	private void placeAlgorithmTimeOnScreen(Double time) {
		txtTimer.setText("Tiempo Total Algoritmo: " + time + "secs.");
		txtTimer.setVisible(true);
		
	}
}
	