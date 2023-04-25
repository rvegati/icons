/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fonts.google.com.AbstractModels;

import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author rvega
 */
public class ModeloComboString extends AbstractListModel implements ComboBoxModel {

    public List<String> listaTipoProceso;
    public String seleccionado;

    public List<String> getListaTipoProceso() {
        return this.listaTipoProceso;
    }

    public void setListaTipoProceso(List<String> listaTipoProceso) {
        this.listaTipoProceso = listaTipoProceso;
    }

    public String getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(String seleccionado) {
        this.seleccionado = seleccionado;
    }

    @Override
    public int getSize() {
        int cantidad = 0;

        if (this.listaTipoProceso != null) {
            cantidad = this.listaTipoProceso.size();
        }
        return cantidad;
    }

    @Override
    public Object getElementAt(int index) {
        Object valor = "";

        if (this.listaTipoProceso != null) {
            valor = this.listaTipoProceso.get(index).trim();
        }

        return valor;
    }

    @Override
    public void setSelectedItem(Object anItem) {
        this.seleccionado = null;
        if (anItem != null && this.listaTipoProceso != null) {
            for (String p : this.listaTipoProceso) {
                if (p.trim().equals(anItem.toString()) == true) {
                    this.seleccionado = p;
                    return;
                }
            }
        }
    }

    @Override
    public Object getSelectedItem() {
        Object valorSeleccionado = "";
        if (this.seleccionado != null) {
            valorSeleccionado = this.seleccionado.trim();
        }
        return valorSeleccionado;
    }

    public void setTipoProceso(String anItem) {
        this.seleccionado = null;
        if (anItem != null && this.listaTipoProceso != null) {
            for (String p : this.listaTipoProceso) {
                if (p.trim().equals(anItem.trim()) == true) {
                    this.seleccionado = p;
                    return;
                }
            }
        }
    }
}
