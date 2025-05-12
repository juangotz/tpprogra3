package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import controller.MapDataLoader;
import model.Edge;
import model.Station;

public class MapDrawer {
	
	private MapDataLoader controller; 
	private JMapViewer _map;

	public MapDrawer(JMapViewer map) {
		this._map = map;
		this.controller = new MapDataLoader();
	}
	
	protected void placeStationsOnMap(List<Edge> aux) {
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
	
	protected void drawStationInMap(int index) {
		Station stationData = controller.getStationData(index);
		Coordinate stationCoordinate = getCoordinateFromStation(stationData);
		String stationName = stationData.getName();
		_map.addMapMarker(new MapMarkerDot(stationName, stationCoordinate));
	}
	
	protected void placePathsOnMap(List <Edge> dataEdges) {
		for(Edge e : dataEdges) {
			Station stationXData = controller.getStationData(e.getFrom());
			Station stationYData = controller.getStationData(e.getTo());
			ArrayList<Coordinate> stationsCoordinates = getCoordinatesForPolygon(stationXData, stationYData);
			
			MapPolygonImpl polygon = new MapPolygonImpl(stationsCoordinates);
			_map.addMapPolygon(polygon);
			setAppropiatePolygonColor(polygon, e.getWeight());
		}
		
	}

	protected void setAppropiatePolygonColor(MapPolygonImpl polygon, int z) {
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
