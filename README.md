# Android Arc-Graph

# Demo

![](https://cloud.githubusercontent.com/assets/6572536/25851824/38de7666-3502-11e7-938d-88222c7818e3.gif)

[Download Demo](https://github.com/classtinginc/arc-graph/releases/download/0.1.0/arcgraph-sample.apk)
# Usage

## Step 1

#### Gradle
```groovy
dependencies {
    compile 'com.github.classtinginc:arc-graph:0.1.1'
}
```

## Step 2

```xml
<com.classting.arcgraph.ArcGraphView
    android:id="@+id/graph"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:graph_stroke_width="5dp"
    app:graph_gap_angle="5"
    app:point_radius="5dp"
    app:point_stroke_width="4dp"
    app:point_score="0"
    app:section1_color="@color/color1"
    app:section2_color="@color/color2"
    app:section3_color="@color/color3"
    app:section4_color="@color/color4"/>
```
or

```kotlin
graph.setScore(100)
graph.setGraphStrokeWidth(5)
graph.setGraphGapAngle(5)
graph.setSectionColors(color1, color2, color3, color4)
graph.setPointerRadius(4)
graph.setPointerStrokeWidth(4)

//you can get view of pointer for animation
graph.getPointerView()
```