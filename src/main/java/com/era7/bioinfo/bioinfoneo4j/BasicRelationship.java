/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.era7.bioinfo.bioinfoneo4j;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;

/**
 *
 * @author ppareja
 */
public abstract class BasicRelationship implements RelationshipType{
    
    protected Relationship relationship = null;

    public BasicRelationship(Relationship rel){
        relationship = rel;
    }

    @Override
    public int hashCode(){
        return relationship.hashCode();
    }

    public Node getEndNode(){
        return relationship.getEndNode();
    }
    public Node getStartNode(){
        return relationship.getStartNode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BasicRelationship other = (BasicRelationship) obj;
        if (this.relationship != other.relationship && (this.relationship == null || !this.relationship.equals(other.relationship))) {
            return false;
        }
        return true;
    }
    

}
