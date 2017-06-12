package ResultCheck;

public class PotenzmengeVariablen {
	 public Boolean [][] potenzmenge;
	  public PotenzmengeVariablen(){
		  potenzmenge = new Boolean[][] {
			  {false, false, false, false },
			  {false, false, false, true },
			  {false, false, true, false },
			  {false, false, true, true },
			  {false, true, false, false },
			  {false, true, false, true },
			  {false, true, true, false },
			  {false, true, true, true },
			  {true, false, false, false },
			  {true, false, false, true },
			  {true, false, true, false },
			  {true, false, true, true },
			  {true, true, false, false },
			  {true, true, false, true },
			  {true, true, true, false },
			  {true, true, true, true } 
		  }; 
	  }
}
