import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;



public class Dijkstra {
	Comparator<Vertex> comp = new Comparator<Vertex>(){

		@Override
		public int compare(Vertex v1, Vertex v2) {
			if (v1.key != v2.key) {
				return v1.key > v2.key? 1 : -1;
			}
			return 0;
		}
		
	};
	PriorityQueue<Vertex> heap = new PriorityQueue<Vertex>(comp);
	
	
	class Tuple {
		Vertex vertex;
		int edge;
		Tuple(Vertex v, int edge) {
			this.vertex = v;
			this.edge = edge;
		}
	}
	
	
	class Vertex {
		int val;
		List<Tuple> adj;
		int key;
		Vertex(int val) {
			this.val = val;
			key = 1000000;
			adj = new ArrayList<Tuple>();
		}
		@Override
	    public int hashCode(){
	        return val;
	    }

	    @Override
	    public boolean equals(Object obj){
	    	Vertex v = (Vertex) obj;
	    	return val == v.val? true : false;
	    }
	}
	
	public Map<Integer, Vertex> readLines(String filename) throws IOException {
		FileReader fileReader = new FileReader(filename);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		Map<Integer, Vertex> map = new HashMap<Integer, Vertex>();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			line = line.trim();
			String[] arr = line.split("\\s+");
			
			int val1 = Integer.parseInt(arr[0]);
			
			if (!map.containsKey(val1)) {
				map.put(val1, new Vertex(val1));
				if (val1 == 1) map.get(1).key = 0;
				heap.offer(map.get(val1));
			}
//			System.out.println(arr.length);
			for (int i = 1; i < arr.length; i++) {
				String[] t = arr[i].trim().split(",");
				int key = Integer.parseInt(t[0].trim());
				int len = Integer.parseInt(t[1].trim());
//				System.out.println(key + " " + len);
				if (!map.containsKey(key)) {
					map.put(key, new Vertex(key));
					heap.offer(map.get(key));
				}
				map.get(val1).adj.add(new Tuple(map.get(key), len));
			}
			
			
		}
		bufferedReader.close();
		
		
		
		return map;
	}
	
	public void getShortestPath(Map<Integer,Vertex> map) {
		
		
		while (heap.size() > 0) {
			Vertex v = heap.poll();
			
			if (heap.size() > 197) {
				System.out.println("v is " + v.val);
				System.out.println("key is " + v.key);
				System.out.println("size is " + heap.size());
				
				System.out.println(heap.contains(map.get(1)));
			}
			
			for (int i = 0; i < v.adj.size(); i++) {
				Tuple t = v.adj.get(i);
				
				Vertex tmp = t.vertex;
				if (!heap.contains(tmp)) {
					continue;
				}
				heap.remove(tmp);
				System.out.println("before offer " + heap.contains(tmp) + tmp.key);
				tmp.key = Math.min(tmp.key, v.key + t.edge);
				if (v.val == 1) System.out.println(v.val + " adj is " + tmp.val + "==>" + tmp.key);
				heap.offer(tmp);
				System.out.println("after offer " + heap.contains(tmp) + tmp.key);
			}
//			if (heap.size() == 198) break;
		}
	}
	
//	7,37,59,82,99,115,133,165,188,197
	public static void main(String[] args) throws IOException {
		Dijkstra d = new Dijkstra();
		Map<Integer,Vertex> map = d.readLines("C:\\Users\\yamgao.ORADEV\\Google Drive\\algorithm\\HW\\HW5\\dijkstraData.txt");
		d.getShortestPath(map);
		System.out.println(map.get(7).key);
		System.out.println(map.get(37).key);
		System.out.println(map.get(59).key);
		System.out.println(map.get(82).key);
		System.out.println(map.get(99).key);
		System.out.println(map.get(115).key);
		System.out.println(map.get(133).key);
		System.out.println(map.get(165).key);
		System.out.println(map.get(188).key);
		System.out.println(map.get(197).key);
	}

}
