import org.capricorn.common.util.internal.unsafe.UnsafeUtil;
import sun.misc.Unsafe;

public class Main {
    public static void main(String[] args) {
       Unsafe unsafe= UnsafeUtil.getUnsafe();
       int _Obase =unsafe.arrayIndexScale(Object[].class);
       int _Oscale = unsafe.arrayIndexScale(Object[].class);
        System.out.println(_Obase);
        System.out.println(_Oscale);
    }
}
