package com.quiz.api.jersey.model;

import javax.xml.bind.annotation.XmlRootElement;

import com.quiz.api.jersey.utils.Links;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "user")
public class UserBean {
    private int userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String emailId;
    private String phoneNumber;
    private List<ExamBean> exams = new ArrayList<>();
    private String instituteName;
    private List<Links> links = new ArrayList<>();
    
    public UserBean() {
     
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<ExamBean> getExams() {
        return exams;
    }

    public void setExams(List<ExamBean> exams) {
        this.exams = exams;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }
    
    public List<Links> getLinks(){
    	return links;
    }
    
    public void setLinks(List<Links> links) {
    	this.links = links;
    }
}
