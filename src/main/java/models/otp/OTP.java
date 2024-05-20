package models.otp;

import java.time.LocalDateTime;

public class OTP {
    private String id;
    private String otp;
    private LocalDateTime expiredTime;
    private String userId;
    private byte isDisabled;
    private LocalDateTime createdAt;

    public OTP(String id, String otp, LocalDateTime expiredTime, String userId,
               byte isDisabled, LocalDateTime createdAt) {
        this.id = id;
        this.otp = otp;
        this.expiredTime = expiredTime;
        this.userId = userId;
        this.isDisabled = isDisabled;
        this.createdAt = createdAt;
    }

    public OTP() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(LocalDateTime expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public byte getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(byte isDisabled) {
        this.isDisabled = isDisabled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
