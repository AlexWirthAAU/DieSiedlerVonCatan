package com.example.diesiedler.presenter;

import com.example.catangame.GameSession;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Christina Senger
 * <p>
 * Klasse, um die Deserialisierung von Objecten vom Server sicherer zumachen.
 * Die Deserialisierung soll nur durchgeführt werden, wenn das Objekt von einer
 * erlaubten Klasse ist.
 */
public class SecureObjectInputStream extends ObjectInputStream {

    private List<Class<?>> allowed = new ArrayList<>();

    /**
     * Kostruktor - Liste der erlaubten Klassen wird befüllt
     *
     * @param inputStream InputStream vom Socket
     * @throws IOException wenn der super-Konstruktor den InputStream nicht lesen kann
     */
    public SecureObjectInputStream(InputStream inputStream) throws IOException {
        super(inputStream);
        allowed.add(GameSession.class);
        allowed.add(ArrayList.class);
        allowed.add(String.class);
        allowed.add(Number.class);
        allowed.add(Integer.class);
    }

    /**
     * Ist der Klassenname des Objekt in der Liste der erlaubten Klasse,
     * wird die Klasse aufgelöst und das Objekt kann deserialisiert werden.
     * Ansonsten wird eine InvalicClassException geworfen.
     *
     * @param osc Instanz der Klasse ObjectStream
     * @return Objekt der Klasse Class passend zu osc
     * @throws IOException bei üblichen IO-Problemen
     * @throws ClassNotFoundException wenn die Klasse des serialisierten Objekt nicht gefunden werden kann
     *
     */
    @Override
    protected Class<?> resolveClass(ObjectStreamClass osc) throws IOException, ClassNotFoundException {

        for (Class<?> c : allowed) {
            if (osc.getName().equals(c.getName())) {
                return super.resolveClass(osc);
            }
        }

        throw new InvalidClassException("Unauthorized deserialization", osc.getName());
    }
}
