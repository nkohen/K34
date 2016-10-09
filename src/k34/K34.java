package k34;

/**
 * This program calculates labelings of K_3^4
 * @author Nadav
 * @author Camila
 */
public class K34 {
    
    //Prints all of o as Vertex objects
    static void printOrderV(Vertex[] o)
    {
        for (Vertex o1 : o) {
            if(o1!=null)
                System.out.println(o1);
        }
    }
    
    //Prints all of o as the integers cooresponding to the Vertex objects
    static void printOrderN(Vertex[] o)
    {
        for (Vertex o1 : o) {
            if(o1!=null)
                System.out.println(o1.number());
        }
    }
    
    static void printOrderBoth(Vertex[] o){
        for(Vertex o1 : o){
            System.out.println(o1 + " --> " + o1.number());
        }
    }
    
    static Vertex[] arrayVertices(){
        Vertex[] list = new Vertex[81];
        int x = 0;
        for(int i = 1; i < 4; i++){
            for(int j = 1; j < 4; j++){
                for(int k = 1; k < 4; k++){
                    for(int w = 1; w < 4; w++){
                        list[x] = new Vertex(i, j, k, w);
                        x++;
                    }
                }
            }
        }
        return list;
    }
    
    static void genSolutions(){        
        //set represents how many vertices have been labeled
        int set = 0;
        
        //used[i] is true if the Vertex.numToVertex(i) is used in ord
        boolean[] used = new boolean[81];
        
        //tried[i][j] is true if fixing j for ord[i] has been tried or is not an option
        boolean[][] tried = new boolean[81][4];
        
        //untried[i] contains how many indices have not been tried for ord[i] and is here only for efficient implementation
        int[] untried = new int[81];
        
        //chos[i] contains the index of the fixed coordinate in ord[i]
        int[] chos = new int[81];
        
        //ord will be the ordering of all 81 vertices
        Vertex[] ord = new Vertex[81];
        
        //This program assumes that the first four labels are as follows (wlog) 
        ord[0] = new Vertex(1,1,1,1);
        used[0] = true;
        ord[1] = new Vertex(2,2,2,2);
        used[ord[1].number()] = true;
        ord[2] = new Vertex(1,3,3,3);
        used[ord[2].number()] = true;
        ord[3] = new Vertex(3,1,1,2);
        used[ord[3].number()] = true;
        chos[3] = 3;
        
        set = 4;
        
        //All options are yet untried (ignoring the first four vertices as they are unaffected by untried
        for(int i = 0; i<81; i++)
            untried[i] = 4;
        
        //next will be our prospective next Vertex to be labeled
        Vertex next;
        
        //The outer loop makes the program to continue looking for solutions after one has been found
        while(set!=3)
        {
            //This loop terminates when a solution has been found or when there are none left to find
            while (set != 3 && set != 81) {
                //found will be true if a vertex to put in ord[set] has been found
                boolean found = false;
                //do not fix the same spot twice in a row
                if(!tried[set][chos[set-1]])
                    untried[set]--;
                tried[set][chos[set-1]] = true;
                //This loop finds a Vertex, next, to add to ord[set]
                while(untried[set] != 0)
                {
                    int guess = (int)(Math.random()*untried[set])+1;
                    int index = 0;
                    while(guess>0)
                    {
                        if(!tried[set][index])
                            guess--;
                        index++;
                    }
                    index--;
                    //index now stores the index of the coordinate to mach ord[set-2], one not previously tried
                    int[] coors = new int[4];
                    //calculate the forced coordinates of this Vertex
                    for(int i = 0; i<4; i++)
                    {
                        if(i==index)
                            coors[i]=ord[set-2].coor[index];
                        else
                        {
                            boolean[] others = new boolean[3];
                            others[ord[set-1].coor[i]-1] = true;
                            others[ord[set-2].coor[i]-1] = true;
                            for(int j = 0; j<3; j++)
                            {
                                if(!others[j])
                                {
                                    coors[i] = j+1;
                                    break;
                                }
                            }
                        }
                    }
                    next = new Vertex(coors);
                    tried[set][index] = true;
                    untried[set]--;
                    //if this vertex is unused it satisfies the radio condition
                    if(!used[next.number()])
                    {
                        ord[set] = next;
                        used[next.number()] = true;
                        chos[set] = index;
                        set++;
                        found = true;
                        break;
                    }
                }
                //if there is no possible Vertex to place in ord[set], backtrack with this attempt still recorded in tried above
                if(!found)
                {
                    untried[set] = 4;
                    tried[set][0] = false;
                    tried[set][1] = false;
                    tried[set][2] = false;
                    tried[set][3] = false;
                    set--;
                    chos[set] = 0;
                    used[ord[set].number()] = false;
                    ord[set] = null;
                }
            }
            //ord[80] != null iff ord is full and contains a valid ordering of K_3^4
            if (ord[80] != null) {
                System.out.println("Solution:");
                printOrderV(ord);
                System.out.println("---------");
                printOrderN(ord);
                System.out.println("---------\n");
                
                //after printing a solution, backtrack as if there was no possible Vertex to place in ord[80]
                set--;
                chos[set] = 0;
                used[ord[set].number()] = false;
                ord[set] = null;
            } else
            {
                System.out.println("No solution exists.");
            }
        }
        System.out.println("All solutions have been considered.");
    }
    public static void main(String[] args) {
        
        genSolutions();
        //Vertex[] list = arrayVertices();
        //printOrderBoth(list);

    }
}