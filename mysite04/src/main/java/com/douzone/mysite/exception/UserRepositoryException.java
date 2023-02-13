package com.douzone.mysite.exception;

public class UserRepositoryException extends RuntimeException {
	public UserRepositoryException(String message) {
		super(message);
	}
	public UserRepositoryException() {
		super("UserRepositoryException Occurs");
	}
}
