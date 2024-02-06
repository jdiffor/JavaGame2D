package com.jdiffor.Game2D;

public class WinManager {

	private long startTime;
	private long endTime;
	
	public WinManager() {
		this.startTime = Utils.time();
	}
	
	public void finish() {
		if(this.endTime < this.startTime) {
			this.endTime = Utils.time();
		}
	}
	
	public int secondsPlayed() {
		return (int) ((this.endTime - this.startTime) / 1000000000);
	}
	
	public boolean isDone() {
		return this.endTime > this.startTime;
	}
	
}
