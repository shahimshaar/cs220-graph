package graph.impl;

import java.lang.reflect.Array;
import java.util.*;
import java.util.PriorityQueue;

import graph.IGraph;
import graph.INode;
import graph.NodeVisitor;

/**
 * A basic representation of a graph that can perform BFS, DFS, Dijkstra,
 * and Prim-Jarnik's algorithm for a minimum spanning tree.
 * 
 * @author jspacco
 *
 */
public class Graph implements IGraph
{
    private Map<String, INode> newgraph;
    private Collection<INode> valueset;
    private Set<INode> newneighbors;
    public Graph(){
        newgraph = new HashMap<String, INode>();
    }
    /**
     * Return the {@link Node} with the given name.
     * 
     * If no {@link Node} with the given name exists, create
     * a new node with the given name and return it. Subsequent
     * calls to this method with the same name should
     * then return the node just created.
     * 
     * @param name
     * @return
     */
    public INode getOrCreateNode(String name) {
        if(newgraph.containsKey(name)){
            return newgraph.get(name);
        }
        else{
            INode tempnode = new Node(name);
            newgraph.put(name,tempnode);
            return newgraph.get(name);
        }
    }

    /**
     * Return true if the graph contains a node with the given name,
     * and false otherwise.
     * 
     * @param name
     * @return
     */
    public boolean containsNode(String name) {
        return newgraph.containsKey(name);
    }

    /**
     * Return a collection of all of the nodes in the graph.
     * 
     * @return
     */
    public Collection<INode> getAllNodes() {

        valueset = newgraph.values();
        return valueset;
    }
    // helper function for prim jarnik
    public int getGraphSize(IGraph x){
        int num = 0;
        Iterator<INode> itr = newneighbors.iterator();
        while(itr.hasNext()){
            num++;
        }
        return num;

    }
    
    /**
     * Perform a breadth-first search on the graph, starting at the node
     * with the given name. The visit method of the {@link NodeVisitor} should
     * be called on each node the first time we visit the node.
     * 
     * 
     * @param startNodeName
     * @param v
     */
    public void breadthFirstSearch(String startNodeName, NodeVisitor v)
    {
        Set<INode> visited = new HashSet<INode>();
        // Queue for FIFO
        Queue<INode> tovisit = new LinkedList<INode>();
        tovisit.add(getOrCreateNode(startNodeName));
        while(!tovisit.isEmpty()){
            INode x = tovisit.remove();
            if(visited.contains(x))
                continue;
            v.visit(x);
            visited.add(x);

            newneighbors = (Set<INode>) x.getNeighbors();
            Iterator<INode> itr = newneighbors.iterator();
            for(INode n : newneighbors){
                if(!visited.contains(n))
                    tovisit.add(n);
            }
        }
    }

    /**
     * Perform a depth-first search on the graph, starting at the node
     * with the given name. The visit method of the {@link NodeVisitor} should
     * be called on each node the first time we visit the node.
     * 
     * 
     * @param startNodeName
     * @param v
     */
    public void depthFirstSearch(String startNodeName, NodeVisitor v)
    {
        Set<INode> visited = new HashSet<INode>();
        //Stack for LIFO
        Stack<INode> tovisit = new Stack<INode>();
        tovisit.add(getOrCreateNode(startNodeName));
        while(!tovisit.isEmpty()){
            INode x = tovisit.pop();
            if(visited.contains(x))
                continue;
            v.visit(x);
            visited.add(x);

            newneighbors = (Set<INode>) x.getNeighbors();
            //Iterator<INode> itr = newneighbors.iterator();
            for(INode n : newneighbors){
                if(!visited.contains(n))
                    tovisit.push(n);
            }
        }
    }

    /**
     * Perform Dijkstra's algorithm for computing the cost of the shortest path
     * to every node in the graph starting at the node with the given name.
     * Return a mapping from every node in the graph to the total minimum cost of reaching
     * that node from the given start node.
     * 
     * <b>Hint:</b> Creating a helper class called Path, which stores a destination
     * (String) and a cost (Integer), and making it implement Comparable, can be
     * helpful. Well, either than or repeated linear scans.
     * 
     * @param startName
     * @return
     */
    public Map<INode,Integer> dijkstra(String startName) {
        Map result = new HashMap<INode, Integer>();
        PriorityQueue todo = new PriorityQueue();
        todo.add(new Path(startName, 0));
        while (result.size()<newgraph.size()){
            Path nextpath = (Path) todo.poll();
            String nodestring = nextpath.getDest();
            // fetching the node using its key (string name)
            INode newnode = getOrCreateNode(nodestring);
            if (result.containsKey(newnode))
                    continue;
            Integer cost = nextpath.cost();
            result.put(newnode, cost);
            newneighbors = (Set<INode>) newnode.getNeighbors();
            //Iterator<INode> itr = newneighbors.iterator();
            for(INode n : newneighbors)
                todo.add(new Path(n.getName(), cost + newnode.getWeight(n)));
            
        }
        return result;
    }
    
    /**
     * Perform Prim-Jarnik's algorithm to compute a Minimum Spanning Tree (MST).
     * 
     * The MST is itself a graph containing the same nodes and a subset of the edges 
     * from the original graph.
     * 
     * @return
     */
    public IGraph primJarnik() {
        return null;
    }
}