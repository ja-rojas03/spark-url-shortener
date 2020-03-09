package Utils;

import Controllers.Controller;
import Models.Url;
import Services.Urls;

import java.util.HashMap;
import java.util.Map;

public class Base62 {

    private Long counter;
    private Map<Long, String> indexToUrl;
    private Map<String, Long> urlToIndex;
    private String base62;

    public Base62() {
        counter = 1L;
        indexToUrl = new HashMap<>();
        urlToIndex = new HashMap<>();
        base62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    }

    // Encodes a URL to a shortened URL.
    public String encode(String longUrl) {
        if (urlToIndex.containsKey(longUrl)) {
            return "http://tinyurl.com/"+base62Encode(urlToIndex.get(longUrl));
        } else {
            indexToUrl.put(Long.parseLong(Integer.toString(Controller.getInstance().getMyUrls().size()+1)), longUrl);
            urlToIndex.put(longUrl, new Urls().getSizeUrl()+1);

            Url url = new Url(longUrl);
            url.setUrlIndexada("/clicky.com/"+urlToIndex.get(longUrl));
            url.setUrlBase62("/clicky.com/"+base62Encode(urlToIndex.get(longUrl)));
            Controller.getInstance().getMyUrls().add(url);
            String finalUrl = "/clicky.com/"+base62Encode(urlToIndex.get(longUrl));
            return finalUrl;
        }
    }

    // Decodes a shortened URL to its original URL.
    public String decode(String shortUrl) {
        String base62Encoded = shortUrl.substring(shortUrl.lastIndexOf("/") + 1);
        long decode = 0;
        for(int i = 0; i < base62Encoded.length(); i++) {
            decode = decode * 62 + base62.indexOf("" + base62Encoded.charAt(i));
        }
        return indexToUrl.get(decode);
    }

    private String base62Encode(long value) {
        StringBuilder sb = new StringBuilder();
        while (value != 0) {
            sb.append(base62.charAt((int)(value % 62)));
            value /= 62;
        }
        return sb.reverse().toString();
    }
}
