package api.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReporterNG implements ITestListener {

    public ExtentSparkReporter sparkReporter;
    public ExtentTest test;
    public ExtentReports extent;
    
    String repName;
    
    public void onStart(ITestContext testcontext) {
    	
    	String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    	repName = "Test-Report"+timeStamp+".html";
    	
    	sparkReporter=new ExtentSparkReporter(".\\reports\\"+repName);
    	
    	sparkReporter.config().setDocumentTitle("RestAssuredAutomationProject");
    	sparkReporter.config().setReportName("Pet store Users API");
    	sparkReporter.config().setTheme(Theme.DARK);
    	
    	extent = new ExtentReports();
    	extent.attachReporter(sparkReporter);
    	extent.setSystemInfo("Application", "Pet store Users API");
    	extent.setSystemInfo("Operating system", System.getProperty("os.name"));
    	extent.setSystemInfo("User name", System.getProperty("user.name"));
    	extent.setSystemInfo("Environment", "QA");
    }
    
    public void onTestSuccess(ITestResult result) {
    	
    	test = extent.createTest(result.getName());
    	test.assignCategory(result.getMethod().getGroups());
    	test.createNode(result.getName());
    	test.log(Status.PASS, "Test passed");
    }
    
    public void onTestFailure(ITestResult result) {
    	
    	test = extent.createTest(result.getName());
    	test.createNode(result.getName());
    	test.assignCategory(result.getMethod().getGroups());
    	test.log(Status.PASS, "Test passed");
    	test.log(Status.FAIL, result.getThrowable().getMessage());
    }
    
    public void onFinish(ITestContext testContext) {
    	
    	extent.flush();
    }
    
    
}