/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.clementlevallois.adhoc.java4scientometrics;

import java.util.Objects;
import java.util.Set;

/**
 *
 * @author LEVALLOIS
 */
public class Source {
    
    private String name;
    private Set<AbstractItem> refs;

    public Source(String name, Set<AbstractItem> refs) {
        this.name = name;
        this.refs = refs;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AbstractItem> getRefs() {
        return refs;
    }

    public void setRefs(Set<AbstractItem> refs) {
        this.refs = refs;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.name);
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
        final Source other = (Source) obj;
        return Objects.equals(this.name, other.name);
    }
    
    
}
