import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class nqueens {

	public static int n;
	public static PrintWriter cnfgenerator;
	public static ArrayList<String> clauses;
	public static boolean bool = true;
	

	
/*For n-queens to place a queen in a correct position we have to 
check all possible positions of rows, columns,Right Left Diagonal,Left Right Diagonal*/
	private static void clausegenerator() throws Exception {
		addrows();		
		addcolumns();
		RightLeftdiagonal();
		LeftRightdiagonal();
		
	}
/*method to add rows*/
	
	public static void addrows() throws Exception { 
		for (int i = 0; i < n; i++) {
			int[] RowValues = new int[n]; 	// Creating an array RowValues to save a row clauses.
			for (int j = 0; j < n; j++) {
				RowValues[j] = (j + 1) + (i * n); //stores the values in the format [1,2,3,4..n]
			}
			FinalClause(RowValues, !bool); 
			//we are adding these Values to another method FinalClause() which will consolidate all the clauses.
		}
	}
	
/*method to add columns*/
	
	public static void addcolumns() throws Exception { 
		for (int i = 1; i <= n; i++) {
			int[] ColumnValues = new int[n];	// Creating an empty array ColumnValues to save a column clause.
			for (int j = 0; j < n; j++) {
				ColumnValues[j] = (j * n) + i; //stores the values in the format [1,5,9,13]
			}
			FinalClause(ColumnValues, !bool);
			//we are adding these Values to another method FinalClause() which will consolidate all the clauses.
		}
	}

// generating clauses from top right to left bottom  diagonal 
	
	private static void RightLeftdiagonal() throws Exception		
	{
		int start = 0;
		for (int i = n; i > 0; i--)
		{
			int[] RightdiagonolValues = new int[start + 1];				// creating an empty array RightdiagonalValues to store the Right diagonal clauses
			int y = 0;												
			for (int j = i; j <= n * (start + 1); j += n + 1) {		/*This loops runs and stores the Right Diagonal values from Right to left side of the matrix. takes each diagonal clause and add it to final clause*/
				RightdiagonolValues[y] = j;								/*this method derives for example n=3, [3],[2,6],[1,5,9]*/
				y++;
			}
			start++;
			FinalClause(RightdiagonolValues, bool);					//we are adding these Values to another method FinalClause() which will consolidate all the clauses.
		}
		int y = n - 1;
		for (int i = n + 1; i < n * n; i += n) {					// Get the diagonals from the left diagonals of the matrix.
			int[] LeftdiagonolValues = new int[y];					// creating an empty array LeftdiagonalValues to store the Left diagonal clauses
			start = 0;
			for (int j = i; j < n * n; j += n + 1) {				/*This loops runs and stores the Left Diagonal values from Right to left side of the matrix. takes each diagonal clause and add it to final clause*/
				LeftdiagonolValues[start] = j;						/*this method derives for example n=3, [4,8],[7] */
				start++;
			}
			y--;
			FinalClause(LeftdiagonolValues, bool);					//we are adding these Values to another method FinalClause() which will consolidate all the clauses.
		}
	}

	
	
//generating clauses from top left to right bottom  diagonal 
	
	public static void LeftRightdiagonal() throws Exception {
		for (int i = n; i > 0; i--) {							
			int[] LeftdiagnolValue = new int[i];				/* creating an empty array LeftdiagonalValues to store the Left diagonal clauses*/
			int x = 0;
			for (int j = i; x < i; j += n - 1) 					/*This loops runs and stores the Left Diagonal values from left to right side of the matrix. takes each diagonal clause and add it to final clause*/
			{
				LeftdiagnolValue[x] = j;						/*this method derives the values as for example n=3, [3,5,7],[2,4],[1]*/
				x++;
			}
			FinalClause(LeftdiagnolValue, bool);				/*Adding these Values to another method FinalClause() which will consolidate all the clauses.*/
		}

		int y = n - 1;
																/* loop to Get the diagonals from the right side*/
		for (int i = n * 2; i < n * n; i += n) {

			int[] RightdiagnolValue = new int[y];					// creating an empty array RightdiagonalValues to store the Right diagonal clauses.
			int x = 0;
			for (int j = i; j < n * n; j += n - 1) {			/*This loops runs and stores the Right Diagonal values from right to left side of the matrix. takes each diagonal clause and add it to final clause*/
				RightdiagnolValue[x] = j;						/*this method derives the values as for example n=3, [6,8],[9] */
				x++;
			}
			y--;
			FinalClause(RightdiagnolValue, bool);				//adding these Values to another method FinalClause() which will consolidate all the clauses.
		}
	}
	/*we are adding all the clauses obtained to this method FinalClause() which will consolidate all the clauses.*/
	
	public static void FinalClause(int[] array, boolean diagnol) throws Exception {	
		if (array.length == 1) 														/*when the array size that we are passing from above 4 methods is 1 then we are not doing anything*/
		{		
			// ignore
		} else {	
			/* processing the values obtained from above 4 methods separately.*/
			/*When the data is from addrows() and addcolumns() methods, we are concatenating the values to a String s and adding O in the last to get cnf format*/
			if (!diagnol) {															
				String s = new String();
				for (int i = 0; i < array.length; i++) {
					s += array[i] + " ";
				}
				s += "0";
				clauses.add(s);		/*adding these string values to the final ArrayList<> clauses*/
			}
			/*When the data is from RightLeftdiagonal() and LeftRightdiagonal() methods*/
			for (int k = 0; k < array.length; k++) {
				for (int l = k + 1; l < array.length; l++) {
					clauses.add("-" + array[k] + " -" + array[l] + " 0");

				}
			}
		}
	}
	
	
//Printing the clause values to a .cnf file
	public static void generateCNFFile() throws FileNotFoundException, UnsupportedEncodingException {
		cnfgenerator.println("p cnf " + n + " " + clauses.size());

		for (int i = 0; i < clauses.size(); i++) {
			cnfgenerator.println(clauses.get(i));
		}
		cnfgenerator.close();
	}

	public static void main(String[] args) //code execution starts here
	{
		try {
			if (args.length == 1) {				
				n = Integer.parseInt(args[0]);	// passing the n value taken from the user
			}
			cnfgenerator = new PrintWriter("queens.cnf"); //generating an empty .cnf file to store cnf values
			clauses = new ArrayList<String>();	//ArrayList to store the required clauses(conditions)
		    clausegenerator();			//calling method to create clauses.
			generateCNFFile();			//calling method to generate the cnf file

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
