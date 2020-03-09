package SOAP;

import Models.Url;
import Models.Usuario;
import Services.Urls;
import Services.Users;
import Utils.Base62;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import org.h2.engine.User;

import javax.jws.WebService;
import java.util.List;

@WebService (endpointInterface = "SOAP.UrlWebService")
public class UrlWebServiceImpl implements UrlWebService{
    @Override
    public List<Url> getUrlsFromUser(String username) {
        Usuario user = new Users().searchUserByUsername(username);
        return new Urls().getUrlByUser(user.getId());
    }

    @Override
    public String hello() {
        return null;
    }


    @Override
    public Url shortenUrl (String urlOriginal, String username) {
        Url url = new Url(urlOriginal);
        String shortenedUrl = new Base62().encode(urlOriginal);
        url.setUrlBase62(shortenedUrl);
        url.setUrlIndexada(Long.toString(new Urls().getSizeUrl()+1));
        String baseUrl = "http://api.linkpreview.net/?key=5de79e90a156b442e87430922b367e4ad6f85640a7bca&q=";
        String incomingUrl = url.getUrlOriginal();
        String completedUrl = baseUrl + incomingUrl;
        HttpResponse<kong.unirest.JsonNode> preview = Unirest.get(completedUrl)
                .asJson();

        JSONObject myObj = preview.getBody().getObject();
        String imagePreview = myObj.getString("image");
        String descriptionPreview = myObj.getString("description");

        url.setIamgePreview(imagePreview);
        url.setDescriptionPreview(descriptionPreview);

        Usuario user;
        if (username != null && !username.isEmpty()) {
            user = new Users().searchUserByUsername(username);
            if (user == null)  {
                new Urls().crear(url);
                return null;
            }
            else {
                url.setCreador(user);
                user.getUrlCreadas().add(url);
                new Urls().crear(url);
                new Users().editar(user);
            }


        }
        return url;
    }
}
