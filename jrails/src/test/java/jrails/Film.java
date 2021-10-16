package jrails;

public class Film extends Model {

	

	@Column
	public int year;
	
	@Column
	public String title;
	
	@Column
	public boolean on;

}
