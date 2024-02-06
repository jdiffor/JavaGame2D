package com.jdiffor.Game2D;

import java.net.URI;
import java.net.URISyntaxException;

public class AssetManager {
	
	public AssetManager() {
		
	}
	
	public static URI loadResource(String location) {
		AssetManager am = new AssetManager();
		try {
			return am.getClass().getClassLoader().getResource(location).toURI();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			System.err.println("Location: " + location);
			return null;
		}
	}
	
	public static String map(String name) {
		return "maps/" + name + ".mmak";
	}
	
	public static String mapImage(String name) {
		return "maps/" + name + ".png";
	}
	
	public static String characterImage(String name) {
		return "sprites/character/" + name;
	}
	
	public static String groundImage(String name) {
		return "sprites/ground/" + name;
	}
	
	public static String inventoryItemImage(String name) {
		return "sprites/inventoryItems/" + name;
	}
	
	public static String mapItemImage(String name) {
		return "sprites/mapItems/" + name;
	}
	
}
