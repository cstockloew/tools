/*	
	Copyright 2007-2014 Fraunhofer IGD, http://www.igd.fraunhofer.de
	Fraunhofer-Gesellschaft - Institut für Graphische Datenverarbeitung
	
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
package org.universaal.uaalpax.model;

public final class LaunchURL implements java.io.Serializable, Comparable<LaunchURL> {
	private static final long serialVersionUID = 2548205785470662951L;
	
	public final String url;
	
	public LaunchURL(String url) {
		this.url = url;
	}
	
	@Override
	public int hashCode() {
		return url.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		else if (!(obj instanceof LaunchURL))
			return false;
		
		return url.equals(((LaunchURL) obj).url);
	}
	
	@Override
	public String toString() {
		return url;
	}

	public int compareTo(LaunchURL o) {
		return this.url.compareTo(o.url);
	}
}
