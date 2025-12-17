package br.com.ms_pedidos.exception;

public class EntityNotFoundException extends BusinessException {

    private static final Long serialVersionUID = 1L;

    public EntityNotFoundException(Class<?> entityClass, Object id) {
        super(String.format("%s não encontrada para o parâmetro %s", entityClass.getSimpleName(), id));
    }

}