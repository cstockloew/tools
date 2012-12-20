package org.universaal.uaalpax.model;

public final class LaunchURL implements java.io.Serializable, Comparable<LaunchURL> {
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
