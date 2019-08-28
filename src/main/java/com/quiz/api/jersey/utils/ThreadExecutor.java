package com.quiz.api.jersey.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadExecutor {

   public static ExecutorService getExecutor(){
        return Executors.newFixedThreadPool(5);
    }
}