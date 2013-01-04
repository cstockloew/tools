package org.universaal.tools.conformanceTools.run;

public class Result {

	private String checkName, checkDescription, resultImg, resultDscr; 

	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public String getCheckDescription() {
		return checkDescription;
	}

	public void setCheckDescription(String checkDescription) {
		this.checkDescription = checkDescription;
	}

	public String getResultImg() {
		return resultImg;
	}

	public void setResultImg(String resultImg) {
		this.resultImg = resultImg;
	}

	public String getResultDscr() {
		return resultDscr;
	}

	public void setResultDscr(String resultDscr) {
		this.resultDscr = resultDscr;
	}

	public Result(String checkName, String checkDescription, String resultImg, String resultDscr){
		this.checkName = checkName;
		this.checkDescription = checkDescription;
		this.resultImg = resultImg;
		this.resultDscr = resultDscr;
	}
}
