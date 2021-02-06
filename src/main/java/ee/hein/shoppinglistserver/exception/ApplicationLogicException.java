package ee.hein.shoppinglistserver.exception;

public class ApplicationLogicException extends RuntimeException {

    public enum ErrorCode {

        RETRY;

        public String formatErrorCode() {
            return "error." + name().toLowerCase().replace("_", "-");
        }
    }

    public ApplicationLogicException(ErrorCode error) {
        super(error.formatErrorCode(), null, true, false);
    }
}
