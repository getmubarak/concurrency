public class Main 
{  
    // Function to find the minimum weight 
    // Hamiltonian Cycle
    static int tsp(int[][] graph, boolean[] visitCity, 
                   int currPos, int n, 
                   int count, int cost, int ans) 
    {
  
        // If last node is reached and it has a link
        // to the starting node i.e the source then
        // keep the minimum value out of the total cost
        // of traversal and "ans"
        // Finally return to check for more possible values
        if (count == n && graph[currPos][0] > 0) 
        {
            ans = Math.min(ans, cost + graph[currPos][0]);
            return ans;
        }
  
        // BACKTRACKING STEP
        // Loop to traverse the adjacency list
        // of currPos node and increasing the count
        // by 1 and cost by graph[currPos,i] value
        for (int i = 0; i < n; i++) 
        {
            if (visitCity[i] == false && graph[currPos][i] > 0) 
            {
  
                // Mark as visited
            	visitCity[i] = true;
                ans = tsp(graph, visitCity, i, n, count + 1,
                          cost + graph[currPos][i], ans);
  
                // Mark ith node as unvisited
                visitCity[i] = false;
            }
        }
        return ans;
    }
  
    // Driver code
    public static void main(String[] args) throws InterruptedException
    {
        //0. New York - 1. Los Angeles - 2. Chicago - 3. Minneapolis - 4. Denver - 5. Dallas - 6. Seattle - 7. Boston - 8. San Francisco - 9. St. Louis - 10. Houston - 11. Phoenix - 12. Salt Lake City
      
        long start = System.currentTimeMillis();
        // n is the number of nodes i.e. V
        int n = 12;
  
        int[][] graph = 
         {
             {0, 2451, 713, 1018, 1631, 1374, 2408, 213, 2571, 875, 1420, 2145, 1972},
        {2451, 0, 1745, 1524, 831, 1240, 959, 2596, 403, 1589, 1374, 357, 579},
        {713, 1745, 0, 355, 920, 803, 1737, 851, 1858, 262, 940, 1453, 1260},
        {1018, 1524, 355, 0, 700, 862, 1395, 1123, 1584, 466, 1056, 1280, 987},
        {1631, 831, 920, 700, 0, 663, 1021, 1769, 949, 796, 879, 586, 371},
        {1374, 1240, 803, 862, 663, 0, 1681, 1551, 1765, 547, 225, 887, 999},
        {2408, 959, 1737, 1395, 1021, 1681, 0, 2493, 678, 1724, 1891, 1114, 701},
        {213, 2596, 851, 1123, 1769, 1551, 2493, 0, 2699, 1038, 1605, 2300, 2099},
        {2571, 403, 1858, 1584, 949, 1765, 678, 2699, 0, 1744, 1645, 653, 600},
        {875, 1589, 262, 466, 796, 547, 1724, 1038, 1744, 0, 679, 1272, 1162},
        {1420, 1374, 940, 1056, 879, 225, 1891, 1605, 1645, 679, 0, 1017, 1200},
        {2145, 357, 1453, 1280, 586, 887, 1114, 2300, 653, 1272, 1017, 0, 504},
        {1972, 579, 1260, 987, 371, 999, 701, 2099, 600, 1162, 1200, 504, 0},  
        };
        
        // Find the minimum weight Hamiltonian Cycle
        //i is the depot: the start and end location for the route. the depot 0, which corresponds to New York.
        Thread threads[] = new  Thread[n];
        for(int i =0;i< n; i++)
        {
        	
        	final int index = i;
        	Runnable runnable =
        	        () -> { 
        		// Boolean array to check if a node
	            // has been visited or not
	            boolean[] v = new boolean[n];
	            v[index] = true;
	            int ans = Integer.MAX_VALUE;
	           	ans = tsp(graph, v, index, n, 1, 0, ans); 	
	        	System.out.println(index + " : " + ans);
        	};
            threads[i] = new Thread(runnable);
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        // ans is the minimum weight Hamiltonian Cycle
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed);
    }
}
