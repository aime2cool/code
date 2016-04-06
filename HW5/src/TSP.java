import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TSP {
	class Vertex {
		float x;
		float y;

		Vertex(float x, float y) {
			this.x = x;
			this.y = y;
		}

		public float distanceTo(Vertex v) {
			return (float) Math.sqrt((x - v.x) * (x - v.x) + (y - v.y) * (y - v.y));
		}

		public boolean equals(Object v) {
			Vertex v1 = (Vertex) v;
			return x == v1.x && y == v1.y;
		}

		public int hashCode() {
			return (Float.valueOf(x).toString() + Float.valueOf(y).toString()).hashCode();
		}
	}

	Map<Integer, Vertex> map = new HashMap<Integer, Vertex>();
	int n;

	public void readLines(String filename) throws IOException {
		FileReader fileReader = new FileReader(filename);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		int i = 0;
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			line = line.trim();
			if (i == 0) {
				n = Integer.parseInt(line);
				i++;
				continue;
			}
			String[] arr = line.split("\\s+");

			float x = Float.parseFloat(arr[0]);
			float y = Float.parseFloat(arr[1]);

			map.put(i, new Vertex(x, y));
			i++;
		}
		bufferedReader.close();

	}

	public float tsp() {

		Map<Integer,List<Integer>> sizemap = getSubset();
		Map<Integer,Integer> nummap = new HashMap<Integer,Integer>();
		for (int i = 0; i < sizemap.get(0).size(); i++) {
			nummap.put(sizemap.get(0).get(i), i);
		}
		float[][] predp = new float[sizemap.get(0).size()][n + 1];
		predp[0][1] = 0;

		for (int m = 1; m < n; m++) {
			
			List<Integer> subsets = sizemap.get(m);
			Map<Integer,Integer> curnummap = new HashMap<Integer,Integer>();
			float[][] dp = new float[subsets.size()][n + 1];
			System.out.println("size: " + m + "subsets: " + subsets.size());
			for (int i = 0; i < subsets.size(); i++) {
				curnummap.put(subsets.get(i), i);
				int num = subsets.get(i);
				int count = 2;
				List<Integer> curset = new ArrayList<Integer>();
				while (num != 0) {
					if ((num & 1) == 1) {
						curset.add(count);
						
					}
					num >>= 1;
					count++;
				}
				num = subsets.get(i);
				for (int j : curset) {
					
					if (m == 1) {
						dp[i][j] = map.get(1).distanceTo(map.get(j));
						continue;
					}
					dp[i][j] = Float.MAX_VALUE;
					for (int k : curset) {
						if (k == j)
							continue;
						
						
						int pre = num & (~(1<<(j-2)));
//						int preindex = sizemap.get(m-1).indexOf(Integer.valueOf(pre));
						int preindex = nummap.get(pre);
//						System.out.println("j: "+j + " num: "+ num + " pre: " + pre);
						dp[i][j] = Math.min(dp[i][j], predp[preindex][k] + map.get(k).distanceTo(map.get(j)));
					}
//					System.out.println("j:"+ j + " " + "distance:" + dp[num][j]);
				}

			}
			predp = dp;
			nummap = curnummap;

		}
		float res = Float.MAX_VALUE;
		for (int j = 2; j <= n; j++) {
			res = Math.min(res, predp[0][j] + map.get(j).distanceTo(map.get(1)));
		}
//		return (float) Math.floor(res);
		return res;
	}

	private List<List<Integer>> getSubsetWith1(int m) {
		// TODO Auto-generated method stub
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		helper(res, m, new ArrayList<Integer>(), 1, n);
		return res;
	}

	private void helper(List<List<Integer>> res, int m, ArrayList<Integer> list, int start, int num) {
		// TODO Auto-generated method stub
		
		if (list.size() == m) {
			List<Integer> cp = new ArrayList<Integer>(list);
			res.add(cp);
			return;
		}
		
		for (int i = start; i <= num; i++) {
			if (list.size() == 0 && i != 1) {
				break;
			}
			list.add(i);
			helper(res, m, list, i + 1, num);
			list.remove(list.size() - 1);
		}
	}

	private Map<Integer, List<Integer>> getSubset() {
		Map<Integer,List<Integer>> map = new HashMap<Integer,List<Integer>>();
		for (int i = 0; i < Math.pow(2, n - 1); i++) {
//			System.out.println(Math.pow(2, n - 1));
			int count = 0;
			int num = i;
			while (num != 0) {
				if ((num & 1) == 1) count++;
				num >>= 1;
			}
			
			if (!map.containsKey(count)) {
//				System.out.println(count);
				map.put(count, new ArrayList<Integer>());
			}
			map.get(count).add(i);
		}
		return map;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		TSP t = new TSP();
		t.readLines("C:\\Users\\yamgao.ORADEV\\Google Drive\\algorithm\\HW\\HW5\\tsp.txt");
		System.out.println(t.tsp());
	}

}
