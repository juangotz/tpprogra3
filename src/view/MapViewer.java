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
import model.Triplet;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;

public class MapViewer 
{

	private JFrame frame;
	private JPanel mapPanel;
	private JPanel controlPanel;
	private JMapViewer _map;
	private MapController controller = new MapController();

	private JButton kruskalButton;


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
		controller.loadTestPark();
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
		_map.setDisplayPosition(new Coordinate(-58.7008, -34.521), 15);
		_map.setScrollWrapEnabled(false);
				
		mapPanel.add(_map);
		
		kruskalButton = new JButton("Hacer AGM con Kruskal");
		kruskalButton.setBounds(10, 11, 195, 23);
		kruskalButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				doMSTwithKruskal();
			}
		});
		controlPanel.add(kruskalButton);
		
	}
	
	private void doMSTwithKruskal() {
		ArrayList<Triplet<Integer, Integer, Integer>> aux = controller.doMSTWithKruskal();
		placeStationsOnMap(aux);
		placePathsOnMap(aux);
		
	}

	private void placeStationsOnMap(ArrayList<Triplet<Integer, Integer, Integer>> dataTriplet) {
		ArrayList<Integer> addedStations = new ArrayList<Integer>();
		for (Triplet<Integer, Integer, Integer> t : dataTriplet) {
			if (!addedStations.contains(t.getX())) {
				drawStationInMap(t.getX());
				addedStations.add(t.getX());
			}
			if (!addedStations.contains(t.getY())) {
				drawStationInMap(t.getY());
				addedStations.add(t.getY());
			}
		}
		
	}
	
	private void placePathsOnMap(ArrayList<Triplet<Integer, Integer, Integer>> dataTriplet) {
		for(Triplet<Integer, Integer, Integer> t : dataTriplet) {
			Triplet<Double, Double, String> stationXData = controller.getStationData(t.getX());
			Triplet<Double, Double, String> stationYData = controller.getStationData(t.getY());
			ArrayList<Coordinate> stationsCoordinates = getCoordinatesForPolygon(stationXData, stationYData);
			
			MapPolygonImpl polygon = new MapPolygonImpl(stationsCoordinates);
			_map.addMapPolygon(polygon);
			setAppropiatePolygonColor(polygon, t.getZ());
			System.out.println();
		}
		
	}
	
	private void drawStationInMap(int index) {
		Triplet<Double, Double, String> stationData = controller.getStationData(index);
		Coordinate stationCoordinate = getCoordinateFromTriplet(stationData);
		String stationName = stationData.getZ();
		_map.addMapMarker(new MapMarkerDot(stationName, stationCoordinate));
	}

	private void setAppropiatePolygonColor(MapPolygonImpl polygon, int z) {
		if (z>=0 && z<=5) {
			polygon.setColor(Color.GREEN);
			polygon.setBackColor(Color.GREEN);
			polygon.setStroke(new BasicStroke(3));
		}
		if (z>5 && z<=10) {
			polygon.setColor(Color.YELLOW);
			polygon.setBackColor(Color.YELLOW);
			polygon.setStroke(new BasicStroke(3));
		}
		if (z>10 && z<=15) {
			polygon.setColor(Color.ORANGE);
			polygon.setBackColor(Color.ORANGE);
			polygon.setStroke(new BasicStroke(3));
		}
		if (z>15) {
			polygon.setColor(Color.RED);
			polygon.setBackColor(Color.RED);
			polygon.setStroke(new BasicStroke(3));
		}
	}
	
	private ArrayList<Coordinate> getCoordinatesForPolygon(Triplet<Double, Double, String> stationAData,
			Triplet<Double, Double, String> stationBData) {
		ArrayList<Coordinate> coordList = new ArrayList<Coordinate>();
		Coordinate stationA = getCoordinateFromTriplet(stationAData);
		Coordinate stationB = getCoordinateFromTriplet(stationBData);
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

	private Coordinate getCoordinateFromTriplet(Triplet<Double, Double, String> stationAData) {
		return new Coordinate(stationAData.getY(),stationAData.getX());
	}

	
}
	