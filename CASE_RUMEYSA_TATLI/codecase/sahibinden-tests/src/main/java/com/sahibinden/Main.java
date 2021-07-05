package com.sahibinden;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;

import com.sahibinden.suite.CustomRunListener;
import com.sahibinden.suite.TestSuiteDetails;
import com.sahibinden.tests.TestBudgetAndFrequencyCapping;
import com.sahibinden.tests.TestCreateAd;
import com.sahibinden.tests.TestMatchCriteria;
import com.sahibinden.tests.TestStatistics;

public class Main {

    public static void main(String[] args) {
        executeTestClass(TestCreateAd.class);
        executeTestClass(TestMatchCriteria.class);
        executeTestClass(TestBudgetAndFrequencyCapping.class);
        executeTestClass(TestStatistics.class);
    }


    static void executeTestClass(Class cls)
    {
        System.out.println("-------------------------------------------------------------");
        JUnitCore jUnitCore = new JUnitCore();
        CustomRunListener customRunListener = new CustomRunListener();
        jUnitCore.addListener(customRunListener);

        Request request = Request.aClass(cls);
        Result result = jUnitCore.run(request);
        Map<String, TestSuiteDetails> myTestResultMap = customRunListener.getMap();


        myTestResultMap.forEach(
                (key,value) -> {
                    System.out.println(key +" : "+ value.getTestStatus());

                    if (value.getTestStatus().equals( "Failed"))
                    {
                        System.out.println("\t " + value.getTestDescription());
                    }
                }
        );

        System.out.println("-------------------------------------------------------------");

    }
}
