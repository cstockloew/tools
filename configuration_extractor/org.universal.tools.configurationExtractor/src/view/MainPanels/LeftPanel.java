//package view.MainPanels;
//import view.Buttons.*;
//import view.Elements.BigElement;
//import view.LowLevelPanels.BigRootPanel;
//import view.LowLevelPanels.ParentPanel;
//
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.util.LinkedList;
//import java.util.Vector;
//
//import javax.swing.Box;
//import javax.swing.BoxLayout;
//import javax.swing.JButton;
//import javax.swing.JLabel;
//import javax.swing.JList;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.tree.DefaultMutableTreeNode;
//
//
//@SuppressWarnings("serial")
//public class LeftPanel extends JPanel{
//	private BigRootPanel bRP;
//	private JScrollPane jsp;
//	private JList jl;
//	private LinkedList <BigElement> elements;
//	private Vector<String> listData;
//	private static LeftPanel instance = new LeftPanel();
//	
//	private TreePanel tree= TreePanel.getInstance();
//	private LinkedList <DefaultMutableTreeNode> leafsNotVisible;
//	
//	private LinkedList <DefaultMutableTreeNode> leafsVisible=new LinkedList <DefaultMutableTreeNode>();
//	
//	
//	
//	
//	private LeftPanel(){
//
//		bRP=BigRootPanel.getInstance();
//	   	this.setPreferredSize(new Dimension(130, 70));
//		this.setLayout( new BoxLayout(this, BoxLayout.Y_AXIS) );
//		
//	  	
//		    	
//		    
//	}
//	public void initElementsList(LinkedList <BigElement> elements){
//		this.elements= elements;
//		init();
//	}
//	public static LeftPanel getInstance(){
//		return instance;
//	}
//	private void init(){
//		JLabel jt =new JLabel("Elementen-Panel");
//		jt.setBackground(Color.DARK_GRAY);
//		
//		this.add(jt);
//		
//		initListData();
//		initTreeList();
//
//		jl =new JList(listData);
//		jsp=new JScrollPane(jl);
//		
//		this.add(jsp);
//		refresh();
//
//		MouseListener mouseListener = new MouseAdapter() {
//		      public void mouseClicked(MouseEvent mouseEvent) {
//		        JList theList = (JList) mouseEvent.getSource();
//		        if (mouseEvent.getClickCount() == 2) {
//		          int numberOfPanels = bRP.getParentsList().size();
//		          int index = theList.locationToIndex(mouseEvent.getPoint());
//		          if (index >= 0) {
//		        	  
//		        	  String genderOptions[]=new String[numberOfPanels] ;
//						for(int i=0;i<numberOfPanels;i++){
//							genderOptions[i]=bRP.getParentsList().get(i).getTitle();
//						}
//						
//							if(genderOptions.length>0){
//								String gender = (String) JOptionPane.showInputDialog( null,
//									          "Panel wählen",
//									          "Bitte Panel wählen",
//									          JOptionPane.PLAIN_MESSAGE,
//									          null, genderOptions,
//									          genderOptions[0] );
//								if(gender!=null){
//									int lastClickedelement= index;
//						//			System.out.println(index);
//									ParentPanel pp=bRP.getParentPanel(gender);
//									BigElement bE=(elements.get(lastClickedelement));
//									
//									DefaultMutableTreeNode child= leafsNotVisible.get(lastClickedelement);
//									DefaultMutableTreeNode parent =bRP.getNode(pp.getTypeOfPanel() + pp.getTitle());
//									tree.addLeaf(child,parent);
//									
//									
//									bE.setParentPanel(pp);
//									pp.addElement(bE);
//								
//									leafsVisible.add(child);
//									leafsNotVisible.remove(lastClickedelement);
//									
//									listData.remove(lastClickedelement);
//									elements.remove(lastClickedelement);
//									refresh();
//								}
//							}	
//		          }
//		        }
//		      }
//		    };
//		jl.addMouseListener(mouseListener);
//		
//		
//		
//			
//		AddPanelButton aPB=new AddPanelButton(bRP);
//		this.add(aPB);
//		this.add(Box.createRigidArea(new Dimension(0,30)));
//		
//		ImportButton imp = new ImportButton();
//		ExportButton exp = new ExportButton();
//		this.add(imp);
//		this.add(exp);
//		}
//	
//	public void addBigElement(BigElement e){
//		listData.add(e.getElementType() +" "+ e.getTitle());
//		elements.add(e);
//		
//		DefaultMutableTreeNode leaf=new DefaultMutableTreeNode(e.getElementType() +" "+ e.getTitle());
//		leafsNotVisible.add(leaf);
//	
//		for(int i=0;i<leafsVisible.size();i++){
//			
//			if((leafsVisible.get(i).toString()).equals(leaf.toString())){
////				System.out.println("ja");
//				tree.removeNode(leafsVisible.get(i));
//				leafsVisible.remove(i);
//			}
//		}
//		
//		refresh();
//	}
//	public void deleteBigElement(BigElement e){
//		int index=elements.indexOf(e);
//		listData.remove(index);
//		elements.remove(index);
//		refresh();
//	}
//	
//	
//	private void initListData(){
//		listData=new Vector <String>();
//		for(int i=0;i<elements.size();i++){
////			System.out.println(elements.get(i).getElementType() +" " +elements.get(i).getTitle());
//			listData.add(elements.get(i).getElementType() +" " +  elements.get(i).getTitle());
//		}
//
//	}
//	
//	private void initTreeList(){
//		leafsNotVisible=new LinkedList <DefaultMutableTreeNode>();
//		for(int i=0;i<listData.size();i++){
//
//			leafsNotVisible.add(new DefaultMutableTreeNode(listData.get(i))) ;
//		}
//
//	}
//	
//	
//	private void refresh(){
//		jsp.setVisible(false);
//		
//		jsp.setVisible(true);
//
//	}
//	
//}
