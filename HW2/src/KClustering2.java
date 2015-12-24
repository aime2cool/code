import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class KClustering2 {
	
	
	public Map<String,Boolean> readLines(String filename) throws IOException {
		Map<String,Boolean> g = new HashMap<String,Boolean>();
		FileReader fileReader = new FileReader(filename);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		int i = 0;
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			if (i == 0) {
				i++;
				continue;
			}
			line = line.trim().replaceAll(" ", "");
//			System.out.println(line);
			if (!g.containsKey(line)) {
				g.put(line, false);
			}
			i++;
//			if (i > 75641) {
//				break;
//			}
			
		}
//		System.out.println(g.size());
		bufferedReader.close();
		return g;
	}
	
	public int getK(Map<String,Boolean> g) {
//		Map<String,ArrayList<String>> clusters = new HashMap<String,ArrayList<String>>();
//		boolean[] isVisited = new boolean[g.size()];
		int count = 0;
		for (String s : g.keySet()) {
			if (!g.get(s)) {
				bfs(g, s);
				count++;
			}
		}
		return count;
		
	}
	
	private void bfs(Map<String,Boolean> g, String s) {
		LinkedList<String> q = new LinkedList<String>();
		q.offer(s);
		g.put(s, true);
		int old = 1;
		int cur = 0;

		while (!q.isEmpty()) {
			String tmp = q.poll();
			old--;
			for (int i = 0; i < tmp.length(); i++) {
				char[] arr = tmp.toCharArray();
				arr[i] = arr[i] == '1'? '0':'1';
				
				String tmps1 = new String(arr);
				
				if (g.containsKey(tmps1)) {
					if (!g.get(tmps1)) {
						q.offer(tmps1);
						g.put(tmps1, true);
						cur++;
					}
						
				}
			}
			for (int i = 0; i < tmp.length(); i++) {
				char[] arr = tmp.toCharArray();
				arr[i] = arr[i] == '1'? '0':'1';
					
					for (int j = i + 1; j < tmp.length(); j++) {
						arr[j] = arr[j] == '1'? '0' : '1';
						String tmps2 = new String(arr);
						if (g.containsKey(tmps2) && !g.get(tmps2)) {
							q.offer(tmps2);
							g.put(tmps2, true);
							cur++;
						}
						arr[j] = arr[j] == '1'? '0' : '1';//reverse to previous string
					}
				
				
			}
			if (old == 0) {
				old = cur;
				cur = 0;

			}
		}
		return;
		
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		KClustering2 k = new KClustering2();
		Map<String,Boolean> g = k.readLines("C:\\Users\\yamgao.ORADEV\\Google Drive\\algorithm\\HW\\HW2\\clustering_big.txt");
//		Map<String,Boolean> g = k.readLines("C:\\Users\\yamgao.ORADEV\\Google Drive\\algorithm\\HW\\HW2\\test.txt");
		System.out.println(k.getK(g));
	}

}
