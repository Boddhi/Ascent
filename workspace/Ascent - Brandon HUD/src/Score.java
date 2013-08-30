//Vanshil Shah
//June 4th, 2013
//This class is used to keep the name associated with the score. 

public class Score implements Comparable<Score> {
	private String name;
	private double score;
	
	public Score(String s){
		name = s.substring(0, s.indexOf("||"));
		if (name.equals("")){
			name = "Anonym";
		}
		if (name.length()>8)
			name = name.substring(0, 5) + "...";
		score = Double.parseDouble(s.substring(s.indexOf("||")+2));
	}
	
	public String toString(){
		String s = name + " : " + score;
		return s;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public int compareTo(Score s) {
			int scoreCmp = Double.compare(score, s.score);
		return scoreCmp;
	}

}
//:3