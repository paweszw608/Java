package pl.polsl.lab.worldcupstatsserver.models;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class representing my own container used to store data. Class implements
 * Iterable and Interator to manage container index changes.
 *
 * @see java.util.Iterator
 * @see java.util.ArrayList
 *
 * @author Paweł Szwajnoch
 * @version 1.1
 */
class MyContainer<T> implements Iterable<T>, Iterator<T> {

    /**
     * ArrayList reference
     */
    ArrayList<T> contents;
    /**
     * Index that helps with moving through container
     */
    private int index = 0;

    MyContainer() {
        contents = new ArrayList<>();
    }

    /**
     * Adds new element to container
     *
     * @param elem element to add
     */
    public void add(T elem) {
        contents.add(elem);
    }

    /**
     * Returns element from passed index
     *
     * @param index index of element that should be returned
     * @return element from passed index
     */
    public T get(int index) {
        return contents.get(index);
    }

    /**
     * Returns container iterator
     *
     * @return container iterator
     */
    @Override
    public Iterator<T> iterator() {
        index = 0;
        return this;
    }

    /**
     * Checks if there is next element in the container
     *
     * @return true if if there is next element in the container
     */
    @Override
    public boolean hasNext() {
        return (index < contents.size());
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     */
    @Override
    public T next() {
        return contents.get(index++);
    }

    /**
     * Removes (deletes) element from the cointainer
     */
    @Override
    public void remove() {
        contents.remove(index);
        index--;
    }

}
