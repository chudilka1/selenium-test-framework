package com.masteringselenium.listeners;

import com.masteringselenium.driver.DriverFactory;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.ByteArrayInputStream;

@Slf4j
public class AllureListener extends TestListenerAdapter {

    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    private ByteArrayInputStream saveScreenshot(WebDriver driver) {
        log.info("Saving screenshot");
        return new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
    }

    @Attachment(value = "HTML", type = "text/html")
    private static String attachHtml(WebDriver driver) {
        log.info("Attaching HTML logs");
        return driver.getPageSource();
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        log.info("On start of {}", iTestContext.getName());
        iTestContext.setAttribute("WebDriver", DriverFactory.getDriver());
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        log.info("On finish of {}", iTestContext.getName());
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        final String testClass = iTestResult.getInstanceName();
        final String testMethod = getTestMethodName(iTestResult);
        log.info("Start execution of {}.{}", testClass, testMethod);
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        log.info(getTestMethodName(iTestResult) + " succeed");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        final String testClass = iTestResult.getInstanceName();
        final String testMethod = getTestMethodName(iTestResult);
        log.error("Test failed: {}.{}", testClass, testMethod);
        final WebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            log.warn("Screenshot captured for test case: {}.{}", testClass, testMethod);
            Allure.addAttachment("Page screenshot", saveScreenshot(driver));

            //Save html
            Allure.addAttachment("HTML source", "text/html", driver.getPageSource(), ".html");
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        log.warn(getTestMethodName(iTestResult) + " skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        log.warn(getTestMethodName(iTestResult) + " skipped");
    }
}
