package dto;

import java.util.Date;

public class CourseDetailDto {
	
	private int courseNo;
	private String courseName;
	private int courseQuota;
	private int reqCnt;
	private String status;
	private Date courseCreateDate;
	private String teacherId;
	private String studentId;
	
	public int getCourseNo() {
		return courseNo;
	}
	public void setCourseNo(int courseNo) {
		this.courseNo = courseNo;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getCourseQuota() {
		return courseQuota;
	}
	public void setCourseQuota(int courseQuota) {
		this.courseQuota = courseQuota;
	}
	
	public int getReqCnt() {
		return reqCnt;
	}
	
	public void setReqCnt(int reqCnt) {
		this.reqCnt = reqCnt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCourseCreateDate() {
		return courseCreateDate;
	}
	
	public void setCourseCreateDate(Date courseCreateDate) {
		this.courseCreateDate = courseCreateDate;
	}
	
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	
	

}
