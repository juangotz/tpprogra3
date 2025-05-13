package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openstreetmap.gui.jmapviewer.Coordinate;

public class ParkTest {
	
//	@Test(expected = IllegalArgumentException.class)
//	 public void createParkWithNoStations() {
//		 Park p = new Park(new ArrayList<Station>(), new ArrayList<Edge>());
//		
//	 }
//	
//	 @Test (expected = IllegalArgumentException.class)
//	 public void createParkWithNoEdges() {
//		 Park p = generateNoEdgesPark();
//	 }
//	
	
	@Test(expected = IllegalArgumentException.class)
	 public void testStationNotFound() {
		 Park p = generateTemplatePark();
		Station s= p.getStationData(5);
	 }
	
	 @Test
	 public void testFoundStation() {
		Park p = generateTemplatePark();
		Station s = p.getStationData(1);
		assertTrue(s!=null);
	 }
	 
	 @Test
	 public void testAvailableCoordinates() {
		 Park p = generateTemplatePark();
		 Coordinate coord = new Coordinate(0.0,0.0);
		 assertTrue(p.availableCoordinate(coord));
	 }
	 
	 @Test
	 public void testNotAvailableCoordinates() {
		 Park p = generateTemplatePark();
		 Coordinate coord = new Coordinate(-34.521,-58.7008);
		 assertFalse(p.availableCoordinate(coord));
	 }
	 
//	 @Test
//	 public void successfulKruskal() {
//		 Park p = generateTemplatePark();
//		 List<Edge> mst = p.doMSTWithKruskal();
//		 List<Edge> expected = new ArrayList<>();
//		 expected.add(new Edge(1,2,3));
//		 expected.add(new Edge(2,3,4));
//		 expected.add(new Edge(0,1,10));
//		 assertTrue(compareLists(mst, expected));
//	 }
//	 
//	 @Test
//	 public void successfulPrim() {
//		 Park p = generateTemplatePark();
//		 List<Edge> mst = p.doMSTWithPrim();
//		 List<Edge> expected = new ArrayList<>();
//		 expected.add(new Edge(0,1,10));
//		 expected.add(new Edge(1,2,3));
//		 expected.add(new Edge(2,3,4));
//		 assertTrue(compareLists(mst, expected));
//	 }

	 
	private Park generateTemplatePark() {
		ArrayList<Station> stations = new ArrayList<Station>();
		ArrayList<Edge> edges = new ArrayList<Edge>();
		Station a = new Station (-34.521, -58.7008, 0, "A");
		Station b = new Station (-34.519, -58.7000, 1, "B");
		Station c = new Station (-34.518, -58.7021, 2, "C");
		Station d = new Station (-34.516, -58.7012, 3, "D");
		Edge ab = new Edge(0, 1, 10);
		Edge cd = new Edge(2, 3, 4);
		Edge bc = new Edge(1, 2, 3);
		Edge ad = new Edge(0, 3, 19);
		edges.add(ab);
		edges.add(bc);
		edges.add(ad);
		edges.add(cd);
		stations.add(a);
		stations.add(b);
		stations.add(c);
		stations.add(d);
		return new Park(stations, edges);
	}
	
	private Park generateNoEdgesPark() {
		ArrayList<Station> stations = new ArrayList<Station>();
		ArrayList<Edge> edges = new ArrayList<Edge>();
		Station a = new Station (-34.521, -58.7008, 0, "A");
		Station b = new Station (-34.519, -58.7000, 1, "B");
		Station c = new Station (-34.518, -58.7021, 2, "C");
		Station d = new Station (-34.516, -58.7012, 3, "D");
		stations.add(a);
		stations.add(b);
		stations.add(c);
		stations.add(d);
		return new Park(stations, edges);
	}
	
	private boolean compareLists(List<Edge> mst, List<Edge> expected) {
		if (expected.size()!=mst.size())
			return false;
		for (int index = 0; index < mst.size();index++) {
			
			if(!equalInts(mst.get(index).getFrom(),expected.get(index).getFrom())
					|| !equalInts(mst.get(index).getTo(),expected.get(index).getTo())
					|| !equalInts(mst.get(index).getWeight(),expected.get(index).getWeight())) {
				return false;
			}
		}
		return true;
	}
	private boolean equalInts(int a, int b) {
		return a==b;
	}
}
