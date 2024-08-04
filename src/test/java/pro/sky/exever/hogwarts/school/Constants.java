package pro.sky.exever.hogwarts.school;

import pro.sky.exever.hogwarts.school.model.Faculty;
import pro.sky.exever.hogwarts.school.model.Student;

public class Constants {
    public static final String AGE_MIN_PARAM = "ageMin";
    public static final String AGE_MAX_PARAM = "ageMax";
    public static final Integer AGE_MIN_VALUE = 12;
    public static final Integer AGE_MAX_VALUE = 13;
    public static final String FACULTY1_NAME = "Fuckulty1";
    public static final String FACULTY1_COLOR = "Color1";
    public static final String FACULTY2_NAME = "Fuckulty2";
    public static final String FACULTY2_COLOR = "Color2";
    public static final String FACULTY3_NAME = "Fuckulty3";
    public static final String FACULTY3_COLOR = "Color3";
    public static final String STUDENT1_NAME = "Harry";
    public static final int STUDENT1_AGE = 12;
    public static final String STUDENT2_NAME = "Larry";
    public static final int STUDENT2_AGE = 13;
    public static final String STUDENT3_NAME = "Penny";
    public static final int STUDENT3_AGE = 14;
    public static final Faculty FACULTY1 = new Faculty(1L, FACULTY1_NAME, FACULTY1_COLOR);
    public static final Faculty FACULTY2 = new Faculty(2L, FACULTY2_NAME, FACULTY2_COLOR);
    public static final Faculty FACULTY3 = new Faculty(3L, FACULTY3_NAME, FACULTY3_COLOR);
    public static final Student STUDENT1 = new Student(1L, STUDENT1_NAME, STUDENT1_AGE, FACULTY1);
    public static final Student STUDENT2 = new Student(2L, STUDENT2_NAME, STUDENT2_AGE, FACULTY2);
    public static final Student STUDENT3 = new Student(3L, STUDENT3_NAME, STUDENT3_AGE, FACULTY3);
}
