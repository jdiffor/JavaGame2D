package com.jdiffor.Game2D;

public enum Direction {
	UP(new Vector(0, -1)),
	LEFT(new Vector(-1, 0)),
	DOWN(new Vector(0, 1)),
	RIGHT(new Vector(1, 0));
	
	Vector vector;
	
	Direction (Vector vector) {
		this.vector = vector;
	}
}
