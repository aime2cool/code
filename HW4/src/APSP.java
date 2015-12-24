import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;


public class APSP {
	int vNum;
	int edgeNum;
	class Tuple {
		Vertex v;
		int e;
		Tuple(Vertex v, int edge) {
			this.v = v;
			this.e = edge;
		}
	}
	class Vertex {
		int val;
		int p;
		List<Tuple> in;
		List<Tuple> out;
		int key;
		Vertex(int val) {
			this.val = val;
			key = Integer.MAX_VALUE;
			in = new ArrayList<Tuple>();
			out = new ArrayList<Tuple>();
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
	Map<Integer,Vertex> map = new HashMap<Integer,Vertex>();
	Comparator<Vertex> comp = new Comparator<Vertex>() {
		public int compare(Vertex v1, Vertex v2) {
			if (v1.key != v2.key) return v1.key > v2.key? 1 : -1;
			return 0;
		}
	};
	PriorityQueue<Vertex> heap = new PriorityQueue<Vertex>(comp);
	
	public void readLines(String filename) throws IOException {
		FileReader fileReader = new FileReader(filename);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		int i = 0;
		String line = null;
		map.put(0, new Vertex(0));
		while ((line = bufferedReader.readLine()) != null) {
			line = line.trim();
			String[] arr = line.split("\\s+");
			
			int val1 = Integer.parseInt(arr[0]);
			int val2 = Integer.parseInt(arr[1]);
			if (i == 0) {
				vNum = val1;
				edgeNum = val2;
				
				i++;
				continue;
			}
			int len = Integer.parseInt(arr[2]);
			if (!map.containsKey(val1)) {
				map.put(val1, new Vertex(val1));
//				if (val1 == 1) map.get(1).key = 0;
				map.get(0).out.add(new Tuple(map.get(val1), 0));
				map.get(val1).in.add(new Tuple(map.get(0), 0));
				heap.offer(map.get(val1));
			}
			if (!map.containsKey(val2)) {
				map.put(val2, new Vertex(val2));
//				if (val2 == 1) map.get(1).key = 0;
				map.get(0).out.add(new Tuple(map.get(val2), 0));
				map.get(val2).in.add(new Tuple(map.get(0), 0));
				heap.offer(map.get(val2));
			}
			map.get(val1).out.add(new Tuple(map.get(val2), len));
			map.get(val2).in.add(new Tuple(map.get(val1), len));
		}
		bufferedReader.close();
		
	}
	
	public void BellmanFord() {
		long[][] A = new long[vNum + 2][vNum + 1];
		A[0][0] = 0;
		for (int i = 1; i <= vNum; i++) {
			A[0][i] = Integer.MAX_VALUE;
		}
		for (int i = 1; i <= vNum + 1; i++) {
			for (int j = 1; j < vNum + 1; j++) {
				
				long min = A[i - 1][j];
				for (Tuple t : map.get(j).in) {
					min = Math.min(min, t.e + A[i - 1][t.v.val]);
				}
				
				A[i][j] = min;

				if (i == vNum) {
					map.get(j).p = (int) min;
					System.out.println("j:" + j + ",path:"+min);
				}
				if (i == vNum + 1 && A[i][j] < A[i - 1][j]) {
					System.out.println("NULL");
					System.exit(1);
				}
			}
		}
		changeEdgeLength();
	}
	
	private void changeEdgeLength() {
		// TODO Auto-generated method stub
		map.remove(0);
		for (int i = 1; i <= vNum; i++) {
			for (Tuple t : map.get(i).out) {
				t.e += map.get(i).p - t.v.p;
				if (t.e < 0) {
					System.out.println(map.get(i).val + "->" + t.v.val);
					System.out.println("p is "+map.get(i).p + "->" + t.v.p);
					System.out.println(t.e);
				}
				
//				System.out.println(t.e);
			}
		}
	}
	
	private int Dijkstra(Vertex s) {
		int shortest = Integer.MAX_VALUE;
		while (!heap.isEmpty()) {
			Vertex tmp = heap.poll();
			if (tmp.key == Integer.MAX_VALUE) break;
//			if (s.val == 2) {
//				System.out.println(tmp.key);
//			}
			if (shortest > tmp.key - (s.p - tmp.p)) {
				shortest = tmp.key - (s.p - tmp.p);
			}
			for (Tuple t : tmp.out) {
				if (!heap.contains(t.v)) {
					continue;
				}
				heap.remove(t.v);
				t.v.key = Math.min(t.v.key, tmp.key + t.e);
				heap.offer(t.v);
				
			}
		}
//		System.out.println(shortest);
		return shortest;
	}
	public int nDijkstra() {
		int res = Integer.MAX_VALUE;
		for (int i = 1; i <= vNum; i++) {
			heap.remove(map.get(i));
			map.get(i).key = 0;
			heap.offer(map.get(i));
			res = Math.min(res, Dijkstra(map.get(i)));
			resetVkey();
		}
		return res;
	}
	private void resetVkey() {
		// TODO Auto-generated method stub
//		heap = new PriorityQueue<Vertex>(comp);
		for (int i = 1; i <= vNum; i++) {
			heap.remove(map.get(i));
			map.get(i).key = Integer.MAX_VALUE;
			heap.offer(map.get(i));
		}
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		APSP a = new APSP();
		a.readLines("C:\\Users\\yamgao.ORADEV\\Google Drive\\algorithm\\HW\\HW4\\g3.txt");
		a.BellmanFord();
		System.out.println(a.nDijkstra());
	}

}
