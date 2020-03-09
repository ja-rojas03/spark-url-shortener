package Filters;

import Models.Url;
import Models.Usuario;
import Services.Urls;
import Services.Users;
import spark.Request;
import spark.Response;

import static spark.Spark.before;
import static spark.Spark.halt;

public class Filtros {
    static private void checkIfUserIsCreatorOrAdmin(Request req, Response res) {
        Usuario user = req.session().attribute("usuario");
        String urlid = req.params("id");
        Url url = new Urls().findUrlById(urlid);
        if(url == null || user == null){
            res.redirect("/");
        }
//        else if(user.getId() != url.getCreador().getId() && !user.isAdministrador()){
//            res.redirect("/");
//        }
    }

    static private void checkIfIsAdmin(Request req, Response res) {
        Usuario user = req.session().attribute("usuario");
        if( user == null || !user.isAdministrador()){
            res.redirect("/");
        }
    }

    static private void checkIfLoggedIn(Request req, Response res) {
        Usuario user = req.session().attribute("usuario");
        if(user != null) res.redirect("/");
    }
    public void manejarFiltros(){
        before((request, response) -> {
            Usuario usuario = request.session().attribute("usuario");
            String id = request.cookie("usuario_id");
            if(id != null && usuario == null){
                //String unhashedUsername = DigestUtils.getDigest(username);
                Usuario userLog = new Users().searchUserById(id);
                request.session(true).attribute("usuario", userLog);
            }
        });

        before("/stats/:id", Filtros::checkIfUserIsCreatorOrAdmin);

        before("/users", Filtros::checkIfIsAdmin);

        before("/login", Filtros::checkIfLoggedIn);

        before("/register", Filtros::checkIfLoggedIn);
    }
}
