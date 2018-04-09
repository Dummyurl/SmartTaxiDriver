package in.taxi.smartdriver.net.invokers;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import in.taxi.smartdriver.model.BasicBean;
import in.taxi.smartdriver.net.ServiceNames;
import in.taxi.smartdriver.net.WebConnector;
import in.taxi.smartdriver.net.parsers.BasicParser;
import in.taxi.smartdriver.net.utils.WSConstants;

public class DocumentUploadInvoker extends BaseInvoker {

    public DocumentUploadInvoker() {
        super();
    }

    public DocumentUploadInvoker(HashMap<String, String> urlParams,
                                 JSONObject postData) {
        super(urlParams, postData);

    }

    public BasicBean invokeDocumentUploadWS(ArrayList<String> fileList) {

        System.out.println("POSTDATA>>>>>>>" + postData);

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.DOCUMENT_UPDATE),
                WSConstants.PROTOCOL_HTTP, null, postData, fileList);

        String wsResponseString = webConnector.connectToMULTIPART_POST_service("document_update");

        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        BasicBean basicBean = null;
        if (wsResponseString.equals("")) {

            return basicBean = null;
        } else {
            basicBean = new BasicBean();
            BasicParser basicParser = new BasicParser();
            basicBean = basicParser.parseBasicResponse(wsResponseString);
            return basicBean;
        }
    }
}
