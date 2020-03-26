package com.Rheo.Rheo2020.eunm;

public enum UserType {

    OFFICE_MEMBER(1),
    OFFICE_NOMEMBER(2),
    STUDENT_MEMBER(3),
    STUDENT_NOMEMBER(4);



    private Integer type;

    UserType(Integer type) {
        this.type = type;
    }


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
