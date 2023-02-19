package company.tap.java.assessment.utils.enums;

public enum StatusType {
    PENDING("pending"),
    UNDER_REVIEW("under_review"),
    VERIFIED("verified");
    private String value;
    StatusType(String status)
    {
        this.value = status;
    }
    public String getFieldStatus()
    {
        return value;
    }
}
