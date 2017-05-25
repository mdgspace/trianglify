Welcome to the Trianglify documentation! Here's the ultimate guide to using Trianglify in your app.

# Documentation
## Contents
1. [Usages](#1-usages)
    1. [Java](#11-java)
    2. [XML](#12-xml)
2. [APIs](#2-api-documentation)
    1. [Attributes and Methods](#21-attributes-and-methods)
        1. [TrianglifyView](#211-trianglifyview)
        2. [Palette](#212-palette)
    2. [Details of Bleed and Grid Dimensions](#22-details-of-bleed-and-grid-dimensions)
    4. [Note on Units of CellSize, Variance, Bleed & Grid Height](#24-note-on-units-of-cellsize-variance-bleed-and-grid-height)
    5. [Setting Palette using setPalette](#25-setting-palette-using-setpalette-method)
    6. [Using Custom Palettes](#26-using-custom-palettes)
    7. [Updating the View](#27-updating-the-view)
3. [Performance Analysis](#3-performance-analysis)
4. [UML Diagrams](#4-uml-diagrams)

## 1. Usages
### 1.1 Java

#### Import Statements
Include following lines along with other import statements at the beginning of your activity's java class:  

```java
import com.sdsmdg.kd.trianglify.views.TrianglifyView;
import com.sdsmdg.kd.trianglify.models.Palette;
```

To use trianglify view include the following lines to get an instance of view and set its properties:

```java
trianglifyView = (TrianglifyView) findViewById(R.id.trianglify_main_view); 
trianglifyView.setGridWidth(trianglifyView.getWidth())
            .setGridHeight(trianglifyView.getHeight())
            .setBleedX(50)
            .setBleedY(50)
            .setCellSize(20)
            .setVariance(10)
            .setTypeGrid(0)
            .setPalette(Palette.getPalette(26))
            .setDrawStrokeEnabled(true);
```

### 1.2 XML

```xml
<com.sdsmdg.kd.trianglify.views.TrianglifyView
    android:id="@+id/trianglify_main_view"
    app:cellSize="20dp"
    app:variance="10dp"
    app:bleedX="50dp"
    app:bleedY="50dp"
    app:gridType="rectangle"
    app:palette="Spectral"
    app:fillStrokes="true"
    app:fillTriangle="true" />
```
 

## 2. API Documentation
### 2.1 Attributes and Methods
#### 2.1.1 TrianglifyView
| Property                    | Default values | Java method             | Attribute       | Description                                                                                                                   |
|-----------------------------|----------------|-------------------------|-----------------|-------------------------------------------------------------------------------------------------------------------------------|
| Grid Height                 | NA             | .setGridHeight(...)     | grid_height     | Height of the grid to generate                                                                                                |
| Grid Width                  | NA             | .setGridWidth(...)      | grid_width      | Width of the grid to generate                                                                                                 |
| X-axis Bleed                | 0 px         | .setBleedX(...)         | bleed_x         | TrianglifyView generates total area having width = gridWidth + 2*bleedX to avoid unfilled triangles at the edges of the view  |
| Y-Axis Bleed                | 0 px           | .setBleedY(...)         | bleed_y         | TrianglifyView generates total area having height = gridWidth + 2*bleedY to avoid unfilled triangles at the edges of the view |
| Variance                    | 10 px          | .setVariance(...)       | variance        | Displacement of points from original grid position to create triangles of different sizes                                     |
| Cell Size                   | 40 px          | .setCellSize(...)       | cell_size       | Size of cells of rectangular grid used to generated vertices of the triangles                                                 |
| Grid Type*                  | 0              | .setTypeGrid(...)       | grid_type       | Type of grid 0 for Rectangular                                                                                                |
| Fill Triangles with color   | true           | .setFillTriangle(...)   | fill_triangles  | Fills the triangle generated with color chosen                                                                                |
| Draw strokes                | false          | .setDrawStrokes(...)    | draw_strokes    | Draws triangle's border with neighboring triangle's color                                                                     |
| Color Palette               | YlGn           | .setPalette(...)        | palette         | Set of existing colors to color triangles                                                                                     |
| Random Coloring             | false          | .setRandomColoring(...) | random_coloring | If random coloring is on triangles will be colored randomly instead of linear interpolation                                   |
| Fill the View Completely    | false          | .setFillViewCompletely(...) | fillViewCompletely| If fillViewCompletely is true, then it will throw illegalArgumentsException whenever both `BleedX` and `BleedY` are not greater than `cellSize`. Refer to [Section 2.2](#22-details-of-bleed-and-grid-dimensions)

*Current release contains only one GridType accessible with id `0`  

**Other methods**  
The following methods are getters for corresponding properties and are not covered in the table above:  
* isDrawStrokeEnabled
* isRandomColoringEnabled
* isFillTriangle
* isFillViewCompletely
* getVariance
* getTypeGrid
* getPalette
* getGridWidth
* getGridHeight
* getCellSize
* getBleedX
* getBleedY

The following are additional methods provided for the developer:

**`getViewState`**

This method returns the state of the view. For more information on states of view read **Smart update of view using smartUpdate** in [Section 2.7 Updating the View](#27-updating-the-view).

**`clearView`**

This method clears the triangulation of the view and sets it to `null`.

#### 2.1.2 Palette
| Method      | Return Type | Type        | Parameters                                                             | Description                                                                                                                     |
|-------------|-------------|-------------|------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------|
| Constructor | -           | Constructor | int c0, int c1, int c2, int c3, int c4, int c5, int c6, int c7, int c8 | Takes nine parameters as colors to construct palette                                                                           |
| Constructor | -           | Constructor | int[] colors                                                           | Takes nine parameters as an array of colors to construct palette                                                               |
| getPalette  | Palette     | static      | int paletteIndex                                                       | Returns palette object corresponding to passed value of paletteIndex, palette is constructed from a predefined set of colors. |
| indexOf     | int         | static      | Palette palette                                                        | Returns index of palette object in predefined palette, -1 if doesn't exists                                                     |
| getColor    | int         | -           | int index                                                              | Returns color corresponding to index passed from the set of colors for a palette                                                 |

<p>

### 2.2 Details of Bleed and Grid Dimensions
* **Bleed:** Bleed defines the dimensions of extra size that TrianglifyView view generates so that triangles on the edge don't appear to be chopped off. In most of the cases `min{bleedX, bleedY} > cellSize` would ensure that the view is completely filled.  
* **Grid Dimensions:** `GridHeight` and `GridWidth` defines the dimensions of the visible area of the view.  

Total area generated by TrianglifyView is (`gridHeight` + 2  * `BleedY`) * (`gridWidth` + 2 * `BleedX`) while total area visible is (`gridHeight`) * (`gridWidth`)  

Following image demonstrates region covered by `gridHeight`, `gridWidth`, `bleedX` and `bleedY`
<p>
<img src="resources/default_pattern_explained.jpg" data-canonical-src="resources/default_pattern_explained.jpg" width="350" height="350" />

### 2.4 Note on Units of CellSize, Variance, Bleed and Grid Height
Attributes of `CellSize`, `Variance`, `Bleed` and `GridHeight` supplied to TrianglifyView using Java are interpreted as `px`. To convert px to dp or vice-versa define and use following methods:

```java
public int dpToPx(int dp) {
    DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
    return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
}

public int pxToDp(int px) {
    DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
    return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
}
```

### 2.5 Setting Palette using setPalette method
`TrianglifyView`'s `setPalette` method accepts `Palette` object. Palette can be set using one of the following methods: 
* To use one of the predefined palette, for example **Spectral** use `trianglifyView.setPalette(Palette.getPalette(Palette.Spectral))`.
* To use palette referring to its index use `Palette.getPalette(<index>)`.  
* To use custom palette refer to section [2.6 Using custom Palettes](#26-using-custom-palettes).

> **Note:** Index used for addressing palette should be less than `Palette.DEFAULT_PALETTE_COUNT`, if index is greater than `DEFAULT_PALETTE_COUNT` `IllegalArgumentException` is thrown.

**Note: Palette enum defines total of 28 named palettes that can be used to generate views without specifying colors.** 

### 2.6 Using Custom Palettes
Custom Palettes can be used by creating new palette using one of the following constructor:

```java
Palette customPalette = new Palette(int c0, int c1, int c2, int c3, int c4, int c5, int c6, int c7, int c8);
```

or

```java
Palette customPalette = new Palette(int colors[]);
```
> Array based constructor will throw `IllegalArgumentException` if size of array is not exactly 9.

### 2.7 Updating the View

`TrianglifyView` should be updated on change of any of the parameter for changes to take effect. `TrianglifyView` can be updated by the following two methods:

**Smart update of view using `smartUpdate`**

`smartUpdate` updates the view smartly by looking at the parameter changes in the view since the last update, and accordingly decides which parts of the view should be updated. More formal description is:
* If only the `drawStroke` or `fillTriangle` values have been changed since the last update, the triangulation is only replotted in accordance to the new paint strokes. 
* If the `randomColoring` or `palette` have been changed without any changes in the grid parameters, only the new colors are assigned to the triangulation without regenerating the grid and delaunay triangulation, after which the view is plotted in accordance to the `fillTriangle` and `drawStrokes` parameters.
* If there are changes in the grid parameters, the whole triangulation has to be generated from scratch. The method generates a new grid according to the parameters and fits a delaunay triangulation. This is followed by colorization and plotting of the triangulation onto the view.

**Complete regeneration using `generateAndInvalidate`**

This is used when the triangulation is to be generated from scratch. The method generates a grid according to the parameters and fits a delaunay triangulation. This is followed by colorization and plotting of the triangulation onto the view.

**(Basic) Performance Comparision of the Two Methods**

`smartUpdate` method regenerates the whole triangulation only when the grid parameters have been changed, thereby bypassing the unnecessary regeneration of grid and delaunay triangulation in situations when parameters other than grid parameters have been changed. This leads to a faster rendering of view. 

The `generateAndInvalidate` method regenerates the whole triangulation irrespective of which parameters have been changed, carrying out steps that might not be necessary, hence causing hindrance to performance.



## 3. Performance Analysis
Few notes on performance of Trianglify
* Performance takes a serious hit with decrease in cell size. Time complexity of the algorithm to generate triangles from grid of points is Ω(n*log(n)). Decreasing cell size increases n (number of points on the grid). 
* Performance of coloring is faster on the use of random coloring rather than gradient.
* On use of smartUpdate, performance of coloring depends on the parameters changed since last triangulation. Check [2.7 Updating the View](#27-updating-the-view) for more details.

## 4. UML diagrams
Complete UML diagram for the project structures are available as Draw.io link hosted in google drive 
| [Link](https://www.draw.io/?state=%7B%22ids%22:%5B%220Bz_2jvdEtUlrWlB0LXJvRnBQZ0U%22%5D,%22action%22:%22open%22,%22userId%22:%22109172653085429225560%22%7D)  
*(Note that you'll require a google account to access the file, if this is your first time then choose `open with Draw.io` option on top of the browser window. Then scroll to the center of the document to view diagrams)*
