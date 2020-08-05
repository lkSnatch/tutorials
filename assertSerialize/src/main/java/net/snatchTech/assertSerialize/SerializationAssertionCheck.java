package net.snatchTech.assertSerialize;

import java.io.Serializable;
import java.util.Objects;

public class SerializationAssertionCheck implements Serializable {

    //private static final long serialVersionUID = 7171967375950265774L;
    private int someNumber = 5;

    private void check() {
        assert someNumber == 5;
    }

    public int getSomeNumber() {
        return someNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        SerializationAssertionCheck that = (SerializationAssertionCheck) o;
        return getSomeNumber() == that.getSomeNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSomeNumber());
    }
}
