
public class BSToptimal {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n = 7;
		double[][] dp = new double[n + 1][n + 1];
		double[] w = {0.05,0.4,0.08,0.04,0.1,0.1,0.23};
		for (int s = 0; s <= n - 1; s++) {
			
			for (int i = 1; i <= n && i + s <= n; i++) {
				double sum = 0;
				for (int j = i; j <= i + s; j++) {
					sum += w[j - 1];
				}
				double mincost = Double.MAX_VALUE;
				for (int r = i; r <= i + s; r++) {
					double tmp = sum;
					if (i <= r - 1) {
						tmp += dp[i][r - 1];
					}
				    if (r + 1 <= i + s) {
						tmp += dp[r + 1][i + s];
					}
					mincost = Math.min(mincost, tmp);
					
				}
				dp[i][i + s] = mincost;
			}
		}
		System.out.println(dp[1][n]);
	}
	

}
