package com.example.healthcareapplication.model;

public class Common {
    public static final String SUCCESS = "Thành công";
    public static final Long ACTIVE_STATUS = 1L;
    public static final Long INACTIVE_STATUS = 0L;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static";
    public static class HEALTH_INFO {
        public static final Long HEIGHT = 1L;
        public static final Long WEIGHT = 2L;
        public static final Long BMI = 3L;
        public static final Long SLEEP_TIME = 4L;
        public static final Long TRAINING_TIME = 5L;

    }

}
