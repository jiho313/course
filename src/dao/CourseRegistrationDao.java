package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.ConnUtils;
import vo.CourseRegistration;

public class CourseRegistrationDao {
	
	private static CourseRegistrationDao instance = new CourseRegistrationDao();
	private CourseRegistrationDao() {};
	public static CourseRegistrationDao getInstance() {
		return instance;
	}
	
	public void updateCourseRegistrationByCourseNo(CourseRegistration reg) {
		String sql = "update academy_course_registrations "
				+ "set reg_canceled = ? "
				+ "where course_no = ? ";
		try {
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, reg.getRegCanceled());
			pstmt.setInt(2, reg.getCourseNo());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
	
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}		
	
	public void updateCourseRegistrationByRegNo(CourseRegistration reg) {
		String sql = "update academy_course_registrations "
				+ "set reg_canceled = ? "
				+ "where reg_no = ? ";
		try {
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, reg.getRegCanceled());
			pstmt.setInt(2, reg.getRegNo());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
	
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}		
	
	// 과정 번호로 개설한 수강과정 조회하기
	public CourseRegistration getRegistrationByCourseNo(String studentId, int courseNo) {
		String sql = "select reg_no, student_id, course_no, reg_canceled, reg_create_date "
				+ "from academy_course_registrations "
				+ "where course_no = ? "
				+ "and student_id = ?";
		try {
			CourseRegistration reg = null;
			
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, courseNo);
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
	// 과정 번호를 입력받아 수강과정 조회하기
	public CourseRegistration getRegistrationByCourseNo(int courseNo) {
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

	// 수강신청 번호를 입력받아 수강신청한 수강과정 조회하기
	public CourseRegistration getRegistrationByRegNo(int regNo) {
		String sql = "select reg_no, student_id, course_no, reg_canceled, reg_create_date "
				+ "from academy_course_registrations "
				+ "where reg_no = ? ";
		try {
			CourseRegistration reg = null;
			
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, regNo);
			
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
	
	// 학생 아이디로 신청한 수강신청 목록 모두 조회하기
	public List<Map<String, Object>> getRegistrationsById(String studentId) {
		String sql = "select r.reg_no, r.reg_create_date, r.reg_canceled, c.course_name "
				+ "from academy_course_registrations r, academy_courses c "
				+ "where r.course_no = c.course_no "
				+ "and student_id = ? ";
		try {
			List<Map<String, Object>> regs = new ArrayList<>();
			
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, studentId);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Map<String, Object> map = new HashMap<>();
				
				map.put("no", rs.getInt("reg_no"));
				map.put("createDate", rs.getDate("reg_create_date"));
				map.put("canceled", rs.getString("reg_canceled"));
				map.put("name", rs.getString("course_name"));
				
				regs.add(map);
			}
			
			rs.close();
			pstmt.close();
			conn.close();

			return regs;
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}		
	}
	
	// 수강 신청하기
	public void insertCourseRegistration(String studentId, int courseNo) {
		String sql = "insert into academy_course_registrations " 
				+ "(reg_no, student_id, course_no) " + "values "
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
