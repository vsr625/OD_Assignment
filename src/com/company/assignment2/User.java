package com.company.assignment2;

import java.io.Serializable;

public class User implements Serializable, Comparable<User> {
    private String fullName;
    private int age;
    private String address;
    private int rollNumber;
    private String coursesEnrolled;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getCoursesEnrolled() {
        return coursesEnrolled;
    }

    public void setCoursesEnrolled(String coursesEnrolled) {
        this.coursesEnrolled = coursesEnrolled;
    }

    public User(String fullName, int age, String address, int mRollNumber, String mCoursesEnrolled) {
        this.fullName = fullName;
        this.age = age;
        this.address = address;
        this.rollNumber = mRollNumber;
        this.coursesEnrolled = mCoursesEnrolled;
    }

    @Override
    public int compareTo(User user) {
        if (this.getFullName().equals(user.getFullName())) {
            if (this.getRollNumber() < user.getRollNumber()) return -1;
            else if (this.getRollNumber() == user.getRollNumber()) return 0;
            else return 1;
        } else
            return this.getFullName().compareTo(user.getFullName());
    }
}
