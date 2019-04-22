package com.sp.ex3.board.model;

import java.util.Date;

public class BoardVO {

	private int no;
	private String memberid;
	private String password;
	private String title;
	private String content;
	private Date regdate;
	private int newImgTerm;
	private long cnt;
	private String ip_address;

	private String fileName;
	private double fileSize;
	private long downCount;

	private int groupno;
	private int step;
	private int sortno;
	private String delflag;

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public int getNewImgTerm() {
		return newImgTerm;
	}

	public void setNewImgTerm(int newImgTerm) {
		this.newImgTerm = newImgTerm;
	}

	public long getCnt() {
		return cnt;
	}

	public void setCnt(long cnt) {
		this.cnt = cnt;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public double getFileSize() {
		return fileSize;
	}

	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}

	public long getDownCount() {
		return downCount;
	}

	public void setDownCount(long downCount) {
		this.downCount = downCount;
	}

	public int getGroupno() {
		return groupno;
	}

	public void setGroupno(int groupno) {
		this.groupno = groupno;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getSortno() {
		return sortno;
	}

	public void setSortno(int sortno) {
		this.sortno = sortno;
	}

	public String getDelflag() {
		return delflag;
	}

	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}

	@Override
	public String toString() {
		return "BoardVO [no=" + no + ", memberid=" + memberid + ", password=" + password + ", title=" + title
				+ ", content=" + content + ", regdate=" + regdate + ", newImgTerm=" + newImgTerm + ", cnt=" + cnt
				+ ", ip_address=" + ip_address + ", fileName=" + fileName + ", fileSize=" + fileSize + ", downCount="
				+ downCount + ", groupno=" + groupno + ", step=" + step + ", sortno=" + sortno + ", delflag=" + delflag
				+ "]";
	}

}
