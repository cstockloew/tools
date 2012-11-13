package org.universaal.tools.conformanceTools.utils;

import java.io.File;
import java.io.PrintStream;

public class HtmlPage {

	private String page;

	private Head header;
	private Body body;

	private PrintStream out;

	public HtmlPage(String headerText) {

		this.header = new Head();
		this.body = new Body(headerText);

		this.page = this.header.getHeader() + this.body.getBody();
	}

	private String getPre(){
		return "<html>";
	}
	private String getPost(){
		return "</html>";
	}

	public void write(File file){

		try{
			out = new PrintStream(file);
			out.append(getPre());
			out.append(getPage());
			out.append(getPost());
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			out.close();
		}
	}

	public Head getHeader(){
		return this.header;
	}
	public Body getBody(){
		return this.body;
	}
	public String getPage(){
		return this.header.getHeader() + this.body.getBody();
	}


	private class Head{

		private String header;

		private Head(){
			header = "<head></head>";
		}

		public String getHeader(){
			return this.header;
		}
	}


	public class Body{

		private String body;

		private Body(String headerText){
			this.body= "<h1><font color='red'><center>"+headerText+"</center></font></h1>";
		}

		public String getBody(){
			openBody();
			closeBody();
			return this.body;
		}

		private void openBody(){
			if(!this.body.startsWith("<body>"))
				this.body = "<body>" + this.body;
		}
		private void closeBody(){
			if(!this.body.endsWith("</body>"))
				this.body = this.body + "</body>";
		}

		public void addElement(String element){
			this.body = this.body.concat(element);
		}
	}

	public class Table{

		private String table;
		private String[][] arrayTable;
		private int rows, columns;

		public Table(int rows, int columns){
			this.table = "";
			this.arrayTable = new String[rows][columns]; // rows x columns
			this.rows = rows;
			this.columns = columns;
		}

		public void addContent(String content, int row, int column){
			this.arrayTable[row][column] = content;
		}
		
		public void addContentCentered(String content, int row, int column){
			addContent("<center>"+content+"</center>", row, column);
		}

		public String getTable(){
			
			this.table = "<table border='1' width='95%'>";
			for(int i = 0; i < this.rows; i++){
				this.table = this.table.concat("<tr>");
				for(int j = 0; j < columns; j++){
					if(this.arrayTable[i][j] != null && !this.arrayTable[i][j].isEmpty())
						this.table = this.table.concat("<td style='padding-left: 20px;'>"+this.arrayTable[i][j]+"</td>");
					else
						this.table = this.table.concat("<td style='padding-left: 20px;'></td>");
				}
				this.table = this.table.concat("</tr>");
			}
			this.table = this.table.concat("</table>");
			
			return this.table;
		}
	}
}