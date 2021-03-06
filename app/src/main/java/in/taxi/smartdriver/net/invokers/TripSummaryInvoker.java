package in.taxi.smartdriver.net.invokers;


import org.json.JSONObject;

import java.util.HashMap;

import in.taxi.smartdriver.model.TripSummaryBean;
import in.taxi.smartdriver.net.ServiceNames;
import in.taxi.smartdriver.net.WebConnector;
import in.taxi.smartdriver.net.parsers.TripSummaryParser;
import in.taxi.smartdriver.net.utils.WSConstants;

public class TripSummaryInvoker extends BaseInvoker {

    public TripSummaryInvoker() {
        super();
    }

    public TripSummaryInvoker(HashMap<String, String> urlParams,
                              JSONObject postData) {
        super(urlParams, postData);
    }

    public TripSummaryBean invokeTripSummaryWS() {

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.TRIP_SUMMARY), WSConstants.PROTOCOL_HTTP, urlParams, null);

        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
//    String wsResponseString=webConnector.connectToPOST_service();
        String wsResponseString = webConnector.connectToGET_service();
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        TripSummaryBean tripSummaryBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
            registerBean.setWebError(true);*/
            return tripSummaryBean = null;
        } else {
            tripSummaryBean = new TripSummaryBean();
            TripSummaryParser tripSummaryParser = new TripSummaryParser();
            tripSummaryBean = tripSummaryParser.parseTripSummaryResponse(wsResponseString);
            return tripSummaryBean;

        }
    }
}
