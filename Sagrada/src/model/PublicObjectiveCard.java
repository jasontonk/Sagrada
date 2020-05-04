package model;

public class PublicObjectiveCard {

	private String name;
	private String description;
	private int points;
	private int id;
	
	public PublicObjectiveCard(String name, String description, int points, int id) {
		this.name = name;
		this.description = description;
		this.points = points;
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
