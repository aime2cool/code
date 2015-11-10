import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MinCut {
	class Vertex {
		int val;
		List<Vertex> adj;
		Vertex (int l) {
			val = l;
			adj = new ArrayList<Vertex>();
		}
//		void insertVertex(int v) {
//			Vertex vt = new Vertex(v);
//			adj.add(vt);
//		}
		void replaceVertex(Vertex v1, Vertex v2) {
			for (int i = 0; i < adj.size(); i++) {
				if (adj.get(i).val == v1.val) {
					adj.set(i, v2);
				}
			}
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
	
	public List<Vertex> readLines(String filename) throws IOException {
		FileReader fileReader = new FileReader(filename);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		Map<Integer,Vertex> map = new HashMap<Integer,Vertex>();
		
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			line = line.trim();
			String[] arr = line.split("\\s+");
			
			int key = Integer.parseInt(arr[0]);
			
			if (!map.containsKey(key)) {
				Vertex v = new Vertex(key);
				map.put(key, v);
			}
			
			Vertex head = map.get(key);
			
			for (int i = 1; i < arr.length; i++) {
				int num = Integer.parseInt(arr[i]);
				if (!map.containsKey(num)) {
					Vertex u = new Vertex(num);
					map.put(num, u);
				}
				head.adj.add(map.get(num));
			}
			
		}
		bufferedReader.close();
		List<Vertex> graph = new ArrayList<Vertex>(map.values());
		return graph;
	}
	
	public int minCut(List<Vertex> graph) {
		List<Vertex> g = new ArrayList<Vertex>(graph);
		while (g.size() > 2) {
			int rmid = 0;
			Vertex remain = null;

			while (true) {
				Random r = new Random();
				rmid = r.nextInt(g.size());
				if (g.get(rmid).adj.size() > 0) {
					remain = g.get(rmid).adj.get(0);
					break;
				}
			}
			Vertex remove = g.get(rmid);
			for (Vertex v : remove.adj) {
				if (v.val != remain.val && v.val != remove.val) {
					remain.adj.add(v);
					v.replaceVertex(remove, remain);
				}
				
			}
			
//			for (int i = 0; i < g.size(); i++) {
//				System.out.println("vertex is " + g.get(i).val);
//				for (Vertex v : g.get(i).adj) {
//					System.out.println("adj is" + v.val);
//				}
//			}
			
			Iterator<Vertex> i = remain.adj.iterator();
			while (i.hasNext()) {
				Vertex tmp = i.next();
				if (tmp.val == remove.val || tmp.val == remain.val) {
					i.remove();
				}
			}
			
			g.remove(rmid);
			
//			for (int j = 0; j < g.size(); j++) {
//				System.out.println("===============================vertex is " + g.get(j).val);
//				for (Vertex v : g.get(j).adj) {
//					System.out.println("adj is" + v.val);
//				}
//			}
//			System.out.println("remove val " + remove.val);
//			System.out.println("remain val " + remain.val);
		}
		System.out.println("v1 is " + g.get(0).val);
//		System.out.println(g.get(1).adj.size());
		for (Vertex e : g.get(0).adj) {
			System.out.println(e.val);
			
		}
		System.out.println();
		System.out.println("v2 is " + g.get(1).val);
		for (Vertex e : g.get(1).adj) {
			System.out.println(e.val);
			
		}
		return g.get(0).adj.size();
	}
	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		MinCut m = new MinCut();
		List<Vertex> graph = m.readLines("C:\\Users\\yamgao.ORADEV\\Google Drive\\algorithm\\HW\\HW3\\kargerMinCut.txt");
		int min = Integer.MAX_VALUE;
		int i = 0;
//		List<Vertex> graph = new ArrayList<Vertex>();
//		Vertex v1 = m.new Vertex(1);
//		Vertex v2 = m.new Vertex(2);
//		Vertex v3 = m.new Vertex(3);
//		Vertex v4 = m.new Vertex(4);
//		graph.add(v1);
//		graph.add(v2);
//		graph.add(v3);
//		graph.add(v4);
//		v1.adj.add(v2);
//		v1.adj.add(v3);
//		v1.adj.add(v4);
//		v2.adj.add(v1);
//		v2.adj.add(v3);
//		v2.adj.add(v4);
//		v3.adj.add(v1);
//		v3.adj.add(v2);
//		v3.adj.add(v4);
//		v4.adj.add(v1);
//		v4.adj.add(v2);
//		v4.adj.add(v3);
		
//		while (i < 200) {
			int tmp = m.minCut(graph);
			System.out.println(tmp);
//			min = Math.min(min, tmp);
//			System.out.println("min is " + min);
//			i++;
//		}
//		
//		System.out.println(min);
	}

}
