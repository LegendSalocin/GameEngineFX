package de.salocin.gameenginefx.util.error;

public class ReportedException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public final ExceptionCode code;
	
	public ReportedException(ExceptionCode code) {
		this(code, null, null);
	}
	
	public ReportedException(ExceptionCode code, String message) {
		this(code, message, null);
	}
	
	public ReportedException(ExceptionCode code, Throwable cause) {
		this(code, null, cause);
	}
	
	public ReportedException(ExceptionCode code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}
	
}
