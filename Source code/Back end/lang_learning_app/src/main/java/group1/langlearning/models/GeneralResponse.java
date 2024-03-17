package group1.langlearning.models;

public class GeneralResponse {
    private boolean status;
    private String responseCode;
    private String responseMessage;
    private Object data;

    public boolean isStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public String getResponseCode() {
        return responseCode;
    }
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }
    public String getResponseMessage() {
        return responseMessage;
    }
    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public GeneralResponse(boolean status, String responseCode, String responseMessage, Object data) {
        this.status = status;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.data = data;
    }
    public GeneralResponse(boolean status, String responseCode, String responseMessage) {
        this.status = status;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }
    @Override
    public String toString() {
        return "GeneralResponse [status=" + status + ", responseCode=" + responseCode + ", responseMessage="
                + responseMessage + ", data=" + data + "]";
    }

    
    
}
