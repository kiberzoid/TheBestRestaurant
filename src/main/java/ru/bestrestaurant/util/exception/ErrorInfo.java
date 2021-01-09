package ru.bestrestaurant.util.exception;

public class ErrorInfo {

    private final String url;
    private final String name;
    private final String message;

    public ErrorInfo(CharSequence url, String name, String message) {
        this.url = url.toString();
        this.name = name;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorInfo{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
