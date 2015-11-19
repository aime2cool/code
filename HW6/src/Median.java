import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Median {
	
	public int getMedian(String filename) throws IOException {
		int res = 0;
		FileReader fileReader = new FileReader(filename);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		PriorityQueue<Integer> smallMaxheap = new PriorityQueue<Integer>(Collections.reverseOrder());
		PriorityQueue<Integer> largeMinheap = new PriorityQueue<Integer>();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			line = line.trim();
			int num = Integer.parseInt(line);
			if (smallMaxheap.isEmpty() || num <= smallMaxheap.peek()) {
				smallMaxheap.offer(num);
				
			}
			else {
				largeMinheap.offer(num);
			}
			balanceHeap(smallMaxheap, largeMinheap);
			res += smallMaxheap.peek();
		}
		bufferedReader.close();
		return res % 10000;
		
	}
	
	
	private void balanceHeap(PriorityQueue<Integer> maxheap, PriorityQueue<Integer> minheap) {
		if (maxheap.size() < minheap.size()) {
			maxheap.offer(minheap.poll());
		}
		else if (maxheap.size() > minheap.size() + 1) {
			minheap.offer(maxheap.poll());
		}
	}


	public static void main(String[] args) throws IOException {
		Median m = new Median();
		System.out.println(m.getMedian("C:\\Users\\yamgao.ORADEV\\Google Drive\\algorithm\\HW\\HW6\\Median.txt"));

	}

}
