package it.unibo.javadyno.model.dyno.real.api;

import it.unibo.javadyno.model.dyno.api.Dyno;

/**
 * Real Dyno Interface.
 * This interface extends the Dyno interface and adds a method to send configuration
 * to the MCU that manages the real dynamometer.
 */
public interface RealDyno extends Dyno {

    /**
     * Sends configuration to the MCU.
     *
     * @param config the configuration details to send in JSON format.
     */
    void sendConfiguration(String config);
}
