package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.ConnUtils;
import util.DaoHelper;
import vo.Course;

public class CourseDao {
	
	private static CourseDao instance = new CourseDao();
	private CourseDao() {};
	public static CourseDao getInstance() {
		return instance;
	}
	
	// 과정 번호를 입력받아 해당 과정 불러오기
	public Course getCouseByNo(int courseNo) {
		String sql = "select course_no, course_name, course_quota, course_req_cnt, "
				+ "course_status, course_create_date, teacher_id "
				+ "from academy_courses "
				+ "where course_no = ? ";
		try {
			Course course = null;

			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, courseNo);
			
			ResultSet rs = pstmt.executeQuery();

			
			if (rs.next()) {
				course =  new Course();
				
				course.setNo(rs.getInt("course_no"));
				course.setName(rs.getString("course_name"));
				course.setquota(rs.getInt("course_quota"));
				course.setReqCnt(rs.getInt("course_req_cnt"));
				course.setStatus(rs.getString("course_status"));
				course.setCreateDate(rs.getDate("course_create_date"));
				course.setTeacherId(rs.getString("teacher_id"));
			}
			
			rs.close();
			pstmt.close();
			conn.close();
			
			return course;
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}		
		
	// 과정 객체를 입력받아 과정 정보 변경하기 
	public void updateCourse(Course course) {
		String sql = "update academy_courses "
				+ "set course_name = ?, "
				+ 	  "course_quota = ?, "
				+ 	  "course_req_cnt = ?, "
				+ 	  "course_status =? "
				+ "where course_no = ? ";
	DaoHelper.update(sql, 
			course.getName(), 
			course.getquota(), 
			course.getReqCnt(), 
			course.getStatus(),
			course.getNo());
	}
}
