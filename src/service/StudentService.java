package service;

import java.util.List;

import controller.LoginUser;
import dao.StudentDao;
import vo.Course;
import vo.Student;

public class StudentService {
	
	// 기능이 구현되어 있는 객체는 객체를 한 개만 생성해야하기 때문에
	// 싱글톤 패턴을 구현한다.
	private static StudentService instance = new StudentService();
	private StudentService() {};
	public static StudentService getInstance() {
		return instance;
	}
	
	private StudentDao studentDao = StudentDao.getInstance();
	
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
		
		// 탈퇴한 수강생인지 체크 탈퇴했다고 데이터가 삭제되는 게 아니라 아이디는 남아있다. 그렇기 때문에
		// 삭제 여부가 N,인지 Y인지를 조회해서 로그인을 막는 예외를 던진다.
		// getDeleted의 실행결과가 null 일수도 있다. null이어도 에러가 나지 않는다. 상수 먼저 적고 비교해야 한다.
		if("Y".equals(student.getDeleted())) {
			throw new RuntimeException("삭제된 아이디입니다.");
		}
		
		// getDeleted의 실행결과가 null일수도 있다. 그렇게 되면 null.equals메서드를 실행할 수 없고 nullpointexeption 발생
//		if(student.getDeleted().equals("Y")) {
//			throw new RuntimeException("삭제된 아이디입니다.");
//		}
		
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
