/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.era7.bioinfo.bioinfoneo4j;

import java.util.Iterator;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;

/**
 *
 * @author ppareja
 */
public class Neo4jManager {

    protected GraphDatabaseService graphService = null;

    public Neo4jManager(String dbFolder, boolean createServices) {

        if (createServices) {
            graphService = new EmbeddedGraphDatabase(dbFolder);
        }

    }

    public Node createNode() {
        return graphService.createNode();
    }

    public Transaction beginTransaction() {
        return graphService.beginTx();
    }

    public void deleteAll() {
        Iterator<Node> iterator = graphService.getAllNodes().iterator();
        Transaction txn = beginTransaction();
        try {
            while (iterator.hasNext()) {
                Node node = iterator.next();
                Iterator<Relationship> relIt = node.getRelationships().iterator();
                while (relIt.hasNext()) {
                    relIt.next().delete();
                }
                node.delete();
            }
            txn.success();
        } catch (Exception e) {
            e.printStackTrace();
            txn.failure();
        }
        txn.finish();
    }

    public void shutDown() {
        graphService.shutdown();
    }

    public Node getReferenceNode() {
        return graphService.getReferenceNode();
    }

    public GraphDatabaseService getGraphService() {
        return graphService;
    }
}
