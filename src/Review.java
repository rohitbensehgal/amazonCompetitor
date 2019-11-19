import java.util.Comparator;

public class Review implements Comparator<Review> {
	
	private int stars;
	private String review;
	
	
	
	public Review(int stars, String review) {
		super();
		this.stars = stars;
		this.review = review;
	}
	
	public Review() {
		// TODO Auto-generated constructor stub
	}

	public int getStars() {
		return stars;
	}
	public void setStars(int stars) {
		this.stars = stars;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}

	@Override
	public int compare(Review o1, Review o2) {
		// TODO Auto-generated method stub
		return o1.getStars() - o2.getStars();
	}
}
