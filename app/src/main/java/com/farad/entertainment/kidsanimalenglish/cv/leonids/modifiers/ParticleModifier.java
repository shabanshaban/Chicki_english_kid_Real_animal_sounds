package com.farad.entertainment.kidsanimalenglish.cv.leonids.modifiers;


import com.farad.entertainment.kidsanimalenglish.cv.leonids.Particle;

public interface ParticleModifier {

	/**
	 * modifies the specific value of a particle given the current miliseconds
	 * @param particle
	 * @param miliseconds
	 */
	void apply(Particle particle, long miliseconds);

}
