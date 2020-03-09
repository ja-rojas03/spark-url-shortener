package Models;

import Models.Url;
import com.google.gson.annotations.Expose;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class Usuario implements Serializable {
        @Id
        private String id;
        @Expose
        private String username;
        @Expose
        private String nombre;
        private String password;
        private boolean administrador;
        @OneToMany
        @Expose()
        private List<Url> urlCreadas;

        public Usuario(){

        }
        public Usuario(String username, String nombre, String password, boolean administrador){
            this.username = username;
            this.nombre = nombre;
            this.password = password;
            this.administrador = administrador;
            this.urlCreadas = new ArrayList<>();
        }

        public String getUsername() {
            return username;
        }
        public String getNombre() {
            return nombre;
        }
        public String getPassword() {
            return password;
        }
        public boolean isAdministrador() {
            return administrador;
        }
        public String getId() {
            return id;
        }
        public List<Url> getUrlCreadas() {
            return urlCreadas;
        }

        public void setUsername(String username) {
            this.username = username;
        }
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        public void setAdministrador(boolean administrador) {
            this.administrador = administrador;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        public void setId(String id) {
            this.id = id;
        }
        public void setUrlCreadas(List<Url> urlCreadas) {
            this.urlCreadas = urlCreadas;
        }
}
