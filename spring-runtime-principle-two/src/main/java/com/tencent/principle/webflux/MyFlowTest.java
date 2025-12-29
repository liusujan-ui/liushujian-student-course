package com.tencent.principle.webflux;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * @author 观自在
 * @description  reactive响应式编程
 * @date 2025-12-29 15:30
 */
public class MyFlowTest {

    public static void main(String[] args) throws InterruptedException {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();

        MyProcessor myProcessor = new MyProcessor();

        publisher.subscribe(myProcessor);

        //定义消费者 ---
        Flow.Subscriber<String> subscriber = new Flow.Subscriber<>() {
            private Flow.Subscription subscription;
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                this.subscription.request(1);
            }

            @Override
            public void onNext(String item) {
                System.out.println("接收到的数据：" + item);

            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                this.subscription.cancel();
            }

            @Override
            public void onComplete() {
                System.out.println("发送方执行完毕");
            }
        };

        myProcessor.subscribe(subscriber);


        publisher.submit(111);
        publisher.close();

        Thread.currentThread().join(1000);
    }
}
