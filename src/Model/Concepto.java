package Model;

public class Concepto {
    
    private int conId;
    private String conDescripcion;
    private String conEstado;
    
    public Concepto() {
    }
    
    public Concepto(int conId, String conDescripcion, String conEstado) {
        this.conId = conId;
        this.conDescripcion = conDescripcion;
        this.conEstado = conEstado;
    }
    
    public int getConId() {
        return conId;
    }
    
    public void setConId(int conId) {
        this.conId = conId;
    }
    
    public String getConDescripcion() {
        return conDescripcion;
    }
    
    public void setConDescripcion(String conDescripcion) {
        this.conDescripcion = conDescripcion;
    }
    
    public String getConEstado() {
        return conEstado;
    }
    
    public void setConEstado(String conEstado) {
        this.conEstado = conEstado;
    }
    
    @Override
    public String toString() {
        return conDescripcion;
    }
}