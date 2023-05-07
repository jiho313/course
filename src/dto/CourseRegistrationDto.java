package dto;

import java.util.Date;

public class CourseRegistrationDto {
	
	private int regNo;
	private Date regCreateDate;
	private String regCanceled;
	private String courseName;
	public int getRegNo() {
		return regNo;
	}
	public void setRegNo(int regNo) {
		this.regNo = regNo;
	}
	public Date getRegCreateDate() {
		return regCreateDate;
	}
	public void setRegCreateDate(Date regCreateDate) {
		this.regCreateDate = regCreateDate;
	}
	public String getRegCanceled() {
		return regCanceled;
	}
	public void setRegCanceled(String regCanceled) {
		this.regCanceled = regCanceled;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	
	
	
	

}
