package in.taxi.smartdriver.net.invokers;


import org.json.JSONObject;

import java.util.HashMap;

import in.taxi.smartdriver.model.AuthBean;
import in.taxi.smartdriver.net.ServiceNames;
import in.taxi.smartdriver.net.WebConnector;
import in.taxi.smartdriver.net.parsers.PhoneRegisrationParser;
import in.taxi.smartdriver.net.utils.WSConstants;

public class PhoneRegistrationInvoker extends BaseInvoker {

    public PhoneRegistrationInvoker() {
        super();
    }

    public PhoneRegistrationInvoker(HashMap<String, String> urlParams,
                                    JSONObject postData) {
        super(urlParams, postData);
    }

    public AuthBean invokePhoneRegistrationWS() {

        System.out.println("POSTDATA>>>>>>>" + postData);

        WebConnector webConnector;

        webConnector = new WebConnector(new StringBuilder(ServiceNames.PHONE_REGISTRATION), WSConstants.PROTOCOL_HTTP, null, postData);

        String wsResponseString = webConnector.connectToPOST_service();

        System.out.println(">>>>>>>>>>> response: " + wsResponseString);
        AuthBean authBean = null;
        if (wsResponseString.equals("")) {

            return authBean = null;
        } else {
            authBean = new AuthBean();
            PhoneRegisrationParser phoneRegisrationParser = new PhoneRegisrationParser();
            authBean = phoneRegisrationParser.parsePhoneRegistrationResponse(wsResponseString);
            return authBean;
        }
    }
}
