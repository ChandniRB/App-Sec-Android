package appsec.model;

public class ApiResponseParams {
   private String status;
	private String message;
    
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String errmsg) {
		this.message = errmsg;
	}

}
