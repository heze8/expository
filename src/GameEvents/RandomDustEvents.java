package GameEvents;

import java.util.concurrent.ThreadLocalRandom;

import ExpositoryConstant.ExpositoryConstant;

public class RandomDustEvents implements ExpositoryConstant {
	
	public static void rngLoot() {
		int rand = ThreadLocalRandom.current().nextInt(1, 101);
		
		Ores 50%
		Copper 15%
		cloth 10%
		Glass shards 15%
		Metal alloy 2%
		Also some possibility of finding blueprint 8%
		Will unlock various options to build things within the spaceship

	}
}
