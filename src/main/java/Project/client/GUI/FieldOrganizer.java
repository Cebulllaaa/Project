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
		hexSize = 6 * triSize + 1;

		this.unorganizedFileds = unorganizedFileds;
		organizedFields = new FieldButton[4 * edge - 3][]; // 4 * edge - 3 = 4 * (edge - 1) + 1

	}

	public void organize() {
		setSizes();

		putFirstTriangle();
		putSecondTriangle();
		putThirdTriangle();
		putFourthTriangle();
		putFifthTriangle();
		putSixthTriangle();

		putHexagon();

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
			organizedFields[i + (3 * edge) - 2] = new FieldButton[edge - 1 - i];
		}

	}

	private void putFirstTriangle() {
		int actualPos = hexSize;

		for (int i=0; i < edge - 1; i++) {
			for (int j=0; j <= i; j++) {
				organizedFields[i][i-j] = unorganizedFileds[actualPos + j];
			}

			actualPos += i + 1;

		}

	}

	private void putSecondTriangle() {
		int actualPos = hexSize + triSize;

		for (int i=0; i < edge - 1; i++) {
			for (int j=0; j <= i; j++) {
				organizedFields[edge - 1 + j][i-j] = unorganizedFileds[actualPos + j];
			}

			actualPos += i + 1;

		}

	}

	private void putThirdTriangle() {
		int actualPos = hexSize + 2 * triSize;

		for (int i=0; i < edge - 1; i++) {
			for (int j=0; j <= i; j++) {
				organizedFields[3 * edge - 3 + j - i][j] = unorganizedFileds[actualPos + j];
			}

			actualPos += i + 1;

		}

	}

	private void putFourthTriangle() {
		int actualPos = hexSize + 3 * triSize;

		for (int i=0; i < edge - 1; i++) {
			for (int j=0; j <= i; j++) {
				organizedFields[4 * edge - 4 - i][j] = unorganizedFileds[actualPos + j];
			}

			actualPos += i + 1;

		}

	}

	private void putFifthTriangle() {
		int actualPos = hexSize + 4 * triSize;

		for (int i=0; i < edge - 1; i++) {
			for (int j=0; j <= i; j++) {
				FieldButton[] tab = organizedFields[3 * edge - 3 - j];
				tab[tab.length - 1 - i + j] = unorganizedFileds[actualPos + j];
			}

			actualPos += i + 1;

		}

	}

	private void putSixthTriangle() {
		int actualPos = hexSize + 5 * triSize;

		for (int i=0; i < edge - 1; i++) {
			for (int j=0; j <= i; j++) {
				FieldButton[] tab = organizedFields[edge - 1 + i - j];
				tab[tab.length - 1 - j] = unorganizedFileds[actualPos + j];
			}

			actualPos += i + 1;

		}

	}

	private void putHexagon() {
		FieldButton[][] tempTab = new FieldButton[2 * edge - 1][];

		for (int i=0; i < edge; i++) {
			tempTab[i] = new FieldButton[edge + i];
		}

		for (int i=0; i < edge - 1; i++) {
			tempTab[edge + i] = new FieldButton[2 * edge - 2 - i];
		}

		fillTempTab(tempTab);

		for (int i=0; i < edge; i++) {
			for (int j=0; j < tempTab[i].length; j++) {
				organizedFields[edge - 1 + i][edge - 1 - i + j] = tempTab[i][j];
			}
		}

		for (int i=0; i < edge - 1; i++) {
			for (int j=0; j < tempTab[edge + i].length; j++) {
				organizedFields[2 * edge - 1 + i][i + 1 + j] = tempTab[edge + i][j];
			}
		}

	}

	private void fillTempTab(FieldButton[][] tempTab) {
		// w zalozeniu: tempTab[edge - 1][edge - 1] to srodek
		tempTab[edge - 1][edge - 1] = unorganizedFileds[0];
		int start = 1;

		for (int i=1; i < edge; i++) {
			start = putRowHexagon(tempTab, i, start);
		}

	}

	private int putRowHexagon(FieldButton[][] tempTab, int i, int start) {
		int X = edge - 1; // X - pozioma pozycja w tempTab
		int Y = edge - 1; // Y - pionowa pozycja w tempTab

		Y -= i;

		for (int j=0; j < i; j++) {
			tempTab[Y][X] = unorganizedFileds[start + j];
			X--;
		}

		start += i;

		for (int j=0; j < 2*i; j++) {
			tempTab[Y][X] = unorganizedFileds[start + j];
			Y++;
		}

		start += 2 * i;

		for (int j=0; j < i; j++) {
			tempTab[Y][X] = unorganizedFileds[start + j];
			X++;
		}

		start += i;

		for (int j=0; j < i; j++) {
			tempTab[Y][X] = unorganizedFileds[start + j];
			X++;
			Y--;
		}

		start += i;

		for (int j=0; j < i; j++) {
			tempTab[Y][X] = unorganizedFileds[start + j];
			X--;
			Y--;
		}

		start += i;

		return start;

	}

	public FieldButton[][] getOrganizedFields() {
		return organizedFields;
	}

}
