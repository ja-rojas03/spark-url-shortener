package Services;

import Models.Usuario;
import org.h2.engine.User;

import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class Users extends Entities {

    public Users(){

    }

    public List<Usuario> getUsuarios(){
        return getEntityManager().createQuery("Select u from Usuario u", Usuario.class).getResultList();
    }

    public boolean validateUsername(String username){
        Usuario usuario = null;
        Query query = getEntityManager().createQuery("Select u from Usuario u where u.username =:username");
        query.setParameter("username", username);
        try{
            usuario = (Usuario) query.getSingleResult();
        }catch(NoResultException e){
            return true;
        }
        return false;
    }

    public boolean validatePassword(String username, String password){
        Usuario usuario = null;
        System.out.println("El usuario es: " + username);
        System.out.println("El password es: " + password);
        Query query = getEntityManager().createQuery("Select u from Usuario u where u.username =:username and " +
                "u.password =:password ");
        query.setParameter("username", username);
        query.setParameter("password", password);
        try{
            usuario = (Usuario) query.getSingleResult();
        }catch(NoResultException e){
            return false;
        }
        return true;
    }

    public Usuario searchUserByUsername(String username){
        Query query = getEntityManager().createQuery("Select u from Usuario u where u.username =:username");
        query.setParameter("username", username);
        try{
            return (Usuario) query.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }

    public Usuario searchUserById(String id){
        Query query = getEntityManager().createQuery("Select u from Usuario u where u.id =:id");
        query.setParameter("id", id);
        try{
            return (Usuario) query.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }

    public long getSizeUsuario(){
        return (long) getEntityManager().createQuery("Select count(u.id) from Usuario u").getSingleResult();
    }

    public void grantAdmin(String id) {
        System.out.println("INSISDE GRANT FUNC");

        EntityTransaction tx = getEntityManager().getTransaction();
        Query query = getEntityManager().createQuery("UPDATE Usuario SET administrador = true WHERE id =:id");
        query.setParameter("id", id);

        tx.begin();
        System.out.println("GRANTED??");
        System.out.println("TX STATUS"+ tx.isActive());
        int res = query.executeUpdate();
        tx.commit();
        getEntityManager().close();

    }
}
