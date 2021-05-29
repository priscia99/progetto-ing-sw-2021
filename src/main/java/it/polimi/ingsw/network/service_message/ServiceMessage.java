package it.polimi.ingsw.network.service_message;

import it.polimi.ingsw.client.Client;

import java.io.Serializable;

public interface ServiceMessage<T> {

    void execute(T target);
}
