CREATE TABLE public.grades (
	grade_id serial8 NOT NULL,
	grade_name varchar(50) null,
	priority int8 null,
	cr_date date null,
	up_date date null
);

CREATE TABLE public.subjects (
	subject_id serial8 NOT NULL,
	subject_name varchar(50) null,
	subject_code varchar(10) null,
	cr_date date null,
	up_date date null
);

CREATE TABLE public.schools (
	school_id serial8 NOT NULL,
	school_name varchar(50) null,
	school_code varchar(10) null,
	address varchar(250) null,
	contact_number varchar(50) null,
	email varchar(50) null,
	syllabus varchar(50) null,
	pan_number varchar(20) null,
	active_status varchar(10) null,
	login_name varchar(50) null,
	login_pwd varchar (50) null,
	exp_date date null,
	cr_date date null,
	up_date date null
);

CREATE TABLE public.teachers (
	teacher_id serial8 NOT NULL,
	school_id int8 not null,
	teacher_name varchar(50) not null,
	designation varchar(50) null,
	qualification varchar(50) null,
	mobile_no int8 null,
	email varchar(50) null,
	aadhar_no int8 null,
	address varchar(250) null,
	subjects varchar(250) null,
	login_name varchar(50) null,
	login_pwd varchar(50) null,
	joining_date date null,
	status varchar(10) null,
	releving_date date null,
	cr_date date null,
	up_date date null
);

CREATE TABLE public.academic_years (
	academic_year_id serial8 NOT NULL,
	academic_year varchar(50) null,
	cr_date date null,
	up_date date null
);

CREATE TABLE public.parents (
	parent_id serial8 NOT NULL,
	school_id int8 not null,
	parent_name varchar(50) not null,
	designation varchar(50) null,
	qualification varchar(50) null,
	relationship varchar(50) null,
	mobile_no int8 null,
	email varchar(50) null,
	aadhar_no int8 null,
	address varchar(250) null,
	login_name varchar(50) null,
	login_pwd varchar(50) null,
	status varchar(10) null,
	cr_date date null,
	up_date date null
);

CREATE TABLE public.students (
	student_id serial8 NOT NULL,
	school_id int8 not null,
	student_name varchar(50) not null,
	parent_id int8 null,
	register_no varchar(30) null,
	blood_group varchar(10) null,
	mobile_no int8 null,
	aadhar_no int8 null,
	dob date null,
	gender varchar(20) null,
	community varchar(20) null,
	nationality varchar(30) null,
	religion varchar(30) null,
	joining_date date null,
	status varchar(10) null,
	releving_date date null,
	cr_date date null,
	up_date date null
);

CREATE TABLE public.school_calendar (
	calendar_id serial8 NOT NULL,
	school_id int8 not null,
	academic_year_id int8 null,
	event_date date null,
	event_name varchar(100) null,
	event_type varchar(30) null,
	cr_date date null,
	up_date date null
);

CREATE TABLE public.periods_master (
	period_id serial8 NOT NULL,
	school_id int8 not null,
    priority int8 null,
	period_name varchar(100) null,
	period_type varchar(20) null,
	period_short_name varchar(20) null,
	time_from varchar(10) null,
	time_to varchar(10) null,
	cr_date date null,
	up_date date null
);

CREATE TABLE public.school_grades (
	school_grade_id serial8 NOT NULL,
	school_id int8 not null,
	grade_id int8 not null,
	class_teacher_id int8 not null,
	academic_year_id int8 not null,
	section_name varchar(50) null,
	cr_date date null,
	up_date date null
);

create table public.teachersSubjectsXref (
	id serial8 NOT NULL,
	teacher_id int8 not null,
	subject_id int8 not null
);

CREATE TABLE public.time_table (
	time_table_id serial8 NOT NULL,
	school_id int8 not null,
	academic_year_id int8 not null,
	school_grade_id int8 not null,
	period_id int8 not null,
	subject_id int8 not null,
	teacher_id int8 not null,
	weekday varchar(20) not null,
	cr_date date null,
	up_date date null
);

CREATE TABLE public.teacher_attendance (
	teacher_attendance_id serial8 NOT NULL,
	school_id int8 not null,
	academic_year_id int8 not null,
	teacher_id int8 not null,
	from_date date not null,
	to_date date not null,
	absent_period varchar(20) null,
	leave_type varchar(20) null,
	reason varchar(100) null,
	cr_date date null,
	up_date date null
);

CREATE TABLE public.academic_students (
	academic_student_id serial8 NOT NULL,
	school_id int8 not null,
	academic_year_id int8 not null,
	school_grade_id int8 not null,
	student_id int8 not null,
	promotion_status varchar(100) null,
	promotion_grade_id int8 null,
	cr_date date null,
	up_date date null
);

CREATE TABLE public.exams (
	exam_id serial8 NOT NULL,
	school_id int8 not null,
	academic_year_id int8 not null,
	exam_name varchar(100) not null,
	start_date date not null,
	end_date date not null,
	cr_date date null,
	up_date date null
);


CREATE TABLE public.exam_grades (
	exam_grade_id serial8 NOT NULL,
	exam_id int8 not null,
	school_grade_id int8 not null,
	subject_id int8 not null,
	exam_date date not null,
	max_mark int8 not null,
	time_from varchar(20) not null,
	time_to varchar(20) not null,
	cr_date date null,
	up_date date null
);

CREATE TABLE public.exam_marks (
	mark_id serial8 NOT NULL,
	exam_grade_id int8 not null,
	student_id int8 not null,
	attended_status varchar(20) not null,
	mark_obtained int8 null,
	mark_percentage int8 null,
	cr_date date null,
	up_date date null
);

CREATE TABLE public.mark_grades (
	mark_grade_id serial8 NOT NULL,
	school_id int8 not null,
	min_mark decimal not null,
	max_mark decimal not null,
	mark_grade varchar(20) not null,
	cr_date date null,
	up_date date null
);

CREATE TABLE public.exam_report (
	report_id serial8 NOT NULL,
	exam_id int8 not null,
	school_grade_id int8 not null,
	student_id int8 not null,
	total_marks decimal not null,
	total_max_marks decimal not null,
	overall_percentage decimal not null,
	overall_grade varchar(10) not null,
	cr_date date null,
	up_date date null
);

CREATE TABLE public.student_attendance (
	student_attendance_id serial8 NOT NULL,
	academic_student_id int8 not null,
	absent_date date not null,
	absent_period varchar(20) null,
	reason varchar(100) null,
	cr_date date null,
	up_date date null
);

CREATE TABLE public.super_admin (
	admin_id serial8 NOT NULL,
	login_name varchar(50) not null,
	login_pwd varchar(50) not null
);

CREATE TABLE public.school_circulars (
	circular_id serial8 NOT NULL,
	school_id int8 not null,
    academic_year_id int8 not null,
    circular_date date null,
	circular_title varchar(50) null,
	circular_message varchar(250) null,
	circular_to varchar(20) null,
	cr_date date null,
    up_date date null
);