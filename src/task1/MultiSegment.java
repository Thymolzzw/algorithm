package task1;

import java.util.Arrays;

public class MultiSegment {
	public static int[][][] weights = {
			{ {4, 2, 3}, },
			{ {9, 8, Integer.MAX_VALUE}, {8, 7, 8}, {Integer.MAX_VALUE, 4, 7}, },
			{ {5, 6}, {8, 6}, {6, 5}, },
			{ {7}, {3}, },
	};

	public static int getPathLength(int[] states) {
		int sum=0;
		int states_pre = 0;
		int states_tail = 0;
		sum += weights[0][states_pre][states[0]];
		for(int i=1;i<states.length;i++){
			int w = weights[i][states[i-1]][states[i]];
			if(w == Integer.MAX_VALUE)
				return Integer.MAX_VALUE;
			sum+=w;
		}
		sum += weights[weights.length-1][states[states.length-1]][states_tail];
		return sum;
	}

	public static int[] getShortestPath( ) {
		int[] sizes = new int[]{3, 3, 2};
		Digital digital = new Digital(sizes);
		int[] shortestPath = new int[sizes.length];
		Arrays.fill(shortestPath, 0);
		shortestPath[shortestPath.length-1] = -1;
		int short_len = Integer.MAX_VALUE;
		while(digital.next()){
			int[] states=digital.getStates();
			int len=MultiSegment.getPathLength(states);
			if(short_len > len){
				short_len = len;
				shortestPath = states;
			}
		}
		return shortestPath;
	}

	public static void main(String[] args) {

		System.out.print("最短路径状态序列：");
		int[] states = MultiSegment.getShortestPath();
		for(int s:states){
			System.out.print(s);
		}
		System.out.println();
		System.out.print("最短路径长度：");
		int len = MultiSegment.getPathLength(states);
		System.out.println(len);
	}
}