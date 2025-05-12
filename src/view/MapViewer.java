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
	private JTextField txtEnviroment;
	
	private JButton primButton;
	private JButton kruskalButton;
	private JButton restablishButton;
	private JButton loadButton;
	private JTextField fileRoute;
	
	private boolean arePathsDrawn;
	
	private AlgorithmController controller;
	private MapDataLoader loader;
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
		fileRoute = createTextField(21, 40, 167, 20, controlPanel);
		fileRoute.setText("src/FileReader/mapa1.xml");
		
		loadButton = createButton("Cargar", 215, 40, 85, 20, controlPanel);;
		
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String address = fileRoute.getText();
				if (address.isEmpty()) {
					JOptionPane.showMessageDialog(_map, "No se puede encontrar ruta", "ERROR", JOptionPane.ERROR_MESSAGE);
					return;
				}
				loader = new MapDataLoader(address);
				controller = new AlgorithmController(address);
				drawer.setController(loader);
				List<Edge> aux = loader.getEdges();
				_map.setDisplayPosition(new Coordinate(loader.getFocusCoordinateLat(),loader.getFocusCoordinateLon()), 15);
				drawer.placeStationsOnMap(aux);
				drawer.placePathsOnMap(aux);
				kruskalButton.setVisible(true);
				primButton.setVisible(true);
				restablishButton.setVisible(true);
			}
		});
		
		//Boton para hacer AGM con Kruskal
		
		kruskalButton = createButton("Opción 1",62, 115, 195, 20,controlPanel);
		kruskalButton.setVisible(false);	
		kruskalButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				if(!arePathsDrawn) {
					
					List<Edge> aux = controller.doMSTWithKruskal();
					double time = controller.getKruskalTime();
					int average = loader.getEnviromentalDamage(aux);
					
					_map.removeAllMapPolygons();
					drawer.placePathsOnMap(aux);
					
					arePathsDrawn = true;
					placeAlgorithmTimeOnScreen(time);
					placeEnviromentalDamageOnScreen(average);
					
				} else {
					JOptionPane.showMessageDialog(_map, "Caminos ya estan dibujados. Borrar primero.", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		//Boton para hacer AGM con Prim
		
		primButton = createButton("Opción 2",62, 145, 195, 20,controlPanel);
		primButton.setVisible(false);
		primButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				if(!arePathsDrawn) {
					
					List<Edge> aux = controller.doMSTWithPrim();
					double time = controller.getPrimTime();
					int average = loader.getEnviromentalDamage(aux);
					
					_map.removeAllMapPolygons();
					drawer.placePathsOnMap(aux);
					
					arePathsDrawn = true;
					placeAlgorithmTimeOnScreen(time);
					placeEnviromentalDamageOnScreen(average);
					
				} else {
					JOptionPane.showMessageDialog(_map, "Caminos ya estan dibujados. Borrar primero.", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		//Boton para Borrar mapa.
		restablishButton = createButton("Restablecer Mapa",62, 175, 195, 20,controlPanel);
		restablishButton.setVisible(false);
		restablishButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!arePathsDrawn) {
					JOptionPane.showMessageDialog(_map, "No existe camino para borrar.", "ERROR", JOptionPane.ERROR_MESSAGE);
				} else {
					drawer.restablishOriginalPaths();
					txtTimer.setVisible(false);
					txtEnviroment.setVisible(false);
					arePathsDrawn = false;
				}
			}
		});
		
		// Etiqueta de texto del tiempo
		txtTimer = createTextField(62, 260, 195, 40,controlPanel);
		txtTimer.setEditable(false);
		txtTimer.setFont(new Font("Tahoma", Font.BOLD, 9));
		txtTimer.setText("Tiempo Total Algoritmo: x sec");
		txtTimer.setVisible(false);
		
		JLabel lblGenerateTrails = createLabel("Generar Senderos con \r\nminimo impacto ambiental:",20, 85, 290, 20,new Font("Tahoma", Font.BOLD, 10),controlPanel);	
		JLabel lblAddPark = createLabel("Agregar parque mediante archivo:",20, 10, 250, 20,new Font("Tahoma", Font.BOLD, 10),controlPanel);

		
		txtEnviroment = createTextField(62, 210, 195, 40,controlPanel);
		txtEnviroment.setEditable(false);
		txtEnviroment.setFont(new Font("Tahoma", Font.BOLD, 9));
		txtEnviroment.setText("Impacto Ambiental: Y");
		txtEnviroment.setVisible(false);
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
	
	private void placeEnviromentalDamageOnScreen(int average) {
		txtEnviroment.setText("Impacto Ambiental: " + average);
		txtEnviroment.setVisible(true);
		
	}
}
	