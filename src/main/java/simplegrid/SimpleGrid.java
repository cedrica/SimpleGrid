package simplegrid;
/**
 * @author ca.leumaleu
 */

import java.io.IOException;
import java.net.URL;

import javax.management.StringValueExp;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SimpleGrid extends HBox {

	private CustomCell[][]	cells;
	private double			rowHeight;
	private double			columnWidth;
	private int				rowCount;
	private int				colCount;
	private Color			gridlineColor = new Color(0, 0, 0,0);

	public SimpleGrid() {
		super();
		URL location;
		try {
			location = new URL(getClass().getResource("/simplegrid/SimpleGrid.fxml").toExternalForm());
			FXMLLoader view = new FXMLLoader(location);
			view.setRoot(this);
			view.load();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		this.setFillHeight(true);
	}

	public SimpleGrid(double height, double width) {
		this.setHeight(height);
		this.setWidth(width);
		this.setFillHeight(true);
	}

	/**
	 * initialize the grid with given column and row count
	 * 
	 * @param rowNumber:
	 *        0 based index
	 * @param colNumber:
	 *        0 based index
	 */
	public void init(int rowCount, int colCount) {
		this.rowCount = rowCount;
		this.colCount = colCount;
		cells = new CustomCell[rowCount][colCount];
		for (int columnIndex = 0; columnIndex < colCount; columnIndex++) {
			VBox vb = new VBox();
			for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
				CustomCell customCell = new CustomCell();
				if (rowIndex == rowCount - 1 && columnIndex < colCount - 1) {
					customCell.setStyle(SimpleGridStyle.NO_RIGHT_BORDER + SimpleGridStyle.DIMENSIONBG + SimpleGridStyle.GRIDLINE_COLOR);
				} else if (columnIndex == colCount - 1 && rowIndex < rowCount - 1) {
					customCell.setStyle(SimpleGridStyle.NO_BOTTOM_BORDER + SimpleGridStyle.DIMENSIONBG + SimpleGridStyle.GRIDLINE_COLOR);
				} else if (rowIndex == rowCount - 1 && columnIndex == colCount - 1) {
					customCell.setStyle(SimpleGridStyle.ALL_BORDER + SimpleGridStyle.DIMENSIONBG + SimpleGridStyle.GRIDLINE_COLOR);
				} else {
					customCell.setStyle(SimpleGridStyle.NO_RIGHT_BOTTOM_BORDER + SimpleGridStyle.DIMENSIONBG + SimpleGridStyle.GRIDLINE_COLOR);
				}
				customCell.setAlignment(Pos.CENTER);
				cells[rowIndex][columnIndex] = customCell;
				vb.getChildren().add(cells[rowIndex][columnIndex]);
				vb.setVgrow(customCell, Priority.ALWAYS);
			}
			this.setHgrow(vb, Priority.ALWAYS);
			this.getChildren().add(vb);
		}
	}

	public void setGridlineColor(Color color) {
		this.gridlineColor = color;
		String hex = String.format("#%02X%02X%02X", (int) (color.getRed() * 255), (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
		if (cells == null) {
			SimpleGridStyle.GRIDLINE_COLOR = "-fx-border-color:" + hex + ";";
			return;
		} else {
			if (cells.length > 0) {
				SimpleGridStyle.GRIDLINE_COLOR = "-fx-border-color:" + hex + "";
				String[] styles = cells[0][0].getStyle().split(";");
				String gridLines = styles[3];
				for (CustomCell[] cell : cells) {
					for (CustomCell customCell : cell) {
						String newStyle = customCell.getStyle().replaceAll(gridLines, SimpleGridStyle.GRIDLINE_COLOR);
						customCell.setStyle(newStyle);
					}
				}
			}
		}
	}

	public void setGridlineVisible(boolean b) {
			if (cells.length > 0) {
				if(!b){
					String[] styles = cells[0][0].getStyle().split(";");
					String gridLines = styles[0];
					for (CustomCell[] cell : cells) {
						for (CustomCell customCell : cell) {
							String newStyle = customCell.getStyle().replaceAll(gridLines, SimpleGridStyle.NO_BORDER);
							customCell.setStyle(newStyle);
						}
					}
				}
		
		}
	}
	
	/**
	 * get the cell at the specified row and column index
	 * 
	 * @param rowIndex:
	 *        0 based index
	 * @param colIndex:
	 *        0 based index
	 * @return
	 * @throws Exception
	 */
	public CustomCell getCell(int rowIndex, int colIndex) throws Exception {
		if (rowIndex > rowCount || colIndex > colCount) {
			throw new Exception("Cannot set Height: given row or column index is out of bounds");
		}
		return cells[rowIndex][colIndex];
	}

	/**
	 * assign a height to all the cells of the grid
	 * @param rowHeight
	 */
	public void setRowHeight(double rowHeight) {
		this.rowHeight = rowHeight;
		this.setMaxHeight(rowHeight * rowCount);
		for (CustomCell[] customCells : cells) {
			for (CustomCell customCell : customCells) {
				customCell.setMaxHeight(rowHeight);
			}
		}
	}

	/**
	 * assign the given width to the given column
	 * @param colIndex
	 * @param colWidth
	 */
	public void resetColumnWidth(int colIndex, double colWidth) {
		for (int i = 0; i < rowCount; i++) {
			cells[i][colIndex].setMinWidth(colWidth);
		}
	}

	// None sence
	// public void resetColumnHeight(int colIndex, double rowHeight) {
	// for (int i = 0; i<rowCount; i++) {
	// cells[i][colIndex].setMaxHeight(rowHeight);
	// }
	// }
	/**
	 * merge cell corresponding to the given configuration
	 * @param firstRow
	 *        : 0-based
	 * @param lastRow
	 *        : 0-based
	 * @param firstCol
	 *        : 0-based
	 * @param lastCol
	 *        : 0-based
	 */
	public void merge(int firstRow, int lastRow, int firstCol, int lastCol) {
		String hex = String.format("#%02X%02X%02X", (int) (gridlineColor.getRed() * 255), (int) (gridlineColor.getGreen() * 255),
						(int) (gridlineColor.getBlue() * 255));
		String mergeText = "";
		for (int i = firstRow; i <= lastRow; i++) {
			for (int j = firstCol; j <= lastCol; j++) {
				CustomCell cell = cells[i][j];
				if (j == firstCol) {
					if (i == firstRow) {
						String[] styles = cell.getStyle().split(";");
						String borderColor = styles[3];

						String newStyle = cell.getStyle().replaceAll(borderColor,
										"-fx-border-color: " + hex + " transparent transparent " + hex + ";");
						cell.setStyle(newStyle);
					} else if (i == lastRow) {
						String[] styles = cell.getStyle().split(";");
						String borderColor = styles[3];
						String newStyle = cell.getStyle().replaceAll(borderColor,
										"-fx-border-color:transparent transparent transparent " + hex + ";");
						cell.setStyle(newStyle);
					} else {
						String[] styles = cell.getStyle().split(";");
						String borderColor = styles[3];
						String newStyle = cell.getStyle().replaceAll(borderColor,
										"-fx-border-color:transparent transparent transparent " + hex + ";");
						cell.setStyle(newStyle);
					}
				} else if (j == colCount) {
					if (i == firstRow) {
						String[] styles = cell.getStyle().split(";");
						String borderColor = styles[3];
						String newStyle = cell.getStyle().replaceAll(borderColor,
										"-fx-border-color: " + hex + " " + hex + " transparent " + hex + ";");
						cell.setStyle(newStyle);
					} else if (i == lastRow) {
						String[] styles = cell.getStyle().split(";");
						String borderColor = styles[3];
						String newStyle = cell.getStyle().replaceAll(borderColor,
										"-fx-border-color:transparent " + hex + " " + hex + " transparent;");
						cell.setStyle(newStyle);
					} else {
						String[] styles = cell.getStyle().split(";");
						String borderColor = styles[3];
						String newStyle = cell.getStyle().replaceAll(borderColor,
										"-fx-border-color:transparent " + hex + " transparent transparent;");
						cell.setStyle(newStyle);
					}
				} else {
					if (i == firstRow) {
						String[] styles = cell.getStyle().split(";");
						String borderColor = styles[3];
						String newStyle = cell.getStyle().replaceAll(borderColor,
										"-fx-border-color: " + hex + " transparent transparent transparent;");
						cell.setStyle(newStyle);
					} else if (i == lastRow) {
						String[] styles = cell.getStyle().split(";");
						String borderColor = styles[3];
						String newStyle = cell.getStyle().replaceAll(borderColor,
										"-fx-border-color:transparent transparent " + hex + " transparent;");
						cell.setStyle(newStyle);
					} else {
						String[] styles = cell.getStyle().split(";");
						String borderColor = styles[3];
						String newStyle = cell.getStyle().replaceAll(borderColor,
										"-fx-border-color:transparent transparent transparent transparent;");
						cell.setStyle(newStyle);
					}
				}
				String temp = cell.getText().getText();
				if (!temp.isEmpty()) {
					mergeText += temp + ",";
				}
				cell.setText("");
			}
		}
		if(!mergeText.isEmpty()){
			mergeText = removeLastSemiColumn(mergeText);
			int[] centerPos = calculateCenterPos(firstRow, lastRow, firstCol, lastCol);
			int colCount = lastCol - firstCol + 1;
			Pos p = calculatePosition(firstRow, lastRow, firstCol, lastCol);
			if (colCount > 1) {
				String[] strParts = splitMergeText(mergeText);
				cells[centerPos[0]][centerPos[1]].setText(strParts[0]);
				cells[centerPos[0]][centerPos[1]].align(p);
				if (p == Pos.TOP_RIGHT) {
					cells[centerPos[0]][centerPos[1]+1].setText(strParts[1]);
					cells[centerPos[0]][centerPos[1]+1].align(Pos.TOP_LEFT);
				} else if (p == Pos.BOTTOM_RIGHT) {
					cells[centerPos[0]][centerPos[1]+1].setText(strParts[1]);
					cells[centerPos[0]][centerPos[1]+1].align(Pos.BOTTOM_LEFT);
				}

			} else {
				cells[centerPos[0]][centerPos[1]].setText(mergeText);
				cells[centerPos[0]][centerPos[1]].align(p);
			}
		}
	}

	private Pos calculatePosition(int firstRow, int lastRow, int firstCol, int lastCol) {
		Pos p = null;

		int rowCount = lastRow - firstRow + 1;
		int colCount = lastCol - firstCol + 1;
		if (colCount % 2 != 0 && rowCount % 2 != 0) {
			p = Pos.CENTER;
		} else if (colCount % 2 != 0 && rowCount % 2 == 0) {
			if(colCount > 1){
				p = Pos.TOP_CENTER;
			}else{
				p = Pos.BOTTOM_CENTER;
			}
		} else if (colCount % 2 == 0 && rowCount % 2 == 0) {
			p = Pos.BOTTOM_RIGHT;
		} else if (colCount % 2 == 0 && rowCount % 2 != 0) {
			p = Pos.TOP_RIGHT;
		}
		return p;
	}

	private String[] splitMergeText(String mergeText) {
		if(mergeText.isEmpty())
			return null;
		int halfLenght = mergeText.length()/2;
		String[] res = new String[2];
		res[0] = mergeText.substring(0, halfLenght);
		res[1] = mergeText.substring(halfLenght+1, mergeText.length());
		return res;
	}

	private String removeLastSemiColumn(String mergeText) {
		if(mergeText.isEmpty())
			return "";
		mergeText = mergeText.substring(0, mergeText.length() - 1);
		return mergeText;
	}

	private int[] calculateCenterPos(int firstRow, int lastRow, int firstCol, int lastCol) {
		int[] res = new int[2];
		int rowIndex = (firstRow + lastRow) / 2;
		int colIndex = (firstCol + lastCol) / 2;
		res[0] = rowIndex;
		res[1] = colIndex;
		return res;
	}

	/**
	 * set all the columns width to the given width
	 * 
	 * @param width
	 */
	public void setColumnWidth(double width) {
		this.columnWidth = width;
		for (CustomCell[] customCells : cells) {
			for (CustomCell customCell : customCells) {
				customCell.setMaxWidth(width);
			}
		}
	}

	/**
	 * set the height cells of the cell at the row -> rowIndex
	 * 
	 * @param rowIndex
	 * @param colIndex
	 * @param height
	 * @throws Exception
	 */
	public void setRowHeight(int rowIndex, int colIndex, double height) throws Exception {
		if (rowIndex > rowCount || colIndex > colCount) {
			throw new Exception("Cannot set Height: given row or column index is out of bounds");
		}
		cells[rowIndex][colIndex].setMinHeight(height);
	}

	protected static class SimpleGridStyle {

		public static String	GRIDLINE_COLOR	= "-fx-border-color:gray;";
		public static String	DIMENSIONBG		= "-fx-pref-height:20;-fx-pref-width:100;";

		public static String	NO_TOP_BORDER		= "-fx-border-width: 0.0 1.0 1.0 1.0;";
		public static String	NO_RIGHT_BORDER		= "-fx-border-width: 1.0 0.0 1.0 1.0;";
		public static String	NO_BOTTOM_BORDER	= "-fx-border-width: 1.0 1.0 0.0 1.0;";
		public static String	NO_LEFT_BORDER		= "-fx-border-width: 1.0 1.0 1.0 0.0;";

		public static String	NO_RIGHT_BOTTOM_BORDER	= "-fx-border-width: 1.0 0.0 0.0 1.0;";
		public static String	NO_LEFT_BOTTOM_BORDER	= "-fx-border-width: 1.0 1.0 0.0 0.0;";
		public static String	NO_TOP_LEFT_BORDER		= "-fx-border-width: 0.0 1.0 1.0 0.0;";

		public static String	NO_BORDER	= "-fx-border-width: 0;";
		public static String	ALL_BORDER	= "-fx-border-width: 1;";
	}

	public class CustomCell extends VBox {

		private Label	text;
		private int		rowIndex;
		private int		colIndex;
		private VBox	vb;

		public CustomCell() {
			super();
			text = new Label("");
			vb = new VBox(text);
			vb.setAlignment(Pos.CENTER);
			this.setVgrow(vb, Priority.ALWAYS);
			this.getChildren().add(vb);
		}

		public void align(Pos position) {
			vb.setAlignment(position);
		}

		public Label getText() {
			return text;
		}

		public void setText(String text) {
			this.text.setText(text);
		}

		public int getRowIndex() {
			return rowIndex;
		}

		public void setRowIndex(int rowIndex) {
			this.rowIndex = rowIndex;
		}

		public int getColIndex() {
			return colIndex;
		}

		public void setColIndex(int colIndex) {
			this.colIndex = colIndex;
		}
	}

	public void alignColumn(int colIndex, Pos pos) {
		for (int i = 0; i < rowCount; i++) {
			cells[i][colIndex].align(pos);
		}
	}
}

