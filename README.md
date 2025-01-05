# Shape-Decomposer
# Shape Decomposer

## Description
A Java program that visualizes and decomposes 2D polygons by removing less important points. The shape is represented as a circular doubly linked list, enabling efficient manipulation and rendering.

## Features
- Decompose shapes by removing points based on calculated importance:
  \[
  \text{Importance} = LP + PR - LR
  \]
- Restore points dynamically using a slider.
- Visualize shape transformations with sample datasets.

## Core Components
- **PointNode Class**: Represents a point with coordinates, importance, and linked neighbors.
- **DecomposableShape Class**: Manages the polygon, supports decomposition, and interacts with the stack for restoration.
- **DecomposerFrame Class**: Provides the GUI for visualization and user interaction.

## Usage
1. Load a point file (e.g., `shape.txt`).
2. Use the slider to adjust the number of points in the shape.
3. Observe the polygon’s simplification and restoration in real time.

## Example
Input: `shape.txt`  
Output: Polygon with reduced points based on importance values.  

## Acknowledgments
I received assistance from Diane Mueller on the constructor of decomposableshape  
