package cap.utilities;

import com.google.common.io.Resources;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WaitTimeUtil {

    public static int getWaitTime(String strWaitVaribaleName) {
        int inWaitTime = 60;

        try {
            String strWaitNumber = System.getProperty("wait_time", "");
            String strWaitTime = System.getProperty(strWaitVaribaleName, "");

            inWaitTime = Integer.parseInt(strWaitTime) * Integer.parseInt(strWaitNumber);
        } catch (Exception e) {

        }
        return inWaitTime;
    }

    public static int getWaitForInvisibilityTime(String strWaitVaribaleName) {
        int inWaitTime = 60;

        try {
            String strWaitNumber = System.getProperty("wait-time", "");
            String strWaitTime = System.getProperty(strWaitVaribaleName, "");

            inWaitTime = Integer.parseInt(strWaitTime) * Integer.parseInt(strWaitNumber);
        } catch (Exception e) {

        }
        return inWaitTime;
    }


    public static String getjenkinsExecutionLog() {
        // >>>  localhost:8082//computer/api/xml?tree=computer[executors[currentExecutable[*]]]

        String responseBody = "";
        try {
            // Set the URL for the API request
            URL url = new URL("http://localhost:8082//computer/api/xml?tree=computer[executors[currentExecutable[*]]]");

            // Open a connection to the URL
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Set the HTTP method to GET
            conn.setRequestMethod("GET");

            // Read the XML response from the API
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // Creating an instance of file
            responseBody = response.toString();
            System.out.println(response.toString());
            Path path
                    = Paths.get("config/yasin.xml");

            // Custom string as an input

            byte[] arr = responseBody.getBytes();


            // Now calling Files.writeString() method
            // with path , content & standard charsets
            Files.write(path, arr);

            // Print the XML response

        } catch (Exception ex) {
            System.out.println("" + ex.getMessage());
            ex.printStackTrace();
        }
        return responseBody;
    }

    public static void parseXMLFile(String strXMLData) {
        try {
            // Create a DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the XML document
            Document document = builder.parse(strXMLData);

            // Get the first book element
            Element book = (Element) document.getElementsByTagName("currentExecutable").item(0);

            // Get the title element
            Element title = (Element) book.getElementsByTagName("fullDisplayName").item(0);

            // Get the text content of the title element
            String titleValue = title.getTextContent();


            Element title1 = (Element) book.getElementsByTagName("url").item(0);

            // Get the text content of the title element
            String titleValue1 = title1.getTextContent();

            // Print the title value
            System.out.println("Title: " + titleValue);

            System.out.println("URL: " + titleValue1);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
