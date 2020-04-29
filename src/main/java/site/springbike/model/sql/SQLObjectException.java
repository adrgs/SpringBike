package site.springbike.model.sql;

public class SQLObjectException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SQLObjectException(String message) {
        super(message);
    }
}