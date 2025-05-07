package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

public class Assert
{
	// Verifica que sean iguales como conjuntos
	public static void equalArray(int[] esperado, Set<Integer> obtenido)
	{
		assertEquals(esperado.length, obtenido.size());
		
		for(int i=0; i<esperado.length; ++i)
			assertTrue( obtenido.contains(esperado[i]) );
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