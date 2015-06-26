/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iing.uabc.edu.mx.persistencia.util;

import java.util.Collection;

/**
 *
 * @author donniestorm
 * @param <T> type of the elements in the collection
 */
public class CollectionManager<T> {

    private final Collection<T> collection;
    private final Class<T> elementClass;
    
    public CollectionManager(Collection<T> collection, Class<T> elementClass) {
        this.collection = collection;
        this.elementClass = elementClass;
    }

    public void addElement(T element) {
        collection.add(element);
    }

    public void removeElement(T element) {
        collection.remove(element);
    }

    public T getElement(int index) {
        T element = null;
        if (index >= 0) {
            int i = 0;
            for (T e : collection) {
                if(index == i) {
                    element = e;
                    break;
                }
                i++;
            }
        }
        return element;
    }

    public Collection<T> getCollection() {
        return collection;
    }
    
    public Class<T> getElementClass() {
        return elementClass;
    }
}