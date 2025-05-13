package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import model.Edge;
import model.Park;
import model.Station;

public class MapDrawer {
	
	private JMapViewer _map;

	public MapDrawer(JMapViewer map) {
		this._map = map;
	}

	 public void drawPark(Park park) {
	        clearMap();
	        drawStations(park.getStations());
	        drawPaths(park);
	    }

	    private void drawStations(HashMap<Integer, Station> stations) {
	        for (Station s : stations.values()) {
	            Coordinate coord = new Coordinate(s.getXCoordinate(), s.getYCoordinate());
	            String label = (s.getNodeIndex()+1) + ". " + s.getName();
	            _map.addMapMarker(new MapMarkerDot(label, coord));
	        }
	        System.out.println("cantidad de estaciones: " + stations.size());
	    }

	    private void drawPaths( Park park) {
	    	List<Edge> edges = park.getGraph().getAllEdges();
	    	if(edges.size() != 0) {
	    		for (Edge e : edges) {
		            Station from = park.getStationData(e.getFrom());
		            Station to = park.getStationData(e.getTo());
		            ArrayList<Coordinate> coords = new ArrayList<>();
		            coords.add(new Coordinate(from.getXCoordinate(), from.getYCoordinate()));
		            coords.add(new Coordinate(to.getXCoordinate(), to.getYCoordinate()));
		            coords.add(getMiddleCoordinate(coords.get(0), coords.get(1)));

		            MapPolygonImpl polygon = new MapPolygonImpl(coords);
		            _map.addMapPolygon(polygon);
		            setAppropiatePolygonColor(polygon, e.getWeight());
		        }
	    	}
	    	System.out.println("cantidad de senderos: " + edges.size());
	    }

	protected void setAppropiatePolygonColor(MapPolygonImpl polygon, int z) {
		if (z>=1 && z<=3) {
			colorPolygon(polygon, Color.GREEN);
		}
		if (z>3 && z<=5) {
			colorPolygon(polygon, Color.YELLOW);
		}
		if (z>5 && z<=8) {
			colorPolygon(polygon, Color.ORANGE);
		}
		if (z>8) {
			colorPolygon(polygon, Color.RED);
		}
	}

	protected void colorPolygon(MapPolygonImpl polygon, Color color) {
		polygon.setColor(color);
		polygon.setBackColor(color);
		polygon.setStroke(new BasicStroke(3));
	}
	
	protected ArrayList<Coordinate> getCoordinatesForPolygon(Station stationAData,
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

	protected Coordinate getMiddleCoordinate(Coordinate stationA, Coordinate stationB) {
		Double subY = stationA.getLat() - stationB.getLat();
		Double subX = stationA.getLon() - stationB.getLon();
		Double addY = stationB.getLat() + subY; //StationB.height > StationA.height
		Double addX = stationB.getLon() + subX; //StationB.height > StationA.height
		return new Coordinate (addY, addX);
	}

	protected Coordinate getCoordinateFromStation(Station stationAData) {
		return new Coordinate(stationAData.getXCoordinate(),stationAData.getYCoordinate());
	}
	
	protected void clearMap() {
		_map.removeAllMapMarkers();
		_map.removeAllMapPolygons();
	}
	
}
