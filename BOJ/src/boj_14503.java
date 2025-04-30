import java.io.*;
import java.util.*;

public class boj_14503 {
    static class Robot{
        int x,y,dir;

        public Robot(int x, int y, int dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }

        @Override
        public String toString() {
            return "Robot{" +
                    "x=" + x +
                    ", y=" + y +
                    ", dir=" + dir +
                    '}';
        }
    }
    static int[] dx = {-1,0,1,0}, dy ={0,1,0,-1};
    static int N,M;
    static int[][] board;// 1-- 벽 0 청소해야할 곳 -1 청소한곳
    static Robot robot; // [x,y,dir]
    static int cnt = 0;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        robot = new Robot(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken()));

        board = new int[N][M];
        for(int i = 0 ; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0 ; j < M ; j++){
                board[i][j]= Integer.parseInt(st.nextToken());
            }
        }
        while(true){
            System.out.println(robot + " cnt is : "+ cnt);
            if(is_clean()){
                cnt+=1;
                board[robot.x][robot.y]=-1;
                for(int i = 0 ; i < N;i++){
                    System.out.println(Arrays.toString(board[i]));
                }
                System.out.println("################################################");
                System.out.println(is_dir_clean());
            }


            if(!is_dir_clean()){
                if(is_back()){
                    robot.x-=dx[robot.dir];
                    robot.y-=dy[robot.dir];
                }else{
                    break;
                }
            }else{
                rotate();
                if(is_next_clean()){
                    robot.x+=dx[robot.dir];
                    robot.y+=dy[robot.dir];
                }
            }
        }
        System.out.println(cnt);
    }
    static boolean is_next_clean(){
        return board[robot.x+dx[robot.dir]][robot.y+dy[robot.dir]] == 0;
    }
    static boolean is_dir_clean(){
        for(int d = 0 ; d<4; d++){
            int nx = robot.x + dx[d];
            int ny =robot.y+dy[d];
            if(board[nx][ny]==0){
                return true;
            }
        }
        return false;
    }
    static boolean is_back(){
        return board[robot.x-dx[robot.dir]][robot.y-dy[robot.dir]]!=1;
    }
    static boolean is_clean(){
        return board[robot.x][robot.y]==0;
    }
    static void rotate(){
        robot.dir = (robot.dir+1) %4;
    }
}
