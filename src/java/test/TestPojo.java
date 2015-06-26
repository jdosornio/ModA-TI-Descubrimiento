/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.google.gson.Gson;
import iing.uabc.edu.mx.persistencia.modelo.Recurso;
import iing.uabc.edu.mx.persistencia.util.BeanManager;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author donniestorm
 */
public class TestPojo {

    private int id;
    private String name;
    private List<Recurso> recursos = new ArrayList<>();
    private boolean ok;
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the recursos
     */
    public List<Recurso> getRecursos() {
        return recursos;
    }

    /**
     * @param recursos the recursos to set
     */
    public void setRecursos(List<Recurso> recursos) {
        this.recursos = recursos;
    }

//    public static void main(String[] args) {
//        BeanManager<TestPojo> manager = new BeanManager<>(TestPojo.class);
//
//        manager.setProperty("id", 3);
//        manager.setProperty("name", "Hola Josu");
//        List<Recurso> recursos = new ArrayList<>();
//
//        recursos.add(new Recurso());
//        recursos.add(new Recurso("www.com"));
//        recursos.add(new Recurso("hehe"));
//
//        manager.setProperty("recursos", recursos);
//
//        TestPojo t = manager.getBean();
//
//        JSONObject jsonObject = new JSONObject(t);
//
//        //System.out.println(jsonObject);
//
//        String json = new Gson().toJson(t);
//
//        System.out.println(json);
//
//        TestPojo t2 = new Gson().fromJson(json, TestPojo.class);
//        
//        System.out.println(t2);
//        
//        System.out.println(t2.getId());
//        System.out.println(t2.getName());
//        System.out.println(t2.getRecursos());
//        System.out.println(t2.getRecursos().get(1).getDescUrl());
//        
//        System.out.println("--------------------");
//        
//        JSONArray arr = new JSONArray(recursos);
//        
//        System.out.println(arr);
//    }

    /**
     * @return the ok
     */
    public boolean isOk() {
        return ok;
    }

    /**
     * @param ok the ok to set
     */
    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
