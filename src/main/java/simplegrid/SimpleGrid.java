package simplegrid;


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

	public SimpleGrid() {
		super();

		this.setHeight(400);
		this.setWidth(400);
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
				if (rowIndex == rowCount - 1 && columnIndex <  colCount - 1) {
					customCell.setStyle(SimpleGridStyle.NO_RIGHT_BORDER + SimpleGridStyle.DIMENSIONBG + SimpleGridStyle.GRIDLINE_COLOR);
				} else if (columnIndex == colCount - 1 && rowIndex < rowCount - 1) {
					customCell.setStyle(SimpleGridStyle.NO_BOTTOM_BORDER + SimpleGridStyle.DIMENSIONBG + SimpleGridStyle.GRIDLINE_COLOR);
				} else if (rowIndex == rowCount - 1  && columnIndex == colCount - 1) {
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

	public void setGridlineColor(Color color){
		String hex = String.format( "#%02X%02X%02X",
			            (int)( color.getRed() * 255 ),
			            (int)( color.getGreen() * 255 ),
			            (int)( color.getBlue() * 255 ) );
		if(cells == null){
			SimpleGridStyle.GRIDLINE_COLOR = "-fx-border-color:"+hex+";";
			return;
		}else{
			if(cells.length > 0){
				SimpleGridStyle.GRIDLINE_COLOR = "-fx-border-color:"+hex+"";
				System.out.println(SimpleGridStyle.GRIDLINE_COLOR);
				String[] styles = cells[0][0].getStyle().split(";");
				String gridLines = styles[3];
				for(CustomCell[] cell : cells){
					for (CustomCell customCell : cell) {
						String newStyle = customCell.getStyle().replaceAll(gridLines, SimpleGridStyle.GRIDLINE_COLOR );
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

	public void setRowHeight(double rowHeight) {
		this.rowHeight = rowHeight;
		for (CustomCell[] customCells : cells) {
			for (CustomCell customCell : customCells) {
				customCell.setMaxHeight(rowHeight);
			}
		}
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
		cells[rowIndex][colIndex].setMaxHeight(height);
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

		public CustomCell() {
			super();
			text = new Label("");
			this.setVgrow(text, Priority.ALWAYS);
			this.getChildren().add(text);
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
}

