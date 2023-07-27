package co.com.telefonica.ws.dto;

import java.util.Date;

public class OdsUserDTO {
    private Long countId;
    private String idType;
    private String idNumber;
    private Date loadDate;

    public OdsUserDTO() {
    }

    public OdsUserDTO(Long countId, String idType, String idNumber, Date loadDate) {
        this.countId = countId;
        this.idType = idType;
        this.idNumber = idNumber;
        this.loadDate = loadDate;
    }

    public Long getCountId() {
        return countId;
    }

    public void setCountId(Long countId) {
        this.countId = countId;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Date getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(Date loadDate) {
        this.loadDate = loadDate;
    }
}
