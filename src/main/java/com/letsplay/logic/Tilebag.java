package com.letsplay.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;
import java.util.Random;

import com.letsplay.ui.GameTile;
import com.letsplay.ui.GameTileBuilder;
import com.letsplay.utils.EmptyTileBagException;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.AbstractComponent;

@SpringComponent
public class Tilebag implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5288053459622593380L;
/*	private ArrayList<Tilestate> stateContainer = new ArrayList<Tilestate>(100);*/
	private ArrayList<Tilestate> stateContainer = new ArrayList<Tilestate>(23);
	private Random random;
	private int limit;
	
	public Tilebag() {
		this.random = new Random();
		/*this.limit = 100;
		HashMap<Tilestate, Integer> distribution = new HashMap<Tilestate, Integer>(100);
		distribution.put(new Tilestate("A", Tilestate.A), 9);
		distribution.put(new Tilestate("B", Tilestate.B), 2);
		distribution.put(new Tilestate("C", Tilestate.C), 2);
		distribution.put(new Tilestate("D", Tilestate.D), 4);
		distribution.put(new Tilestate("E", Tilestate.E), 12);
		distribution.put(new Tilestate("F", Tilestate.F), 2);
		distribution.put(new Tilestate("G", Tilestate.G), 3);
		distribution.put(new Tilestate("H", Tilestate.H), 2);
		distribution.put(new Tilestate("I", Tilestate.I), 9);
		distribution.put(new Tilestate("J", Tilestate.J), 1);
		distribution.put(new Tilestate("K", Tilestate.K), 1);
		distribution.put(new Tilestate("L", Tilestate.L), 4);
		distribution.put(new Tilestate("M", Tilestate.M), 2);
		distribution.put(new Tilestate("N", Tilestate.N), 6);
		distribution.put(new Tilestate("O", Tilestate.O), 8);
		distribution.put(new Tilestate("P", Tilestate.P), 2);
		distribution.put(new Tilestate("Q", Tilestate.Q), 1);
		distribution.put(new Tilestate("R", Tilestate.R), 6);
		distribution.put(new Tilestate("S", Tilestate.S), 4);
		distribution.put(new Tilestate("T", Tilestate.T), 6);
		distribution.put(new Tilestate("U", Tilestate.U), 4);
		distribution.put(new Tilestate("V", Tilestate.V), 2);
		distribution.put(new Tilestate("W", Tilestate.W), 2);
		distribution.put(new Tilestate("X", Tilestate.X), 1);
		distribution.put(new Tilestate("Y", Tilestate.Y), 2);
		distribution.put(new Tilestate("Z", Tilestate.Z), 1);
		distribution.put(new Tilestate("blank", Tilestate.BLANK), 2);
		*/
		this.limit = 23;
		HashMap<Tilestate, Integer> distribution = new HashMap<Tilestate, Integer>(23);
		distribution.put(new Tilestate("A", Tilestate.A), 3);
		distribution.put(new Tilestate("B", Tilestate.B), 2);
		distribution.put(new Tilestate("C", Tilestate.C), 2);
		distribution.put(new Tilestate("D", Tilestate.D), 1);
		distribution.put(new Tilestate("E", Tilestate.E), 2);
		distribution.put(new Tilestate("F", Tilestate.F), 2);
		distribution.put(new Tilestate("G", Tilestate.G), 1);
		distribution.put(new Tilestate("H", Tilestate.H), 1);
		distribution.put(new Tilestate("I", Tilestate.I), 1);
		distribution.put(new Tilestate("K", Tilestate.K), 1);
		distribution.put(new Tilestate("L", Tilestate.L), 1);
		distribution.put(new Tilestate("M", Tilestate.M), 1);
		distribution.put(new Tilestate("N", Tilestate.N), 1);
		distribution.put(new Tilestate("O", Tilestate.O), 1);
		distribution.put(new Tilestate("P", Tilestate.P), 1);
		distribution.put(new Tilestate("R", Tilestate.R), 1);
		distribution.put(new Tilestate("S", Tilestate.S), 1);

		Iterator<Tilestate> keyIterator = distribution.keySet().iterator();
		
		while (keyIterator.hasNext()) {
			Tilestate tileState = keyIterator.next();
			int limit = distribution.get(tileState);
			
			for (int counter = 0; counter < limit; counter++) {
				this.stateContainer.add(tileState);
			}
			
		}
		
	}
	
	
	public GameTile getTile() throws EmptyTileBagException {
		if (this.stateContainer.size() != 0) {
			int temp = this.random.nextInt(this.limit);
			Tilestate tileState = this.stateContainer.remove(temp);
			this.limit = this.limit - 1;
			return GameTileBuilder.get().setWeight(tileState).build();
		} else {
			throw new EmptyTileBagException("Empty Tilebag");
		}
	}
	
	public int tilesRemaining() {
		return this.stateContainer.size();
	}
	
	public void returnTile(Optional<AbstractComponent> gameTile) {
		GameTile Tile = null;
		if(gameTile.isPresent())
			Tile = (GameTile) gameTile.get();
		this.stateContainer.add(Tile.getTileState());
		this.limit++;
	}
	
	public String toString() {
		return this.stateContainer.toString();
	}
	
	public boolean isTileBagEmpty() {
		return this.stateContainer.isEmpty();
	}
	
}
