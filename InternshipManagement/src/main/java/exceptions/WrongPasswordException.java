package exceptions;

public class WrongPasswordException extends Exception {

  private static final long serialVersionUID = 5245044335516751216L;

  public WrongPasswordException() {
  }

  public WrongPasswordException(String message) {
    super(message);
  }

  public WrongPasswordException(StringBuilder message) {
    super(message.toString());
  }

  public WrongPasswordException(Throwable cause) {
    super(cause);
  }

  public WrongPasswordException(String message, Throwable cause) {
    super(message, cause);
  }

  public WrongPasswordException(StringBuilder message, Throwable cause) {
    super(message.toString(), cause);
  }

}
