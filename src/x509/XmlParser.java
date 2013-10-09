/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package x509;

/**
 *
 * @author phoenix
 */
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XmlParser {

  public static void main(String[] args) {

    try {
//      FileInputStream file = new FileInputStream(new File("data/TransactionData.xml"));
      FileInputStream file = new FileInputStream(new File("data/TransactionDataSelfSigned.xml"));

      DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

      DocumentBuilder builder = builderFactory.newDocumentBuilder();

      Document xmlDocument = builder.parse(file);

      XPath xPath = XPathFactory.newInstance().newXPath();

      System.out.println("*************************");
      /*Presciber */
      String exprDEA = "/Message/Body/*/Prescriber/Identification/DEANumber/text() ";
      String exprSSN = "/Message/Body/*/Prescriber/Identification/SocialSecurity/text()";
      String exprLastName = "/Message/Body/*/Prescriber/Name/LastName/text()";
      String exprFirstName = "/Message/Body/*/Prescriber/Name/FirstName/text()";
      String exprAddress1 = "/Message/Body/*/Prescriber/Address/AddressLine1/text()";
      String exprAddress2 = "/Message/Body/*/Prescriber/Address/AddressLine2/text()";
      String exprCityName = "/Message/Body/*/Prescriber/Address/City/text()";
      String exprState = "/Message/Body/*/Prescriber/Address/State/text()";
      String exprPostalCode = "Message/Body/*/Prescriber/Address/ZipCode/text() ";
      /* Patient */
      String exprpLastName = "/Message/Body/*/Patient/Name/LastName/text()";
      String exprpFirstName = "/Message/Body/*/Patient/Name/FirstName/text()";
      String exprpAddress1 = "/Message/Body/*/Patient/Address/AddressLine1/text()";
      String exprpAddress2 = "/Message/Body/*/Patient/Address/AddressLine2/text()";
      String exprpCity = "/Message/Body/*/Patient/Address/City/text()";
      String exprpState = "/Message/Body/*/Patient/Address/State/text()";
      String exprpPostalCode = "/Message/Body/*/Patient/Address/ZipCode/text()";
      /* Medication */
      String drugName = "/Message/Body/*/MedicationPrescribed/DrugDescription/text()";
      String drugStrength = "/Message/Body/*/MedicationPrescribed/DrugCoded/Strength/text()";
      String quantity = "/Message/Body/*/MedicationPrescribed/Quantity/Value/ text()";
      String dosage = "/Message/Body/*/MedicationPrescribed/Directions/text()";
      String date1 = "substring(Message/Body/*/MedicationPrescribed/WrittenDate/Date/text(),1,4)";
      String date2 = "substring(Message/Body/*/MedicationPrescribed/WrittenDate/Date/text(),6,2)";
      String date3 = "substring(Message/Body/*/MedicationPrescribed/WrittenDate/Date/text(),9,2)";
      String date4 = "substring(Message/Body/*/MedicationPrescribed/WrittenDate/Date/text(),1,4)";
      String date5 = "substring(Message/Body/*/MedicationPrescribed/WrittenDate/Date/text(),6,2)";
      String date6 = "substring(Message/Body/*/MedicationPrescribed/WrittenDate/Date/text(),9,2)";
      String dateEffective1 = "substring(Message/Body/*/MedicationPrescribed/EffectiveDate/Date/text(),1,4)";
      String dateEffective2 = "substring(Message/Body/*/MedicationPrescribed/EffectiveDate/Date/text(),6,2)";
      String dateEffective3 = "substring(Message/Body/*/MedicationPrescribed/EffectiveDate/Date/text(),9,2)";
      String dateEffective4 = "substring(Message/Body/*/MedicationPrescribed/EffectiveDate/Date/text(),1,4)";
      String dateEffective5 = "substring(Message/Body/*/MedicationPrescribed/EffectiveDate/Date/text(),6,2)";
      String dateEffective6 = "substring(Message/Body/*/MedicationPrescribed/EffectiveDate/Date/text(),9,2)";
      String refillQualifier = "/Message/Body/*/MedicationPrescribed/Refills/Qualifier/text()";
      String refillQuantity = "/Message/Body/*/MedicationPrescribed/Refills/Value/text()";
      String note = "/Message/Body/*/MedicationPrescribed/Note/text()";

      LinkedList<String> fieldXpathList = new LinkedList<String>();
      fieldXpathList.push(exprDEA);//1
      fieldXpathList.push(exprSSN);//2
      fieldXpathList.push(exprLastName);//3
      fieldXpathList.push(exprFirstName);//4
      fieldXpathList.push(exprAddress1);//5
      fieldXpathList.push(exprAddress2);//6
      fieldXpathList.push(exprCityName);//7
      fieldXpathList.push(exprState);//8
      fieldXpathList.push(exprPostalCode);//9
      // patient
      fieldXpathList.push(exprpLastName);//10
      fieldXpathList.push(exprpFirstName);//11
      fieldXpathList.push(exprpAddress1);//12
      fieldXpathList.push(exprpAddress2);//13
      fieldXpathList.push(exprpCity);//14
      fieldXpathList.push(exprpState);//15
      fieldXpathList.push(exprpPostalCode);//16
      // Medication
      fieldXpathList.push(drugName);//17
      fieldXpathList.push(drugStrength);//18
      fieldXpathList.push(quantity);//19
      fieldXpathList.push(dosage);//20
      fieldXpathList.push(date1);//21
      fieldXpathList.push(date2);//22
      fieldXpathList.push(date3);//23
      fieldXpathList.push(date4);//24
      fieldXpathList.push(date5);//25
      fieldXpathList.push(date6);//26
      fieldXpathList.push(dateEffective1);//27
      fieldXpathList.push(dateEffective2);//28
      fieldXpathList.push(dateEffective3);//29
      fieldXpathList.push(dateEffective4);//30
      fieldXpathList.push(dateEffective5);//31
      fieldXpathList.push(dateEffective6);//32
      fieldXpathList.push(refillQualifier);//33
      fieldXpathList.push(refillQuantity);//34
      fieldXpathList.push(note);//35

      //System.out.println(fieldXpathList);

      LinkedList fieldValueList = new LinkedList();
      String fieldValueString = "";
      for (String fieldXpath : fieldXpathList) {
        String fieldValue = xPath.compile(fieldXpath).evaluate(xmlDocument);
        fieldValueList.push(fieldValue);
        fieldValueString = fieldValue + fieldValueString;
        System.out.println(fieldValue);
      }
      System.out.println("fieldValueString:" + fieldValueString);

      System.out.println("*************************");

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (XPathExpressionException e) {
      e.printStackTrace();
    }
  }

  public Document loadXMLFromString(String xml) throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    factory.setNamespaceAware(true);
    DocumentBuilder builder = factory.newDocumentBuilder();

    return builder.parse(new ByteArrayInputStream(xml.getBytes()));
  }
}
