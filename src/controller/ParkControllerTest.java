package controller;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openstreetmap.gui.jmapviewer.Coordinate;

import model.Park;

public class ParkControllerTest {

	@Test
	public void testExistingPark() {
		
		ParkController pc = new ParkController();
		pc.createNewPark();
		assertTrue(pc.parkExists());
	}
	
	@Test
	public void testNonExistingPark() {
		
		ParkController pc = new ParkController();
		assertFalse(pc.parkExists());
	}
	
	@Test
	public void testAddNewStation() {
		ParkController pc = new ParkController();
		pc.createNewPark();
		Coordinate coord = new Coordinate(0.0,0.0);
		pc.addNewStation("s",coord);
		assertNotEquals(pc.getPark().getStations().size(),0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddDuplicatedStation() {
		ParkController pc = new ParkController();
		pc.createNewPark();
		Coordinate coord = new Coordinate(0.0,0.0);
		pc.addNewStation("s", coord);
		pc.addNewStation("s", coord);
	}
	
	@Test(expected = RuntimeException.class)
	public void testAddSecondPark() {
		ParkController pc = new ParkController();
		pc.createNewPark();
		pc.createNewPark();
	}
	
}
