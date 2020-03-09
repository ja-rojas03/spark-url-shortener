package Services;

import Models.Url;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class Urls extends Entities {
    public List<Url> getUrls(){
        return getEntityManager().createQuery("Select u from Url u").getResultList();
    }

    public Url findUrlByShort(String urlShort){
        Query query = getEntityManager().createQuery("Select u from Url u where u.urlBase62 =:urlShort");
        query.setParameter("urlShort", urlShort);
        try{
            return (Url) query.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }

    public Url findUrlByOriginal(String urlOriginal){
        Query query = getEntityManager().createQuery("Select u from Url u where u.urlOriginal =:urlOriginal");
        query.setParameter("urlOriginal", urlOriginal);
        try{
            return (Url) query.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }

    public Url findUrlById(String id){
        Query query = getEntityManager().createQuery("Select u from Url u where u.urlIndexada =:id");
        query.setParameter("id", id);
        try{
            return (Url) query.getSingleResult();
        }catch(NoResultException e){
            return null;
        }
    }

    public long getSizeUrl(){
        return (Long) getEntityManager().createQuery("Select count(u.urlIndexada) from Url u").getSingleResult();
    }

    public List<Url> getUrlByUser(String userid){
        Query query = getEntityManager().createQuery("Select u from Url u where u.creador.id =:userid");
        query.setParameter("userid", userid);
        System.out.println("QUERIS"+ query.getResultList());
        try{
            return (List<Url>) query.getResultList();
        }catch(NoResultException e){
            return null;
        }
    }

    public List<Url> getAllUrls(){
        Query query = getEntityManager().createQuery("Select * from Url u");
        System.out.println("QUERIS"+ query.getResultList());
        try{
            return (List<Url>) query.getResultList();
        }catch(NoResultException e){
            return null;
        }
    }

    public List<Url> getUrlByNonUser() {
        Query query = getEntityManager().createQuery("Select u from Url u where u.creador.id = null");
        System.out.println("QUERIS"+ query.getResultList());
        try{
            return (List<Url>) query.getResultList();
        }catch(NoResultException e){
            return null;
        }
    }
}
