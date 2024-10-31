package me.est412.mozilla.universalchardet;

import org.mozilla.universalchardet.UniversalDetector;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class CharsetDetector {

    public static void main(String[] args) {
        System.out.println();
        System.out.println("Guessing charset by org.mozilla.universalchardet.UniversalDetector");
        System.out.println();
        if (args.length != 1) {
            System.out.println("Usage: java -jar mozilla-encoding-detector-<version>.jar <base64-encoded-string>");
            return;
        }
        byte[] decoded = Base64.getDecoder().decode(args[0]);
        String charset = guessEncoding(decoded);

        System.out.println("********************");
        System.out.println("Encoding detected:");
        System.out.println("********************");
        System.out.println(charset);

        if ("unknown".equals(charset)) {
            charset = "UTF-8";
        }

        System.out.println();
        System.out.println("********************");
        System.out.println("Content as " + charset +  " : ");
        System.out.println("********************");
        try {
            System.out.println(new String(decoded, charset));
        } catch (UnsupportedEncodingException e) {
            System.out.println("Can't convert to String: " + e.getMessage());
        }
    }

    public static String guessEncoding(byte[] bytes) {
        UniversalDetector detector = new UniversalDetector(null);
        detector.handleData(bytes, 0, bytes.length);
        detector.dataEnd();
        String encoding = detector.getDetectedCharset();
        detector.reset();
        if (encoding == null) {
            encoding = "unknown";
        }
        return encoding;
    }

}
