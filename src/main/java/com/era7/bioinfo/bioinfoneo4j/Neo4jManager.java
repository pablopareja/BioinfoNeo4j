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
import org.neo4j.index.IndexService;
import org.neo4j.index.lucene.LuceneFulltextIndexService;
import org.neo4j.index.lucene.LuceneFulltextQueryIndexService;
import org.neo4j.index.lucene.LuceneIndexService;
import org.neo4j.kernel.EmbeddedGraphDatabase;

/**
 *
 * @author ppareja
 */
public class Neo4jManager {

    protected GraphDatabaseService graphService = null;
    protected IndexService indexService = null;
    protected IndexService fullTextIndexService = null;
    protected IndexService fullTextQueryIndexService = null;
    //private static PathFinder pathFinder = null;

    public Neo4jManager(String dbFolder, boolean createServices) {

        if (createServices) {
            graphService = new EmbeddedGraphDatabase(dbFolder);
            indexService = new LuceneIndexService(graphService);
            fullTextIndexService = new LuceneFulltextIndexService(graphService);
            fullTextQueryIndexService = new LuceneFulltextQueryIndexService(graphService);
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
        indexService.shutdown();
        fullTextIndexService.shutdown();
    }

    public Node getReferenceNode() {
        return graphService.getReferenceNode();
    }

    public IndexService getIndexService() {
        return indexService;
    }

    public IndexService getFullTextIndexService() {
        return fullTextIndexService;
    }

    public IndexService getFullTextQueryIndexService() {
        return fullTextQueryIndexService;
    }

    public GraphDatabaseService getGraphService() {
        return graphService;
    }
}
