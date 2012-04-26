package org.universAAL.owl2uml.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyHashMap extends HashMap<String, List<String>> {

	public MyHashMap() {
		super();
	}

	public MyHashMap(int size) {
		super(size);
	}

	public List<String> get(String key) {
		return super.get(key);
	}

	public List<String> put(String key, String value) {
		List<String> list = this.get(key);
		if (list == null) {
			List<String> valList = new ArrayList<String>(1);
			valList.add(value);

			super.put(key, valList);
		} else {
			list.add(value);
			super.remove(key);
			super.put(key, list);
		}
		return null;
	}

	public List<String> putRange(String key, String value,
			ArrayList<String> type) {
		int sz = type.size();
		if (sz == 1) {

			List<String> list = this.get(key);
			if (list == null) {
				List<String> valList = new ArrayList<String>(1);
				valList.add(value + '#' + type.get(0));

				super.put(key, valList);
			} else {
				list.add(value + '#' + type.get(0));
				super.remove(key);
				super.put(key, list);
			}
		} else {// enumerated datatype property
			List<String> list = this.get(key);
			String serializedEnumarations = "";
			for (int j = 0; j < sz; j++) {
				serializedEnumarations = serializedEnumarations + type.get(j)
						+ "#";// serializing enumeration
			}
			if (list == null) {
				List<String> valList = new ArrayList<String>(1);
				valList.add(value + '#' + serializedEnumarations);

				super.put(key, valList);
			} else {
				list.add(value + '#' + serializedEnumarations);
				super.remove(key);
				super.put(key, list);
			}

		}
		return null;
	}
}
