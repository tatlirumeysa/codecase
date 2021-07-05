package com.sahibinden.suite;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class CustomRunListener extends RunListener {

    private Map<String,TestSuiteDetails> map = new LinkedHashMap<>();

    private long testSuiteStartTime;
    private long testSuiteElapsedTime;
    private int ignoredCount;


    public void testRunStarted(Description description) throws Exception {
        testSuiteStartTime = System.currentTimeMillis();
    }


    public void testStarted(Description description) throws Exception {
        TestSuiteDetails testSuiteDetails = new TestSuiteDetails();
        testSuiteDetails.setTestCaseName(description.getMethodName());
        String[]  arr = description.getTestClass().getName().split("\\.");
        String name = arr[arr.length-1];
        testSuiteDetails.setTestClassName(name);
        String[]  arr1 = name.split("_");
        String testSuite = arr1[0];
        testSuiteDetails.setTestSuiteNmae(testSuite);
        testSuiteDetails.setTestStatus("Passed");
        testSuiteDetails.setStartTime(System.currentTimeMillis());
        map.put(description.getMethodName(),testSuiteDetails);
    }

    public void testFinished(Description description) throws Exception {
        TestSuiteDetails testSuiteDetails= null;
        if(map.containsKey(description.getMethodName())){
            testSuiteDetails = map.get(description.getMethodName());
            testSuiteDetails.setElaspsedTime(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()-testSuiteDetails.getStartTime()));
        }
        map.put(description.getMethodName(),testSuiteDetails);
    }

    public void testRunFinished(org.junit.runner.Result result) throws Exception {
        testSuiteElapsedTime = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()-testSuiteStartTime);
    }

    public void testFailure(Failure failure) throws Exception {
        TestSuiteDetails testSuiteDetails= null;
        if(map.containsKey(failure.getDescription().getMethodName())){
            testSuiteDetails = map.get(failure.getDescription().getMethodName());
        }else{
            testSuiteDetails = new TestSuiteDetails();
        }
        testSuiteDetails.setTestCaseName(failure.getDescription().getMethodName());
        testSuiteDetails.setTestDescription(failure.getException().toString());
        testSuiteDetails.setTestStatus("Failed");
        map.put(failure.getDescription().getMethodName(),testSuiteDetails);
    }

    public void testIgnored(Description description) throws Exception {
        TestSuiteDetails testSuiteDetails= null;
        if(map.containsKey(description.getMethodName())){
            testSuiteDetails = map.get(description.getMethodName());
        }else{
            testSuiteDetails = new TestSuiteDetails();
            testSuiteDetails.setTestCaseName(description.getMethodName());
            String[]  arr = description.getTestClass().getName().split("\\.");
            String name = arr[arr.length-1];
            testSuiteDetails.setTestClassName(name);
            String[]  arr1 = name.split("_");
            String testSuite = arr1[0];
            testSuiteDetails.setTestSuiteNmae(testSuite);
            ignoredCount++;
        }


        testSuiteDetails.setTestStatus("Ignored");
        map.put(description.getMethodName(),testSuiteDetails);
    }

    public int getIgnoredCount() {
        return ignoredCount;
    }

    public void setIgnoredCount(int ignoredCount) {
        this.ignoredCount = ignoredCount;
    }

    public Map<String, TestSuiteDetails> getMap() {
        return map;
    }

    public long getTestSuiteStartTime() {
        return testSuiteStartTime;
    }

    public long getTestSuiteElapsedTime() {
        return testSuiteElapsedTime;
    }
}
