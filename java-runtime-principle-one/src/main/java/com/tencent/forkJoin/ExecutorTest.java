package com.tencent.forkJoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author 观自在
 * @description
 * @date 2025-12-06 21:43
 */

public class ExecutorTest {

    /*
    思考：现在很多网络地址存在Arraylist中，我需要做网络请求
    为了并发执行，我就需要将这个列表进行拆分
     */

    static ArrayList<String> urls = new ArrayList<String>(){
        {
            for (int i = 0; i < 30; i++) {
                add(String.format("www.baidu%s.com", i));
                add(String.format("www.sina%s.com", i));
                add(String.format("www.google%s.com", i));
            }
        }
    };

    public static String doRequest(String url){
        // 模拟网络请求
        return "Kane ... Read ..."+url+"\n";
    }

    static class Task implements Callable<String> {
        int start;
        int end;
        public Task(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String call() throws Exception {
            StringBuilder result = new StringBuilder();
            for (int i = start; i <= end; i++) {
                result.append(doRequest(urls.get(i)));
            }
            return result.toString();
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool= Executors.newFixedThreadPool(4);

        List<Future> futures=new ArrayList<>();

        int size=urls.size();

        int groupSize=10;

        int groupCount=(size-1)/groupSize+1;

        for (int groupIndex = 0; groupIndex < groupCount-1; groupIndex++) {
            int left=groupIndex*groupSize;
            int right=groupIndex*groupSize+groupSize;

            Future<String> future = pool.submit(new Task(left, right));

            futures.add(future);
        }

        for (Future future : futures) {
            System.out.println(future.get());
        }


    }
}
