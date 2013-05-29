/*
	Copyright 2011 SINTEF, http://www.sintef.no

	See the NOTICE file distributed with this work for additional 
	information regarding copyright ownership

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	  http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.universaal.tools.importexternalproject.xmlparser;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Class that helps searching through the list of ProjectObjects, and also implements
 * a comparator, so the list can be sorted by name.
 * @author Adrian
 *
 */
public class ProjectSearcher implements Comparator<ProjectObject> {

	/**
	 * Searches through the ArrayList of ProjectObjects that is given as input,
	 * looking for matches either in name or tags.
	 * @param input - ArrayList containing the ProjectObjects that should be searched.
	 * @param search - String that is to be searched for.
	 * @return ArrayList containing all ProjectObjects that match the searchterm.
	 */
	public static ArrayList<ProjectObject> search(ArrayList<ProjectObject> input,
			String search){
		ArrayList<ProjectObject> result = new ArrayList<ProjectObject>();
		ArrayList<String> tempTags = new ArrayList<String>();
		ProjectObject current = null;

		if(search.equals("")){
			result = input;
		}else{
			for(int i=0; i<input.size(); i++){
				current = input.get(i);

				if(current.getName().equalsIgnoreCase(search)){
					result.add(current);
				}else{
					tempTags = current.getTags();
					for(int j=0; j<tempTags.size(); j++){
						if(tempTags.get(j).equalsIgnoreCase(search)){
							result.add(current);
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * Comparator used to sort the list of ProjectObjects by name.
	 */
	@Override
	public int compare(ProjectObject o1, ProjectObject o2) {
		return (o1.getName()).compareTo(o2.getName());
	}

}
