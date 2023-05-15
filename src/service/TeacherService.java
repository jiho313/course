package service;

import java.util.List;

import controller.LoginUser;
import dao.CourseDao;
import dao.CourseRegistrationDao;
import dao.TeacherDao;
import dto.CourseDetailDto;
import vo.Course;
import vo.CourseRegistration;
import vo.Teacher;

public class TeacherService {
	
	private static TeacherService instance = new TeacherService();
	private TeacherService() {};
	public static TeacherService getInstance() {
		return instance;
	}
	
	private TeacherDao teacherDao = TeacherDao.getInstance();
	private CourseDao courseDao = CourseDao.getInstance();
	private CourseRegistrationDao courseRegistrationDao = CourseRegistrationDao.getInstance();

	// 개설한 과정 번호로 강좌 상세 조회하기
	public List<CourseDetailDto> getCourseDetailDtoByCousreNo(int courseNo, String teacherId) {
		Course course = courseDao.getCouseByNo(courseNo);

		if (course == null || !course.getTeacherId().equals(teacherId)) {
			throw new RuntimeException("본인이 개설한 강좌만 상세조회가 가능합니다.");
		}

		return teacherDao.getCourseDetailDtoByNo(courseNo, teacherId);

	}

	// 강사가 자신이 개설한 강좌 정보 폐쇄하기
	public void deleteCourse(int courseNo, String teacherId) {
		Course course = courseDao.getCouseByNo(courseNo);
		CourseRegistration reg = courseRegistrationDao.getRegistrationByCourseNo(courseNo);
		if (course == null) {
			throw new RuntimeException("입력한 강좌가 존재하지 않습니다.");
		}
		if ("과정취소".equals(course.getStatus())) {
			throw new RuntimeException("이미 폐쇄된 강좌입니다.");
		}
		if (!teacherId.equals(course.getTeacherId())) {
			throw new RuntimeException("본인이 개설한 강좌만 폐쇄 가능합니다.");
		}
		
		course.setStatus("과정취소");
		courseRegistrationDao.updateCourseRegistration(reg, true);
		
		reg.setRegCanceled("Y");
		courseDao.updateCourse(course);

	}

	// 모든 개설 강좌 조회
	public List<Course> getAllCreatedCourses(String teacherId) {
		List<Course> courses = teacherDao.getCreatedCourses(teacherId);
		
		if (courses.isEmpty()) {
			throw new RuntimeException("개설된 강좌가 존재하지 않습니다.");
		}
		return courses;
	}

	// 과정 개설하기
	public void createCourse(Course course) {
		teacherDao.insertCourse(course);
	}

	// 강사 로그인
	public LoginUser login(String id, String password) {
		Teacher teacher = teacherDao.getTeacherById(id);

		if (teacher == null) {
			throw new RuntimeException("생성된 아이디가 없습니다.");
		}

		if (!teacher.getPassword().equals(password)) {
			throw new RuntimeException("비밀번호가 일치하지 않습니다.");
		}

		LoginUser user = new LoginUser(teacher.getId(), teacher.getName(), teacher.getType());
		return user;
	}

	// 강사 회원 가입
	public void registerTeacher(Teacher teacher) {
		// 아이디 중복확인
		Teacher savedTeacher = teacherDao.getTeacherById(teacher.getId());

		if (savedTeacher != null) {
			throw new RuntimeException("["+teacher.getId()+"]중복된 아이디가 존재합니다.");
		}

		teacherDao.insertTeacher(teacher);
	}
}
