import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;



public class PrimMST {
	class Tuple {
		Vertex v;
		int len;
		public Tuple (Vertex v, int len) {
			this.v = v;
			this.len = len;
		}
	}
	class Vertex {
		int key;
		int val;
		List<Tuple> adj;
		public Vertex (int value) {
			key = Integer.MAX_VALUE;
			val = value;
			adj = new ArrayList<Tuple>();
		}
	}
	Comparator<Vertex> comp = new Comparator<Vertex>(){

		@Override
		public int compare(Vertex v1, Vertex v2) {
			// TODO Auto-generated method stub
			if (v1.key != v2.key) {
				return v1.key > v2.key? 1 : -1;
			}
			
			return 0;
		}
		
	};
	PriorityQueue<Vertex> heap = new PriorityQueue<Vertex>(comp);
	Map<Integer, Vertex> map = new HashMap<Integer, Vertex>();
	public void readLines(String filename) throws IOException {
		FileReader fileReader = new FileReader(filename);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
//		Map<Integer, Vertex> map = new HashMap<Integer, Vertex>();
		String line = null;
		int count = 0;
		while ((line = bufferedReader.readLine()) != null) {
			if (count == 0) {
				count++;
				continue;
			}
			line = line.trim();
			String[] arr = line.split("\\s+");
			
			int val1 = Integer.parseInt(arr[0]);
			int val2 = Integer.parseInt(arr[1]);
			if (!map.containsKey(val1)) {
				map.put(val1, new Vertex(val1));
				if (val1 == 1) map.get(1).key = 0;
				heap.offer(map.get(val1));
			}
			if (!map.containsKey(val2)) {
				map.put(val2, new Vertex(val2));
				if (val2 == 1) map.get(1).key = 0;
				heap.offer(map.get(val2));
			}

			int len = Integer.parseInt(arr[2]);
			map.get(val1).adj.add(new Tuple(map.get(val2), len));
			map.get(val2).adj.add(new Tuple(map.get(val1), len));
			
			
		}
		bufferedReader.close();
		
		
		
//		return map;
	}
	
	public void getPrimMST() {
		long res = 0;
		while (!heap.isEmpty()) {
			Vertex tmp = heap.poll();
			res += tmp.key;
			for (Tuple t : tmp.adj) {
				if (heap.contains(t.v)) {
					heap.remove(t.v);
					t.v.key = Math.min(t.v.key, t.len);
					heap.offer(t.v);
				}
			}
		}
		System.out.println(res);
	}
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		PrimMST p = new PrimMST();
		p.readLines("C:\\Users\\yamgao.ORADEV\\Google Drive\\algorithm\\HW\\HW1\\edges.txt");
		p.getPrimMST();
	}

}
