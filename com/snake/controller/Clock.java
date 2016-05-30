package com.snake.controller;

/**
 * The {@code Clock} class is responsible for tracking the number of cycles
 * that have elapsed over time. 
 * @author Brendan Jones
 *
 */

public class Clock {
	/**
	 * The number of milliseconds that make up one cycle.
	 */
    private float millisPerCycle;

    /**
	 * The number of milliseconds that make up one cycle.
	 */
    private long lastUpdate;

    /**
	 * The number of cycles that have elapsed and have not yet been polled.
	 */
    private int elapsedCycles;

    /**
	 * The amount of excess time towards the next elapsed cycle.
	 */
    private float excessCycles;

	/**
	 * Whether or not the clock is paused.
	 */
    private boolean isPaused;

    /**
	 * Creates a new clock and sets it's cycles-per-second.
	 * @param cyclesPerSecond The number of cycles that elapse per second.
	 */
    public Clock(float cyclesPerSecond) {
        setCyclesPerSecond(cyclesPerSecond);
        reset();
    }

    /**
	 * Sets the number of cycles that elapse per second.
	 * @param cyclesPerSecond The number of cycles per second.
	 */
    public void setCyclesPerSecond(float cyclesPerSecond) {
        this.millisPerCycle = (1.0f / cyclesPerSecond) * 1000;
    }

    /**
	 * Resets the clock stats. Elapsed cycles and cycle excess will be reset
	 * to 0, the last update time will be reset to the current time, and the
	 * paused flag will be set to false.
	 */
    public void reset() {
        this.elapsedCycles = 0;
        this.excessCycles = 0.0f;
        this.lastUpdate = getCurrentTime();
        this.isPaused = false;
    }

    /**
	 * Updates the clock stats. The number of elapsed cycles, as well as the
	 * cycle excess will be calculated only if the clock is not paused. This
	 * method should be called every frame even when paused.
	 */
    public void update() {
        //Get the current time and calculate the delta time.
        long currUpdate = getCurrentTime();
        float delta = (float)(currUpdate - lastUpdate) + excessCycles;

        //Update the number of elapsed and excess ticks if we're not paused.
        if(!isPaused) {
            this.elapsedCycles += (int)Math.floor(delta / millisPerCycle);
            this.excessCycles = delta % millisPerCycle;
        }

        //Set the last update time for the next update cycle.
        this.lastUpdate = currUpdate;
    }

    /**
	 * Pauses or unpauses the clock. While paused, a clock will not update
	 * elapsed cycles or cycle excess.
	 */
    public void setPaused(boolean paused) {
        this.isPaused = paused;
    }

    /**
	 * Checks to see if a cycle has elapsed for this clock yet. If so,
	 * the number of elapsed cycles will be decremented by one.
	 * @return Whether or not a cycle has elapsed.
	 */
    public boolean hasElapsedCycle() {
        if(elapsedCycles > 0) {
            this.elapsedCycles--;
            return true;
        }
        return false;
    }

    /**
	 * Calculates the current time in milliseconds using the computer's high
	 * resolution clock.
	 * @return The current time in milliseconds.
	 */
    private static final long getCurrentTime() {
        return (System.nanoTime() / 1000000L);
    }

}
