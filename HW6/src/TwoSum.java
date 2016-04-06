import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TwoSum {
	public List<Long> readLines(String filename) throws IOException {
		FileReader fileReader = new FileReader(filename);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		List<Long> nums = new ArrayList<Long>();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			line = line.trim();
			if (line.charAt(0) == '-') {
				nums.add(0 - Long.parseLong(line.substring(1)));
			}
			else {
				nums.add(Long.parseLong(line.trim()));
			}
			
		}
		bufferedReader.close();
		
		return nums;
	}
	
	public int getNumOfT(List<Long> nums) {
		int count = 0;
		Set<Integer> tset = new HashSet<Integer>();
		for (int i = -10000; i <= 10000; i++) {
			tset.add(i);
		}
		Set<Long> set = new HashSet<Long>();
		for (long i : nums) {
			Iterator<Integer> iterator = tset.iterator();
			while (iterator.hasNext()) {
				int tmp = iterator.next();
				if (set.contains(tmp - i)) {
					count++;
//					System.out.println(count);
					iterator.remove();
				}
			}
			
			set.add(i);
		}
		return count;
	}
	
	public static void main(String[] args) throws IOException {
		TwoSum ts = new TwoSum();
		List<Long> nums = ts.readLines("C:\\Users\\yamgao.ORADEV\\Google Drive\\algorithm\\HW\\HW6\\algo1-programming_prob-2sum.txt");
		System.out.println(ts.getNumOfT(nums));

	}

}
