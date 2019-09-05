package com.lj.testing.server.config;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

    public static ExtentReports createInstance(String fileName) {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(fileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(fileName);
//        ExtentEmailReporter extentEmailReporter = new ExtentEmailReporter("email-" + fileName);
        ExtentReports extent = new ExtentReports();
        extent.setSystemInfo("OS", "Mac");
        extent.attachReporter(htmlReporter);
        return extent;
    }

}
