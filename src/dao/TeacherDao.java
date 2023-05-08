package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.CourseDetailDto;
import util.ConnUtils;
import vo.Course;
import vo.Teacher;

public class TeacherDao {

	private static TeacherDao instance = new TeacherDao();
	private TeacherDao() {};
	public static TeacherDao getInstance() {
		return instance;
	}

	// 강좌 번호로 강사가 개설한 강좌 상세 조회
	public List<CourseDetailDto> getCourseDetailDtoByNo(int courseNo, String teacherId){
		String sql = "select c.course_no, c.course_name, c.course_quota, c.course_req_cnt, c.course_status, "
				+ "c.course_create_date, c.teacher_id, r.student_id "
				+ "from academy_courses c, academy_course_registrations r "
				+ "where c.course_no = r.course_no "
				+ "and c.course_no = ? "
				+ "and teacher_id = ? "
				+ "order by c.course_no asc";  
		try {
			List<CourseDetailDto> dtos = new ArrayList<>();
			
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, courseNo);
			pstmt.setString(2, teacherId);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				CourseDetailDto dto = new CourseDetailDto();
				
				dto.setCourseNo(rs.getInt("course_no"));
				dto.setCourseName(rs.getString("course_name"));
				dto.setCourseQuota(rs.getInt("course_quota"));
				dto.setReqCnt(rs.getInt("course_req_cnt"));
				dto.setStatus(rs.getString("course_status"));
				dto.setCourseCreateDate(rs.getDate("course_create_date"));
				dto.setTeacherId(rs.getString("teacher_id"));
				dto.setStudentId(rs.getString("student_id"));
				
				dtos.add(dto);
			}

			rs.close();
			pstmt.close();
			conn.close();

			return dtos;

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	// 강사가 개설한 강좌 조회
	public List<Course> getCreatedCourses(String teacherId){
		String sql = "select course_no, course_quota, course_req_cnt, course_status, course_name "
				+ "from academy_courses "
				+ "where teacher_id = ? "
				+ "order by course_no asc";
		try {
			List<Course> courses = new ArrayList<>();
			
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, teacherId);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Course course = new Course();
				
				course.setNo(rs.getInt("course_no"));
				course.setquota(rs.getInt("course_quota"));
				course.setReqCnt(rs.getInt("course_req_cnt"));
				course.setStatus(rs.getString("course_status"));
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
	
	// 과정 개설하기
	public void insertCourse(Course course) {
		String sql = "insert into academy_courses "
				+ "(course_no, course_name, course_quota, teacher_id) "
				+ "values "
				+ "(courses_seq.nextval, ?, ?, ?) ";
		try {
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, course.getName());
			pstmt.setInt(2, course.getquota());
			pstmt.setString(3, course.getTeacherId());

			pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}		
	}
		
	// 강사 계정 중복체크
	public Teacher getTeacherById(String id) {
		String sql = "select teacher_id, teacher_password, teacher_name, teacher_phone, teacher_email, "
				+ "teacher_salary, teacher_retired, teacher_create_date " 
				+ "from academy_teachers "
				+ "where teacher_id = ?";
		try {
			Teacher teacher = null;
			
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				teacher = new Teacher();
				teacher.setId(rs.getString("teacher_id"));
				teacher.setPassword(rs.getString("teacher_password"));
				teacher.setName(rs.getString("teacher_name"));
				teacher.setPhone(rs.getString("teacher_phone"));
				teacher.setEmail(rs.getString("teacher_email"));
				teacher.setSalary(rs.getInt("teacher_salary"));
				teacher.setRetired(rs.getString("teacher_retired"));
				teacher.setCreateDate(rs.getDate("teacher_create_date"));
			}

			rs.close();
			pstmt.close();
			conn.close();

			return teacher;

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	// 강사 계정 생성
	public void insertTeacher(Teacher teacher) {
		String sql = "insert into academy_teachers "
				+ "(teacher_id, teacher_password, teacher_name, teacher_phone, teacher_email, teacher_salary) "
				+ "values " 
				+ "(?, ? ,? ,? ,?, ?) ";

		try {
			Connection conn = ConnUtils.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, teacher.getId());
			pstmt.setString(2, teacher.getPassword());
			pstmt.setString(3, teacher.getName());
			pstmt.setString(4, teacher.getPhone());
			pstmt.setString(5, teacher.getEmail());
			pstmt.setInt(6, teacher.getSalary());

			pstmt.executeUpdate();

			pstmt.close();
			conn.close();

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

}
