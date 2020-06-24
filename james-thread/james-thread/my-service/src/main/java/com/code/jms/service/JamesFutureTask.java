package com.code.jms.service;

import java.util.concurrent.*;

public class JamesFutureTask<V> implements Runnable, Future<V> {

    private Callable<V> callable;//封装业务逻辑

    V result = null;//执行结果

    public JamesFutureTask(Callable<V> callable) {
        if (callable == null) {
            throw new NullPointerException();
        } else {
            this.callable = callable;
        }
    }

    @Override
    public void run() {
        try {
            result = callable.call();
            synchronized (this){
                this.notifyAll();//相当于F8
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public V get() throws InterruptedException, ExecutionException {
        if (null != result){
            return result;
        }
       //阻塞，监听到有返回值，相当于打断点
        synchronized (this){
            this.wait();
        }
        return null;
    }


    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }


    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
