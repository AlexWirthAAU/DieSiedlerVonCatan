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
 * Class to make Deserialisation of Object send from Server more save.
 * The Deserialisation should only be carried out, when the Object is
 * an Instance of an allowed Class.
 */
public class SecureObjectInputStream extends ObjectInputStream {

    private List<Class<?>> allowed = new ArrayList<>(); // List of allowed Classes

    /**
     * Constructor - List of allowed Classes is filled.
     *
     * @param inputStream InputStream of the Socket
     * @throws IOException when the super-Constructor cannot read the Stream
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
     * Is the Classname of the Object part of the List of allowed Classes,
     * the Class is resolved and the Object can be serialised.
     * Else an InvalicClassException is thrown.
     *
     * @param osc Instanc of the Class ObjectStream
     * @return Object of Class Class, correspondending to osc
     * @throws IOException on standard IO-Issues
     * @throws ClassNotFoundException when the Class of the deseralised Object cannot be found
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
