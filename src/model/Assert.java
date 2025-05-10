package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

public class Assert
{
	// Verifica que sean iguales como conjuntos
	public static void equalArray(int[] expected, Set<Integer> obtained)
	{
		assertEquals(expected.length, obtained.size());
		
		for(int i=0; i<expected.length; ++i)
			assertTrue( obtained.contains(expected[i]) );
	}

	public static void equalArray(int[] expected, List<Edge> obtained)
	{
		assertEquals(expected.length, obtained.size());
		
		int i = 0;
		
		for (Edge edge : obtained) {
			assertEquals(edge.getWeight(), expected[i]);
			i++;
		}
	}
}