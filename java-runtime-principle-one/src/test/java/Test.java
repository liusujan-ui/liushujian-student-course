import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.*;

/**
 * @author 观自在
 * @description
 * @date 2025-12-07 10:27
 */

@Slf4j
public class Test {

    @BeforeEach
    public void setUp() {
        log.info("before");
    }

    @AfterEach
    public void tearDown() {
        log.info("after");
    }

    @org.junit.jupiter.api.Test
    public void test() throws ParseException {
        int[] arr={1,2,3,4,5};
        int[] arr2=new int[arr.length+1];
        System.arraycopy(arr,0,arr2,0,arr.length);
        int[] ints = Arrays.copyOf(arr, arr2.length+2);

        Integer a=1;

        // 自动拆箱，自动封箱
        Integer i = new Integer(2);
        int b=i;
//        System.out.println(b);

        BigInteger bigInteger = new BigInteger("122222222222222222222222222222222222222222222");
        BigDecimal bigDecimal = new BigDecimal("12121212121.232132132131232132132121312321");
        log.info("{},{},\n{},{}",ints,arr2,bigInteger,bigDecimal);

        // 时间工具类
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = simpleDateFormat.parse("2024-10-01 20:33:01");
//        System.out.println(parse);


        LocalDate parse1 = LocalDate.parse("2024-10-01 12:12:12",DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        System.out.println(parse1.format(DateTimeFormatter.ofPattern("yyyy:MM:dd")));
//
//        System.out.println(parse1.getLong(ChronoField.EPOCH_DAY));
//
//        System.out.println(1>>2);
    }


    @org.junit.jupiter.api.Test
    public void test2() throws ParseException, ExecutionException, InterruptedException {
    // 第一种方式
        new T1().start();
        new T1().start();


        new Thread(new T2()).start();

        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<String> submit = service.submit(new T3());
        String s = submit.get();
//        System.out.println(s);
    }


    @org.junit.jupiter.api.Test
    public void test3() throws Exception {

        Class<Person> personClass = Person.class;

        ClassLoader loader = personClass.getClassLoader();
//        System.out.println(loader);
//        System.out.println(loader.getParent());
//        System.out.println(loader.getParent().getParent());
        Method[] method = personClass.getMethods();
//        for (Method method1 : method) {
//            System.out.println(method1.getName());
//        }

        Method method1 = personClass.getMethod("test");
        method1.setAccessible(true);
        Constructor<Person> declaredConstructor = personClass.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        Person t1 = declaredConstructor.newInstance();

        Object invoke = method1.invoke(t1);
        System.out.println(invoke);
    }


//    static class Person{
//        private String name;
//        private int age;
//
//
//        public String test(){
//            return "test";
//        }
//    }

    class T1 extends Thread{

        public String test(){
            return "test";
        }

        private final Logger logger= LoggerFactory.getLogger(T1.class);
        public void run() {
            logger.info("{}",Thread.currentThread().getName());
        }
    }

    class T3 implements Callable<String>{
        @Override
        public String call() throws Exception {
            return String.format("%s",Thread.currentThread().getName());
        }
    }

    static class T2 implements Runnable {
        private final Logger log = LoggerFactory.getLogger(T2.class);

        @Override
        public void run() {
            log.info("{}",Thread.currentThread().getName());
        }
    }

    @org.junit.jupiter.api.Test
    void test4(){

    }






}
