package exceptions;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class ValidationException extends Exception {

  private static final long serialVersionUID = 7881770371912247695L;

  private String fieldName;
  private Object value;

  public ValidationException(StringBuilder sb) {
    super(sb.toString());
  }

  public ValidationException(Throwable t) {
    super(t);
  }

  public ValidationException(String s) {
    super(s);
  }

  public ValidationException(String s, Throwable t) {
    super(s, t);
  }

  public ValidationException(StringBuilder sb, String fieldName, Object value) {
    super(sb.toString());
    setFieldName(fieldName);
    setValue(value);
  }

  public ValidationException(String s, String fieldName, Object value) {
    super(s);
    setFieldName(fieldName);
    setValue(value);
  }

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

}
