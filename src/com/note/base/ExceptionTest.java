package com.note.base;

/**
 * @author youze
 * on 2018/7/17
 */
public class ExceptionTest {

    /**
     * test 方法运行返回 0，因为执行到 try 的 return ret; 语句前会先将返回值 ret 保存在一个临时变量中，
     * 然后才执行 finally 语句，最后 try 再返回那个临时变量，finally 中对 ret 的修改不会被返回。
     *
     * @return
     */
    public static int test() {
        int ret = 0;
        try {
            return ret;
        } finally {
            ret = 1;
        }
    }

    /**
     * test1 方法运行返回 2，因为 5/0 会触发 ArithmeticException 异常，但是 finally 中有 return 语句，
     * finally 中 return 不仅会覆盖 try 和 catch 内的返回值且还会掩盖 try 和 catch 内的异常，
     * 就像异常没有发生一样（特别注意，当 finally中没有 return 时该方法运行会抛出 ArithmeticException 异常），
     * 所以这个方法就会返回 2，而且不再向上传递异常了。
     *
     * @return
     */
    public static int test1() {
        int ret = 0;
        try {
            int a = 5 / 0;
            return ret;
        } finally {
            return 2;
        }
    }

    /**
     * test3 方法运行抛出 hello 异常，因为如果 finally 中抛出了异常，则原异常就会被掩盖。
     */
    public static void test3() {
        try {
            int a = 5 / 0;
        } finally {
            throw new RuntimeException("error");
        }
    }
}
