package Practice;

public class Practice1_6 {
	final private static int N=10;
	public static void main (String[] args){
		int[][] Ima = new int[N][N];
		for (int i=0;i<N;i++){
			for (int j=0;j<N;j++){
				Ima[i][j]=0;
				if(i==0){
					Ima[i][j]=1;		
				}
				if(j==7){
					Ima[i][j]=1;	
				}
					
			}
		}
		for (int i=0;i<N;i++){
			for (int j=0;j<N;j++){
				
				System.out.print(Ima[i][j]);
				System.out.print(" ");
				if(j%10==N-1){
					System.out.print("\n");	
				}
			}
		
		}
		
		System.out.print("\n");	
		Practice1_6 Practice1_6=new Practice1_6();
		Practice1_6.Rotate(Ima);
	}
	private void Rotate(int[][] Image){
	
		boolean left=false;
		
		for (int layer=0;layer<N/2;layer++){
			for(int count=layer;count<N-1-layer;count++){
				int head=Image[layer][count];
				int i=layer;
				int	j=count;
				int temp;
				for (int d=0; d<3; d++){
		
					if (left){
						Image[i][j]=Image[j][N-1-i];
						temp=i;
						i=j;
						j=N-1-temp;
					}
					else{
						Image[i][j]=Image[N-1-j][i];
						temp=i;
						i=N-1-j;
						j=temp;
					}
				}
				
				Image[i][j]=head;
			
			}
		}
		for (int i=0;i<N;i++){
			for (int j=0;j<N;j++){
				System.out.print(Image[i][j]);
				System.out.print(" ");
				if(j%10==N-1){
					System.out.print("\n");	
				}
			}
		}
	}
	
}
