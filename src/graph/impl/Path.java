package graph.impl;

public class Path implements Comparable<Path>{
    private String dest;
    private int cost;

    public Path(String dest, int cost){
        this.dest = dest;
        this.cost = cost;
    }

    public int compareTo(Path other){
        return this.cost-other.cost;
    }
    public String toString(){
        return this.dest+"with cost "+this.cost;
    }

    public String getDest(){
        return dest;
    }

    public Integer cost() {
        return cost;
    }
}
