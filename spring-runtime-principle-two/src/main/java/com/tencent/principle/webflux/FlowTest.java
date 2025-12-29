package com.tencent.principle.webflux;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * @author 观自在
 * @description spring webflux 需要使用 jdk9及以上的jdk
 *              reactive响应式编程
 * @date 2025-12-29 15:14
 */
public class FlowTest {

    public static void main(String[] args) throws Exception {
        //1.定义发布者，数据类型integer
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();

        //2.定义消费者
        Flow.Subscriber<Integer> subscriber = new Flow.Subscriber<>() {

            private Flow.Subscription subscription;
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                //订阅关系管理
                this.subscription = subscription;
                this.subscription.request(1);

            }

            @Override
            public void onNext(Integer item) {
                //获取到数据后，开始处理
                System.out.println("我接受到的数据是："+item);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                this.subscription.cancel();
            }

            @Override
            public void onComplete() {
                System.out.println("数据处理完毕！！！");
            }
        };

        //3.发布者和订阅者建立联系
        publisher.subscribe(subscriber);

        //创建数据
        //TODO -- 数据库，redis，缓存 省略掉数据获取的步骤
        int data=110;
        publisher.submit(data);
        publisher.close();

        Thread.currentThread().join(1000);
    }
}
