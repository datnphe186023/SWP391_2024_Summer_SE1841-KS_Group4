package models.pupil;

import models.personnel.Personnel;

import java.util.Date;

public class Pupil {
    private String id;
    private String userId;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String status;
    private Date birthday;
    private boolean gender;
    private String firstGuardianName;
    private String firstGuardianPhoneNumber;
    private String avatar;
    private String secondGuardianName;
    private String secondGuardianPhoneNumber;
    private Personnel createdBy;
    private String parentSpecialNote;

    public Pupil(String id, String userId, String firstName, String lastName,
                 String address, String email, String status, Date birthday, boolean gender,
                 String firstGuardianName, String firstGuardianPhoneNumber, String avatar,
                 String secondGuardianName, String secondGuardianPhoneNumber, Personnel createdBy,
                 String parentSpecialNote) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.status = status;
        this.birthday = birthday;
        this.gender = gender;
        this.firstGuardianName = firstGuardianName;
        this.firstGuardianPhoneNumber = firstGuardianPhoneNumber;
        this.avatar = avatar;
        this.secondGuardianName = secondGuardianName;
        this.secondGuardianPhoneNumber = secondGuardianPhoneNumber;
        this.createdBy = createdBy;
        this.parentSpecialNote = parentSpecialNote;
    }

    public Pupil() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getfirstGuardianName() {
        return firstGuardianName;
    }

    public void setfirstGuardianName(String firstGuardianName) {
        this.firstGuardianName = firstGuardianName;
    }

    public String getfirstGuardianPhoneNumber() {
        return firstGuardianPhoneNumber;
    }

    public void setfirstGuardianPhoneNumber(String firstGuardianPhoneNumber) {
        this.firstGuardianPhoneNumber = firstGuardianPhoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getsecondGuardianName() {
        return secondGuardianName;
    }

    public void setsecondGuardianName(String secondGuardianName) {
        this.secondGuardianName = secondGuardianName;
    }

    public String getsecondGuardianPhoneNumber() {
        return secondGuardianPhoneNumber;
    }

    public void setsecondGuardianPhoneNumber(String secondGuardianPhoneNumber) {
        this.secondGuardianPhoneNumber = secondGuardianPhoneNumber;
    }

    public Personnel getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Personnel createdBy) {
        this.createdBy = createdBy;
    }

    public String getParentSpecialNote() {
        return parentSpecialNote;
    }

    public void setParentSpecialNote(String parentSpecialNote) {
        this.parentSpecialNote = parentSpecialNote;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
