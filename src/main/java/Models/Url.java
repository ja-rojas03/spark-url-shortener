package Models;

import com.google.gson.annotations.Expose;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Entity
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class Url implements Serializable {
    @Id
    @Expose
    private String urlIndexada;
    @Expose
    private String urlOriginal;
    @Expose
    private String urlBase62;
    @OneToOne
    @Expose(serialize = false)
    @XmlTransient
    private Usuario creador;

    private String iamgePreview;
    private String descriptionPreview;

    public Url(){

    }

    public Url(String urlOriginal, String urlIndexada, String urlBase62){

    }

    public Url(String urlOriginal) {
        this.urlOriginal = urlOriginal;
    }

    public String getUrlOriginal() {
        return urlOriginal;
    }

    public void setUrlOriginal(String urlOriginal) {
        this.urlOriginal = urlOriginal;
    }

    public String getUrlIndexada() {
        return urlIndexada;
    }

    public void setUrlIndexada(String urlIndexada) {
        this.urlIndexada = urlIndexada;
    }

    public String getUrlBase62() {
        return urlBase62;
    }

    public void setUrlBase62(String urlBase62) {
        this.urlBase62 = urlBase62;
    }

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public String getIamgePreview() {
        return iamgePreview;
    }

    public void setIamgePreview(String iamgePreview) {
        this.iamgePreview = iamgePreview;
    }

    public String getDescriptionPreview() {
        return descriptionPreview;
    }

    public void setDescriptionPreview(String descriptionPreview) {
        this.descriptionPreview = descriptionPreview;
    }
}
