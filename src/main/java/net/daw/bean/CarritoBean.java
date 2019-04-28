/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.bean;

/**
 *
 * @author Yolanda
 */
public class CarritoBean {

    private ProductoBean obj_producto;
    private int cantidad;

    /**
     *
     * @return Devuelve el valor de obj_producto
     */
    public ProductoBean getObj_producto() {
        return obj_producto;
    }

    /**
     *
     * @param obj_producto Asigna un valor a obj_producto
     */
    public void setObj_producto(ProductoBean obj_producto) {
        this.obj_producto = obj_producto;
    }

    /**
     *
     * @return Devuelve el valor de Cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     *
     * @param cantidad Asigna un valor a Cantidad
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
