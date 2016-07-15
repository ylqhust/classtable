package com.ylq.library.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 16/7/11.
 */
public class Stack<T> {
    private List<T> mStack;

    public Stack() {
        mStack = new ArrayList<T>();
    }

    public void push(T t) {
        synchronized (Stack.class) {
            mStack.add(t);
        }
    }

    public T pop() {
        synchronized (Stack.class) {
            if (mStack.size() == 0)
                return null;
            return mStack.remove(mStack.size() - 1);
        }
    }

    public boolean isEmpty() {
        synchronized (Stack.class) {
            if (mStack.size() == 0)
                return true;
            return false;
        }
    }

    public T top() {
        synchronized (Stack.class) {
            if (mStack.size() == 0)
                return null;
            return mStack.get(mStack.size() - 1);
        }
    }

    public int size() {
        synchronized (Stack.class) {
            return mStack.size();
        }
    }
}
