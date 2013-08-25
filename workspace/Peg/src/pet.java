import java.util.*;

public class pet {
	public static void main (String []args) {
		Scanner s = new Scanner(System.in);
		int n = s.nextInt();
		int [] swag = new int [n];
		for (int i = 0; i < n; i++) {
			swag[i] = s.nextInt();						
		}
		Arrays.sort(swag);
		for (int i =0; i < n; i++) {
			System.out.println(swag[i]);
		}

	}
}