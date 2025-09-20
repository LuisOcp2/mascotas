package Model;

public class User {
    
    private int usuId;
    private String usuIdentificacion;
    private String usuLogin;
    private String usuPass;
    private String usuNombre;
    private String usuApellido;
    private String usuEmail;
    private String usuDir;
    private String usuTel;
    private String usuEstado;
    private int perId;
    private String imgFirma;
    
    public User() {
    }
    
    public User(int usuId, String usuIdentificacion, String usuLogin, String usuPass, 
                String usuNombre, String usuApellido, String usuEmail, String usuDir, 
                String usuTel, String usuEstado, int perId, String imgFirma) {
        this.usuId = usuId;
        this.usuIdentificacion = usuIdentificacion;
        this.usuLogin = usuLogin;
        this.usuPass = usuPass;
        this.usuNombre = usuNombre;
        this.usuApellido = usuApellido;
        this.usuEmail = usuEmail;
        this.usuDir = usuDir;
        this.usuTel = usuTel;
        this.usuEstado = usuEstado;
        this.perId = perId;
        this.imgFirma = imgFirma;
    }
    
    public int getUsuId() {
        return usuId;
    }
    
    public void setUsuId(int usuId) {
        this.usuId = usuId;
    }
    
    public String getUsuIdentificacion() {
        return usuIdentificacion;
    }
    
    public void setUsuIdentificacion(String usuIdentificacion) {
        this.usuIdentificacion = usuIdentificacion;
    }
    
    public String getUsuLogin() {
        return usuLogin;
    }
    
    public void setUsuLogin(String usuLogin) {
        this.usuLogin = usuLogin;
    }
    
    public String getUsuPass() {
        return usuPass;
    }
    
    public void setUsuPass(String usuPass) {
        this.usuPass = usuPass;
    }
    
    public String getUsuNombre() {
        return usuNombre;
    }
    
    public void setUsuNombre(String usuNombre) {
        this.usuNombre = usuNombre;
    }
    
    public String getUsuApellido() {
        return usuApellido;
    }
    
    public void setUsuApellido(String usuApellido) {
        this.usuApellido = usuApellido;
    }
    
    public String getUsuEmail() {
        return usuEmail;
    }
    
    public void setUsuEmail(String usuEmail) {
        this.usuEmail = usuEmail;
    }
    
    public String getUsuDir() {
        return usuDir;
    }
    
    public void setUsuDir(String usuDir) {
        this.usuDir = usuDir;
    }
    
    public String getUsuTel() {
        return usuTel;
    }
    
    public void setUsuTel(String usuTel) {
        this.usuTel = usuTel;
    }
    
    public String getUsuEstado() {
        return usuEstado;
    }
    
    public void setUsuEstado(String usuEstado) {
        this.usuEstado = usuEstado;
    }
    
    public int getPerId() {
        return perId;
    }
    
    public void setPerId(int perId) {
        this.perId = perId;
    }
    
    public String getImgFirma() {
        return imgFirma;
    }
    
    public void setImgFirma(String imgFirma) {
        this.imgFirma = imgFirma;
    }
    
    @Override
    public String toString() {
        return usuNombre + " " + usuApellido;
    }
}