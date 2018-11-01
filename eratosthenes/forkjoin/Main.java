/*----------------------------------------------------------------

*

* Actividad de programación: Fork-join framework

* Fecha: 23-Sep-2018

* Autor: A01700318 Ramon Romero

*

*--------------------------------------------------------------*/
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Main {


  	public static void initarr(int limit,int []a) {
  	  int c;
  	  for(c = 2; c < limit; c++) {
  	      a[c] = 0;
  	  }
  	}

  	public static void  printprimes(int limit, int []arr) {
  	  int c;

  	  for(c = 2; c <limit; c++) {
  	      if(arr[c] == 0) {
  					System.out.print(""+c+" ");
  	      }
  	  }
  		System.out.print("\n");
  	  /* code */
  	}

    private static final int MAXTHREADS = Runtime.getRuntime().availableProcessors();
    private static final int N = 10;

	public static void main(String args[]) throws Exception {

    int limit=16;
    if (args.length>1){
      System.err.println("Error: uso: %s [limite_superior_positivo]\n");
      System.exit(-1);
    }else if (args.length==1) {
      int parsed=Integer.parseInt(args[0]);
      //System.err.println(parsed);

      if (parsed<0){
        System.err.println("Error: uso: %s [limite_superior_positivo]\n");
        System.exit(-1);
      }else{
        limit=parsed;
      }
    }

    long startTime, stopTime;
    double acum = 0;
    acum = 0;
    int [] arr = new int[limit];


    for (int j = 1; j <= N; j++) {
    startTime = System.currentTimeMillis();
    arr = new int[limit];




    //

    int sqroot = (int)Math.sqrt(limit);
    ForkJoinPool pool = new ForkJoinPool(MAXTHREADS);;
    //

    //
    //initarr(limit, arr);
    int c;
    int m;


    for(c = 2; c <= sqroot; c++) {
        if(arr[c] == 0) {

          //
            pool.invoke(new ForkSieve(0, limit, arr, c));

          //


        }
    }
    //
      stopTime = System.currentTimeMillis();
      acum +=  (stopTime - startTime);
    }
    System.out.printf("avg time = %.5f\n", (acum / N));












  //printprimes(limit, arr);







    System.exit(0);



	}
}
/*----------------------------------------------------------------

*

* Actividad de programación: Fork-join framework

* Fecha: 23-Sep-2018

* Autor: A01700318 Ramon Romero

*

*--------------------------------------------------------------*/

class ForkSieve extends RecursiveAction {
	private static final long MIN = 10000;
  public int [] arr;
  public int ct=-1;
  public int begin;
  public int end;


  public ForkSieve( int b, int e, int [] a, int c ) {
     this.arr   = a;
     this.begin = b;
     this.end   = e;
     this.ct    = c;
  }


	public void computeDirectly() {

    for (int i = this.begin+1; i < this.end; i++) {
      //System.err.println(ct+" "+i);

      this.arr[ct] = 0;
      if(i%ct == 0) {
          this.arr[i] = 1;

      }

    }
	}

	@Override
	protected void compute() {
		if ( (this.end - this.begin) <= ForkSieve.MIN ) {
			computeDirectly();

		} else {
			int middle = (end + begin) / 2;

			invokeAll(new ForkSieve( begin, middle, arr, ct),
					  new ForkSieve( middle, end, arr, ct));
		}
	}
}
