package vip.kassadin.httpbin.blade;

import com.blade.kit.BladeKit;
import com.blade.mvc.Const;

import java.io.*;

/**
 * @author kassadin
 */
public class Resources {

    public static String TEMPLATE_PATH = "/templates/";

    public static byte[] get(String relativePath) throws IOException {
        InputStream in;
        if (BladeKit.isInJar()) {
            String viewPath = TEMPLATE_PATH + relativePath;
            System.out.println(viewPath);
            in = Thread.currentThread().getClass().getResourceAsStream(viewPath);
        } else {
            File viewPath = buildPath(new File(Const.CLASSPATH), TEMPLATE_PATH, relativePath);
            in = new FileInputStream(viewPath);
        }
        return readBytes(in);
    }

    public static File buildPath(File base, String... segments) {
        File cur = base;
        for (String segment : segments) {
            if (cur == null) {
                cur = new File(segment);
            } else {
                cur = new File(cur, segment);
            }
        }
        return cur;
    }

    public static byte[] readBytes(InputStream in) throws IOException {
        BufferedInputStream bufin = new BufferedInputStream(in);
        int buffSize = 1024;
        ByteArrayOutputStream out = new ByteArrayOutputStream(buffSize);

        byte[] temp = new byte[buffSize];
        int size = 0;
        while ((size = bufin.read(temp)) != -1) {
            out.write(temp, 0, size);
        }
        bufin.close();

        return out.toByteArray();
    }

}
