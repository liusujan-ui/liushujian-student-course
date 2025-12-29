package com.tencent.principle.webflux;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * @author 观自在
 * @description reactive响应式编程
 * @date 2025-12-29 15:28
 */
public class MyProcessor extends SubmissionPublisher<String> implements Flow.Processor<Integer, String> {

    private Flow.Subscription subscription;
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(1);
    }

    @Override
    public void onNext(Integer item) {
        System.out.println("打印当前的数据：" + item);
        this.submit(String.valueOf(item+100));
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        this.subscription.cancel();
    }

    @Override
    public void onComplete() {
        System.out.println("调用完成！！！");
    }
}
