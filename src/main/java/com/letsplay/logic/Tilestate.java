package com.letsplay.logic;

import java.io.Serializable;

public class Tilestate	implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -511585086480155323L;
	private final String letter;
	private final int weight;
	public static final int A = 1;
	public static final int B = 3;
	public static final int C = 3;
	public static final int D = 2;
	public static final int E = 1;
	public static final int F = 4;
	public static final int G = 2;
	public static final int H = 4;
	public static final int I = 1;
	public static final int J = 8;
	public static final int K = 5;
	public static final int L = 1;
	public static final int M = 3;
	public static final int N = 1;
	public static final int O = 1;
	public static final int P = 3;
	public static final int Q = 10;
	public static final int R = 1;
	public static final int S = 1;
	public static final int T = 1;
	public static final int U = 1;
	public static final int V = 4;
	public static final int W = 4;
	public static final int X = 8;
	public static final int Y = 4;
	public static final int Z = 10;
	public static final int BLANK = 0;
	
	
	public Tilestate(String letter, int weight){
		
		this.letter = letter;
		this.weight = weight;
	}
	
	public String getLetter(){
		return this.letter;
	}
	
	public int getWeight(){
		return this.weight;
	}
	
	public String toString() {
		return "letter = " + this.letter + " and weight = " + this.weight; 
	}
	
}
