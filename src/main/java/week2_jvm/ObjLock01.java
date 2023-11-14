package week2_jvm;

import org.openjdk.jol.info.ClassLayout;

public class ObjLock01 {
    public static void main(String[] args) {
        Object obj = new Object();
        System.out.println("new object: " + ClassLayout.parseInstance(obj).toPrintable());
    }
}
