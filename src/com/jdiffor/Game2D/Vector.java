package com.jdiffor.Game2D;

public class Vector {

	private final static double rootTwo = Math.sqrt(2);
	
	private int x;
	private int y;
	
	public Vector() {
		this(0, 0);
	}
	
	public Vector(int z) {
		this(z, z);
	}
	
	public Vector(int x, int y) {
		this.x = x;
		this.y = y;
	}	
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}	
	
	public void multiply(int scale) {
		this.x *= scale;
		this.y *= scale;
	}
	
	public void multiply(double scale) {
		this.x *= scale;
		this.y *= scale;
	}
	
	public void add(Vector other) {
		this.x += other.x;
		this.y += other.y;
	}
	
	public Vector add(Vector one, Vector two) {
		return new Vector(one.x + two.x, one.y + two.y);
	}
	
	public Vector getMultiplied(int scale) {
		return new Vector(this.x * scale, this.y * scale);
	}
	
	public Vector getMultiplied(double scale) {
		return new Vector((int) (this.x * scale), (int) (this.y * scale));
	}
	
	public Vector getEachComponentMultiplied(Vector other) {
		return new Vector(this.x * other.x, this.y * other.y);
	}
	
	public void multiplyEachComponent(Vector other) {
		this.x *= other.x;
		this.y *= other.y;
	}
	
	public void clear() {
		this.x = 0;
		this.y = 0;
	}
	
	public int getLengthInt() {
		return (int) Math.sqrt(this.x * this.x + this.y + this.y);
	}
	
	public double getLengthDouble() {
		return Math.sqrt(this.x * this.x + this.y + this.y);
	}
	
	public double getLength() {
		return this.getLengthDouble();
	}
	
	public static Vector getEdgeLengthFromHypotenuse(int hypotenuse) {
		return new Vector((int) (hypotenuse / rootTwo));
	}
	
	
}
