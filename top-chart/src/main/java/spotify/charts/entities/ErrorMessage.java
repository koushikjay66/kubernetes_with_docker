package spotify.charts.entities;

public class ErrorMessage {
    private String message;

    public ErrorMessage() {}

    public ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
