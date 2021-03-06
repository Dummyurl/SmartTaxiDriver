package in.taxi.smartdriver.net.invokers;

import org.json.JSONObject;

import java.util.HashMap;

import in.taxi.smartdriver.model.TripBean;
import in.taxi.smartdriver.net.ServiceNames;
import in.taxi.smartdriver.net.WebConnector;
import in.taxi.smartdriver.net.parsers.TripParser;
import in.taxi.smartdriver.net.utils.WSConstants;

/**
 * Created by Jemsheer K D on 12 June, 2017.
 * Package in.techware.lataxidriver.net.invokers
 * Project LaTaxiDriver
 */

public class TripCompletionInvoker extends BaseInvoker {

    public TripCompletionInvoker() {
        super();
    }

    public TripCompletionInvoker(HashMap<String, String> urlParams,
                                 JSONObject postData) {
        super(urlParams, postData);
    }

    public TripBean invokeTripCompletionWS() {

        System.out.println("POSTDATA>>>>>>>" + postData);

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.TRIP_END), WSConstants.PROTOCOL_HTTP, null, postData);

        //		webConnector= new WebConnector(new StringBuilder(ServiceNames.AUTH_EMAIL), WSConstants.PROTOCOL_HTTP, postData,null);
        //webConnector= new WebConnector(new StringBuilder(ServiceNames.MODELS), WSConstants.PROTOCOL_HTTP, null);
        String wsResponseString = webConnector.connectToPOST_service();
        //	String wsResponseString=webConnector.connectToGET_service();
        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        TripBean tripBean = null;
        if (wsResponseString.equals("")) {
            /*registerBean=new RegisterBean();
            registerBean.setWebError(true);*/
            return tripBean = null;
        } else {
            tripBean = new TripBean();
            TripParser tripParser = new TripParser();
            tripBean = tripParser.parseTripResponse(wsResponseString);
            return tripBean;
        }
    }
}
