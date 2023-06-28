/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.clementlevallois.adhoc.java4scientometrics;

import java.util.Objects;

/**
 *
 * @author LEVALLOIS
 */
public class AbstractItem implements Comparable<AbstractItem> {

    private int index;
    private String content;
    private String incipit;
    private SOURCE source;
    private String authors;
    private boolean onTopic = false;

    public enum SOURCE {
        BERGER, HUMPHREYS, HARTMANN, HARTMANN_2023, ZOTERO, RAMBOCAS, WOS
    }

    public AbstractItem() {
    }

    public AbstractItem(int index, String abstractItem) {
        this.index = index;
        this.content = abstractItem;
        this.incipit = content.substring(0, Math.min(39, content.length()));
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        this.incipit = content.substring(0, Math.min(39, content.length()));
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public boolean isOnTopic() {
        return onTopic;
    }

    public void setOnTopic(boolean onTopic) {
        this.onTopic = onTopic;
    }
    
    
    
    
    @Override
    public int compareTo(AbstractItem other) {
        if (this.incipit.toLowerCase().equals(other.incipit.toLowerCase())) {
            return 0;
        } else {
            if (this.index > other.index) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.incipit);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractItem other = (AbstractItem) obj;
        return this.incipit.equals(other.incipit);
    }

    public SOURCE getSource() {
        return source;
    }

    public void setSource(SOURCE source) {
        this.source = source;
    }

}
