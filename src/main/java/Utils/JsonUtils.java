package Utils;

import Controllers.Controller;
import Models.Url;
import Models.Usuario;
import Services.Urls;
import Services.Visits;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import spark.ResponseTransformer;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Map;

public class JsonUtils {
    private static Gson gson = new GsonBuilder().serializeNulls().create();
    private static Gson gsonExpose = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public static String toJsonNonExpose(Object object) {
        return gson.toJson(object);
    }

    public static String toJson(Object object) {
        return gsonExpose.toJson(object);
    }

    public static ResponseTransformer json() {
        return JsonUtils::toJson;
    }

    public static String jsonUrls(Usuario user) throws InterruptedException {

        String userString = toJson(user);
        JSONObject obj = new JSONObject(userString);
        JSONArray urlArr = obj.getJSONArray("urlCreadas");
        JSONArray urlArrnew = new JSONArray();
        String urlog = "";
        String urlBase62 = "";

        for(Object val : urlArr){
            JSONObject ob = (JSONObject) val;
            urlog = ob.getString("urlOriginal");
            urlBase62 = ob.getString("urlBase62");
            Visits visits = new Visits();
            Map<String, Object> attributes = Controller.getInstance().getStats(urlog, urlBase62, visits);
            attributes.put("urlOriginal", urlog);
            attributes.put("urlBase62", urlBase62);
            urlArrnew.put(attributes);

        }

        obj.put("urlCreadas",urlArrnew);
        return obj.toString();
    }

    public static String jsonUrl(Url url, Visits visits) throws IOException {
        String urlString = toJson(url);
        JSONObject obj = new JSONObject(urlString);
        Map<String, Object> attributes = Controller.getInstance().getStats(url.getUrlOriginal(), url.getUrlBase62(), visits);
        //Encode 64 process
        String urlS = url.getIamgePreview();
        URL urlac = new URL(urlS);
        InputStream in = new BufferedInputStream(urlac.openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        while (-1!=(n=in.read(buf)))
        {
            out.write(buf, 0, n);
        }
        out.close();
        in.close();
        byte[] response = out.toByteArray();

        String encodedURL = new String(Base64.encodeBase64(response));
        Date today = new Date();


        obj.put("stats", attributes);
        obj.put("creationDate", today.toString());
        obj.put("iamgePreview", encodedURL);

        return obj.toString();
    }
}