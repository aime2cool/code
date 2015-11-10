import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Inversion {
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
//			System.out.println(res[i]);
		}
		return res;
	}

	public long countAndSort(int[] arr, int start, int end) {
		if (start >= end) {
			return 0;
		}
		long count = 0;
		int mid = (start + end) / 2;
		count += countAndSort(arr, start, mid);
		count += countAndSort(arr, mid + 1, end);
		count += splitMergeCount(arr, start, end, mid);
		return count;
	}

	private long splitMergeCount(int[] arr, int start, int end, int mid) {
		int[] left = new int[mid - start + 1];
		int[] right = new int[end - mid];
		for (int i = 0; i < left.length; i++) {
			left[i] = arr[start + i];
		}
		for (int i = 0; i < right.length; i++) {
			right[i] = arr[mid + 1 + i];
//			System.out.println(right[i]);
		}
		long count = 0;
		int i = 0;
		int j = 0;
//		System.out.println(start + " " + end + " " + mid);
		for (int k = start; k <= end; k++) {
//			System.out.println(i + " " + left[i]);
//			System.out.println(j + " " + right[j]);
			if (j == right.length) {
				arr[k] = left[i];
				i++;
			}
			else if (i == left.length) {
				arr[k] = right[j];
				j++;
			}
			else if (left[i] < right[j]) {
				arr[k] = left[i];
				i++;
			} else {
				arr[k] = right[j];
				count += left.length - i;//not mid - i, now only consider left array
				j++;
			}

		}

		return count;

	}

	public static void main(String[] args) throws IOException {
		Inversion inv = new Inversion();
		String filename = "C:\\Users\\yamgao.ORADEV\\Google Drive\\algorithm\\HW\\HW1\\IntegerArray.txt";
		int[] arr = inv.readLines(filename);
//		int[] arr = {5,4,3,2,1};
//		int[] arr = { 4, 80, 70, 23, 9, 60, 68, 27, 66, 78, 12, 40, 52, 53, 44, 8, 49, 28, 18, 46, 21, 39, 51, 7, 87, 99, 69, 62, 84, 6, 79, 67, 14, 98, 83, 0, 96, 5, 82, 10, 26, 48, 3, 2, 15, 92, 11, 55, 63, 97, 43, 45, 81, 42, 95, 20, 25, 74, 24, 72, 91, 35, 86, 19, 75, 58, 71, 47, 76, 59, 64, 93, 17, 50, 56, 94, 90, 89, 32, 37, 34, 65, 1, 73, 41, 36, 57, 77, 30, 22, 13, 29, 38, 16, 88, 61, 31, 85, 33, 54 };
		long count = inv.countAndSort(arr, 0, arr.length - 1);
		System.out.println(count);
	}
}
