/*
 * Copyright (C) 2010-2012  "Oh no sequences!"
 *
 * This is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.era7.bioinfo.bioinfoneo4j;

import java.util.Iterator;
import java.util.Map;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.EmbeddedReadOnlyGraphDatabase;

/**
 *
 * @author ppareja
 */
public class Neo4jManager {

    protected static GraphDatabaseService graphService = null;

    public Neo4jManager(String dbFolder, boolean createServices, boolean readOnly, Map<String, String> config) {
        
        if (createServices) {
            if(!readOnly){
                if(config != null){
                    graphService = new EmbeddedGraphDatabase(dbFolder,config);
                }else{
                    graphService = new EmbeddedGraphDatabase(dbFolder);
                }
                
            }else{
                if(config != null){
                    graphService = new EmbeddedReadOnlyGraphDatabase(dbFolder,config);
                }else{
                    graphService = new EmbeddedReadOnlyGraphDatabase(dbFolder);
                }                
            }            
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
