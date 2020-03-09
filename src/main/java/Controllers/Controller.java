package Controllers;

import Models.Url;
import Models.Usuario;
import Models.Visita;
import Services.Visits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Controller {

    private ArrayList<Url> myUrls;
    private ArrayList<Usuario> myUsers;
    private ArrayList<Visita> myVisits;
    private static Controller controller;

    public Controller() {
        this.myUrls = new ArrayList<>();
        this.myUsers = new ArrayList<>();
        this.myVisits = new ArrayList<>();
    }

    public static Controller getInstance() {
        return controller == null ? new Controller() : controller;
    }

    public Url findUrlByShort(String urlShort){
        for(Url url : Controller.getInstance().getMyUrls()){
            if(url.getUrlBase62().equalsIgnoreCase(urlShort)){
                return url;
            }
        }
        return null;
    }
    public Map<String,Object> getStats(String url, String urlShort, Visits visits) {

        //Browsers
        long chromeVisits = visits.getSizeVisitaByShortUrlBrowser(urlShort, "Chrome");
        long operaVisits = visits.getSizeVisitaByShortUrlBrowser(urlShort, "Opera");
        long firefoxVisits = visits.getSizeVisitaByShortUrlBrowser(urlShort, "Firefox");
        long edgeVisits = visits.getSizeVisitaByShortUrlBrowser(urlShort, "Edge");
        long safariVisits = visits.getSizeVisitaByShortUrlBrowser(urlShort, "Safari");

        //Days
        long mondayVisits = visits.getSizeByShortUrlDay(urlShort, "MONDAY");
        long tuesdayVisits = visits.getSizeByShortUrlDay(urlShort, "TUESDAY");
        long wednesdayVisits = visits.getSizeByShortUrlDay(urlShort, "WEDNESDAY");
        long thursdayVisits = visits.getSizeByShortUrlDay(urlShort, "THURSDAY");
        long fridayVisits = visits.getSizeByShortUrlDay(urlShort, "FRIDAY");
        long saturdayVisits = visits.getSizeByShortUrlDay(urlShort, "SATURDAY");
        long sundayVisits = visits.getSizeByShortUrlDay(urlShort, "SUNDAY");

        //Hours
        long zero = visits.getSizeByShortUrlHour(urlShort, 0);
        long one = visits.getSizeByShortUrlHour(urlShort, 1);
        long two = visits.getSizeByShortUrlHour(urlShort, 2);
        long three = visits.getSizeByShortUrlHour(urlShort, 3);
        long four = visits.getSizeByShortUrlHour(urlShort, 4);
        long five = visits.getSizeByShortUrlHour(urlShort, 5);
        long six = visits.getSizeByShortUrlHour(urlShort, 6);
        long seven = visits.getSizeByShortUrlHour(urlShort, 7);
        long eight = visits.getSizeByShortUrlHour(urlShort, 8);
        long nine = visits.getSizeByShortUrlHour(urlShort, 9);
        long ten = visits.getSizeByShortUrlHour(urlShort, 10);
        long eleven = visits.getSizeByShortUrlHour(urlShort, 11);
        long twelve = visits.getSizeByShortUrlHour(urlShort, 12);
        long thirteen = visits.getSizeByShortUrlHour(urlShort, 13);
        long fourteen = visits.getSizeByShortUrlHour(urlShort, 14);
        long fifteen = visits.getSizeByShortUrlHour(urlShort, 15);
        long sixteen = visits.getSizeByShortUrlHour(urlShort, 16);
        long seventeen = visits.getSizeByShortUrlHour(urlShort, 17);
        long eighteen = visits.getSizeByShortUrlHour(urlShort, 18);
        long nineteen = visits.getSizeByShortUrlHour(urlShort, 19);
        long twenty = visits.getSizeByShortUrlHour(urlShort, 20);
        long twenty_one = visits.getSizeByShortUrlHour(urlShort, 21);
        long twenty_two = visits.getSizeByShortUrlHour(urlShort, 22);
        long twenty_three = visits.getSizeByShortUrlHour(urlShort, 23);

        //OS
        long windows10 = visits.getSizeVisitaByShortUrlOs(urlShort, "Windows 10");
        long windows7 = visits.getSizeVisitaByShortUrlOs(urlShort, "Windows 7");
        long ubuntu1604 = visits.getSizeVisitaByShortUrlOs(urlShort, "Ubuntu 16.04");
        long ubuntu1804 = visits.getSizeVisitaByShortUrlOs(urlShort, "Ubuntu 18.04");
        long android9 = visits.getSizeVisitaByShortUrlOs(urlShort, "Android 9");
        long android8 = visits.getSizeVisitaByShortUrlOs(urlShort, "Android 8");

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("chromeVisits", chromeVisits);
        attributes.put("operaVisits", operaVisits);
        attributes.put("firefoxVisits", firefoxVisits);
        attributes.put("edgeVisits", edgeVisits);
        attributes.put("safariVisits", safariVisits);

        attributes.put("mondayVisits", mondayVisits);
        attributes.put("tuesdayVisits", tuesdayVisits);
        attributes.put("wednesdayVisits", wednesdayVisits);
        attributes.put("thursdayVisits", thursdayVisits);
        attributes.put("fridayVisits", fridayVisits);
        attributes.put("saturdayVisits", saturdayVisits);
        attributes.put("sundayVisits", sundayVisits);

        attributes.put("zero", zero);
        attributes.put("one", one);
        attributes.put("two", two);
        attributes.put("three", three);
        attributes.put("four", four);
        attributes.put("five", five);
        attributes.put("six", six);
        attributes.put("seven", seven);
        attributes.put("eight", eight);
        attributes.put("nine", nine);
        attributes.put("ten", ten);
        attributes.put("eleven", eleven);
        attributes.put("twelve", twelve);
        attributes.put("thirteen", thirteen);
        attributes.put("fourteen", fourteen);
        attributes.put("fifteen", fifteen);
        attributes.put("sixteen", sixteen);
        attributes.put("seventeen", seventeen);
        attributes.put("eighteen", eighteen);
        attributes.put("nineteen", nineteen);
        attributes.put("twenty", twenty);
        attributes.put("twenty_one", twenty_one);
        attributes.put("twenty_two", twenty_two);
        attributes.put("twenty_three", twenty_three);

        attributes.put("windows10",windows10);
        attributes.put("windows7",windows7);
        attributes.put("ubuntu1604",ubuntu1604);
        attributes.put("ubuntu1804",ubuntu1804);
        attributes.put("android8",android8);
        attributes.put("android9",android9);
        return attributes;
    }

    public ArrayList<Url> getMyUrls() {
        return myUrls;
    }
}
