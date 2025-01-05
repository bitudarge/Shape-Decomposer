
/**
 * Name:Bitanya Darge
 * Date: 4/30/2024
 * CSC 202
 * Project 3-DecomposableShape.java
 * 
 * This class stores a list x/y coordinates that, when connected end 
 * to end form a shape. The least important nodes can sequentially
 * be removed from the shape and restored to the list of x/y
 * coordinates. 
 * 
 * Citations of Assistance (who and what OR declare no assistance):
 * I received assistance from Diane Mueller on the constructor of decomposableshape 
 * 
 * 
 */

import java.util.Scanner;
import java.awt.Polygon;

public class DecomposableShape {
	// Instance Variables
	private int initialNumPoints;
	private int currentNumPoints;
	private PointNode front;
	private StackADT<PointNode> stack;

	/**
	 * It creates a node that is used by the constructor
	 * 
	 * @param input - the Scanner input connected to a data file of points
	 * @return NewNode - the node that was created
	 */

	private PointNode createNewNode(Scanner input) {
		String[] data = input.nextLine().strip().split(",");
		int x = Integer.parseInt(data[0]);
		int y = Integer.parseInt(data[1]);
		PointNode newNode = new PointNode(x, y);
		return newNode;
	}

	/**
	 * constructs a decomposable shape with initial , initial number of points,
	 * current number of points, front node, and stack.
	 * 
	 * @param - input - the Scanner input connected to a data file of points
	 */
	public DecomposableShape(Scanner input) {
		this.initialNumPoints = 0;
		this.currentNumPoints = 0;
		this.front = null;
		this.stack = new ArrayStack<>();

		PointNode current = front;
		PointNode firstNode = createNewNode(input);
		front = firstNode;
		firstNode.prev = front;
		current = front;
		initialNumPoints++;
		currentNumPoints = initialNumPoints;
		while (input.hasNextLine()) {
			PointNode newNode = createNewNode(input);
			current.next = newNode;
			newNode.prev = current;
			current = newNode;

			initialNumPoints++;
			currentNumPoints = initialNumPoints;
		}
		current.next = front;
		front.prev = current;
		calculateAllImportance();
	}

	// Calculates the importance of each node in the shape.
	private void calculateAllImportance() {
		PointNode current = front;
		do {
			current.calculateImportance();
			current = current.next;
		} while (current != front);
	}

	/**
	 * Converts the decomposable shape to a Polygon object.
	 * 
	 * @return polygon - object representing the shape
	 */
	public Polygon toPolygon() {
		int[] xpoints = new int[currentNumPoints];
		int[] ypoints = new int[currentNumPoints];
		PointNode currentNode = front;
		for (int i = 0; i < this.currentNumPoints; i++) {
			xpoints[i] = currentNode.x;
			ypoints[i] = currentNode.y;
			currentNode = currentNode.next;
		}
		return new Polygon(xpoints, ypoints, currentNumPoints);
	}

	/**
	 * Adjusts the size of the shape to the specified target percentage of the
	 * initial number of points. Removes or restores nodes as necessary.
	 * 
	 * @param target - the target percentage of the initial number of points
	 */
	public void setToSize(int target) {
		int targetPoints = (int) Math.round(target / 100.0 * initialNumPoints);
		int difference = currentNumPoints - targetPoints;

		if (difference > 0) {
			for (int i = 0; i < difference; i++) {
				findAndRemoveLeastImportant(targetPoints);
			}
		} else if (difference < 0) {
			while (currentNumPoints != targetPoints) {
				PointNode newNode = stack.pop();
				newNode.prev.next = newNode;
				newNode.next.prev = newNode;
				newNode.prev.calculateImportance();
				newNode.next.calculateImportance();
				currentNumPoints++;
			}
		}
	}

	// Finds and removes the least important node from the shape.
	private void findAndRemoveLeastImportant(int targetPoints) {
		while (currentNumPoints != targetPoints) {
			PointNode current = front;
			PointNode leastImportant = current;

			for (int i = 0; i < currentNumPoints; i++) {
				if (current.importance < leastImportant.importance) {
					leastImportant = current;
				}
				current = current.next;
			}
			stack.push(leastImportant);
			currentNumPoints--;
			if (leastImportant == front) {
				front = front.next;
			}

			leastImportant.prev.next = leastImportant.next;
			leastImportant.next.prev = leastImportant.prev;
			leastImportant.next.calculateImportance();
			leastImportant.prev.calculateImportance();
		}
	}

	@Override
	public String toString() {
		String result = "";
		PointNode current = front;
		do {
			result += current.toString() + "\n";
			current = current.next;
		} while (current.next != front);
		result += current.toString() + "";
		return result;
	}

	private static class PointNode {
		// Instance variables
		private int x;
		private int y;
		private double importance;
		private PointNode prev;
		private PointNode next;

		/**
		 * Constructs a PointNode with the given x and y coordinates.
		 * 
		 * @param x the x-coordinate
		 * @param y the y-coordinate
		 */
		public PointNode(int x, int y) {
			this.x = x;
			this.y = y;
			importance = 0;
			prev = null;
			next = null;
		}

		/**
		 * Calculates the importance of the point based on its neighbors.
		 */
		public void calculateImportance() {
			double LP = Math.sqrt(Math.pow(prev.x - x, 2) + Math.pow(prev.y - y, 2));
			double PR = Math.sqrt(Math.pow(next.x - x, 2) + Math.pow(next.y - y, 2));
			double LR = Math.sqrt(Math.pow(next.x - prev.x, 2) + Math.pow(next.y - prev.y, 2));
			importance = LP + PR - LR;
		}

		@Override
		public String toString() {
			return "x = " + x + ", y = " + y + ", importance = " + importance;
		}
	}
}
