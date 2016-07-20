package de.salocin.gameenginefx.util;

import java.util.ArrayList;

public class CollectionHelper {
	
	public static String toString(ArrayList<String> arrayList) {
		return toString(arrayList, 0, arrayList.size() - 1);
	}
	
	public static String toString(ArrayList<String> arrayList, int indexStart, int indexTo) {
		return toString(arrayList, " ", indexStart, indexTo);
	}
	
	public static String toString(ArrayList<String> arrayList, String separator, int indexStart, int indexTo) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = indexStart; i < arrayList.size(); i++) {
			if (i > indexTo) {
				continue;
			}
			
			if (i > indexStart) {
				sb.append(separator);
			}
			
			sb.append(arrayList.get(i));
		}
		
		return sb.toString();
	}
	
}
