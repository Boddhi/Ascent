import java.util.*;

public class aplusb {
	public static void main(String []args) {
		Scanner s = new Scanner (System.in);
		
		int n = s.nextInt();
		
		for (int i = 0; i <= n; i++) {
			String line = s.nextLine();
			int counter = 0;
				for (int z = 0; z < line.length(); z++) {
					if (line.charAt(i) == ' ') 
						break;
					else 
						counter++;
			}
			System.out.println(counter);
		}
	}
}
