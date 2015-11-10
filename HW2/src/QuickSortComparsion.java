import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


	
public class QuickSortComparsion {
	public int[] readLines(String filename) throws IOException {
		FileReader fileReader = new FileReader(filename);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		List<Integer> lines = new ArrayList<Integer>();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			lines.add(Integer.parseInt(line.trim()));
		}
		bufferedReader.close();
		int[] res = new int[lines.size()];
		for (int i = 0; i < lines.size(); i++) {
			res[i] = lines.get(i);
		}
		return res;
	}
	
	public int countQuickSort(int[] arr, int l, int r) {
		if (l >= r) {
			return 0;
		}
//		int p = partition1(arr, l, r);
//		int p = partition2(arr, l, r);
		int p = partition3(arr, l, r);
		int count = r - l; 
		count += countQuickSort(arr, l, p - 1);
		count += countQuickSort(arr, p + 1, r);
		return count;
	}
	
	private int partition1(int[] arr, int l, int r) {
		int pivot = arr[l];
		int i = l + 1;
		int j = l + 1;
		while (j < arr.length) {
			if (arr[j] < pivot) {
				int tmp = arr[j];
				arr[j] = arr[i];
				arr[i] = tmp;
				i++;
			}
			j++;
		}
		arr[l] = arr[i - 1];
		arr[i - 1] = pivot;
		return i - 1;
	}
	
	private int partition2(int[] arr, int l, int r) {
		int pivot = arr[r];
		arr[r] = arr[l];
		arr[l] = pivot;
		int i = l + 1;
		int j = l + 1;
		while (j < arr.length) {
			if (arr[j] < pivot) {
				int tmp = arr[j];
				arr[j] = arr[i];
				arr[i] = tmp;
				i++;
			}
			j++;
		}
		arr[l] = arr[i - 1];
		arr[i - 1] = pivot;
		return i - 1;
	}
	
	private int partition3(int[] arr, int l, int r) {
		int mid = (l + r) / 2;
		
		int pivot = 0;
		if (arr[mid] >= arr[l] && arr[mid] <= arr[r] || arr[mid] <= arr[l] && arr[mid] >= arr[r]) {
			pivot = arr[mid];
			arr[mid] = arr[l];
			arr[l] = pivot;
		}
		else if (arr[l] >= arr[mid] && arr[l] <= arr[r] || arr[l] <= arr[mid] && arr[l] >= arr[r]) {
			pivot = arr[l];
			
		}
		else {
			pivot = arr[r];
			arr[r] = arr[l];
			arr[l] = pivot;
		}
		
		int i = l + 1;
		int j = l + 1;
		while (j < arr.length) {
			if (arr[j] < pivot) {
				int tmp = arr[j];
				arr[j] = arr[i];
				arr[i] = tmp;
				i++;
			}
			j++;
		}
		arr[l] = arr[i - 1];
		arr[i - 1] = pivot;
		return i - 1;
	}
	
	public static void main(String[] args) throws IOException {
		QuickSortComparsion q = new QuickSortComparsion();
		String filename = "C:\\Users\\yamgao.ORADEV\\Google Drive\\algorithm\\HW\\HW2\\QuickSort.txt";
		int[] arr = q.readLines(filename);
//		int[] arr = {3,9,8,4,6,10,2,5,7,1};
		int count = q.countQuickSort(arr, 0, arr.length - 1);
		System.out.println(count);
	}

}
