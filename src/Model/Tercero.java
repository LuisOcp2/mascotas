/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

public class Tercero {

    int id;
    String ti;
    String identificacion;
    String representante_legal;
    String razon_social;
    String direccion;
    String tel1;
    String tel2;
    String observaciones;
    String estado;

    public Tercero(int id, String ti, String identificacion, String representante_legal, String razon_social, String direccion, String tel1, String tel2, String observaciones, String estado) {
        this.id = id;
        this.ti = ti;
        this.identificacion = identificacion;
        this.representante_legal = representante_legal;
        this.razon_social = razon_social;
        this.direccion = direccion;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.observaciones = observaciones;
        this.estado = estado;
    }

    public Tercero() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTi() {
        return ti;
    }

    public void setTi(String ti) {
        this.ti = ti;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getRepresentante_legal() {
        return representante_legal;
    }

    public void setRepresentante_legal(String representante_legal) {
        this.representante_legal = representante_legal;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Tercero{" + "id=" + id + ", ti=" + ti + ", identificacion=" + identificacion + ", representante_legal=" + representante_legal + ", razon_social=" + razon_social + ", direccion=" + direccion + ", tel1=" + tel1 + ", tel2=" + tel2 + ", observaciones=" + observaciones + ", estado=" + estado + '}';
    }

}
