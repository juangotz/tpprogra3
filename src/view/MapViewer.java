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

import controller.MapController;
import model.Edge;
import model.Station;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.JTextField;
import java.awt.Font;

public class MapViewer 
{

	private JFrame frame;
	private JPanel mapPanel;
	private JPanel controlPanel;
	private JMapViewer _map;
	private MapController controller = new MapController();
	private JTextField txtTimer;
	private JButton primButton;
	private JButton kruskalButton;
	private JButton clearButton;
	private boolean arePathsDrawn;


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
		controller.loadPark();
		frame = new JFrame();
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
		
		_map = new JMapViewer();
		_map.setDisplayPosition(controller.getFocusCoordinate(), 15); // -58.7008, -34.517coordenadas de parque nacional
		_map.setScrollWrapEnabled(false);
				
		mapPanel.add(_map);
		
		kruskalButton = new JButton("Hacer AGM con Kruskal");
		kruskalButton.setBounds(0, 70, 195, 23);
		kruskalButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				if(!arePathsDrawn) {
					doMSTwithKruskal();
				} else {
					JOptionPane.showMessageDialog(_map, "Caminos ya estan dibujados. Borrar primero.", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		controlPanel.add(kruskalButton);
		
		primButton = new JButton("Hacer AGM con Prim");
		primButton.setBounds(0, 11, 195, 23);
		primButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				if(!arePathsDrawn) {
					doMSTwithPrim();
				} else {
					JOptionPane.showMessageDialog(_map, "Caminos ya estan dibujados. Borrar primero.", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		controlPanel.add(primButton);
		
		clearButton = new JButton("Borrar Mapa");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!arePathsDrawn) {
					JOptionPane.showMessageDialog(_map, "No existe camino para borrar.", "ERROR", JOptionPane.ERROR_MESSAGE);
				} else
				clearMap();
			}
		});
		clearButton.setBounds(0, 130, 195, 23);
		controlPanel.add(clearButton);
		
		txtTimer = new JTextField();
		txtTimer.setFont(new Font("Tahoma", Font.BOLD, 9));
		txtTimer.setText("Tiempo Total Algoritmo: x sec");
		txtTimer.setBounds(10, 233, 185, 42);
		controlPanel.add(txtTimer);
		txtTimer.setColumns(10);
		txtTimer.setVisible(false);
		
		//Hacemos Kruskal y Prim de antemano por temas del compilador de Java.
		doMSTwithKruskal();
		clearMap();
		doMSTwithPrim();
		clearMap();
		
	}
	
	private void doMSTwithKruskal() {
		double time = controller.getKruskalTime();
		List<Edge> aux = controller.doMSTWithKruskal();
		placeStationsOnMap(aux);
		placePathsOnMap(aux);
		arePathsDrawn = true;
		placeAlgorithmTimeOnScreen(time);
	}

	private void doMSTwithPrim() {
		double time = controller.getPrimTime();
		List<Edge> aux = controller.doMSTWithPrim();
		placeStationsOnMap(aux);
		placePathsOnMap(aux);
		arePathsDrawn = true;
		placeAlgorithmTimeOnScreen(time);
	}

	private void placeStationsOnMap(List<Edge> aux) {
		ArrayList<Integer> addedStations = new ArrayList<Integer>();
		for (Edge e : aux) {
			if (!addedStations.contains(e.getFrom())) {
				drawStationInMap(e.getFrom());
				addedStations.add(e.getFrom());
			}
			if (!addedStations.contains(e.getTo())) {
				drawStationInMap(e.getTo());
				addedStations.add(e.getTo());
			}
		}
		
	}
	
	private void drawStationInMap(int index) {
		Station stationData = controller.getStationData(index);
		Coordinate stationCoordinate = getCoordinateFromStation(stationData);
		String stationName = stationData.getName();
		_map.addMapMarker(new MapMarkerDot(stationName, stationCoordinate));
	}
	
	private void placePathsOnMap(List <Edge> dataEdges) {
		for(Edge e : dataEdges) {
			Station stationXData = controller.getStationData(e.getFrom());
			Station stationYData = controller.getStationData(e.getTo());
			ArrayList<Coordinate> stationsCoordinates = getCoordinatesForPolygon(stationXData, stationYData);
			
			MapPolygonImpl polygon = new MapPolygonImpl(stationsCoordinates);
			_map.addMapPolygon(polygon);
			setAppropiatePolygonColor(polygon, e.getWeight());
		}
		
	}

	private void setAppropiatePolygonColor(MapPolygonImpl polygon, int z) {
		if (z>=0 && z<=5) {
			colorPolygon(polygon, Color.GREEN);
		}
		if (z>5 && z<=10) {
			colorPolygon(polygon, Color.YELLOW);
		}
		if (z>10 && z<=15) {
			colorPolygon(polygon, Color.ORANGE);
		}
		if (z>15) {
			colorPolygon(polygon, Color.RED);
		}
	}

	private void colorPolygon(MapPolygonImpl polygon, Color color) {
		polygon.setColor(color);
		polygon.setBackColor(color);
		polygon.setStroke(new BasicStroke(3));
	}
	
	private ArrayList<Coordinate> getCoordinatesForPolygon(Station stationAData,
			Station stationBData) {
		ArrayList<Coordinate> coordList = new ArrayList<Coordinate>();
		Coordinate stationA = getCoordinateFromStation(stationAData);
		Coordinate stationB = getCoordinateFromStation(stationBData);
		Coordinate stationAB = getMiddleCoordinate(stationA, stationB); //Necesario para dibujar la linea.
		coordList.add(stationA);
		coordList.add(stationB);
		coordList.add(stationAB);
		return coordList;
	}

	private Coordinate getMiddleCoordinate(Coordinate stationA, Coordinate stationB) {
		Double subY = stationA.getLat() - stationB.getLat();
		Double subX = stationA.getLon() - stationB.getLon();
		Double addY = stationB.getLat() + subY; //StationB.height > StationA.height
		Double addX = stationB.getLon() + subX; //StationB.height > StationA.height
		return new Coordinate (addY, addX);
	}

	private Coordinate getCoordinateFromStation(Station stationAData) {
		return new Coordinate(stationAData.getXCoordinate(),stationAData.getYCoordinate());
	}
	
	private void placeAlgorithmTimeOnScreen(Double time) {
		txtTimer.setText("Tiempo Total Algoritmo: " + time + "secs.");
		txtTimer.setVisible(true);
		
	}
	
	private void clearMap() {
		_map.removeAllMapMarkers();
		_map.removeAllMapPolygons();
		arePathsDrawn = false;
		txtTimer.setVisible(false);
	}
}
	