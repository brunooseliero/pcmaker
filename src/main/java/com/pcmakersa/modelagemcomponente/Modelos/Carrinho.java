/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pcmakersa.modelagemcomponente.Modelos;

import java.util.ArrayList;
import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author devops
 */
public class Carrinho {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToMany(cascade = CascadeType.ALL)
    private ArrayList<Componente> componente;    

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Componente> getComponente() {
        return componente;
    }

    public void setComponente(ArrayList<Componente> componente) {
        this.componente = componente;
    }

    
}
