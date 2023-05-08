package vo;

import java.util.Date;

public class CourseRegistration {

	private int regNo;
	private String studentId;
	private int courseNo;
	private String regCanceled;
	private Date regCreateDate;

	public int getRegNo() {
		return regNo;
	}

	public void setRegNo(int regNo) {
		this.regNo = regNo;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public int getCourseNo() {
		return courseNo;
	}

	public void setCourseNo(int courseNo) {
		this.courseNo = courseNo;
	}

	public String getRegCanceled() {
		return regCanceled;
	}

	public void setRegCanceled(String regCanceled) {
		this.regCanceled = regCanceled;
	}

	public Date getRegCreateDate() {
		return regCreateDate;
	}

	public void setRegCreateDate(Date regCreateDate) {
		this.regCreateDate = regCreateDate;
	}

	@Override
	public String toString() {
		return "CourseRegistration [regNo=" + regNo + ", studentId=" + studentId + ", courseNo=" + courseNo
				+ ", regCanceled=" + regCanceled + ", regCreateDate=" + regCreateDate + "]";
	}

	
}
