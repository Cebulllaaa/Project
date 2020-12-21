package Project.client.GUI;

public class FieldOrganizer {

	private FieldButton[][] organizedFields;
	private FieldButton[] unorganizedFileds;
	private int edge;
	private int hexSize;
	private int triSize;

	public FieldOrganizer(FieldButton[] unorganizedFileds, int edgeLength) {
		edge = edgeLength;
		triSize = ((edge - 1) * edge) / 2;
		hexSize = 6 * triSize;

		this.unorganizedFileds = unorganizedFileds;
		organizedFields = new FieldButton[4 * (edge - 1)][];

	}

	public void organize() {
		setSizes();

		putFirstTriangle();
		putSecondTriangle();
		putThirdTriangle();
		putFourthTriangle();
		putFifthTriangle();
		putSixthTriangle();

		//

	}

	private void setSizes() {
		for (int i=0; i < edge-1; i++) {
			organizedFields[i] = new FieldButton[i+1];
		}

		for (int i=0; i < edge-1; i++) {
			organizedFields[i + edge - 1] = new FieldButton[3*edge - 2 - i];
		}

		organizedFields[2 * (edge - 1)] = new FieldButton[(2 * edge) - 1];

		for (int i=0; i < edge-1; i++) {
			organizedFields[i + (2 * edge) - 1] = new FieldButton[2*edge + i];
		}

		for (int i=0; i < edge-1; i++) {
			organizedFields[i + (3 * edge) - 2] = new FieldButton[edge - 1 + i];
		}

	}

	private void putFirstTriangle() {
		int actualPos = hexSize;

		for (int i=0; i < edge - 1; i++) {
			for (int j=0; j <= i; i++) {
				organizedFields[i][i-j] = unorganizedFileds[actualPos + j];
			}

			actualPos += i + 1;

		}

	}

	private void putSecondTriangle() {
		int actualPos = hexSize + triSize;

		for (int i=0; i < edge - 1; i++) {
			for (int j=0; j <= i; i++) {
				organizedFields[edge - 1 + j][i-j] = unorganizedFileds[actualPos + j];
			}

			actualPos += i + 1;

		}

	}

	private void putThirdTriangle() {
		int actualPos = hexSize + 2 * triSize;

		for (int i=0; i < edge - 1; i++) {
			for (int j=0; j <= i; i++) {
				organizedFields[3 * edge - 3 + j - i][j] = unorganizedFileds[actualPos + j];
			}

			actualPos += i + 1;

		}

	}

	private void putFourthTriangle() {
		int actualPos = hexSize + 3 * triSize;

		for (int i=0; i < edge - 1; i++) {
			for (int j=0; j <= i; i++) {
				organizedFields[4 * edge - 4 - i][j] = unorganizedFileds[actualPos + j];
			}

			actualPos += i + 1;

		}

	}

	private void putFifthTriangle() {
		int actualPos = hexSize + 4 * triSize;

		for (int i=0; i < edge - 1; i++) {
			for (int j=0; j <= i; i++) {
				FieldButton[] tab = organizedFields[3 * edge - 3 - j];
				tab[tab.length - 1 - i + j] = unorganizedFileds[actualPos + j];
			}

			actualPos += i + 1;

		}

	}

	private void putSixthTriangle() {
		int actualPos = hexSize + 5 * triSize;

		for (int i=0; i < edge - 1; i++) {
			for (int j=0; j <= i; i++) {
				FieldButton[] tab = organizedFields[edge - 1 + i - j];
				tab[tab.length - 1 - j] = unorganizedFileds[actualPos + j];
			}

			actualPos += i + 1;

		}

	}

	public FieldButton[][] getOrganizedFields() {
		return organizedFields;
	}

}
