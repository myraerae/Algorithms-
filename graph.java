package tu;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import org.w3c.dom.NodeList;

/**
 * @author Administrator
 *����һ�����ݽṹͼ(��ݼ�Alt+shift+J)
 *ͼ��������ȡ�������ȡ���������
 */
public class graph {
	//����һ���вι��죬����ʼ������ֵ
	public graph(int n) {
		edges = new int[n][n];
		numofedges = 0;
		nodeList = new ArrayList<String>(n);
		storgeGraphh = new ArrayList<String>(n);
		indegree = new int[n];
	}
	//�����Ա����
	private ArrayList<String> nodeList;//�洢���㼯��
	private int[][] edges;//�ڽӾ���
	private int numofedges;//�ߵ�����
	private boolean[] isvisit ;
	private ArrayList<String> storgeGraphh ;
	private int[] indegree;
	//�����������5�����㲢���룬�������������ʾ����
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n = 5;
		String nodes[] ={"A","B","C","D","E"};
		graph graphh = new graph(n);
		for(String node:nodes){
			graphh.insertNode(node);
		}
		//��ӱ�
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
	
	//�õ���һ���ڽӶ�����±�
   public int getFirstNeighbor(int index){
	   for(int j=0;j<nodeList.size();j++){
		   if(edges[index][j]>0){
			   return j;
		   }
	   }
	   return -1;
   }
   
   //����ǰһ���ڽӶ�������ȡ��һ���ڽӶ���
   public int getNextNeighbor(int v1,int v2){
	   for(int j=v2+1;j<nodeList.size();j++){
		   if(edges[v1][j]>0){
			   return j;
		   }
	   }
	   return -1;
   }
   //��ȡ���е��ڽӽڵ�
   public ArrayList<Integer> getAllNextNode(int v1,int v2){
	   ArrayList<Integer> a = new ArrayList<>();
	   int b = v2;
	   while((b=getNextNeighbor(v1, b))!=-1){
		   a.add(b);
	   }
	   return a;
   }
//�����������ù�����ȵ�˼��
   public void tuopusort(){
	   LinkedList<Integer> q = new LinkedList<>();//������У�����ǰ���ڵ�
	   LinkedList<Integer> result = new LinkedList<>();//�������������±�
	   LinkedList<String> last = new  LinkedList<>();//����������
	   //��һ����Ҫ���ñ�����������Ϊ0�Ľڵ�
	   for (int i = 0; i <getNumOfNode() ; i++) {
		   if(indegree[i]==0){
			q.addLast(i);
		   }
	   }
	   //�ҵ�ǰ���ڵ�ĺ�̽ڵ�
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
		//������
		for (Integer ww : result) {
			// last.addLast(getValueBynodeList(ww));
			 System.out.print(getValueBynodeList(ww)+"->");
		}
//		return last;
   }
	  
	 
   
   //������ȱ����㷨DFS
   private void dfs(boolean[] isvisit,int i){
	   
	   //���ȷ��ʸýڵ㣬���,�Ӹ��ж���Ϊ�����ļ�ͷȥ��
	   storgeGraphh.add(getValueBynodeList(i));
	   if(storgeGraphh.size()!=5){
		   System.out.print(getValueBynodeList(i)+"->");
	   }else{
		   System.out.print(getValueBynodeList(i));
		   System.out.println("");
	   }
	   
	   //��Ǹýڵ�ΪTRUE�����ʹ�
	   isvisit[i]= true ;
	   //�����ڽӽڵ�ĵ�һ���ڵ�
	   int w = getFirstNeighbor(i);
	   while(w!=-1){
		   //˵�����ڽӽڵ�
		   if(!isvisit[w]){
			   dfs(isvisit,w);
		   }
		   //���w�Ѿ�����ǹ�����Ҫ����ԭ������һ���ڽӽڵ�
		   w = getNextNeighbor(i, w);
	   }
   }
   
   //����������
   public void dfs(){
	   isvisit = new boolean[5];
	   //�������нڵ���л���
	   for(int i = 0;i<getNumOfNode();i++){
		   if(!isvisit[i]){
			   dfs(isvisit,i);
		   }
	   }
   }
   
   //������ȱ���
   private void bfs(boolean[] isvisit,int i){
	   
	   int u;//�ڽӽڵ��Ӧ���±�
	   int w;//�ڽӽ��w
	   //���һ������
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
   //��ȵı���������
   private void bfs(){
	   isvisit = new boolean[5];
	   for (int i = 0; i < getNumOfNode(); i++) {
		   if(!isvisit[i]){
			   bfs(isvisit,i);
		   }
	}
		   
   }
   
   
   //��ʾ�ڽӾ���
   public void showGraph(){
	   //for,ȡ��һά����
	   for(int[] link:edges){
		   //��������Arrays��toString����
		   System.out.println(Arrays.toString(link));
	   }
   }
   
   //���붥��
   public void insertNode(String node){
	   nodeList.add(node);
   }
   
   //�����
   public void insertEdeges(int a,int b,int weight){
	  //��Ϊ������ͼ��˫��Ȩֵһ�£�����һ��������һ������,Ҳ����������ͼ
	   edges[a][b]=weight;
	   //edges[b][a]=weight;
	   indegree[b]++;
	   numofedges++;
   }
   
   //�õ����������
   public int getNumOfNode(){
	  return nodeList.size();
   }
   
   //�õ��ߵ�����
   public int getNumEdges(){
	   return numofedges;
   }
   
   //ͨ������õ�����ֵ
   public String getValueBynodeList(int i){
	   return nodeList.get(i);
   }
   /**
    * 
    * @param a 
    * @param b
    * @return
    */
   //�õ�Ȩ��
   public int getWeight(int a,int b){
	   return edges[a][b];
   }
}
