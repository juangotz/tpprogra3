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
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;

import controller.AlgorithmController;
import controller.MapDataLoader;
import model.Edge;
import model.Park;
import model.Station;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

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
	private JButton LoadButton;
	private JComboBox comboBox;
	
	private boolean arePathsDrawn;
	
	private AlgorithmController controller;
	private MapDrawer drawer;

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
		
		
		frame = new JFrame("Generador de senderos con mínimo impacto ambiental");
		frame.setBounds(100, 100, 800, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		mapPanel = new JPanel();
		mapPanel.setBounds(10, 10, 430, 445);
		frame.getContentPane().add(mapPanel);
		
		controlPanel = new JPanel();
		controlPanel.setBounds(456, 10, 320, 445);
		frame.getContentPane().add(controlPanel);		
		controlPanel.setLayout(null);
		
		
		//Mapa y su foco.
		_map = new JMapViewer();
		_map.setDisplayPosition(new Coordinate(-38.416097, -63.616672), 4);
		mapPanel.add(_map);
		drawer = new MapDrawer(_map);
		
		//Carga el archivo y el grafo original
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Seleccione archivo", "Mapa 1", "Mapa 2"}));
		comboBox.setToolTipText("");
		comboBox.setBounds(21, 220, 167, 20);
		controlPanel.add(comboBox);
		
		LoadButton = createButton("Cargar", 215, 220, 85, 20, controlPanel);
		
		LoadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String adress = comboBox.getSelectedItem().toString(); // solo se lee cuando se hace clic
				if (adress.equals("Seleccione archivo")) {
					JOptionPane.showMessageDialog(_map, "Seleccione una opcion", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				MapDataLoader loader = new MapDataLoader();
				Park park = loader.loadPark(adress);
				drawer.setController(loader);
				List<Edge> aux = loader.getEdges();
				_map.setDisplayPosition(new Coordinate(loader.getFocusCoordinateLat(),loader.getFocusCoordinateLon()), 15);
				drawer.placeStationsOnMap(aux);
				drawer.placePathsOnMap(aux);
			}
		});
		
		//Boton para hacer AGM con Kruskal
		
		kruskalButton = createButton("Opción 1",62, 285, 195, 20,controlPanel);
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
		
		primButton = createButton("Opción 2",62, 315, 195, 20,controlPanel);
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
		clearButton = createButton("Borrar Mapa",62, 345, 195, 20,controlPanel);
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
		txtTimer = createTextField(62, 375, 195, 40,controlPanel);
		txtTimer.setEditable(false);
		txtTimer.setFont(new Font("Tahoma", Font.BOLD, 9));
		txtTimer.setText("Tiempo Total Algoritmo: x sec");
		
		JLabel lblGenerateTrails = createLabel("Generar Senderos con \r\nminimo impacto ambiental:",20, 255, 290, 20,new Font("Tahoma", Font.BOLD, 10),controlPanel);	
		JLabel lblAddPark = createLabel("Agregar parque mediante archivo:",20, 190, 250, 20,new Font("Tahoma", Font.BOLD, 10),controlPanel);

		txtTimer.setVisible(false);
		
		/*
		//Hacemos Kruskal y Prim de antemano por temas del compilador de Java.
		doMSTwithKruskal();
		clearMap();
		doMSTwithPrim();
		clearMap();
		*/
	}
	private JLabel createLabel(String text, int x, int y, int width, int height, Font font, JPanel panel) {
	    JLabel label = new JLabel(text);
	    label.setFont(font);
	    label.setBounds(x, y, width, height);
	    panel.add(label);
	    return label;
	}
	
	private JButton createButton(String text, int x, int y, int width, int height, JPanel panel) {
	    JButton button = new JButton(text);
	    button.setBounds(x, y, width, height);
	    panel.add(button);
	    return button;
	}
	
	private JTextField createTextField(int x, int y, int width, int height, JPanel panel) {
	    JTextField textField = new JTextField();
	    textField.setBounds(x, y, width, height);
	    textField.setColumns(10);
	    panel.add(textField);
	    return textField;
	}
	private void placeAlgorithmTimeOnScreen(Double time) {
		txtTimer.setText("Tiempo Total Algoritmo: " + time + "secs.");
		txtTimer.setVisible(true);
		
	}
}
	