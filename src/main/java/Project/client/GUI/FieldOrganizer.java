package Project.client.GUI;

public class FieldOrganizer {

	private FieldButton[][] organizedFields;
	private FieldButton[] unorganizedFileds;
	private int edge;

	public FieldOrganizer(FieldButton[] unorganizedFileds, int edgeLength) {
		edge = edgeLength;
		this.unorganizedFileds = unorganizedFileds;
		organizedFields = new FieldButton[4 * (edge - 1)][];

	}

	public void organize() {
		//
	}

	public FieldButton[][] getOrganizedFields() {
		return organizedFields;
	}

}
