#### Author: @ca.leumaleu

# SimpleGrid V 1.6.1
1. [Overview](#overview)
2. [SimpleGrid vs GridPane](#difference)
3. [Architecture](#architecture)
4. [How to use](#howToUse)
    - [4.1. Instantiation](#instantiate)  
    - [4.2. Configuration](#configuration)
5. [View](#view)
6. [mvn repository](#mvnRepo)
7. [releases](#releases)

### Overview<a name="overview"></a>
SimpleGrid is a custom control used for purposes similar to those of GridPane but with some slight differences

### SimpleGrid vs GridPane<a name="difference"></a>
| SimpleGrid | GridPane |
|------------|----------|
|setting up easy|setting up a little more difficult|
|lightweight |heavy|
|gridline stylable and more flexible|gridline not stylable and unflexible|
|look prettier|less pretty|
|`no row- or columnspan` |row and columnspan possible|
|cells formatting possible|there is no cell just index and constrains which are realy difficult to use|

### Architecture<a name="architecture"></a>
![architecture](/SimpleGrid/images/simpleGridEap.PNG)

### How to use<a name="howToUse"></a>
#### Instantiation<a name="instantiate"></a>
```java
    SimpleGrid sp = new SimpleGrid(400,400); // height = width = 400
```
#### Configuration<a name="configuration"></a>

```java
    sp.setGridlineColor(Color.BLUE);
    sp.init(5, 2); // 5 rows and 2 columns
    sp.getCell(1, 1).setText("I feel good");
```

### View<a name="view"></a>
![view](/SimpleGrid/images/view.PNG)

### mvn repository<a name="mvnRepo"></a>
```java 
    <dependency>
    	<groupId>com.preag.simplegrid</groupId>
    	<artifactId>SimpleGrid</artifactId>
    	<version>1.6.1-SNAPSHOT</version>
	</dependency>
```

### releases<a name="releases"></a>
Version 1.5.0 is available
New features:
```java
/**
 * merge cells corresponding to the given configuration
 */
public void merge(int firstRow, int lastRow, int firstCol, int lastCol);
/**
* align all text of cells located in the given column
*/
public void alignColumn(int colIndex, Pos pos);
/**
 * assign the given width to the given column
 * @param colIndex
 * @param colWidth
 */
public void resetColumnWidth(int colIndex, double colWidth);
/**
 * assign the given height to all the rows of the grid
 * @param rowHeight
 */
public void setRowHeight(double rowHeight);
```