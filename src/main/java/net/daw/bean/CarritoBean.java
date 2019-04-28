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
     * @return
     */
    public ProductoBean getObj_producto() {
        return obj_producto;
    }

    /**
     *
     * @param obj_producto
     */
    public void setObj_producto(ProductoBean obj_producto) {
        this.obj_producto = obj_producto;
    }

    /**
     *
     * @return
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     *
     * @param cantidad
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
