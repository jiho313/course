package service;

import java.util.List;

import controller.LoginUser;
import dao.StudentDao;
import vo.Course;
import vo.Student;

public class StudentService {
	private StudentDao studentDao = new StudentDao();
	
	// 학생 로그인 상태에서 과정 상태가 '모집중'인 모든 과정 조회하기
	public List<Course> getAllCourses(){
		if (studentDao.getCourses().isEmpty()) {
			throw new RuntimeException("조회된 과정이 없습니다.");
		}
		return studentDao.getCourses();
	} 
	
	// 학생 로그인 하기
	public LoginUser login(String id, String password) {
		Student student = studentDao.getStudentById(id);

		if (student == null) {
			throw new RuntimeException("생성된 아이디가 없습니다.");
		}
		if (!student.getPassword().equals(password)) {
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");
		}
		LoginUser user = new LoginUser(student.getId(), student.getName(), student.getType());
		return user;
	}

	// 아이디 중복 체크
	public void registerStudent(Student student) {
		Student savedStudent = studentDao.getStudentById(student.getId());

		if (savedStudent != null) {
			throw new RuntimeException("[" + student.getId() + "]중복된 아이디입니다.");
		}

		studentDao.insertStudent(student);

	}

}
