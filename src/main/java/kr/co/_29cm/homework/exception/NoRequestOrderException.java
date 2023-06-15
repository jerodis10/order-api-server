package kr.co._29cm.homework.exception;

public class NoRequestOrderException extends RuntimeException{
    public NoRequestOrderException(String message) {
        super(message);
    }
}
