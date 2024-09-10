package cap.utilities;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.google.gson.*;

import java.io.File;
import java.util.*;

public class ReportingUtil {

    private static String strUserDirectory = new StringBuilder().append(System.getProperty("user.dir")).toString();

    private static String strNewWorkbookName = new StringBuilder().append(strUserDirectory)
            .append(File.separator).append("config\\")
            .append("QA-Results").append(".xlsx").toString();

    /*Description: Get Pass, Fail count with TAG Names*/
    public static Map<String, Map<String, Integer>> getTagCounts(String json) {
        Gson gson = new Gson();
        List<Map<String, Object>> scenarios = gson.fromJson(json, List.class);
        Map<String, Map<String, Integer>> tagCounts = new HashMap<>();
        for (Map<String, Object> scenario : scenarios) {
            String tag = (String) scenario.get("tags");
            String status = (String) scenario.get("status");

            Map<String, Integer> statusCounts = tagCounts.getOrDefault(tag, new HashMap<>());
            statusCounts.put(status, statusCounts.getOrDefault(status, 0) + 1);
            tagCounts.put(tag, statusCounts);
        }
        System.out.println("\n########## TAG Count ##########");
        System.out.println(tagCounts);
        System.out.println("\n########## TAG Count ##########");
        return tagCounts;
    }

    public static void getAllInformationFromQAResultsSheet() {
        try {
            Fillo fillo = new Fillo();
            Connection connection = fillo.getConnection(strNewWorkbookName);
            connection.getMetaData().getTableNames().forEach(strTableName -> {
                System.out.println(">>>---> All Datas" + strTableName);
                Map<String, String> finalMap = new LinkedHashMap<>();
                String strQuery = "Select * from ".concat(strTableName);
                try {
                    Recordset rec = connection.executeQuery(strQuery);
                    while (rec.next()) {
                        finalMap.put("featureName", rec.getField("FeatureName"));
                        finalMap.put("scenarioName", rec.getField("ScenarioName"));
                        finalMap.put("tags", rec.getField("Tags"));
                        finalMap.put("testdata", rec.getField("Testdata"));
                        finalMap.put("status", rec.getField("Status"));
                        finalMap.put("jenkinsLog", rec.getField("JenkinsLog"));
                        finalMap.put("specification", rec.getField("Capabilities"));

                        JsonObject jsonObj = generateMAPValueToJSONValues(finalMap);
                        addMyJSONObjectInJSONArray(jsonObj);
                    }
                } catch (FilloException exc) {
                    exc.printStackTrace();
                }
            });
            System.out.println("\n FINAL :: Balaji Check: " + jsonArrayForIndividualScenariosDetails);
        } catch (Exception e) {
            System.out.println("Exception for All Datas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Insert JSONObject in JSONArray using GSON Library
    private static JsonArray jsonArrayForIndividualScenariosDetails = new JsonArray();

    public static JsonArray addMyJSONObjectInJSONArray(JsonObject jsonObject) {
        try {
            jsonArrayForIndividualScenariosDetails.add(jsonObject);
        } catch (Exception ex) {
            System.out.println("### >>>-------> EXCEPTION :: Add My JSON Object In JSON Array:" + ex.getMessage());
        }
        return jsonArrayForIndividualScenariosDetails;
    }

    public static JsonObject generateMAPValueToJSONValues(Map<?, ?> dataMap) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(dataMap);
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        return jsonObject;
    }

    public static Set<Object> resultDatas = new LinkedHashSet<Object>();

    public static void main(String[] args) {
        ReportingUtil.getAllInformationFromQAResultsSheet();

        Map<String, Map<String, Integer>> tagCounts = ReportingUtil.getTagCounts(jsonArrayForIndividualScenariosDetails.toString());
        Map<Object, Object> statusMap = new LinkedHashMap<>();

        for (String outerKey : tagCounts.keySet()) {
            System.out.println("Checking: " + outerKey);
            statusMap.put("Tags",outerKey);
            Map<String, Integer> innerMap = tagCounts.get(outerKey);
            for (String innerKey : innerMap.keySet()) {
                statusMap.put(innerKey,innerMap.get(innerKey).toString());
             }
        }

        mapToHtmlTable(statusMap);
        System.out.println("$$$$$$$$$$$$$$$$$$");
        getExecDetails(jsonArrayForIndividualScenariosDetails);
        System.out.println(jsonArrayForIndividualTestResultsDetails);
        JsonArrayToHTMLTable(jsonArrayForIndividualTestResultsDetails);
    }

    private static JsonArray jsonArrayForIndividualTestResultsDetails = new JsonArray();

    public static JsonArray jsonObjectToJsonArray(JsonObject jsonObject) {
        try {
            jsonArrayForIndividualTestResultsDetails.add(jsonObject);
        } catch (Exception ex) {
            System.out.println("### >>>-------> EXCEPTION :: JsonObject To JsonArray:" + ex.getMessage());
        }
        return jsonArrayForIndividualTestResultsDetails;
    }

    public static void getExecDetails(JsonArray jArray) {
        try {
            // Deserialize the JSON array into an array of TestResult objects
            Gson gson = new Gson();
            TestResult[] results = gson.fromJson(jArray.toString(), TestResult[].class);
            LinkedHashMap<String, String> resultTable = new LinkedHashMap<>();
            // Access the properties of each TestResult object
            for (TestResult result : results) {
                resultTable.put("Scenario Name", result.scenarioName);
                resultTable.put("Test data", result.testdata);
                resultTable.put("Status", result.status);

                resultDatas.add(resultTable);

                JsonObject jsonObj = generateMAPValueToJSONValues(resultTable);
                jsonObjectToJsonArray(jsonObj);
                resultTable.clear();
                resultDatas.clear();
            }
        } catch (Exception ex) {
            System.out.println("### >>>-------> EXCEPTION ::get Exec.Details:" + ex.getMessage());
        }
    }

    public static String mapToHtmlTable(Map<Object, Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("<table>");
        sb.append("<tr><th>Key</th><th>Value</th></tr>");
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            sb.append("<tr>");
            sb.append("<td>").append(entry.getKey()).append("</td>");
            sb.append("<td>").append(entry.getValue()).append("</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");

        System.out.println("777777777777777777777");
        System.out.println(sb.toString());
        System.out.println("777777777777777777777");
        return sb.toString();
    }

    public static String JsonArrayToHTMLTable(JsonArray json){
        String strHTMLTableData="";
        try{

            // parse the json string into a JsonArray
            JsonArray jsonArray = JsonParser.parseString(json.toString()).getAsJsonArray();

            // create a new StringBuilder to build the HTML table
            StringBuilder htmlTableBuilder = new StringBuilder();

            // append the opening table tag and CSS style
            htmlTableBuilder.append("<table id='OverallExecStatus'>");

            // append the table header row
            htmlTableBuilder.append("<tr><th id='hdrExecSts'>Scenario Name</th>" +
                    "<th id='hdrExecSts>Test data</th>" +
                    "<th id='hdrExecSts>Status</th></tr>");

            // loop through the JsonArray and append each row to the HTML table
            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();
                String scenarioName = jsonObject.get("Scenario Name").getAsString();
                String testData = jsonObject.get("Test data").getAsString();
                String status = jsonObject.get("Status").getAsString();

                htmlTableBuilder.append("<tr>");
                htmlTableBuilder.append("<td>").append(scenarioName).append("</td>");
                htmlTableBuilder.append("<td>").append(testData).append("</td>");
                htmlTableBuilder.append("<td>").append(status).append("</td>");
                htmlTableBuilder.append("</tr>");
            }

            // append the closing table tag
            htmlTableBuilder.append("</table>");

            // print the HTML table to the console
            System.out.println(htmlTableBuilder.toString());
            strHTMLTableData=htmlTableBuilder.toString();
        } catch (Exception ex) {
            System.out.println("### >>>-------> EXCEPTION : Json Array To HTML Table:" + ex.getMessage());
        }
        return strHTMLTableData;
    }
    class TestResult {
        String featureName;
        String scenarioName;
        String tags;
        String testdata;
        String status;
        String jenkinsLog;
        String specification;
    }
}
