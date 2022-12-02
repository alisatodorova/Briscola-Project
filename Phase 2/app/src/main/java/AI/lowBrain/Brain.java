package AI.lowBrain;

import application.Card;
/**
 * Interface to have a unified structure on all Brains.
 * Implemented on all Brains beside the BigBrainPruning
 * */
public interface Brain {
    /**
     * This Method is allowing us to change the 'Brain' which is our term for the classification of the agents.
     * @param whereAmI the State wiht the Agents hand and the
     * @param onFloor if there has been a card played already this is passed here otherwise a null object can suffice otherwise.
     * @return the index of the card chosen by the Agent to be played.*/
    public int whatToPlay(State whereAmI, Card onFloor);
}
