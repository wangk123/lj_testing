package com.lj.testing.server.config;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.testng.*;

public class ExtentTestNGITestListener implements ITestListener {
    private static ExtentReports extent = ExtentManager.createInstance("test-output/report.html");
    private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<>();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public synchronized void onStart(ITestContext context) {
        ExtentTest parent = extent.createTest(getClass().getName());
        parentTest.set(parent);
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        extent.flush();
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        String nodeName = result.getParameters().length >= 2
                ? result.getMethod().getMethodName() + "-" + result.getParameters()[0].toString() + "-" + result.getParameters()[1].toString()
                : result.getMethod().getMethodName();
        ExtentTest child = parentTest.get().createNode(nodeName);
        test.set(child);
    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        test.get().pass("Test passed");
    }

    @Override
    public synchronized void onTestFailure(ITestResult result) {
        test.get().fail(result.getThrowable());
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        test.get().skip(result.getThrowable());
    }

    @Override
    public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }
}
