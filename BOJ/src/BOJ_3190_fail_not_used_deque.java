import java.io.*;
import java.util.*;

public class BOJ_3190_fail_not_used_deque {
    static class Node{
        int x,y,dir;

        public Node(int x, int y, int dir) {
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
    }
    //
    // 문제 원인 : board, 정사각형만을 보고 내가 바로 2차원 배열내에서 해결하고자함
    // - 다음번에는 고쳐야하는게 맞고 2차원 배열로 하게되면 각 배열마다의 상태 관리에 대한 어려움이 발생하여
    // - 시간 소모가 오래걸리고 또한 다양한 예외처리와 코드에 대한 확신이 필요해진다 -> 이건 나중에 한번더 도전해볼 만한 문제지만
    // - 지금 다시 알고리즘을 시작한 상황인만큼 어떤 자료구조로 접근하고 알고리즘은 어떻게 써야할지를 찾는 시각을 구해야한다.
    //
    /*
    *  문제 초기 조건
    *  뱀은 맨 위 맨좌측에서 시작하고 뱀의 길이는 1이다.
    *  처음에는 오른쪽으로 향한다
    *  N*N 크기의 정사각 보드위에 있고
    *  * 벽과 부딪히면 게임이 끝
    *  * 초단위 행동 정의
    *  * 뱀은 임시적으로 몸길이를 늘려 머리가 먼저 다음 칸으로향한다
    *  * 다음칸이 벽이나 자기 자신 몸이 있다면 게임이 끝난다. -> outrange || body[nx][ny] != -1  이면 끝
    *  * 사과가 있다면 몸길이를 늘리고
    *
    * */
    static int time = 0;
    static Node head, tail;
    static int N, K, L;
    static boolean[][] board;
    static int[][] body; //이걸 바탕으로 x,y에 있는 뱀의 다음 위치에 대한 방향을 의미한다.
    static int[] dx = {1,0,-1,0}, dy = {0,1,0,-1};
    static Deque<int[]> draft = new ArrayDeque<>();
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        board = new boolean[N][N];
        body = new int[N][N];
        for(int i = 0 ; i < N ; i++){
            Arrays.fill(body[i],-1);
            System.out.println(Arrays.toString(body[i]));
        }
        head = new Node(0, 0, 0);
        tail = new Node(0, 0, 0);
        body[0][0]= 0;

        K = Integer.parseInt(br.readLine());
        for(int i = 0 ; i < K ; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken())-1;
            int y = Integer.parseInt(st.nextToken())-1;
            board[y][x] = true;
        }
        L = Integer.parseInt(br.readLine());
        for(int i = 0 ; i < L ;i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int time = Integer.parseInt(st.nextToken());
            int dir = st.nextToken().equals("D")? 1 : -1 ;
            System.out.println("time = " + time + "dir = " + dir );
            draft.add(new int[] {time, dir});
        }

        while(true){

            time++;
            int nx = head.x + dx[head.dir];
            int ny = head.y + dy[head.dir];
            System.out.println("( head nx ny : (" + nx + " , " + ny + ")");
            // 벽이나 자기자신 몸과 부딪히는지 유무 확인
            if(!inRange(ny,nx)||body[ny][nx]!=-1){
//                System.out.println(inRange(ny,nx));
//                System.out.println(body[ny][nx]);
                break;
            }
            // 이동한 칸에 사과가 없으면 꼬리가 없어지는 로직


            //여긴 대가리 방향을 바꾸는 로직

            int ndir = head.dir;

            if(!draft.isEmpty()&&draft.getFirst()[0]==(time-1)){
                ndir = nextDir(ndir,draft.pop()[1]);
            }
            head = new Node(nx,ny,ndir);
            body[ny][nx]=ndir;

            if(!board[ny][nx]){
                int dir = tail.dir;
                System.out.println("board[nx][ny]쪽 dir "  + dir);
                int tnx = tail.x + dx[dir];
                int tny = tail.y + dy[dir];
                body[tail.y][tail.x]=-1;
                System.out.println("일로오나?");
                tail = new Node(tnx,tny,body[tny][tnx]);
            }else{
                board[ny][nx]=false;
            }
        }

        System.out.println(time-1);
    }
    static boolean inRange(int x,int y){
        return 0<=x&&x<N&&0<=y&&y<N;
    }
    static int nextDir(int curdix, int kind){
        return ( curdix + 4 + kind )%4;
    }
}
