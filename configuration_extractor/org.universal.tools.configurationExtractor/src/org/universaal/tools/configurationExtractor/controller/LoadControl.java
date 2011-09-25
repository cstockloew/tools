package org.universaal.tools.configurationExtractor.controller;

import java.io.File;
import java.io.IOException;
import javax.swing.DefaultListModel;
import org.universaal.tools.configurationExtractor.model.xml.AnnotationsExtractor;

/**
 * Another part of controller(MVC). The goal of LoadControl is to check the load routine. It is singleton pattern.
 */
public class LoadControl {
	private static LoadControl instance = new LoadControl();
	private AnnotationsExtractor annotationsExtractor;
	/*
	 * Constructor is private, because of singleton implementation
	 */
	private LoadControl(){
		annotationsExtractor =  AnnotationsExtractor.getInstance();
	}
	/**
	 * Typical singleton method
	 * @return LoadControl instance (singleton)
	 */
	public static LoadControl getInstance(){
		return instance;
	}
	
	/**
	 * This method extracts Annotations from the special directory of workspace.
	 * @param index describe the item of the list
	 * @param wokspaceDirs list with all directories of workspace
	 */
	public void extractAnnotationsFrom(int index, DefaultListModel wokspaceDirs){
		try {
			annotationsExtractor.extract(new File(""+wokspaceDirs.get(index)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		annotationsExtractor.initialize();
	}
	/**
	 * This method removes all history of program. If the plugin will be started again(without closing of eclipse) the whole history should be removed from cache memory. 
	 */
	public void removeHistory(){
		AnnotationsExtractor ae= AnnotationsExtractor.getInstance();
		ae.removeHistory();
	}
	/**
	 * This method signalizes whether the directory is a use case. 
	 * @return true if root element was found.
	 */
	public boolean isRootFound(){
		return AnnotationsExtractor.getInstance().isRootFound();
	}
}
