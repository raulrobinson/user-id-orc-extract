package co.com.telefonica.ws.dto;

public class PgUserDTO {
    private String idType;
    private String idNumber;

    public PgUserDTO() {
    }

    public PgUserDTO(String idType, String idNumber) {
        this.idType = idType;
        this.idNumber = idNumber;
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

}
