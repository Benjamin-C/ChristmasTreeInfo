package benjaminc.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BetterInts {
	public BetterInts() {
	}
	
	public int[] intListToArray(List<Integer> l) {
		int nums[] = new int[l.size()];
		for(int i = 0; i < l.size(); i++ ) {
			nums[i] = l.get(i);
		}
		return nums;
	}
	
	public String printIntArray(int array[]) {
		boolean hasBegun = false;
		String s = "[ ";
		for(int i = 0; i < array.length; i++) {
			if(hasBegun == true) {
				s = s + ", ";
			} else {
				hasBegun = true;
			}
			s = s + array[i];
		}
		s = s + " ]";
		return s;
	}
	
	public int[] scanFile(File f) {
		Scanner scan = null;
		List<Integer> numsList = new ArrayList<Integer>();
		
		try {
			scan = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while(scan.hasNextInt()) {
			numsList.add(scan.nextInt());
		}
		return this.intListToArray(numsList);
	}
}
