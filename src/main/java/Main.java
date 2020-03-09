import Controllers.Controller;
import Filters.Filtros;
import Handlers.DBHandler;
import Models.Url;
import Models.Usuario;
import Models.Visita;
import SOAP.SoapInit;
import Services.*;
import Utils.Base62;
import Utils.JWT;
import Utils.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import spark.Session;
import ua_parser.Client;
import ua_parser.Parser;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static spark.Spark.*;

public class Main {
    public final static String ACCEPT_TYPE_JSON = "application/json";
    public final static String ACCEPT_TYPE_XML = "application/xml";
    public final static String ACCEPT_TYPE_TEXT = "text/html";

    public static void main(String[] args) throws Exception {
        staticFileLocation("/public");
        port(getHerokuAssignedPort());
        DBHandler.startDb();
        new Filtros().manejarFiltros();
        new Entities<>();
        Users users = new Users();
        Urls urls = new Urls();
        Visits visits = new Visits();
        createAdminUser(users);
        SoapInit.init();

        final Configuration configuration = new Configuration(new Version(2, 3, 0));
        try {
            configuration.setDirectoryForTemplateLoading(new File(
                    "src/main/resources/freemarker"));
        } catch(IOException e) {
            e.printStackTrace();
        }

        /////////////////////////////>>>>>>>>>>>GETS<<<<<<<<<<<////////////////////////////////////////
        /***
         * Index route
         * **/
        get("/", (request, response) ->{

            Map<String, Object> attributes = new HashMap<>();
            Usuario user = request.session(true).attribute("usuario");
            attributes.put("user", user);

            if(user != null){
                attributes.put("links", urls.getUrlByUser(user.getId()));
                attributes.put("creador", "someone");
            }
            else{
                attributes.put("links", urls.getUrlByNonUser());
                attributes.put("creador", "none");
            }

            return renderTemplate(configuration, attributes, "index.ftl");
        });

        /** GETTER TO GO TO SHORTENED URL **/
        get("/clicky.com/:index", (request, response) -> {
            String urlShort = request.pathInfo();
            System.out.println(urlShort);
            Url url = urls.findUrlByShort(urlShort);
            if(url != null){
                System.out.println("Going to..." + url.getUrlOriginal());

                Parser uaParser = new Parser();
                Client c = uaParser.parse(request.userAgent());
                String sistemaOperativo = c.os.family + " " + c.os.major;
                String browser = c.userAgent.family;
                String ip = request.ip();
                String id = UUID.randomUUID().toString();
                long hour = LocalTime.now().getHour();
                String day = LocalDate.now().getDayOfWeek().toString();
                Visita visita = new Visita(url, sistemaOperativo, browser, ip, day, hour);
                visita.setId(id);
                visits.crear(visita);

                response.redirect(url.getUrlOriginal());
            }
            else{
                response.redirect("/");
            }
            return "";
        });

        /** GETTER TO GET LINK STATS **/
        get("/stats/:id", (request, response) -> {
            String urlid = request.params("id");
            Url url = urls.findUrlById(urlid);
            String neededUrl = url.getUrlOriginal();
            String urlShort =  url.getUrlBase62();
            Map<String, Object> attributes = Controller.getInstance().getStats(neededUrl,urlShort, visits);
            Usuario user = request.session().attribute("usuario");
            attributes.put("user", user);
            return renderTemplate(configuration, attributes, "stats.ftl");
        });

        /** GETTER TO LOGIN **/
        get("/login", (request, response) -> {
            String warningText = "";
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("warningText", warningText);
            return renderTemplate(configuration, attributes, "login.ftl");
        });

        /** GETTER TO REGISTER **/
        get("/register", (request, response) -> {
            String warningText = "";
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("warningText", warningText);
            return renderTemplate(configuration, attributes, "register.ftl");
        });

        /** GETTER TO END SESSION **/
        get("/logout", (request, response) -> {
            Session session=request.session(true);
            session.invalidate();
            response.removeCookie("usuario_id");
            response.redirect("/");
            return "";
        });


        /** GETTER TO GET ALL USERS **/
        get("/users", (request, response) -> {
            boolean isFirstAdmin = false;
            Usuario user = request.session().attribute("usuario");

            if(user.isAdministrador()){
                Map<String,Object> attributes = new HashMap<>();
                attributes.put("user", user);
                attributes.put("users", users.getUsuarios());
                return renderTemplate(configuration, attributes, "users.ftl");
            }else {
                return "NOT AN ADMIN!";
            }
        });

        /////////////////////////////>>>>>>>>>>>POSTS<<<<<<<<<<<////////////////////////////////////////

        post("/make-user-admin/:username", (request, response) -> {
            String username = request.params("username");
            if (username.equalsIgnoreCase("Admin")) {
                response.redirect("/users");
                return "";
            }
            Usuario user = users.searchUserByUsername(username);
            boolean isAdmin = request.queryParams("is-admin") != null;
            user.setAdministrador(isAdmin);
            users.editar(user);
            response.redirect("/users");
            return "";
        });
        /** POST TO CREATE SHORTENED URL **/
        post("/createUrl", (request, response) -> {
            String originalUrl = request.queryParams("originalUrl");
            Url url = new Url(originalUrl);
            String shortenedUrl = new Base62().encode(originalUrl);

            url.setUrlBase62(shortenedUrl);
            url.setUrlIndexada(Long.toString(urls.getSizeUrl()+1));

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

            Usuario usuario = request.session().attribute("usuario");
            if(usuario != null) {
                url.setCreador(usuario);
                usuario.getUrlCreadas().add(url);
                urls.crear(url);
                users.editar(usuario);
            } else {
                urls.crear(url);
            }


            response.redirect("/");
            return "";
        });



        /** POST TO LOGIN **/
        post("/login", (request, response) -> {
            String username = request.queryParams("username");
            String password = request.queryParams("password");
            String passwordHash = DigestUtils.md5Hex(password);
            if(!users.validatePassword(username, passwordHash)){
                String warningText = "Usuario o contrasena incorrectos.";
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("warningText", warningText);
                return renderTemplate(configuration, attributes, "login.ftl");
            }

            Session session=request.session(true);
            Usuario usuario = users.searchUserByUsername(username);
            session.attribute("usuario", usuario);
            String remember = request.queryParams("remember");

            if(remember != null){
                response.cookie("usuario_id", usuario.getId(), 604800000);
            }
            response.redirect("/");
            return "";
        });

        /** POST TO REGISTER USER **/
        post("/register", (request, response) -> {
            String nombre = request.queryParams("first_name") + " " + request.queryParams("last_name");
            String username = request.queryParams("username");
            if(!users.validateUsername(username)){
                String warningText = "Usuario ya existe";
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("warningText", warningText);
                return renderTemplate(configuration, attributes, "register.ftl");
            }
            String password = request.queryParams("password");
            String confirmPassword = request.queryParams("confirm_password");
            if(!password.equals(confirmPassword)){
                String warningText = "Las contraseñas no coinciden";
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("warningText", warningText);
                return renderTemplate(configuration, attributes, "register.ftl");
            }
            String hashedPassword = DigestUtils.md5Hex(password);
            Usuario usuario = new Usuario(username, nombre, hashedPassword, false);
            usuario.setId(UUID.randomUUID().toString());
            users.crear(usuario);
            Session session=request.session(true);
            session.attribute("usuario", usuario);
            response.redirect("/");
            return "";
        });

        post("/token", (request, response) -> {
            JsonObject json = new JsonObject();
            json.addProperty("token", JWT.createJWT(UUID.randomUUID().toString(), "isc415", "Access Token", 0));
            return json;
        });

        /** REST **/

        path("/api", () -> {
            before("/*", (request, response) -> {
                String token = request.headers("token") != null ? request.headers("token") : request.headers("TOKEN");

                if (token == null || token.isEmpty() || !JWT.decodeJWT(token)) {
                    halt(401);
                }
            });
            afterAfter("/*", (request, response) -> {
                if(request.headers("Accept") != null && request.headers("Accept").equalsIgnoreCase(ACCEPT_TYPE_XML)){
                    response.header("Content-Type", ACCEPT_TYPE_XML);
                }else{
                    response.header("Content-Type", ACCEPT_TYPE_JSON);
                }
            });
            //end of filter^^

            get("/:username", (request, response) -> {
                String username = request.params("username");
                Usuario user = users.searchUserByUsername(username);
                String obj = JsonUtils.jsonUrls(user);
                return obj;
            });

            post("/", Main.ACCEPT_TYPE_JSON, (request, response) -> {
//                String urlOg = request.params("originalUrl");
                Url url = new Gson().fromJson(request.body(), Url.class);
                String shortenedUrl = new Base62().encode(url.getUrlOriginal());

                url.setUrlBase62(shortenedUrl);
                url.setUrlIndexada(Long.toString(urls.getSizeUrl()+1));

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

                Usuario usuario = request.session().attribute("usuario");
                if(usuario != null) {
                    url.setCreador(usuario);
                    usuario.getUrlCreadas().add(url);
                    urls.crear(url);
                    users.editar(usuario);
                } else {
                    urls.crear(url);
                }
                //TODO make return statement
                String returned = JsonUtils.jsonUrl(url, visits);
                return returned;
            });

        });



    }




    public static void createAdminUser(Users users) {
        //creating User
        if(users.getSizeUsuario() == 0){
            Usuario user = null;
            user = new Usuario("Admin", "Admin", DigestUtils.md5Hex("admin"), true);
            user.setId(UUID.randomUUID().toString());
            users.crear(user);
            System.out.println("Creating admin user...");
        }
        //
    }
  
    public static StringWriter renderTemplate(Configuration configuration, Map<String, Object> model, String templatePath) throws IOException, TemplateException {
        Template plantillaPrincipal = configuration.getTemplate(templatePath);
        StringWriter writer = new StringWriter();
        plantillaPrincipal.process(model, writer);
        return writer;
    }
    /**
     * Metodo para setear el puerto en Heroku
     * @return
     */
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //Retorna el puerto por defecto en caso de no estar en Heroku.
    }

    private static class JsonNode {
    }
}
