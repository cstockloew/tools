package org.universaal.tools.configurationExtractor.model.xml;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import org.eclipse.core.runtime.Platform;
import org.universaal.tools.configurationExtractor.controller.GuiControl;
import org.universaal.tools.configurationExtractor.view.Elements.BigElement;
import org.universaal.tools.configurationExtractor.view.Elements.ElementG;
import org.universaal.tools.configurationExtractor.view.Elements.Root;
import org.universaal.tools.configurationExtractor.view.LowLevelPanels.BigListPanel;
import org.universaal.tools.configurationExtractor.view.LowLevelPanels.BigPanel;
import org.universaal.tools.configurationExtractor.view.LowLevelPanels.ParentPanel;
/**
 * @author Ilja
 * This class involved all methods to extract special annotation (look class Annotations) from use case. AnnotationsExtractor is a singleton.
 */
public class AnnotationsExtractor {
	
	private static AnnotationsExtractor instance=new AnnotationsExtractor();
	private static LinkedList<BigElement> elementsList=new LinkedList<BigElement>();
	private static LinkedList<Integer> elemBelongsTo=new LinkedList<Integer>();
	private static int indexOfParentPanel=-1;
	private static LinkedList<ParentPanel> parentsList=new LinkedList<ParentPanel>();
	private static Root root=null;
	private static File fileWithRootElement=null;
	private static boolean rootFound=false;
	private PanelWithElements panelsWithElements = PanelWithElements.getInstance();

	/**
	 * Remove History should be always called after closing of plugin. If the user started plugin again (without closing of ) all data structures must have initial values.
	 */
	public void removeHistory(){
//		debug output
//		System.out.println("removeHistory");
		elementsList=new LinkedList<BigElement>();
		elemBelongsTo=new LinkedList<Integer>();
		indexOfParentPanel=-1;
		parentsList=new LinkedList<ParentPanel>();
		root=null;
		fileWithRootElement=null;
		rootFound=false;
		TreeWithElements treeWithElements= TreeWithElements.getInstance();
		PanelWithElements panelWithElements= PanelWithElements.getInstance();
		panelWithElements.removeAll();
		treeWithElements.removeAll();
	}
	/*
	 * Typical singleton method 
	 */
	private AnnotationsExtractor(){
	}
	
	/**
	 * After all Annotations were extracted, this method initialized via controller all GUI components
	 */
	public void initialize(){
		if(root!=null){
			GuiControl guiControl = GuiControl.getInstance();
			
				try {
					addRandomNumberToRootId();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				panelsWithElements = PanelWithElements.getInstance();
				panelsWithElements.setRoot(root);        
		                
		        for(int i=0;i<parentsList.size();i++){
		        	panelsWithElements.addParentPanel(parentsList.get(i));
		        	if(parentsList.get(i).getTypeOfPanel().equals("Listpanel ")){
		        		BigListPanel l=(BigListPanel)parentsList.get(i);
		        		l.addListLeaf();
		        	}
		        	for(int j=0;j<elemBelongsTo.size();j++){
		        		if(elemBelongsTo.get(j)==i) {		        			
		        			elementsList.get(j).setParentPanel(parentsList.get(i));  			
		        			DefaultMutableTreeNode child=new DefaultMutableTreeNode(elementsList.get(j).getElementType() +" "+ elementsList.get(j).getId());
		        			guiControl.addLeaf(child, panelsWithElements.getNode(parentsList.get(i).getTitle()));
		        			
		        		}
		        	}
		        }        
		}else{
			JOptionPane.showMessageDialog( null, "Sorry, but there was no @root, please try again " );
		}
	}
	/**
	 * Checks root element of use case 
	 * @return true if there was a correct use case with a root element
	 */
	public boolean isRootFound(){
		return (root!=null);
	}
	/**
	 * After reading of a use case to the root element (in code of use case and in CE) should be added a random number(in next versions of CE can be used UUID).  
	 * @throws IOException
	 */
	private void addRandomNumberToRootId() throws IOException{
		if(fileWithRootElement!=null){
			BufferedReader in = new BufferedReader(new FileReader(fileWithRootElement)); 

			 String randomNumber = "_"+(Math.round(Math.random()*1000000000));

			 //read
			 LinkedList<String> content = new LinkedList<String>();
			 String str = "";
			 while((str=in.readLine()) != null){ 
				 content.add(str);
			 }

			 //write
			 BufferedWriter out = new BufferedWriter(new FileWriter(fileWithRootElement));
			 for(String line : content){
				 
				 if(line.contains("@Root") && line.contains("useCaseName")&& line.contains("id")){
				     root.setId(root.getId()+randomNumber);
					 out.write("@Root(title=\""+root.getTitle()+"\", id=\""+root.getId()+"\", useCaseName=\""+root.getName()+"\")");
					 out.write("\n");
				 }else{
					 out.write(line+"\n");
				 }
 
			 }
			 out.close();

		}else{System.out.println("there was no root at the files!!!");
		}

	}
	
	/**
	 * Extract annotations of all classes from the given directory
	 * @param directory of classes with annotations
	 * @throws IOException
	 */
	public void extract(File directory) throws IOException{
	   LinkedList<File> javaFiles = getFiles(directory);
	   for(File file: javaFiles){
		   try {
				inspectJavaFile(file);
			}
	        catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	        catch (ParseException e) {
				e.printStackTrace();
			}
	        
	   }
	
	
	}	
	
	
	
	
	
	/**
	 * Checks (DFS) all files in a given directory for source files with java extension.
	 * 
	 * @param	startDir Start directory for search java files
	 * @return	the list with all java files
	 */
	private static LinkedList<File> getFiles(File startDir) {
		LinkedList<File> files = new LinkedList<File>();
	    Stack<File> dirs = new Stack<File>();
	    Stack<File> srcDirs = new Stack<File>();

	    //file with source directory
	    File src = null;
	    
	    if(startDir.isDirectory() && startDir.getName().equals("src")){
	    	src=startDir;
	    }else if ( startDir.isDirectory()) {
	    	boolean srcWasFounded=false;
	    	dirs.push( startDir );
		    while (dirs.size() > 0 && !srcWasFounded) {
		    	for (File file : dirs.pop().listFiles()) {
		    		if (file.isDirectory() && file.getName().equals("src")) {
		    			src = file ;
		    			srcWasFounded=true;
		    		}else if (file.isDirectory()) {
		    			dirs.push( file );
		    		}
		    	}
		    }
	    }
	    if(src!=null){
		    srcDirs.push( src );
		    while (srcDirs.size() > 0) {
		    	for (File srcFile : srcDirs.pop().listFiles()) {
		    		if (srcFile.isDirectory()) {
		    			srcDirs.push( srcFile );
		    		}
		    		else if (srcFile.canRead()) {
		    			files.add( srcFile );
		    		}
		    	}
		    }
	
		    Iterator itr = files.iterator(); 
		    while(itr.hasNext()) {
		    	File file=(File) itr.next();
		    	if (!file.getName().endsWith("java")) {
		    		itr.remove();
		    	}
		    } 
	    
	    }
	    return files;
	}
	
	/**
	 * Typical method of singleton pattern
	 * @return singleton instance of AnnotationsExtractor
	 */
	public static AnnotationsExtractor getInstance(){
		return instance;
	}
	
	/**
	 * Firstly this method compiles with javaparser the given java file. Secondly the compiled unit will be searched for Class and Method annotations
	 * @param pFile file with annotations
	 * @throws FileNotFoundException
	 * @throws ParseException
	 * @throws IOException
	 */
	public static void inspectJavaFile(File pFile) 	
    throws FileNotFoundException, ParseException, IOException {

	//debug output	
   	System.out.println("file="+pFile.toString());
    	
        CompilationUnit cu;
        FileInputStream in = new FileInputStream(pFile);
       
        try {
            cu = JavaParser.parse(in);
           
        } finally {
            in.close();
        }
        try {
        	new ClassVisitor().visit(cu, null);
        	new MethodVisitor().visit(cu, null);
       	
        	if(rootFound){
//        		debug output       		
        		System.out.println("file with root is="+pFile.toString());
        		fileWithRootElement=pFile;
        		rootFound=false;
        	}
        	
        }
        catch (NullPointerException npe) {
        		
        	//TODO Output
        }
    }
	
	
	/*
     * This private class provides a method MethodDeclaration for visiting all annotations of methods.
     */
    private static class MethodVisitor extends VoidVisitorAdapter {
   		@Override
   	    public void visit(MethodDeclaration n, Object arg)  {
   			visitAnnotations(n,arg);
   	    }
    }
    /*
     * This private class provides a method MethodDeclaration for visiting all Annotations of classes.
     */
    private static class ClassVisitor extends VoidVisitorAdapter {
        @Override
        public void visit(ClassOrInterfaceDeclaration n, Object arg) {
        	visitAnnotations(n,arg);
        }
    }
	/*
	 * This method finds and parses special defined annotations.
	 */
    private static void visitAnnotations(BodyDeclaration  n, Object arg)  {

		 if (n.getAnnotations() != null) {
	        	
	            for (AnnotationExpr annotation : n.getAnnotations()) {
	                String annotationType = annotation.getName().toString();
                
	            	if (annotationType.equals("Element")){
	            		    	            		
	            		String id="";
	            		String type="";
	            		String label="";
	            		String hoverText="";
	            		String standardValue="";
	            		String domain="";
	           		
	            		String a= annotation.toString();
	            		a=a.substring(9, a.length()-1);
	            		String[] b=a.split(", ");
	            		
	            		for(int i=0;i<b.length;i++){
	            			String c[]= b[i].split(" = ");
	            			if(c[0].equals("id")){
	            				c[1]=c[1].substring(1, c[1].length()-1);
	            				id=c[1];
	            			}else if(c[0].equals("type")){
	            				type=c[1];
	            			}else if(c[0].equals("label")){
	            				c[1]=c[1].substring(1, c[1].length()-1);
	            				label=c[1];
	            			}else if(c[0].equals("hoverText")){
	            				c[1]=c[1].substring(1, c[1].length()-1);
	            				hoverText=c[1];
	            			}else if(c[0].equals("standardValue")){
	            				c[1]=c[1].substring(1, c[1].length()-1);
	            				standardValue=c[1];
	            			}else if(c[0].equals("domain")){
	            				c[1]=c[1].substring(1, c[1].length()-1);
	            				domain=c[1];
	            			}          			
	       			
	            		}

	            		elemBelongsTo.add(indexOfParentPanel);
	            		
		           		ElementG element= new ElementG(type,label,hoverText,standardValue,domain,id,null);
		          		
		          		elementsList.add(element);	
//		          		debug output
//		          		System.out.println("Element " +element.getTitle() +"gefunden!");
	            	}else if (annotationType.equals("NormalPanel")){
	            		String title="";
	            		String a= annotation.toString();
	            		a=a.substring(8, a.length()-1);

	            		String c[]= a.split(" = ");
	            		c[1]=c[1].substring(1, c[1].length()-1);
	            		title=c[1];
	            		BigPanel panel=new BigPanel(title);
	            		parentsList.add(panel);
	            		indexOfParentPanel++;
	            	}else if (annotationType.equals("ListPanel")){
	            		String id="";
	            		String label="";
	            		String hoverText= "";
	            		int limit=-1;
	            		String uRI="";
	          		
	            		String a= annotation.toString();
	            		a=a.substring(11, a.length()-1);
	            		String[] b=a.split(", ");
	            		
	            		for(int i=0;i<b.length;i++){
	            			String c[]= b[i].split(" = ");
	            			
	            			for(int j=0;j<c.length;j++){
	            			}
	            			
	            			if(c[0].equals("id")){
	            				c[1]=c[1].substring(1, c[1].length()-1);
	            				id=c[1];
	            			}else if(c[0].equals("label")){
	            				c[1]=c[1].substring(1, c[1].length()-1);
	            				label=c[1];
	            			}else if(c[0].equals("hoverText")){
	            				c[1]=c[1].substring(1, c[1].length()-1);
	            				hoverText=c[1];
	            			}else if(c[0].equals("limit")){
	            				c[1]=c[1].substring(1, c[1].length()-1);
	          					  limit = new Integer(limit).intValue();
	          					
	            			}else if(c[0].equals("uRI")){
	            				c[1]=c[1].substring(1, c[1].length()-1);
	            				uRI=c[1];
	            			}
	            		}
	            	
	       
	          		BigListPanel listPanel=new BigListPanel(hoverText,id,label,limit,uRI);
  	          		parentsList.add(listPanel);
	          		indexOfParentPanel++;
          		
	            	}else if (annotationType.equals("Root")){
	            		
	            		//file with root found
	            		rootFound=true;
	            		
	            		String id="";
	            		String title="";
	            		String useCaseName="";
	            		String a= annotation.toString();
	            		a=a.substring(6, a.length()-1);
	            		String[] b=a.split(", ");
	            
	            		
	            		for(int i=0;i<b.length;i++){
	            			String c[]= b[i].split(" = ");
	            			c[1]=c[1].substring(1, c[1].length()-1);
	            			if(c[0].equals("id")){

	            				id=c[1];

	            			}else if(c[0].equals("title")){
	            				title=c[1];
	            			}else if(c[0].equals("useCaseName")){
	            				useCaseName=c[1];
	            			}
	            		}
            		 
	          		root= new Root(id,useCaseName,title,"");
//	          		debug output
//	        		System.out.println("root is " +root.getTitle()");
	            	}

	            }	
	            
	       } 
  
	    }
	
	
    /**
	 * This method returns all directories of workspace (in plugin runtime)
	 * 
	 * @return all directories of eclipse workspace
	 */
	public DefaultListModel getDirs() {				
		File dir = new File(Platform.getLocation().toString());		
		DefaultListModel allDirs = new DefaultListModel();
	    File[] files = dir.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
//					debug output
//					System.out.println("Directory "+files[i].toString());
					allDirs.addElement(files[i]);
				}
			}
		}
		return allDirs;
	}
	
	

}
