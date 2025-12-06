package com.tencent.forkJoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author 观自在
 * @description
 * @date 2025-12-06 22:01
 */
public class ForkJoinTest {

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

//    static class Task implements Callable<String> {
//        int start;
//        int end;
//        public Task(int start, int end) {
//            this.start = start;
//            this.end = end;
//        }
//
//        @Override
//        public String call() throws Exception {
//            StringBuilder result = new StringBuilder();
//            for (int i = start; i <= end; i++) {
//                result.append(doRequest(urls.get(i)));
//            }
//            return result.toString();
//        }
//    }
    static class Job extends RecursiveTask<String> {

        List<String> urls;
        int start;
        int end;
        public Job(List<String> urls, int start, int end) {
            this.urls = urls;
            this.start = start;
            this.end = end;
        }
        @Override
        protected String compute() { // 定义任务拆分的规则
            int count = end-start;
            if(count<=10){
                //直接执行
                StringBuilder result= new StringBuilder();
                for (int i = start; i < end; i++) {
                   String response=doRequest(urls.get(i));
                   result.append(response);
                }
                return result.toString();
            }else {
                //拆分任务
                int x=(start+end)/2;
                Job left = new Job(urls, start, x);
                left.fork();
                Job right = new Job(urls, x, end);
                right.fork();

                String result = left.join()+right.join();
                return result;
            }
       }
}

    static ForkJoinPool forkJoinPool = new ForkJoinPool(3,
            ForkJoinPool.defaultForkJoinWorkerThreadFactory,null,true);


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Job job = new Job(urls, 0, urls.size());
        ForkJoinTask<String> forkJoinTask = forkJoinPool.submit(job);

        String result=forkJoinTask.get();
        System.out.println(result);

    }
}
