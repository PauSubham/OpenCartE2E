package utilities;

import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import testBase.testBase;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener {

	public ExtentSparkReporter sparkReporter;
	public ExtentReports extentReport;
	public ExtentTest extentTest;

	String reportName;

	public void onStart(ITestContext testContext) {

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		reportName = "Test-Report-" + timeStamp + ".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + reportName);

		sparkReporter.config().setDocumentTitle("OpenCart Automation Tests Report"); // Title of the Report
		sparkReporter.config().setReportName("OpenCart Functional Tests"); // Name of the Report
		sparkReporter.config().setTheme(Theme.DARK);

		extentReport = new ExtentReports();
		extentReport.attachReporter(sparkReporter);
		extentReport.setSystemInfo("Application", "OpenCart");
		extentReport.setSystemInfo("Module", "Admin");
		extentReport.setSystemInfo("Sub-Module", "Customers");
		extentReport.setSystemInfo("User Name", System.getProperty("user.name"));
		extentReport.setSystemInfo("Environment", "QA");

		String os = testContext.getCurrentXmlTest().getParameter("OS");
		extentReport.setSystemInfo("Operating System", os);

		String browser = testContext.getCurrentXmlTest().getParameter("Browser");
		extentReport.setSystemInfo("Browser", browser);

		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if (!includedGroups.isEmpty()) {
			extentReport.setSystemInfo("Groups", includedGroups.toString());
		}

	}

	public void onTestSuccess(ITestResult result) {

		extentTest = extentReport.createTest(result.getTestClass().getName());
		extentTest.assignCategory(result.getMethod().getGroups());
		extentTest.log(Status.PASS, result.getName() + " got executed successfully");
	}

	public void onTestFailure(ITestResult result) {

		extentTest = extentReport.createTest(result.getTestClass().getName());
		extentTest.assignCategory(result.getMethod().getGroups());

		extentTest.log(Status.FAIL, result.getName() + " got failed");
		extentTest.log(Status.INFO, result.getThrowable().getMessage());

		try {
			String imgPath = new testBase().captureScreen(result.getName());
			extentTest.addScreenCaptureFromPath(imgPath);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {

		extentTest = extentReport.createTest(result.getTestClass().getName());
		extentTest.assignCategory(result.getMethod().getGroups());
		extentTest.log(Status.SKIP, result.getName() + " got skipped");
		extentTest.log(Status.INFO, result.getThrowable().getMessage());
	}

	public void onFinish(ITestContext testContext) {

		extentReport.flush();

		// Opening the report as soon as execution is completed
		String pathOfReport = System.getProperty("user.dir") + "\\reports\\" + reportName;
		File extentReport = new File(pathOfReport);

		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
