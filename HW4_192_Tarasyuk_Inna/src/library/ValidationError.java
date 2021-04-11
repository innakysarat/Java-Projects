package library;

public interface ValidationError {
    String getMessage();
    String getPath();
    Object getFailedValue();

}
