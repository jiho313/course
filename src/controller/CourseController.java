package controller;

import java.util.List;

import dto.CourseDetailDto;
import dto.CourseRegistrationDto;
import service.CourseRegistrationService;
import service.StudentService;
import service.TeacherService;
import util.KeyboardReader;
import vo.Course;
import vo.Student;
import vo.Teacher;

public class CourseController {

	private KeyboardReader reader = new KeyboardReader(); // 그럼 얘도 원래는 싱글톤 패턴이 구현되어야 하나?
	private LoginUser loginUser;
	
	// 기능을 가지고 있는 객체로 싱글톤 패턴이 구현되어 있기 때문에 스태틱 메서드 .getInstance메서드를 이용해 
	// 서비스 객체를 생성한다.
	private StudentService studentService = StudentService.getInstance();
	private TeacherService teacherService = TeacherService.getInstance();
	private CourseRegistrationService courseRegistrationService = CourseRegistrationService.getInstance();

	public void menu() {

		try {
			if (loginUser == null) {
				System.out.println("-------------------------------------------------------------");
				System.out.println("1.로그인(학생)  2.로그인(강사)  3.가입(학생)  4.가입(강사)  0.종료");
				System.out.println("-------------------------------------------------------------");
			} else {
				if ("학생".equals(loginUser.getType())) {
					System.out.println("[" + loginUser.getName() + "]님 환영합니다!");
					System.out.println("-------------------------------------------------------------");
					System.out.println("1.과정조회  2.과정신청  3.등록취소  4.신청현황 5.로그아웃 0.종료");
					System.out.println("-------------------------------------------------------------");
				} else if ("강사".equals(loginUser.getType())) {
					System.out.println("[" + loginUser.getName() + "]님 환영합니다!");
					System.out.println("-------------------------------------------------------------");
					System.out.println("1.과정조회  2.과정등록  3.과정취소  4.과정현황 5.로그아웃 0.종료");
					System.out.println("-------------------------------------------------------------");
				}
			}
			System.out.println();
			System.out.print("### 메뉴번호: ");
			int menu = reader.readInt();
			System.out.println();

			if (menu == 0) {
				System.out.println("<< 프로그램 종료 >>");
				System.out.println("### 프로그램을 종료합니다.");
				System.exit(0);
			}

			if (loginUser == null) {
				if (menu == 1) {
					학생로그인();
				} else if (menu == 2) {
					강사로그인();
				} else if (menu == 3) {
					학생회원가입();
				} else if (menu == 4) {
					강사회원가입();
				}
				
			} else {
				if ("학생".equals(loginUser.getType())) {
					if (menu == 1) {
						학생과정조회();
					} else if (menu == 2) {
						학생과정신청();
					} else if (menu == 3) {
						학생등록취소();
					} else if (menu == 4) {
						학생신청현황조회();
					} else if (menu ==  5) {
						로그아웃();
					}

				} else if ("강사".equals(loginUser.getType())) {
					if (menu == 1) {
						강사과정조회();
					} else if (menu == 2) {
						강사과정등록();
					} else if (menu == 3) {
						강사과정취소();
					} else if (menu == 4) {
						강사과정현황조회();
					}else if (menu ==  5) {
						로그아웃();
					}

				}
			}

		} catch (

		RuntimeException ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace(System.out);
		}

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();

		menu();

	}

	private void 로그아웃() {
		System.out.println("<< 로그아웃 >>");
		System.out.println("["+loginUser.getName()+"]님 안녕히 가세요^^");
		loginUser = null;
	}
	private void 학생로그인() {
		System.out.println("<< 학생 로그인 >>");
		System.out.println("학생 계정으로 로그인합니다.");
		System.out.println("아이디와 비밀번호를 입력하세요.");

		System.out.print("학생 아이디 입력> ");
		String id = reader.readString();
		System.out.print("학생 비밀번호 입력> ");
		String password = reader.readString();

		loginUser = studentService.login(id, password);
		
		System.out.print("로그인이 완료되었습니다!");

	}

	private void 강사로그인() {
		System.out.println("<< 강사 로그인 >>");
		System.out.println("강사 계정으로 로그인합니다.");
		System.out.println("아이디와 비밀번호를 입력하세요.");

		System.out.print("강사 아이디 입력> ");
		String id = reader.readString();
		System.out.print("강사 비밀번호 입력> ");
		String password = reader.readString();

		loginUser = teacherService.login(id, password);

		System.out.print("로그인이 완료되었습니다!");

	}

	private void 학생회원가입() {
		System.out.println("<< 학생 회원가입 >>");
		System.out.println("### 회원가입할 학생의 아이디, 비밀번호, 이름, 핸드폰번호, 이메일을 입력하세요.");

		System.out.print("사용할 학생 아이디> ");
		String id = reader.readString();
		System.out.print("사용할 학생 비밀번호> ");
		String password = reader.readString();
		System.out.print("가입할 학생 이름> ");
		String name = reader.readString();
		System.out.print("가입할 학생 핸드폰번호> ");
		String phone = reader.readString();
		System.out.print("가입할 학생 이메일> ");
		String email = reader.readString();

		Student student = new Student();
		student.setId(id);
		student.setPassword(password);
		student.setName(name);
		student.setPhone(phone);
		student.setEmail(email);

		studentService.registerStudent(student);

		System.out.println("### 학생 가입이 완료되었습니다.");

	}

	private void 강사회원가입() {
		System.out.println("<< 강사회원가입 >>");
		System.out.println("### 회원가입할 학생의 아이디, 비밀번호, 이름, 핸드폰번호, 이메일을 입력하세요.");

		System.out.print("사용할 강사 아이디> ");
		String id = reader.readString();
		System.out.print("사용할 강사 비밀번호> ");
		String password = reader.readString();
		System.out.print("가입할 강사 이름> ");
		String name = reader.readString();
		System.out.print("가입할 강사 핸드폰번호> ");
		String phone = reader.readString();
		System.out.print("가입할 강사 이메일> ");
		String email = reader.readString();
		System.out.print("가입할 강사 희망연봉> ");
		int salary = reader.readInt();

		Teacher teacher = new Teacher();
		teacher.setId(id);
		teacher.setPassword(password);
		teacher.setName(name);
		teacher.setPhone(phone);
		teacher.setEmail(email);
		teacher.setSalary(salary);
		
		teacherService.registerTeacher(teacher);
		
		System.out.println("### 강사 가입이 완료되었습니다.");
	}

	private void 학생과정조회() {
		System.out.println("<< 학생과정조회 >>");

		List<Course> courses = studentService.getAllCourses();
		
		System.out.println("과정번호\t모집정원\t신청자수\t담당강사명\t과정명");
		System.out.println("--------------------------------------------------");
		for(Course course : courses) {
			System.out.print(course.getNo()+ "\t");
			System.out.print(course.getquota()+ "\t");
			System.out.print(course.getReqCnt()+ "\t");
			System.out.print(course.getTeacherId()+ "\t");
			System.out.println(course.getName());
			System.out.println("--------------------------------------------------");
		}
		
	}

	private void 학생과정신청() {
		System.out.println("<< 학생과정신청 >>");
		System.out.println("수강 신청할 과정 번호를 입력하세요.");
		
		int courseNo = reader.readInt();
		courseRegistrationService.registerCourse(loginUser.getId(), courseNo);
		
		System.out.println("### 수강신청이 완료되었습니다.");
		
		
		

	}

	private void 학생등록취소() {
		System.out.println("<< 학생등록취소 >>");
		System.out.println("취소하실 강의 과정번호를 입력하세요.");
		int courseNo = reader.readInt();
		courseRegistrationService.cancelCourseRegistration(courseNo, loginUser.getId());
		
		System.out.println("### 과정 취소가 완료되었습니다.");
	}

	private void 학생신청현황조회() {
		System.out.println("<< 학생신청현황조회 >>");
		List<CourseRegistrationDto> dtos = courseRegistrationService.getAllRegistrationsByNoI(loginUser.getId());
		System.out.println("등록번호\t등록일자\t\t취소여부\t과정명");
		System.out.println("----------------------------------------------------");
		
		for (CourseRegistrationDto dto : dtos) {
			System.out.print(dto.getRegNo() + "\t");
			System.out.print(dto.getRegCreateDate() + "\t");
			System.out.print(dto.getRegCanceled() + "\t");
			System.out.println(dto.getCourseName());
			System.out.println("----------------------------------------------------");
		}
	}

	private void 강사과정조회() {
		System.out.println("<< 강사과정조회 >>");
		System.out.println("### 개설한 모든 과정을 조회합니다.");
		
		System.out.println("과정번호\t모집정원\t신청자수\t과정상태\t과정명");
		System.out.println("----------------------------------------------------------");
		
		List<Course> courses = teacherService.getAllCreatedCourses(loginUser.getId());
		
		for(Course course : courses) {
			System.out.print(course.getNo() + "\t");
			System.out.print(course.getquota() + "\t");
			System.out.print(course.getReqCnt() + "\t");
			System.out.print(course.getStatus() + "\t");
			System.out.println(course.getName());
			System.out.println("----------------------------------------------------------");
		}

	}

	private void 강사과정등록() {
		System.out.println("<< 강사과정등록 >>");
		System.out.println("개설할 과정명, 모집정원을 입력하세요.");
		
		System.out.println("과정명 입력> ");
		String name = reader.readString();
		System.out.println("모집정원 입력> ");
		int quota = reader.readInt();
		
		Course course = new Course();
		course.setName(name);
		course.setquota(quota);
		course.setTeacherId(loginUser.getId());
		
		teacherService.createCourse(course);
		
		
		System.out.println("강좌 개설이 완료되었습니다.");

	}

	private void 강사과정취소() {
		System.out.println("<< 강사과정취소 >>");
		System.out.println("개설 취소하실 과정 번호를 입력하세요.");
		
		int courseNo = reader.readInt();
		
		teacherService.deleteCourse(courseNo, loginUser.getId());
		
		System.out.println("개설하신 강좌가 폐쇄되었습니다.");
		

	}

	private void 강사과정현황조회() {
		System.out.println("<< 강사과정현황조회 >>");
		System.out.println("### 개설하신 과정 번호를 입력하세요.");
		int courseNo = reader.readInt();
		
		List<CourseDetailDto> dtos = teacherService.getCourseDetailDtoByCousreNo(courseNo, loginUser.getId());
		System.out.println("과정번호\t과정명\t모집정원\t신청자수\t과정상태\t개설날짜\t\t강사아이디\t수강학생아이디");
		System.out.println("-----------------------------------------------------------------------");
		for (CourseDetailDto dto : dtos) {
			System.out.print(dto.getCourseNo() + "\t");
			System.out.print(dto.getCourseName() + "\t");
			System.out.print(dto.getCourseQuota() + "\t");
			System.out.print(dto.getReqCnt() + "\t");
			System.out.print(dto.getStatus() + "\t");
			System.out.print(dto.getCourseCreateDate() + "\t");
			System.out.print(dto.getTeacherId() + "\t");
			System.out.println(dto.getStudentId());
			System.out.println("-----------------------------------------------------------------------");
		}
		
	}

	public static void main(String[] args) {
		new CourseController().menu();
	}
}
