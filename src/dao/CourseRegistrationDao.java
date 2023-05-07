package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.CourseRegistrationDto;
import util.ConnUtils;
import vo.CourseRegistration;

public class CourseRegistrationDao {
	
	// 수강신청 번호를 입력 받아 수강신청 정보 변경하기
	public void canceledCourseRegistrationByCourseNo(int courseNo) {
		String sql = "update academy_course_registrations "
				+ "set reg_canceled = 'Y' "
				+ "where course_no = ? ";
		try {
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, courseNo);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
	
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}		
	
	public void canceledCourseRegistrationByRegNo(int regNo) {
		String sql = "update academy_course_registrations "
				+ "set reg_canceled = 'Y' "
				+ "where reg_no = ? ";
		try {
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, regNo);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
	
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}	
	
	// 과정 번호로 개설한 수강과정 조회하기
	public CourseRegistration getRegistrationsByCourseNo(int courseNo) {
		String sql = "select reg_no, student_id, course_no, reg_canceled, reg_create_date "
				+ "from academy_course_registrations "
				+ "where course_no = ? ";
		try {
			CourseRegistration reg = null;
			
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, courseNo);

			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				reg = new CourseRegistration();
				
				reg.setRegNo(rs.getInt("reg_no"));
				reg.setStudentId(rs.getString("student_id"));
				reg.setCourseNo(rs.getInt("course_no"));
				reg.setRegCanceled(rs.getString("reg_canceled"));
				reg.setRegCreateDate(rs.getDate("reg_create_date"));

			}
			
			rs.close();
			pstmt.close();
			conn.close();

			return reg;
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}				
	}
	
	// 과정 번호와 학생 아이디로 수강신청한 수강과정 조회하기
	public CourseRegistration getRegistrationsByRegNoStudentId(int regNo, String studentId) {
		String sql = "select reg_no, student_id, course_no, reg_canceled, reg_create_date "
				+ "from academy_course_registrations "
				+ "where reg_no = ? "
				+ "and student_id = ?";
		try {
			CourseRegistration reg = null;
			
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, regNo);
			pstmt.setString(2, studentId);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				reg = new CourseRegistration();
				
				reg.setRegNo(rs.getInt("reg_no"));
				reg.setStudentId(rs.getString("student_id"));
				reg.setCourseNo(rs.getInt("course_no"));
				reg.setRegCanceled(rs.getString("reg_canceled"));
				reg.setRegCreateDate(rs.getDate("reg_create_date"));

			}
			
			rs.close();
			pstmt.close();
			conn.close();

			return reg;
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}				
	}
	
	// 학생 아이디로 신청한 과정 모두 조회하기
	public List<CourseRegistrationDto> getRegistrationsById(String studentId) {
		String sql = "select r.reg_no, r.reg_create_date, r.reg_canceled, c.course_name "
				+ "from academy_course_registrations r, academy_courses c "
				+ "where r.course_no = c.course_no "
				+ "and student_id = ? ";
		try {
			List<CourseRegistrationDto> dtos = new ArrayList<>();
			
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, studentId);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CourseRegistrationDto reg = new CourseRegistrationDto();
				
				reg.setRegNo(rs.getInt("reg_no"));
				reg.setRegCreateDate(rs.getDate("reg_create_date"));
				reg.setRegCanceled(rs.getString("reg_canceled"));
				reg.setCourseName(rs.getString("course_name"));
				
				dtos.add(reg);
			}
			
			rs.close();
			pstmt.close();
			conn.close();

			return dtos;
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}		
	}
	
	// 학생 아이디로 신청한 과정 조회하기
	public CourseRegistration getSingleRegistrationById(String studentId) {
		String sql = "select reg_no, student_id, course_no"
				+ ", reg_canceled, reg_create_date "
				+ "from academy_course_registrations "
				+ "where student_id = ? ";
		try {
			CourseRegistration reg = null;
			
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, studentId);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				reg = new CourseRegistration();
				reg.setRegNo(rs.getInt("reg_no"));
				reg.setStudentId(rs.getString("student_id"));
				reg.setCourseNo(rs.getInt("course_no"));
				reg.setRegCanceled(rs.getString("reg_canceled"));
				reg.setRegCreateDate(rs.getDate("reg_create_date"));
			}

			pstmt.close();
			conn.close();

			return reg;
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}		
	}
	// 과정신청하기
	public void insertCourseRegistration(String studentId, int courseNo) {
		String sql = "insert into academy_course_registrations " + "(reg_no, student_id, course_no) " + "values "
				+ "(reg_seq.nextval, ?, ?) ";
		try {
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, studentId);
			pstmt.setInt(2, courseNo);

			pstmt.executeUpdate();

			pstmt.close();
			conn.close();

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
}
