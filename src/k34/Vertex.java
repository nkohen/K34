/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package k34;

/**
 * This object represents a Vertex in K_3^4 with four coordinates either 1, 2, or 3
 * @author Nadav
 */
public class Vertex {
    int[] coor = new int[4];
    
    public Vertex(int[] x)
    {
        if(x.length!=4)
            return;
        for(int i = 0; i<4; i++)
        {
            if(x[i]<1||x[i]>3)
                return;
        }
        
        coor = x;
    }
    
    public Vertex(int a, int b, int c, int d)
    {
        coor[0] = a;
        coor[1] = b;
        coor[2] = c;
        coor[3] = d;
    }
    
    //Returns how many coordinates this Vertex and a Vertex v have in common
    int same(Vertex v)
    {
        int s = 0;
        for(int i = 0; i<4; i++)
        {
            if(coor[i] == v.coor[i])
                s++;
        }
        return s;
    }
    
    //Converts this Vertex into a number between 0 and 80 via a 1-1 coorespondence
    //-1 to account for (1,2,3) sort of vertex instead of (0,1,2)
    int number()
    {
        int ind = 0;
        for(int i = 0; i<4; i++)
            ind += Math.pow(3,i)*(coor[3-i]-1);
        return ind;
    }
    
    //Converts any integer between 0-80 into its cooresponding Vertex
    static Vertex numToVertex(int x)
    {
        if (x<0||x>80)
        {
            int[] vert = {1,1,1,1};
            return new Vertex(vert);
        }
        int[] vert = new int[4];
        for(int i = 0; i<4; i++)
        {
            int q = x/(int)Math.pow(3, 3-i);
            vert[i] = q+1;
            x -= q*Math.pow(3, 3-i);
        }
        
        return new Vertex(vert);
    }
    
    Vertex next()
    {
        return numToVertex(number() + 1);
    }
    
    //Returns true if this Vertex has no elements in common with v1 and at most 
    //one element shared with v2 and at most two elements shared with v3
    boolean test(Vertex v1, Vertex v2, Vertex v3)
    {
        for(int i = 0; i<4; i++)
        {
            if(coor[i] == v1.coor[i])
                return false;
        }
        
        int count = 0;
        for(int i = 0; i<4; i++)
        {
            if(coor[i] == v2.coor[i])
                count++;
        }
        if(count > 1){
            return false;
        }
        int count2 = 0;
        for(int i = 0; i < 4; i++){
            if(coor[i] == v3.coor[i]){
                count2++;
            }
        }
        return count2 <= 2;
    }
    
    @Override
    public String toString()
    {
        return ("(" + coor[0] + ", " + coor[1] + ", " + coor[2] + ", " + coor[3] + ")");
    }
}

