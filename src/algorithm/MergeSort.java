package algorithm;

import java.util.ArrayList;
import java.util.List;

import model.Edge;

public class MergeSort {

	public static void sort(List<Edge> list, int left, int right) {
		if(left < right) {
			
			int mid = (left + right)/2;
			sort(list, left, mid);
			sort(list, mid + 1, right);
			merge(list, left, mid, right);
		}
	}

	private static void merge(List<Edge> list, int left, int mid, int right) {

		//Creamos dos sublistas de la lista original
		 List<Edge> leftList = new ArrayList<>(list.subList(left, mid + 1));
	     List<Edge> rightList = new ArrayList<>(list.subList(mid + 1, right + 1));
	     
	     int i = 0;
	     int j = 0;
	     int k = left;
	     
	     //añadimos a la lista las aristas de menor a mayor comparando los pesos
	     while(i < leftList.size() && j < rightList.size()) {
	    	 if (leftList.get(i).getWeight() <= rightList.get(j).getWeight()) {
	    		 list.set(k, leftList.get(i));
	    		 k++;
	    		 i++;
	    	 }
	    	 else {
	    		 list.set(k, rightList.get(j));
	    		 k++;
	    		 j++;
	    	 }
	    }
	     
	     //añadimos a la lista las aristas restantes
	     while (i < leftList.size()) {
	    	 list.set(k, leftList.get(i));
    		 k++;
    		 i++;
	     }

	     while (j < rightList.size()) {
	    	 list.set(k, rightList.get(j));
    		 k++;
    		 j++;
	     }  
	}
}
