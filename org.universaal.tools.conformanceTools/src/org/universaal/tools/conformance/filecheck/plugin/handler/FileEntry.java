package org.universaal.tools.conformance.filecheck.plugin.handler;

public class FileEntry {
    protected String path;
    protected boolean exists;
    protected String contains;

    protected FileEntry() {
	path = null;
	exists = true;
	contains = null;
    }

    protected FileEntry(String p, boolean x, String c) {
	path = p;
	exists = x;
	contains = c;
    }
}
