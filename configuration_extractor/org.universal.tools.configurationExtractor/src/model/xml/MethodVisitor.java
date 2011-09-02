//
//package model.xml;
//
//import japa.parser.ast.body.MethodDeclaration;
//import japa.parser.ast.expr.AnnotationExpr;
//import japa.parser.ast.expr.MarkerAnnotationExpr;
//import japa.parser.ast.visitor.VoidVisitorAdapter;
//
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.LinkedList;
//
//import view.Elements.BigElement;
//import view.Elements.ElementG;
//import view.Elements.ListG;
//import view.Elements.ListPanel;
//import view.Elements.PanelG;
//import view.Elements.Root;
//import view.LowLevelPanels.BigListPanel;
//import view.LowLevelPanels.BigPanel;
//import view.LowLevelPanels.BigRootPanel;
//import view.LowLevelPanels.ParentPanel;
//import view.MainPanels.LeftPanel;
//
//import model.xml.Annotations.Element;
//
//public class MethodVisitor extends VoidVisitorAdapter<Object> {
//	LinkedList<BigElement> elementsList=new LinkedList<BigElement>();
//	LinkedList<BigPanel> panelsList=new LinkedList<BigPanel>();
//	LinkedList<BigListPanel> listPanelslist=new LinkedList<BigListPanel>();
//	Root root=null;
//	
//	/**
//	 * Visits method declarations in java files and extracts annotations
//	 * 
//	 * @param	n	parsed java file containing methods to extract annotations
//	 * @param	arg	needed to override superclass, set to "null"
//	 */
//    @Override
//    public void visit(MethodDeclaration n, Object arg)  {
//    	//GUI-testen
//    	//----------------------------------------------------------------------	;
//    	System.out.println(n.getAnnotations().size());
//    	
//    	
//        if (n.getAnnotations() != null) {
//        	
//            for (AnnotationExpr annotation : n.getAnnotations()) {
//            	System.out.println("hier");
//                System.out.println(annotation.getClass().toString());              
//            	                
//                String annotationType = annotation.getName().toString();
//                System.out.println(annotationType);
//                
//            	if (annotationType.equals("Element")){
//            		
//            		String id="";
//            		String type="";
//            		String label="";
//            		String hoverText="";
//            		String standardValue="";
//            		String domain="";
//           		
//            		System.out.println(annotation.toString());
//            		String a= annotation.toString();
//            		a=a.substring(9, a.length()-1);
//            		String[] b=a.split(", ");
//            		
//            		for(int i=0;i<b.length;i++){
// //           			System.out.println(b[i]);
//            			String c[]= b[i].split(" = ");
//            			
////           			for(int j=0;j<c.length;j++){
////           				System.out.println(c[j]);
////           			}
////            			
//            			if(c[0]=="id"){
//            				id=c[1];
//            			}else if(c[0]=="type"){
//            				type=c[1];
//            			}else if(c[0]=="label"){
//            				label=c[1];
//            			}else if(c[0]=="hoverText"){
//            				hoverText=c[1];
//            			}else if(c[0]=="standardValue"){
//            				standardValue=c[1];
//            			}else if(c[0]=="domain"){
//            				domain=c[1];
//            			}          			
//       			
//            		}
//            		
//	   //      		String type, String label,String title, String standartV,String domain, String id, ParentPanel parent
//	          		ElementG element= new ElementG(type,label,hoverText,standardValue,domain,id,null);
//	          		
//	          		elementsList.add(element);	
//	          		System.out.println("lalalalal");
//            	}else if (annotationType.equals("Panel")){
//            		String title="";
//           		
//            		System.out.println(annotation.toString());
//            		String a= annotation.toString();
//            		a=a.substring(8, a.length()-1);
//
//            		String c[]= a.split(" = ");
//            		title=c[1];
//            	//	String title , ParentPanel parent,BigRootPanel bRP
//            		BigPanel panel=new BigPanel(title,null);
//            		panelsList.add(panel);
//            	}else if (annotationType.equals("ListPanel")){
//            		String id="";
//            		String label="";
//            		String hoverText= "";
//            		int limit=-1;
//            		String uRI="";
//          		
//            		System.out.println(annotation.toString());
//            		String a= annotation.toString();
//            		a=a.substring(13, a.length()-1);
//            		String[] b=a.split(", ");
//            		
//            		for(int i=0;i<b.length;i++){
// //           			System.out.println(b[i]);
//            			String c[]= b[i].split(" = ");
//            			
//            			for(int j=0;j<c.length;j++){
//           				System.out.println(c[j]);
//            			}
//            			
//            			if(c[0]=="id"){
//            				id=c[1];
//            			}else if(c[0]=="label"){
//            				label=c[1];
//            			}else if(c[0]=="hoverText"){
//            				hoverText=c[1];
//            			}else if(c[0]=="limit"){
//            				c[1]=c[1].substring(1, c[1].length()-1);
//          					  limit = Integer.parseInt(c[1]);
//            			}else if(c[0]=="uRI"){
//            				uRI=c[1];
//            			}
//            		}
//            		
////            		String title, String id , ParentPanel parent, BigRootPanel bRP
//          		BigListPanel listPanel=new BigListPanel(hoverText,id,null);
//          		listPanelslist.add(listPanel);
//          		
////          		String label,String title, String limit,String domain,ParentPanel parent
//          		ListG listG=new ListG(label,hoverText,limit,uRI,null);
//          		
//            	}else if (annotationType.equals("Root")){
//            		String id="";
//            		String title="";
//            		String useCaseName="";
//           		
//            		System.out.println(annotation.toString());
//            		String a= annotation.toString();
//            		a=a.substring(6, a.length()-1);
//            		String[] b=a.split(", ");
//            
//            		
//            		for(int i=0;i<b.length;i++){
// //           			System.out.println(b[i]);
//            			String c[]= b[i].split(" = ");
//            			c[1]=c[1].substring(1, c[1].length()-1);
////            			for(int j=0;j<c.length;j++){
////            				System.out.println(c[j]);
////            			}
//            			
//            			if(c[0]=="id"){
//            				id=c[1];
//            			}else if(c[0]=="title"){
//            				title=c[1];
//            			}else if(c[0]=="useCaseName"){
//            				useCaseName=c[1];
//            			}
//            		}
//   //      		String id ,String name, String title, String help
//          		root= new Root(id,useCaseName,title,"");
//            	}
//            	
//            	System.out.println("loop");		
//            }	
//            
//       } 
//        
////        BigRootPanel bigRootPanel= BigRootPanel.getInstance();
////        bigRootPanel.setRoot(root);		
////     
////        LeftPanel lf= LeftPanel.getInstance();
////		lf.initElementsList(elementsList);
//        
//    }
//}