package model;

public class Station {
	private double xCoordinate;
	private double yCoordinate;
	private int nodeIndex;
	private String name;
	
	public Station (double xCoord, double yCoord, int nodeIndex, String name) {
		this.xCoordinate = xCoord;
		this.yCoordinate = yCoord;
		this.nodeIndex = nodeIndex;
		this.name = name;
	}
	
	public double getXCoordinate() {
		return xCoordinate;
	}
	public double getYCoordinate() {
		return yCoordinate;
	}
	public int getNodeIndex() {
		return nodeIndex;
	}
	public String getName() {
		return name;
	}
}
