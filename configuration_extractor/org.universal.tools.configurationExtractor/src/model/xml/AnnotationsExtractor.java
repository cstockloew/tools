package model.xml;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
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
import java.util.UUID;

import java.util.Stack;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import org.eclipse.core.runtime.Platform;

import controller.GuiControl;
import controller.MainPanelControl;

import view.Elements.BigElement;
import view.Elements.ElementG;
import view.Elements.ListPanel;
import view.Elements.Root;
import view.LowLevelPanels.BigListPanel;
import view.LowLevelPanels.BigPanel;
import view.LowLevelPanels.BigRootPanel;
import view.LowLevelPanels.ParentPanel;

import view.MainPanels.TreePanel;


public class AnnotationsExtractor {
	
	private static AnnotationsExtractor instance=new AnnotationsExtractor();
	private static LinkedList<BigElement> elementsList=new LinkedList<BigElement>();

	private static LinkedList<Integer> elemBelongsTo=new LinkedList<Integer>();
	private static int indexOfParentPanel=-1;
	private static LinkedList<ParentPanel> parentsList=new LinkedList<ParentPanel>();

	private static Root root=null;

	private static File fileWithRootElement=null;
	private static boolean rootFound=false;
	
//	private static boolean addToPanel=true;
	
//	BigRootPanel bigRootPanel= BigRootPanel.getInstance();
	private PanelWithElements panelsWithElements = PanelWithElements.getInstance();
	MainPanelControl mpControl = MainPanelControl.getInstance();
//	private TreePanel treePanel= TreePanel.getInstance();
	
	public void removeHistory(){
		System.out.println("removeHistory");
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
		
//		treeWithElements=null;
//		panelWithElements=null;
	}
	
	private AnnotationsExtractor(){
	}
	
	public void initialize(){
		if(root!=null){
			GuiControl guiControl = GuiControl.getInstance();
			
				try {
					addRandomNumberToRootId();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		//		System.out.println("root ist ==="+root);
				panelsWithElements = PanelWithElements.getInstance();
		//		System.out.println("panelsWithElements ist ==="+panelsWithElements);
				panelsWithElements.setRoot(root);        
		                
		        for(int i=0;i<parentsList.size();i++){
		        	panelsWithElements.addParentPanel(parentsList.get(i));
		        	if(parentsList.get(i).getTypeOfPanel().equals("Listpanel ")){
		        		BigListPanel l=(BigListPanel)parentsList.get(i);
		        		l.addListLeaf();
		        	}
		        	for(int j=0;j<elemBelongsTo.size();j++){
		        		if(elemBelongsTo.get(j)==i) {
		        			
		        			System.out.println("i= "+i + " j ="+j);
		        			
		//        			parentsList.get(i).addElement(elementsList.get(j));
		        			elementsList.get(j).setParentPanel(parentsList.get(i));  			
		        			
		        			DefaultMutableTreeNode child=new DefaultMutableTreeNode(elementsList.get(j).getElementType() +" "+ elementsList.get(j).getId());
		        			guiControl.addLeaf(child, panelsWithElements.getNode(parentsList.get(i).getTitle()));
		        			
		        		}
		        	}
		        	System.out.println("size of parent "+parentsList.get(i).getTitle()+" is==="+parentsList.get(i).getElementsList().size());     
		        }        
		}else{
			JOptionPane.showMessageDialog( null, "Sorry, but there was no @root, please try again " );
		}
	}
	
	public boolean isRootFound(){
		return (root!=null);
	}
	
	private void addRandomNumberToRootId() throws IOException{
		if(fileWithRootElement!=null){
			BufferedReader in = new BufferedReader(new FileReader(fileWithRootElement)); 

			 String randomNumber = "_"+(Math.round(Math.random()*1000000000));
			 System.out.println("randomNumber is "+randomNumber);
//			 UUID uuid = UUID.randomUUID();
//		        System.out.println(uuid);

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
//				 @Root(title="Einstellungen vergessene Geräte", id = "VG", useCaseName = "V_G")
//					 System.out.println("@Root(title=\""+root.getTitle()+"\", id=\""+root.getId()+"\", useCaseName=\""+root.getName()+"\")");
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
	 * Searches for all files in a Directory and in all sub-directories, 
	 * writing the one with the correct java-extension into an LinkedList.
	 * 
	 * @param	startDir		Root Directory to start recursive search
	 * @return	all	files of a selected root directory and all sub-directories, which have a specified (e.g. "java") extension
	 */
	public static LinkedList<File> getFiles(File startDir) {
		LinkedList<File> files = new LinkedList<File>();
	    Stack<File> dirs = new Stack<File>();
	    
	    if ( startDir.isDirectory()) {
	    	dirs.push( startDir );
	    }
	    while (dirs.size() > 0) {
	    	for (File file : dirs.pop().listFiles()) {
	    		if (file.isDirectory()) {
	    			dirs.push( file );
	    		}
	    		else if (file.canRead()) {
	    			files.add( file );
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
	    
	    
	    return files;
	}
	
	
	public static AnnotationsExtractor getInstance(){
		return instance;
	}
	
	public static void inspectJavaFile(File pFile) 	
    throws FileNotFoundException, ParseException, IOException {
		
   	System.out.println("file="+pFile.toString());
    	
        CompilationUnit cu;
        FileInputStream in = new FileInputStream(pFile);
       
        try {
            cu = JavaParser.parse(in);
           
        } finally {
            in.close();
        }
        try {
        	new MethodVisitor().visit(cu, null);
        	if(rootFound){
        		System.out.println("file with root is="+pFile.toString());
        		fileWithRootElement=pFile;
        		rootFound=false;
        	}
        	
        }
        catch (NullPointerException npe) {
        		
        	//TODO Output
        }
    }
	
	
	/**
     * Simple visitor implementation for visiting MethodDeclaration nodes.
     */
    private static class MethodVisitor extends VoidVisitorAdapter {

    	 @Override
    	    public void visit(MethodDeclaration n, Object arg)  {
    	    	//GUI-testen
    	    	//----------------------------------------------------------------------	;
//    	    	System.out.println(n.getAnnotations().size());
    	    	
    	    	
    	        if (n.getAnnotations() != null) {
    	        	
    	            for (AnnotationExpr annotation : n.getAnnotations()) {
//    	            	System.out.println("hier");
//    	                System.out.println(annotation.getClass().toString());              
    	            	                
    	                String annotationType = annotation.getName().toString();
//    	                System.out.println(annotationType);
    	                
    	            	if (annotationType.equals("Element")){
    	            		    	            		
    	            		String id="";
    	            		String type="";
    	            		String label="";
    	            		String hoverText="";
    	            		String standardValue="";
    	            		String domain="";
    	           		
//    	            		System.out.println(annotation.toString());
    	            		String a= annotation.toString();
    	            		a=a.substring(9, a.length()-1);
    	            		String[] b=a.split(", ");
    	            		
    	            		for(int i=0;i<b.length;i++){
    	 //           			System.out.println(b[i]);
    	            			String c[]= b[i].split(" = ");
    	            			
//    	           			for(int j=0;j<c.length;j++){
//    	           				System.out.println(c[j]);
//    	           			}
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
 //   	            		ParentPanel parentPanel;
//    	            		if(addToPanel==true){
// //   	            			parentPanel=panelsList.getLast();
//    	            			elemBelongsTo.add(0);
//    	            		}else{
// //   	            			parentPanel=listPanelslist.getLast();
//    	            			elemBelongsTo.add(1);
//    	            		}
    	            		elemBelongsTo.add(indexOfParentPanel);
    	            		
    		   //      		String type, String label,String title, String standartV,String domain, String id, ParentPanel parent
    		          		ElementG element= new ElementG(type,label,hoverText,standardValue,domain,id,null);
    		          		
    		          		elementsList.add(element);	
    		          		System.out.println("Element " +element.getTitle() +"gefunden!");
    	            	}else if (annotationType.equals("NormalPanel")){
    	            		String title="";
    	           		
//    	            		System.out.println(annotation.toString());
    	            		String a= annotation.toString();
    	            		a=a.substring(8, a.length()-1);

    	            		String c[]= a.split(" = ");
    	            		c[1]=c[1].substring(1, c[1].length()-1);
    	            		title=c[1];
    	            	//	String title , ParentPanel parent,BigRootPanel bRP
    	            		BigPanel panel=new BigPanel(title);
    	            	//	panelsList.add(panel);
    	            		parentsList.add(panel);
    	            		indexOfParentPanel++;
    	            	//	addToPanel=true;	
    	            		System.out.println("panel " +panel.getTitle() +"gefunden!");
    	            	}else if (annotationType.equals("ListPanel")){
    	            		String id="";
    	            		String label="";
    	            		String hoverText= "";
    	            		int limit=-1;
    	            		String uRI="";
    	          		
//    	            		System.out.println(annotation.toString());
    	            		String a= annotation.toString();
    	            		a=a.substring(11, a.length()-1);
    	            		String[] b=a.split(", ");
    	            		
    	            		for(int i=0;i<b.length;i++){
 //   	            			System.out.println(b[i]);
    	            			String c[]= b[i].split(" = ");
    	            			
    	            			for(int j=0;j<c.length;j++){
 //   	           				System.out.println(c[j]);
    	            			}
    	            			
    	            			if(c[0].equals("id")){
    	            				c[1]=c[1].substring(1, c[1].length()-1);
    	            				id=c[1];
//    	            				System.out.print(id);
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
    	            	
    	       //     	addToPanel=false;	
//    	            		public BigListPanel(String title , String id ,String label,int limit,String uRI,BigRootPanel bRP)
    	          		BigListPanel listPanel=new BigListPanel(hoverText,id,label,limit,uRI);
    	          		parentsList.add(listPanel);
	            		indexOfParentPanel++;
    	      //    		listPanelslist.add(listPanel);
	            		System.out.println("listpanel " +listPanel.getTitle() +"gefunden!");
//    	          		String label,String title, String limit,String domain,ParentPanel parent
//    	          		ListG listG=new ListG(label,hoverText,limit,uRI,null);
    	          		
    	            	}else if (annotationType.equals("Root")){
    	            		
    	            		//file with root found
    	            		rootFound=true;
    	            		
    	            		String id="";
    	            		String title="";
    	            		String useCaseName="";
    	           		
//    	            		System.out.println(annotation.toString());
    	            		String a= annotation.toString();
    	            		a=a.substring(6, a.length()-1);
    	            		String[] b=a.split(", ");
    	            
    	            		
    	            		for(int i=0;i<b.length;i++){
    	 //           			System.out.println(b[i]);
    	            			String c[]= b[i].split(" = ");
    	            			c[1]=c[1].substring(1, c[1].length()-1);
//    	            			for(int j=0;j<c.length;j++){
//    	            				System.out.println(c[j]);
//    	            			}

    	            			if(c[0].equals("id")){

    	            				id=c[1];

    	            			}else if(c[0].equals("title")){
    	            				title=c[1];
    	            			}else if(c[0].equals("useCaseName")){
    	            				useCaseName=c[1];
    	            			}
    	            		}
    	   //      		String id ,String name, String title, String help
    	            		 
    	          		root= new Root(id,useCaseName,title,"");
    	        		System.out.println("root " +root.getTitle() +"gefunden!");
    	            	}
    	            	
 //   	            	System.out.println("loop");		
    	            }	
    	            
    	       } 
       
    	    }
    }
	
	
	
    /**
	 * Returns all directories of workspace
	 * 
	 * @return all directories of workspace
	 */
	public DefaultListModel getDirs() {				
		File dir = new File(Platform.getLocation().toString());		
		DefaultListModel allDirs = new DefaultListModel();
	    File[] files = dir.listFiles();
		if (files != null) { // read of file(s) permitted
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					System.out.println("Directory "+files[i].toString());
					allDirs.addElement(files[i]);
				}
			}
		}
		return allDirs;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
