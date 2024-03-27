package com.example.app_02.entity;

public class UserInfo {
    private String studentid;
    private String name;
    private String phonenumber;
    private String classname;

    public UserInfo() {
    }

    public UserInfo(String studentid, String name, String phonenumber, String classname) {
        this.studentid = studentid;
        this.name = name;
        this.phonenumber = phonenumber;
        this.classname = classname;
    }

    /**
     * 获取
     * @return studentid
     */
    public String getStudentid() {
        return studentid;
    }

    /**
     * 设置
     * @param studentid
     */
    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return phonenumber
     */
    public String getPhonenumber() {
        return phonenumber;
    }

    /**
     * 设置
     * @param phonenumber
     */
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    /**
     * 获取
     * @return classname
     */
    public String getClassname() {
        return classname;
    }

    /**
     * 设置
     * @param classname
     */
    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String toString() {
        return "User{studentid = " + studentid + ", name = " + name + ", phonenumber = " + phonenumber + ", classname = " + classname + "}";
    }
}
