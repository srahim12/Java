package apps;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import structures.Node;

public class Sorter {

	public static void main(String[] args) 
	throws IOException {
	
		Scanner sysin = new Scanner(System.in);
		System.out.print("Enter input file name: ");
		String inFile = sysin.next();
		//System.out.println("Works after getting file");		
		// create new Radixsort object, using default constructor
		Radixsort rs = new Radixsort();
		//System.out.println("Works after declaring RadixSort object");	
		// sort the items in the input file
		Scanner sc = new Scanner(new File(inFile));
		//System.out.println("Works after getting file into scanner");	
		Node<String> output = rs.sort(sc);
		//System.out.println("Works after sort");	
		// print output
		System.out.println("\nSorted Result:");
		printCLL(output);
		
	}

	/**
	 * Prints the items in a CLL
	 */
	public static<T> void printCLL(Node<T> rear) {
		if (rear == null) {
			return;
		}
		Node<T> ptr = rear;
		do {
			ptr = ptr.next;
			System.out.println(ptr.data);
		} while (ptr != rear);
		System.out.println();
	}
}
