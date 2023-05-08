package service;

import java.util.List;
import java.util.Map;

import dao.CourseDao;
import dao.CourseRegistrationDao;
import vo.Course;
import vo.CourseRegistration;

public class CourseRegistrationService {

	private static CourseRegistrationService intance = new CourseRegistrationService();

	private CourseRegistrationService() {
	};

	public static CourseRegistrationService getInstance() {
		return intance;
	}

	private CourseDao courseDao = CourseDao.getInstance();
	private CourseRegistrationDao courseRegistrationDao = CourseRegistrationDao.getInstance();

	// 학생 아이디로 신청한 과정 모두 조회하기
	public List<Map<String, Object>> getAllRegistrationsByNoI(String studentId) {
		List<Map<String, Object>> regs = courseRegistrationDao.getRegistrationsById(studentId);
		if (regs.isEmpty()) {
			throw new RuntimeException("신청한 과정이 존재하지 않습니다.");
		}
		return regs;
	}

	// 학생 신청한 과정신청 취소하기
	public void cancelCourseRegistration(int regNo, String studentId) {
		CourseRegistration reg = courseRegistrationDao.getRegistrationByRegNo(regNo);
		if (reg == null) {
			throw new RuntimeException("입력한 신청 과정이 존재하지 않습니다.");
		}

		if ("Y".equals(reg.getRegCanceled())){
			throw new RuntimeException("이미 수강취소처리된 과정입니다.");
		}
		
		if (!studentId.equals(reg.getStudentId())) {
			throw new RuntimeException("본인이 신청한 과정만 수강취소할 수 있습니다.");
		}
		
		reg.setRegCanceled("Y");
		courseRegistrationDao.updateCourseRegistrationByRegNo(reg);

		Course course = courseDao.getCouseByNo(reg.getCourseNo());
		course.setReqCnt(course.getReqCnt() - 1);
		if ("모집완료".equals(course.getStatus())) {
			course.setStatus("모집중");
		}
		courseDao.updateCourse(course);
	}

	// 과정 신청하기
	public void registerCourse(String studentId, int courseNo) {
		Course course = courseDao.getCouseByNo(courseNo);
		if (course == null) {
			throw new RuntimeException("개설된 과정이 존재하지 않습니다.");
		}
		
		if (!"모집중".equals(course.getStatus())) {
			throw new RuntimeException("이미 모집이 완료된 과정입니다.");
		}

		// 중복 여부  
		CourseRegistration reg = courseRegistrationDao.getRegistrationByCourseNo(studentId, courseNo);
		if (reg != null) {
			throw new RuntimeException("이미 수강신청(취소)된 과정입니다.");
		}

		course.setReqCnt(course.getReqCnt() + 1);
		if (course.getquota() == course.getReqCnt()) {
			course.setStatus("모집완료");
		}
		courseDao.updateCourse(course);
		courseRegistrationDao.insertCourseRegistration(studentId, courseNo);

	}
}
