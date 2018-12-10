package benjaminc.util;



import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BenFile {
	public BenFile() {
	}
	
	public String[] stringListToArray(List<String> s) {
		String st[] = new String[s.size()];
		for(int i = 0; i < s.size(); i++ ) {
			st[i] = s.get(i);
		}
		return st;
	}
	
	public String printStringArray(String array[]) {
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
	
	public String[] scanFile(File f) {
		Scanner scan = null;
		List<String> str = new ArrayList<String>();
		
		try {
			scan = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while(scan.hasNextLine()) {
			str.add(scan.nextLine());
		}
		return this.stringListToArray(str);
	}
	
	public String[] scan(Scanner scan) {
		List<String> str = new ArrayList<String>();
		
		while(scan.hasNextLine()) {
			str.add(scan.nextLine());
		}
		return this.stringListToArray(str);
	}
	
	public String[] removeBlanks(String array[]) {
		List<String> str = new ArrayList<String>();
		for(int i = 0; i < array.length; i++) {
			if(!array[i].equals("")) {
				str.add(array[i]);
			}
		}
		return stringListToArray(str);
	}
}