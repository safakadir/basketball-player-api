package com.safakadir.basketballplayerapi.exception;

public class MaximumCapacityReachedException extends RuntimeException {
    public MaximumCapacityReachedException(String message) {
        super(message);
    }
}
