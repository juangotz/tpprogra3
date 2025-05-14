package FileReader;

import org.junit.Test;

import model.Edge;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class ReaderTest {
	
	@Test
	public void fileDoesNotExist() {
		String route = "src/FileReader/mapa5.xml";
		FileReader f = new FileReader(route);
		assertFalse(f.verifyFileExists(route));
	}
	
	@Test
	public void fileIsNotXml() {
		String route = "src/FileReader/mapa1.txt";
		FileReader f = new FileReader(route);
		assertFalse(f.verifyXmlFileType(route));
	}
	
	@Test
	public void successfullyReadFile() {
		FileReader f = new FileReader("src/FileReader/mapa1.xml");
		f.readFile();
		List<Edge> expected = new ArrayList<Edge>();
		 expected.add(new Edge(0,1,10));
		 expected.add(new Edge(0,2,6));
		 expected.add(new Edge(0,3,5));
		 expected.add(new Edge(1,3,10));
		 expected.add(new Edge(2,3,4));
		assertTrue(compareLists(f.getEdges(),expected));
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
