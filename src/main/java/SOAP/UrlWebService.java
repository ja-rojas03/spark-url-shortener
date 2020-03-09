package SOAP;


import Models.Url;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface UrlWebService {
    @WebMethod
    List<Url> getUrlsFromUser (String username);

    @WebMethod
    String hello();

    @WebMethod
    Url shortenUrl (String url, String username);
}
