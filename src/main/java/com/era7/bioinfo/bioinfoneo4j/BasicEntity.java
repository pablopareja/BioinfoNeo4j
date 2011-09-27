/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.era7.bioinfo.bioinfoneo4j;

import org.neo4j.graphdb.Node;

/**
 *
 * @author ppareja
 */
public abstract class BasicEntity {

    public static final String NODE_TYPE_PROPERTY = "nodeType";

    //protected String nodeType = null;

    protected Node node = null;


    public BasicEntity(Node n){
        node = n;
    }

    @Override
    public int hashCode(){
        return node.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BasicEntity other = (BasicEntity) obj;
        if (this.node != other.node && (this.node == null || !this.node.equals(other.node))) {
            return false;
        }
        return true;
    }

    public Node getNode(){
        return node;
    }

    public String getNodeType(){
        return String.valueOf(node.getProperty(NODE_TYPE_PROPERTY));
    }
    
    public void setNodeType(String value){
        node.setProperty(NODE_TYPE_PROPERTY, value);
    }

}
