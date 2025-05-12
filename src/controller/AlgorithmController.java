package controller;

import java.util.List;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;

import algorithm.AlgorithmTimer;
import model.Edge;
import model.Park;
import model.Station;

public class AlgorithmController {

	private AlgorithmTimer timer = new AlgorithmTimer();
	private Park park;
	
	public AlgorithmController(Park p) {
		this.park = p;
	}

	public List<Edge> doMSTWithKruskal() {
	        return park.doMSTWithKruskal();
	    }
	
    public List<Edge> doMSTWithPrim() {
        return park.doMSTWithPrim();
    }
	
	public double getKruskalTime() {
		return timer.getKruskalTime(park.getGraph());
	}
	
	public double getPrimTime() {
		return timer.getPrimTime(park.getGraph());
	}

	public void addStationCoordinates(Coordinate coord) {
	    // Si necesitas convertir a Coordinate, lo haces aquí
	    double latitude = coord.getLat();
	    double longitude = coord.getLon();
	    int cont=1;
	    // Usar las coordenadas
	    Station station = new Station(latitude, longitude,cont,"stacion");
	    // Guardar estación en el modelo o hacer cualquier lógica adicional
	    cont++;
	}
}
