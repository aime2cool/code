import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Greedy {
	class Job implements Comparable<Job>{
		int weight;
		int length;
		public Job(int w, int l) {
			this.weight = w;
			this.length = l;
		}
		public int dif() {
			return weight - length;
		}
		public double ratio() {
			return weight * 1.0 / length;
		}
		@Override
		public int compareTo(Job j) {
			// TODO Auto-generated method stub
			if (this.dif() != j.dif()) {
				return this.dif() > j.dif()? -1:1;
			}
			else if (this.weight != j.weight) {
				return this.weight > j.weight? -1:1;
			}
			return 0;
		}
	}
	public List<Job> readLines(String filename) throws IOException {
		FileReader fileReader = new FileReader(filename);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		List<Job> jobs = new ArrayList<Job>();
		String line = null;
		int i = 0;
		while ((line = bufferedReader.readLine()) != null) {
			if (i == 0) {
				i++;
				continue;
			}
			line = line.trim();
			String[] arr = line.split("\\s+");
			Job job = new Job(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
			jobs.add(job);
		}
		bufferedReader.close();
		
		return jobs;
	}
	public long getWeigthedTime(List<Job> jobs) {
		Comparator<Job> comp = new Comparator<Job>() {

			@Override
			public int compare(Job i, Job j) {
				// TODO Auto-generated method stub
				if (i.ratio() != j.ratio()) {
					return i.ratio() > j.ratio()? -1:1;
				}
				else if (i.weight != j.weight) {
					return i.weight > j.weight? -1:1;
				}
				return 0;
			}
			
		};
//		Collections.sort(jobs);
		Collections.sort(jobs, comp);
		long res = 0;
		long t = 0;
		for (Job j : jobs) {
			t += j.length;
			res += j.weight * t;
		}
		return res;
	}
	public static void main(String[] args) throws IOException {
		Greedy g = new Greedy();
		List<Job> jobs = g.readLines("C:\\Users\\yamgao.ORADEV\\Google Drive\\algorithm\\HW\\HW1\\jobs.txt");
		System.out.println(g.getWeigthedTime(jobs));
	}
}
