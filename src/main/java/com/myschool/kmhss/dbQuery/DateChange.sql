ALTER TABLE public.exam_grades ALTER COLUMN exam_date TYPE varchar(20) USING exam_date::varchar;

ALTER TABLE public.exams ALTER COLUMN start_date TYPE varchar(20) USING start_date::varchar;
ALTER TABLE public.exams ALTER COLUMN end_date TYPE varchar(20) USING end_date::varchar;

ALTER TABLE public.school_calendar ALTER COLUMN event_date TYPE varchar(20) USING event_date::varchar;
ALTER TABLE public.school_circulars ALTER COLUMN circular_date TYPE varchar(20) USING circular_date::varchar;

ALTER TABLE public.schools ALTER COLUMN exp_date TYPE varchar(20) USING exp_date::varchar;

ALTER TABLE public.student_attendance ALTER COLUMN absent_date TYPE varchar(20) USING absent_date::varchar;

ALTER TABLE public.students ALTER COLUMN joining_date TYPE varchar(20) USING joining_date::varchar;
ALTER TABLE public.students ALTER COLUMN dob TYPE varchar(20) USING dob::varchar;
ALTER TABLE public.students ALTER COLUMN releving_date TYPE varchar(20) USING releving_date::varchar;

ALTER TABLE public.teacher_attendance ALTER COLUMN from_date TYPE varchar(20) USING from_date::varchar;
ALTER TABLE public.teacher_attendance ALTER COLUMN to_date TYPE varchar(20) USING to_date::varchar;

ALTER TABLE public.teachers ALTER COLUMN joining_date TYPE varchar(20) USING joining_date::varchar;
ALTER TABLE public.teachers ALTER COLUMN releving_date TYPE varchar(20) USING releving_date::varchar;

