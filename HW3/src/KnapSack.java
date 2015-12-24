import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class KnapSack {
	int[] v;
	int[] w;
	static int size;
	static int n;
	public void readLines(String filename) throws IOException {
		FileReader fileReader = new FileReader(filename);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		int i = 0;
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			line = line.trim();
			String[] arr = line.split("\\s+");
			
			int val1 = Integer.parseInt(arr[0]);
			int val2 = Integer.parseInt(arr[1]);
			if (i == 0) {
				size = val1;
				n = val2;
				v = new int[n];
				w = new int[n];
				i++;
				continue;
			}
			v[i - 1] = val1;
			w[i - 1] = val2;
			i++;
			
		}
		bufferedReader.close();
		
	}
	
	public int maxValue(int i, int remainW, int level, Map<Integer,Integer> map) {
		if (i == 0) return 0;
		int max = maxValue(i - 1, remainW, level - 1, map);
		if (remainW >= w[i - 1]) {
			max = Math.max(max, v[i - 1] + maxValue(i - 1, remainW - w[i - 1], level - 1, map));
		}
		return max;
	}
	
	public int getMaxValue1() {
		int[][] dp = new int[n + 1][size + 1];
		for (int i = 1; i <= n; i++) {
			for (int j = 0; j <= size; j++) {
				dp[i][j] = dp[i - 1][j];
				if (j >= w[i - 1]) {
					dp[i][j] = Math.max(dp[i - 1][j], v[i - 1] + dp[i - 1][j - w[i - 1]]);
				}
			}
		}
		return dp[n][size];
	}
	
	public int getMaxValue2() {
		int[] dp = new int[size + 1];
		for (int i = 1; i <= n; i++) {
			int[] cur = new int[size + 1];
			for (int j = 0; j <= size; j++) {
				cur[j] = dp[j];
				if (j >= w[i - 1]) {
					cur[j] = Math.max(dp[j], v[i - 1] + dp[j - w[i - 1]]);
				}
			}
			dp = cur;
		}
		return dp[size];
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		KnapSack k = new KnapSack();
//		k.readLines("C:\\Users\\yamgao.ORADEV\\Google Drive\\algorithm\\HW\\HW3\\knapsack1.txt");
		k.readLines("C:\\Users\\yamgao.ORADEV\\Google Drive\\algorithm\\HW\\HW3\\knapsack_big.txt");
		System.out.println(k.getMaxValue2());//4243395
//		System.out.println(k.getMaxValue1());//2493893
		
		
	}

}
