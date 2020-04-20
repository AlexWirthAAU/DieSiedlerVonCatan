package com.example.diesiedler.presenter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SecureObjectStream extends ObjectInputStream {

    private List<Class<?>> allowed = new ArrayList<>();

    public SecureObjectStream(InputStream inputStream) throws IOException {
        super(inputStream);
        allowed.add(ArrayList.class);
        allowed.add(HashMap.class);
        allowed.add(String.class);
        allowed.add(Integer.class);
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass osc) throws IOException, ClassNotFoundException {
        // Only deserialize instances of AllowedClass
        for (Class<?> c : allowed) {
            if (osc.getName().equals(c.getName())) {
                return super.resolveClass(osc);
            }
        }

        throw new InvalidClassException("Unauthorized deserialization", osc.getName());
    }
}
