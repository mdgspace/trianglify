<br> 
<br> 
<div align="center"><img src="resources/trianglify-logo-with-text-close-fit.png" data-canonical-src="trianglify-logo-180.png" width="154" height="154" /></div>
<br> 
<br> 

# Trianglify

<!--[![Build Status](https://travis-ci.com/sdsmdg/trianglify.svg?token=tRURwj39jsSs5JWUTxs6&branch=develop)](https://travis-ci.com/sdsmdg/trianglify)-->
[![platform](https://img.shields.io/badge/platform-Android-yellow.svg)](https://www.android.com)
[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16s)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![by-SDS-MDG](https://img.shields.io/badge/by-SDS%2C%20MDG-blue.svg)](https://mdg.sdslabs.co)

Trianglify is an Android library that helps creates views with beautiful patterns. Trianglify is based on MVP architecture and licensed under MIT license

# Usages

Include following line in the gradle script of your application to include latest release of Trianglify:
```gradle
compile 'com.sdsmdg.kd:trianglify:0.8-beta'
```

# Releases

## Latest
* [Version 0.8 beta](https://bintray.com/suyashmahar/trianglify/trianglify/0.8-beta)
 | [demo apk](https://www.dropbox.com/s/cn85g497nkwmx14/trianglify-release-0.8-beta.apk?dl=0)
    * Generation of triangulation on worker thread to prevent UI freezing [#20](https://github.com/sdsmdg/trianglify/issues/20)
    * Correct coloring of view, color now drawn matches its name [#18](https://github.com/sdsmdg/trianglify/issues/18)
    * Support for custom palette ([usage](#26-using-custom-palettes)) [#17](https://github.com/sdsmdg/trianglify/issues/17)

## Previous
* [Version 0.7 beta](https://bintray.com/suyashmahar/trianglify/trianglify/0.7-beta)
 [Google Play](https://suyashmahar.me/404)
    * Added library to jCenter | [Link](https://bintray.com/suyashmahar/trianglify/trianglify/)
# Documentation
1. [Example Usages](#1-example-usages)
    1. [Java](#11-java)
    2. [XML](#12-xml)
2. [API Documentation](#2-api-documentation)
    1. [Attributes](#21-attributes)
    2. [Details of Bleed and Grid dimensions](#22-details-of-bleed-and-grid-dimensions)
    3. [Generates](#23-Generates)
    4. [Note on Units of CellSize, Variance, Bleed & Grid Height](#24-note-on-units-of-cellsize-variance-bleed-and-grid-height)
    5. [Setting Palette using `.setPalette()`](#25-setting-palette-using-setpalette-method)
    6. [Using custom Palettes](#26-using-custom-palettes)
	7. [Updating the View](#27-updating-the-view)
3. [Performance analysis](#3-performance-analysis)
4. [UML Diagrams](#4-uml-diagrams)
5. [Credits](#5-credits)
6. [Contributing](#6-contributing)

## 1. Example Usages
### 1.1 Java

Import Statements
```java
import com.sdsmdg.kd.trianglify.views.TrianglifyView;
import com.sdsmdg.kd.trianglify.models.Palette;
```

Java code for using TrianglifyView  

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
### 2.1 Attributes
| Property                    | Default values | Java method             | Attribute       | Description                                                                                                                   |
|-----------------------------|----------------|-------------------------|-----------------|-------------------------------------------------------------------------------------------------------------------------------|
| Grid Height                 | NA             | .setGridHeight(...)     | grid_height     | Height of the grid to generate                                                                                                |
| Grid Width                  | NA             | .setGridWidth(...)      | grid_width      | Width of the grid to generate                                                                                                 |
| X-axis Bleed                | 0 px         | .setBleedX(...)         | bleed_x         | TrianglifyView generates total area having width = gridWidth + 2*bleedX to avoid unfilled triangles at the edges of the view  |
| Y-Axis Bleed                | 0 px           | .setBleedY(...)         | bleed_y         | TrianglifyView generates total area having height = gridWidth + 2*bleedY to avoid unfilled triangles at the edges of the view |
| Variance                    | 10 px          | .setVariance(...)       | variance        | Displacement of points from original grid position to create triangles of different sizes                                     |
| Cell Size                   | 40 px          | .setCellSize(...)       | cell_size       | Size of cells of rectangular grid used to generated vertices of the triangles                                                 |
| Grid Type*                  | 0              | .setGridType(...)       | grid_type       | Type of grid 0 for Rectangular                                                                                                |
| Fill Triangles with color** | true           | .setFillTriangle(...)   | fill_triangles  | Fills the triangle generated with color chosen                                                                                |
| Draw strokes                | false          | .setDrawStrokes(...)    | draw_strokes    | Draws triangle's border with neighboring triangle's color                                                                     |
| Color Palette               | YlGn           | .setPalette(...)        | palette         | Set of existing colors to color triangles                                                                                     |
| Random Coloring             | false          | .setRandomColoring(...) | random_coloring | If random coloring is on triangles will be colored randomly instead of linear interpolation                                   |

*Current release contains only one GridType accessible with id `0`  
**Current release doesn't support custom color palette however a collection of 9 set of colors is available

<p>

### 2.2 Details of Bleed and Grid dimensions
* **Bleed:** Bleed defines the dimensions of extra size that TrianglifyView view generate so that triangles on the edge doesn't appear to be chopped off. In most of the cases `min{bleedX, bleedY} > cellSize` would ensure that the view is completely filled.  
* **Grid Dimensions:** `GridHeight` and `GridWidth` defines the dimensions of the visible area of the view.  

Total area generated by TrianglifyView is (`gridHeight` + `BleedY`) * (`gridWidth` + `BleedX`) while total area visible is (`gridHeight` + `BleedY`) * (`gridWidth` + `BleedX`)  

Following image demonstrates region covered by `gridHeight`, `gridWidth`, `bleedX` and `bleedY`
<img src="resources/default_pattern_explained.jpg" data-canonical-src="resources/default_pattern_explained.jpg" width="400" height="400" />

### 2.3 Generates
<img src="resources/default_pattern.jpg" data-canonical-src="resources/default_pattern.jpg" width="300" height="300" />

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
`TrianglifyView`'s `setPalette` method accepts `Palette` object. Palette can be set using one of the following method: 
* To use one of the predefined palette, for example **Spectral** use `trianglifyView.setPalette(Palette.getPalette(Palette.Spectral))`.
* To use palette referring to its index use `Palette.getPalette(<index>)`.  
* To use custom palette refer to section [2.6 Using custom Palettes](#26-using-custom-palettes).

> **Note:** Index used for addressing palette should be less than `Palette.DEFAULT_PALETTE_COUNT`, if index is greater than `DEFAULT_PALETTE_COUNT` `IllegalArgumentException` is thrown.

> **Note:** Palette enum defines total of 28 named palettes that can be used to generate views without specifying colors.

### 2.6 Using custom Palettes
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



## 3. Performance analysis
Few notes on performance of Trianglify
* Performance takes a serious hit with decrease in cell size. Time complexity of the algorithm to generate triangles from grid of points is Ω(n*log(n)). Decreasing cell size increases n (number of points on the grid). 
* Performance of coloring is faster on the use of random coloring rather than gradient.

## 4. UML diagrams
Complete UML diagram for the project structures are available as Draw.io link hosted in google drive 
| [Link](https://www.draw.io/?state=%7B%22ids%22:%5B%220Bz_2jvdEtUlrWlB0LXJvRnBQZ0U%22%5D,%22action%22:%22open%22,%22userId%22:%22109172653085429225560%22%7D)  
*(Note that you'll require a google account to access the file, if this is your first time then choose `open with Draw.io` option on top of the browser window. Then scroll to the center of the document to view diagrams)*

## 5. Credits
Development of Trianglify wouldn't have been possible without following libraries:
* [Delaunay Triangulator by jdiemke](https://github.com/jdiemke/delaunay-triangulator)

## 6. Contributing
Please see the [CONTRIBUTING](/CONTRIBUTING.md) file for information on contributing to the development of Trianglify.

# License
Trianglify is licensed under `MIT license`. View [license](LICENSE.md).
