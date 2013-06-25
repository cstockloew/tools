/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.universaal.tools.externalserviceintegrator.ontology.tools;

/**
 *
 *
 * Java WordNet Similarity Library
 * authors: Giuseppe Pirrς and Nuno Seco
 *
 * for information contact Giuseppe at  gpirro@deis.unical.it
 *
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.eclipse.core.resources.ResourcesPlugin;

public class IndexBroker {
	/**
	 * A static constant that represents the field name that holds the offset
	 * value of each document.
	 */
	public static final String SYNSET = "synset";

	/**
	 * A static constant that represents the field name that holds the list of
	 * words of each document.
	 */
	public static final String WORDS = "word";

	/**
	 * A static constant that represents the field name that holds the list of
	 * hypernym offsets of each document. This list also contains the offset of
	 * the documented in which it is contained.
	 */
	public static final String HYPERNYM = "hypernym";

	/**
	 * A static constant that represents the field name that holds the
	 * information Content value of each document.
	 */
	public static final String INFORMATION_CONTENT = "ic";

	/**
	 * The directory where the broker will look for the Lucene index.
	 */
	private final String INDEX_DIR = "./wn_index";

	/**
	 * Holds a reference to an instance of a Searcher that allows searches to be
	 * conducted in the opened index.
	 */
	private Searcher _searcher;

	/**
	 * Holds a reference to an instance of a Parser; a parser parses the query.
	 */
	private QueryParser _parser;

	/**
	 * A static reference to an instance of an Index Broker. This variable
	 * guarantees that only one instance of the broker will be allowed for each
	 * Java Virtual Machine launched.
	 */
	private static IndexBroker _instance;

	/**
	 * The Constructor. Has private access to allow the implementation of the
	 * singleton design pattern. Points the searcher to the index directory,
	 * sets the default field to lookup and the defualt operator that is to be
	 * assumed when more than one token is given.
	 */
	private IndexBroker() {
		try {
			String workspacePath = ResourcesPlugin.getWorkspace().getRoot()
					.getLocation().toOSString();
			_searcher = new IndexSearcher(workspacePath+File.separator+INDEX_DIR);
			_parser = new QueryParser(WORDS, new WhitespaceAnalyzer());

		} catch (IOException ex) {
			ex.printStackTrace();

			System.err.println("");
			System.err.println("Please place the " + INDEX_DIR
					+ " in the working directory.");
			// copy wn_index dir to workspace
			copyFile("segments");
			copyFile("_1wbj.cfs");
			copyFile("deletable");
			try{
			String workspacePath = ResourcesPlugin.getWorkspace().getRoot()
					.getLocation().toOSString();
			_searcher = new IndexSearcher(workspacePath+File.separator+INDEX_DIR);
			_parser = new QueryParser(WORDS, new WhitespaceAnalyzer());
			}catch(Exception ext){
				ext.printStackTrace();
			}
		}

	}

	
	private void copyFile(String fileName){
		try {
			String workspacePath = ResourcesPlugin.getWorkspace().getRoot()
					.getLocation().toOSString();
			File keyDir = new File(workspacePath + File.separator
					+ "wn_index" + File.separator);
			keyDir.mkdirs();
			File keyFile = new File(keyDir + File.separator+  fileName);
			if (!keyFile.exists()) {

				InputStream inputStream = getClass().getResourceAsStream(
						"/wn_index/"+fileName);
				OutputStream out = new FileOutputStream(keyFile);
				byte buf[] = new byte[1024];
				int len;
				while ((len = inputStream.read(buf)) > 0)
					out.write(buf, 0, len);
				out.close();
				inputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	/**
	 * Static method that allows other objects to aquire a reference to an
	 * existing broker. If no broker exists than a new one is created.
	 * 
	 * @return IndexBroker
	 */
	public static IndexBroker getInstance() {
		// if (_instance == null) {
		// _instance = new IndexBroker();
		// }
		//
		// return _instance;

		_instance = new IndexBroker();
		return _instance;
	}

	/**
	 * Returns the list of documents that fulfill the given query.
	 * 
	 * @param query
	 *            String The query to be searched
	 * @return Hits A list of hits
	 */
	public Hits getHits(String query) {
		Query q;
		try {
			// synchronized(this){
			q = _parser.parse(query);
			// }
			BooleanQuery.setMaxClauseCount(Integer.MAX_VALUE);
			return _searcher.search(q);
		} catch (ParseException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return null;
	}

}
