package analiticsgoogle;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.AnalyticsReportingScopes;
import com.google.api.services.analyticsreporting.v4.model.*;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HelloAnalyticsReporting {
	private static final String APPLICATION_NAME = "My Project";
	  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	  
	  private static final String KEY_FILE_LOCATION = "<Your key file project>";
	  private static final String SERVICE_ACCOUNT_EMAIL = "<Service email>";
	  private static final String VIEW_ID = "<Your View ID>";
	  
	  public static void main(String[] args) {
	    try {
	      AnalyticsReporting service = initializeAnalyticsReporting();

	      GetReportsResponse response = getReport(service);
	      printResponse(response);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }

	  /**
	   * Initializes an authorized Analytics Reporting service object.
	   *
	   * @return The analytics reporting service object.
	   * @throws IOException
	   * @throws GeneralSecurityException
	   */
	  private static AnalyticsReporting initializeAnalyticsReporting() throws GeneralSecurityException, IOException {

	    HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
	    GoogleCredential credential = new GoogleCredential.Builder()
	        .setTransport(httpTransport)
	        .setJsonFactory(JSON_FACTORY)
	        .setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
	        .setServiceAccountPrivateKeyFromP12File(new File(KEY_FILE_LOCATION))
	        .setServiceAccountScopes(AnalyticsReportingScopes.all())
	        .build();

	    // Construct the Analytics Reporting service object.
	    return new AnalyticsReporting.Builder(httpTransport, JSON_FACTORY, credential)
	        .setApplicationName(APPLICATION_NAME).build();
	  }

	  /**
	   * Query the Analytics Reporting API V4.
	   * Constructs a request for the sessions for the past seven days.
	   * Returns the API response.
	   *
	   * @param service
	   * @return GetReportResponse
	   * @throws IOException
	   */
	  private static GetReportsResponse getReport(AnalyticsReporting service) throws IOException {
	    // Create the DateRange object.
	    DateRange dateRange = new DateRange();
	    dateRange.setStartDate("7DaysAgo");
	    dateRange.setEndDate("today");

	    // Create the Metrics object.
//	    Metric sessions = new Metric()
//	        .setExpression("ga:sessions")
//	        .setAlias("sessions");
	    
	    Metric sessions2 = new Metric()
		        .setExpression("ga:searchSessions")
		        .setAlias("ga:searchSessions");

	    //Create the Dimensions object.
//	    Dimension browser = new Dimension()
//	        .setName("ga:browser");
	    
	    Dimension browser2 = new Dimension()
		        .setName("ga:searchKeyword");

	    // Create the ReportRequest object.
	    ReportRequest request = new ReportRequest()
	        .setViewId(VIEW_ID)
	        .setDateRanges(Arrays.asList(dateRange))
	        .setDimensions(Arrays.asList(browser2))
	        .setMetrics(Arrays.asList(sessions2));

	    ArrayList<ReportRequest> requests = new ArrayList<ReportRequest>();
	    requests.add(request);

	    // Create the GetReportsRequest object.
	    GetReportsRequest getReport = new GetReportsRequest()
	        .setReportRequests(requests);

	    // Call the batchGet method.
	    GetReportsResponse response = service.reports().batchGet(getReport).execute();

	    // Return the response.
	    return response;
	  }

	  /**
	   * Parses and prints the Analytics Reporting API V4 response.
	   *
	   * @param response the Analytics Reporting API V4 response.
	   */
	  private static void printResponse(GetReportsResponse response) {

	    for (Report report: response.getReports()) {
	      ColumnHeader header = report.getColumnHeader();
	      List<String> dimensionHeaders = header.getDimensions();
	      List<MetricHeaderEntry> metricHeaders = header.getMetricHeader().getMetricHeaderEntries();
	      List<ReportRow> rows = report.getData().getRows();

	      if (rows == null) {
	         System.out.println("No data found for " + VIEW_ID);
	         return;
	      }

	      for (ReportRow row: rows) {
	        List<String> dimensions = row.getDimensions();
	        List<DateRangeValues> metrics = row.getMetrics();
	        for (int i = 0; i < dimensionHeaders.size() && i < dimensions.size(); i++) {
	          System.out.println(dimensionHeaders.get(i) + ": " + dimensions.get(i));
	        }

	        for (int j = 0; j < metrics.size(); j++) {
	          System.out.print("Date Range (" + j + "): ");
	          DateRangeValues values = metrics.get(j);
	          for (int k = 0; k < values.getValues().size() && k < metricHeaders.size(); k++) {
	            System.out.println(metricHeaders.get(k).getName() + ": " + values.getValues().get(k));
	          }
	        }
	      }
	    }
	  }
}
