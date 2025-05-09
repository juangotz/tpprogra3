package model;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class StationTest {
	
	 @Test(expected = IllegalArgumentException.class)
	 public void stationNotFound() {
		 Park p = new Park("src/model/mapa.xml"); //Stations vacio.
		Station s= p.getStationData(1);
	 }
	 @Test
	 public void foundStation() {
		Park p = generateTestPark();
		Station s = p.getStationData(1);
		assertTrue(stationFieldsNotNull(s)==true);
	 }

	private Park generateTestPark() {
		ArrayList<Station> stations = new ArrayList<Station>();
		Station a = new Station (-34.521, -58.7008, 0, "A");
		Station b = new Station (-34.519, -58.7000, 1, "B");
		Station c = new Station (-34.518, -58.7021, 2, "C");
		Station d = new Station (-34.516, -58.7012, 3, "D");
		stations.add(a);
		stations.add(b);
		stations.add(c);
		stations.add(d);
		return new Park(stations);
	}
	
	private boolean stationFieldsNotNull(Station s) {
		return s.getName() != null;
	}
}
