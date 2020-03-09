package Models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
public class Visita implements Serializable {
    @Id
    String id;
    @OneToOne
    Url url;
    String sistemaOperativo;
    String navegador;
    String direccionIp;
    String day;
    long hour;

    public Visita(){

    }

    public Visita(Url url, String sistemaOperativo, String navegador, String direccionIp, String day, long hour){
        this.url = url;
        this.sistemaOperativo = sistemaOperativo;
        this.navegador = navegador;
        this.direccionIp = direccionIp;
        this.day = day;
        this.hour = hour;
    }

    public String getId() {
        return id;
    }
    public String getDireccionIp() {
        return direccionIp;
    }
    public String getNavegador() {
        return navegador;
    }
    public String getSistemaOperativo() {
        return sistemaOperativo;
    }
    public Url getUrl() {
        return url;
    }
    public String getDay() { return day; }
    public long getHour() {
        return hour;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setDireccionIp(String direccionIp) {
        this.direccionIp = direccionIp;
    }
    public void setNavegador(String navegador) {
        this.navegador = navegador;
    }
    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }
    public void setUrl(Url url) {
        this.url = url;
    }
    public void setDay(String day) { this.day = day; }
    public void setHour(long hour) {
        this.hour = hour;
    }
}
