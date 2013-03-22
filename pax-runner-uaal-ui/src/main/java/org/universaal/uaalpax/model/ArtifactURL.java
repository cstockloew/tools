package org.universaal.uaalpax.model;

public final class ArtifactURL implements java.io.Serializable, Comparable<ArtifactURL> {
	private static final long serialVersionUID = -746998803398536504L;
	
	public final String url;
	
	public ArtifactURL(String url) {
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
		else if (!(obj instanceof ArtifactURL))
			return false;
		
		return url.equals(((ArtifactURL) obj).url);
	}
	
	@Override
	public String toString() {
		return url;
	}

	public int compareTo(ArtifactURL o) {
		return this.url.compareTo(o.url);
	}
}
