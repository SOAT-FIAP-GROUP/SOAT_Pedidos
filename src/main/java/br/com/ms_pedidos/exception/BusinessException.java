package br.com.ms_pedidos.exception;

public class BusinessException extends RuntimeException {
    private static final Long serialVersionUID = 1L;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Object  ... parms) {
        super(String.format(message, parms));
    }

}