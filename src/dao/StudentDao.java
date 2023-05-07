package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.ConnUtils;
import vo.Course;
import vo.Student;

public class StudentDao {
	
	// 상태가 '모집중'인 과정을 모두 조회하기
	public List<Course> getCourses() {
		String sql = "select c.course_no, c.course_quota, c.course_req_cnt, t.teacher_id, c.course_name "
				+ "from academy_courses c, academy_teachers t "
				+ "where c.teacher_id = t.teacher_id "
				+ "and c.course_status = '모집중' "
				+ "order by c.course_no asc";
		try {
			List<Course> courses = new ArrayList<>();

			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Course course =  new Course();
				course.setNo(rs.getInt("course_no"));
				course.setquota(rs.getInt("course_quota"));
				course.setReqCnt(rs.getInt("course_req_cnt"));
				course.setTeacherId(rs.getString("teacher_id"));
				course.setName(rs.getString("course_name"));
				
				courses.add(course);
			}
			
			rs.close();
			pstmt.close();
			conn.close();
			
			return courses;
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	// 아이디 중복 체크 확인하기
	public Student getStudentById(String id) {
		String sql = "select student_id, student_password, student_name, student_phone, "
				+ "student_email, student_deleted, student_create_date "
				+ "from academy_students "
				+ "where student_id = ? ";
		
		try {
			Student student = null;
			
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				student = new Student();
				student.setId(rs.getString("student_id"));
				student.setPassword(rs.getString("student_password"));
				student.setName(rs.getString("student_name"));
				student.setPhone(rs.getString("student_phone"));
				student.setEmail(rs.getString("student_email"));
				student.setDeleted(rs.getString("student_deleted"));
				student.setCreateDate(rs.getDate("student_create_date"));
			}
			
			rs.close();
			pstmt.close();
			conn.close();
			
			return student;
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}		
		
	}
	// 학생용 계정 회원가입 시키기
	public void insertStudent (Student student) {
		String sql = "insert into academy_students "
				+"(student_id, student_password, student_name, student_phone, student_email) "
				+ "values "
				+ "(?, ?, ?, ?, ?) ";
		try {
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, student.getId());
			pstmt.setString(2, student.getPassword());
			pstmt.setString(3, student.getName());
			pstmt.setString(4, student.getPhone());
			pstmt.setString(5, student.getEmail());
			
			pstmt.executeUpdate();
			
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
		
	}
	


}
