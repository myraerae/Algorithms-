package tu;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import org.w3c.dom.NodeList;

/**
 * @author Administrator
 *定义一个数据结构图(快捷键Alt+shift+J)
 *图的深度优先、广度优先、拓扑排序
 */
public class graph {
	//定义一个有参构造，给初始变量赋值
	public graph(int n) {
		edges = new int[n][n];
		numofedges = 0;
		nodeList = new ArrayList<String>(n);
		storgeGraphh = new ArrayList<String>(n);
		indegree = new int[n];
	}
	//定义成员变量
	private ArrayList<String> nodeList;//存储顶点集合
	private int[][] edges;//邻接矩阵
	private int numofedges;//边的数量
	private boolean[] isvisit ;
	private ArrayList<String> storgeGraphh ;
	private int[] indegree;
	//主方法，添加5个顶点并插入，并插入变最终显示出来
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n = 5;
		String nodes[] ={"A","B","C","D","E"};
		graph graphh = new graph(n);
		for(String node:nodes){
			graphh.insertNode(node);
		}
		//添加边
		graphh.insertEdeges(0, 1, 1);
		graphh.insertEdeges(1, 4, 1);
		graphh.insertEdeges(2, 3, 1);
		graphh.insertEdeges(3, 4, 1);
		graphh.showGraph();
		graphh.dfs();
		graphh.bfs();
		System.out.println(" ");
		graphh.tuopusort();
		
	}
	
	//得到第一个邻接顶点的下标
   public int getFirstNeighbor(int index){
	   for(int j=0;j<nodeList.size();j++){
		   if(edges[index][j]>0){
			   return j;
		   }
	   }
	   return -1;
   }
   
   //根据前一个邻接顶点来获取下一个邻接顶点
   public int getNextNeighbor(int v1,int v2){
	   for(int j=v2+1;j<nodeList.size();j++){
		   if(edges[v1][j]>0){
			   return j;
		   }
	   }
	   return -1;
   }
   //获取所有的邻接节点
   public ArrayList<Integer> getAllNextNode(int v1,int v2){
	   ArrayList<Integer> a = new ArrayList<>();
	   int b = v2;
	   while((b=getNextNeighbor(v1, b))!=-1){
		   a.add(b);
	   }
	   return a;
   }
//拓扑排序，利用广度优先的思想
   public void tuopusort(){
	   LinkedList<Integer> q = new LinkedList<>();//定义队列，储存前驱节点
	   LinkedList<Integer> result = new LinkedList<>();//用来储存结果的下标
	   LinkedList<String> last = new  LinkedList<>();//用来储存结果
	   //第一次需要利用遍历来获得入度为0的节点
	   for (int i = 0; i <getNumOfNode() ; i++) {
		   if(indegree[i]==0){
			q.addLast(i);
		   }
	   }
	   //找到前驱节点的后继节点
		while(!q.isEmpty()){
			int u = q.removeFirst();
			result.add(u);
			for (int w : getAllNextNode(u, u)) {
					indegree[w]--;
					if(indegree[w] == 0){
//						result.add(q.removeFirst());
						q.addLast(w);
					}
			}
	    }
		//输出结果
		for (Integer ww : result) {
			// last.addLast(getValueBynodeList(ww));
			 System.out.print(getValueBynodeList(ww)+"->");
		}
//		return last;
   }
	  
	 
   
   //深度优先遍历算法DFS
   private void dfs(boolean[] isvisit,int i){
	   
	   //首先访问该节点，输出,加个判断是为了最后的箭头去掉
	   storgeGraphh.add(getValueBynodeList(i));
	   if(storgeGraphh.size()!=5){
		   System.out.print(getValueBynodeList(i)+"->");
	   }else{
		   System.out.print(getValueBynodeList(i));
		   System.out.println("");
	   }
	   
	   //标记该节点为TRUE，访问过
	   isvisit[i]= true ;
	   //查找邻接节点的第一个节点
	   int w = getFirstNeighbor(i);
	   while(w!=-1){
		   //说明有邻接节点
		   if(!isvisit[w]){
			   dfs(isvisit,w);
		   }
		   //如果w已经被标记过，就要查找原来的下一个邻接节点
		   w = getNextNeighbor(i, w);
	   }
   }
   
   //方法的重载
   public void dfs(){
	   isvisit = new boolean[5];
	   //遍历所有节点进行回溯
	   for(int i = 0;i<getNumOfNode();i++){
		   if(!isvisit[i]){
			   dfs(isvisit,i);
		   }
	   }
   }
   
   //广度优先遍历
   private void bfs(boolean[] isvisit,int i){
	   
	   int u;//邻接节点对应的下标
	   int w;//邻接结点w
	   //添加一个队列
	   LinkedList<Integer> queue = new LinkedList<Integer>();
	   System.out.print(getValueBynodeList(i)+"->");
	   isvisit[i]=true;
	   queue.addLast(i);
	   while(!queue.isEmpty()){
		   u = queue.removeFirst();
		   w = getFirstNeighbor(u);
		   while(w != -1){
		   if(!isvisit[w]){
			   System.out.print(getValueBynodeList(w)+"->");
			   isvisit[w]=true;
			   queue.addLast(w);
		   }
		   w = getNextNeighbor(u, w);
	   }
	   }  
   }
   //广度的遍历的重载
   private void bfs(){
	   isvisit = new boolean[5];
	   for (int i = 0; i < getNumOfNode(); i++) {
		   if(!isvisit[i]){
			   bfs(isvisit,i);
		   }
	}
		   
   }
   
   
   //显示邻接矩阵
   public void showGraph(){
	   //for,取出一维数组
	   for(int[] link:edges){
		   //调用数组Arrays中toString方法
		   System.out.println(Arrays.toString(link));
	   }
   }
   
   //插入顶点
   public void insertNode(String node){
	   nodeList.add(node);
   }
   
   //插入边
   public void insertEdeges(int a,int b,int weight){
	  //因为是无向图，双向权值一致，插入一条便增加一个数量,也可以又有向图
	   edges[a][b]=weight;
	   //edges[b][a]=weight;
	   indegree[b]++;
	   numofedges++;
   }
   
   //得到顶点的数量
   public int getNumOfNode(){
	  return nodeList.size();
   }
   
   //得到边的数量
   public int getNumEdges(){
	   return numofedges;
   }
   
   //通过链表得到顶点值
   public String getValueBynodeList(int i){
	   return nodeList.get(i);
   }
   /**
    * 
    * @param a 
    * @param b
    * @return
    */
   //得到权重
   public int getWeight(int a,int b){
	   return edges[a][b];
   }
}
