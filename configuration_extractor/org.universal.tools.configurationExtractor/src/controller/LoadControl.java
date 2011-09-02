package controller;

import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListModel;

import model.xml.AnnotationsExtractor;

import view.MainPanels.MainWindow;

public class LoadControl {
	private static LoadControl instance = new LoadControl();
	private AnnotationsExtractor annotationsExtractor;
//	private MainWindow mainWindow;
//	private StartWindow startWindow;
	private LoadControl(){
//		System.out.println("hi,im Configuration Extractor PlugIn");
//		
//		mainWindow=new MainWindow("Configuration Extractor");
//		startWindow=new StartWindow("Configuration Extractor", mainWindow);
//		startWindow.setVisible(true);
		annotationsExtractor =  AnnotationsExtractor.getInstance();
	}
	
	public static LoadControl getInstance(){
		return instance;
	}
	
	public void extractAnnotationsFrom(int index, DefaultListModel wokspaceDirs){
		try {
			annotationsExtractor.extract(new File(""+wokspaceDirs.get(index)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("extract succesful");
		annotationsExtractor.initialize();
//		mainWindow.setVisible(true); 
//		startWindow.setVisible(false);  
	}
	
	public void removeHistory(){
		System.out.println("AUS!");
		AnnotationsExtractor ae= AnnotationsExtractor.getInstance();
		ae.removeHistory();
		System.out.println("gestoppt!!!");
//	    mainWindow.setVisible(true);
	}
	
	public boolean isRootFound(){
		return AnnotationsExtractor.getInstance().isRootFound();
	}
}
