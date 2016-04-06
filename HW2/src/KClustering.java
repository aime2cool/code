import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;



public class KClustering {
	class Vertex {
		Vertex lead;
		int val;
		Vertex (int val) {
			this.val = val;
			lead = this;
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
	class Edge {
		Vertex v1;
		Vertex v2;
		int len;
		Edge (Vertex v1, Vertex v2, int len) {
			this.v1 = v1;
			this.v2 = v2;
			this.len = len;
		}
	}
	class Cluster {
		Vertex lead;
		List<Vertex> list;
		Cluster (Vertex l) {
			lead = l;
			list = new ArrayList<Vertex>();
			list.add(l);
		}
		public void add(Vertex v) {
			list.add(v);
		}
		public int size() {
			return list.size();
		}
		@Override
	    public int hashCode(){
	        return lead.val;
	    }

	    @Override
	    public boolean equals(Object obj){
	    	Cluster c = (Cluster) obj;
	    	return lead == c.lead? true : false;
	    }
	}
	Map<Integer, Vertex> map = new HashMap<Integer, Vertex>();
	Map<Vertex, Cluster> clusters = new HashMap<Vertex, Cluster>();
	Comparator<Edge> comp = new Comparator<Edge>() {

		@Override
		public int compare(Edge e1, Edge e2) {
			if (e1.len != e2.len) {
				return e1.len > e2.len? 1 : -1;
			}
			return 0;
		}
		
	};
	PriorityQueue<Edge> heap = new PriorityQueue<Edge>(comp);
	public void readLines(String filename) throws IOException {
		FileReader fileReader = new FileReader(filename);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		int i = 0;
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			if (i == 0) {
				i++;
				continue;
			}
			line = line.trim();
			String[] arr = line.split("\\s+");
			
			int val1 = Integer.parseInt(arr[0]);
			int val2 = Integer.parseInt(arr[1]);
			int len = Integer.parseInt(arr[2]);
			if (!map.containsKey(val1)) {
				map.put(val1, new Vertex(val1));
				clusters.put(map.get(val1), new Cluster(map.get(val1)));
			}
			if (!map.containsKey(val2)) {
				map.put(val2, new Vertex(val2));
				clusters.put(map.get(val2), new Cluster(map.get(val2)));
			}
			heap.offer(new Edge(map.get(val1), map.get(val2), len));
			
		}
		bufferedReader.close();
		
	}
	
	public int maxSpacing(int k) {
		while (clusters.size() > k) {
			Edge e = heap.poll();
//			System.out.println(e.len);
			if (!e.v1.lead.equals(e.v2.lead)) {
				Cluster c1 = clusters.get(e.v1.lead);
				Cluster c2 = clusters.get(e.v2.lead);
				Cluster lg = c1.size() > c2.size()? c1 : c2;
				Cluster sm = c1.size() > c2.size()? c2 : c1;
				for (Vertex v : sm.list) {
					v.lead = lg.lead;
					lg.add(v);
				}
				clusters.remove(sm.lead);
			}
			
		}
		while (!heap.isEmpty()) {
			Edge e = heap.poll();
			if (!e.v1.lead.equals(e.v2.lead)) {
				return e.len;
			}
		}
		return Integer.MAX_VALUE;
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		KClustering k = new KClustering();
		k.readLines("C:\\Users\\yamgao.ORADEV\\Google Drive\\algorithm\\HW\\HW2\\clustering1.txt");
//		k.readLines("C:\\Users\\yamgao.ORADEV\\Google Drive\\algorithm\\HW\\HW2\\test.txt");
		System.out.println(k.maxSpacing(4));
	}

}
