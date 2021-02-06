package ee.hein.shoppinglistserver.util;

import org.junit.Assert;

public final class ExpectedException {

    private ExpectedException() {
    }

    public static void expect(Runnable fn, Class<? extends Exception> exceptionClass) {
        expect(fn, exceptionClass, null);
    }

    public static void expect(Runnable fn, Class<? extends Exception> exceptionClass, String exceptionMessage) {
        try {
            fn.run();
            String exceptionClassName = exceptionClass.getName();
            Assert.fail("Expected function to fail with exception: " + exceptionClassName + ", message: " + exceptionMessage);
        } catch (Exception e) {
            Assert.assertEquals("Exception class mismatch", exceptionClass, e.getClass());
            if (exceptionMessage != null) {
                Assert.assertEquals("Exception message mismatch", exceptionMessage, e.getMessage());
            }
        }
    }
}