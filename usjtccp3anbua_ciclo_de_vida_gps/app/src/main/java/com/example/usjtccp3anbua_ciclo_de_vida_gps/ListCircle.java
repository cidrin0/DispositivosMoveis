package com.example.usjtccp3anbua_ciclo_de_vida_gps;

import java.util.ArrayList;
import java.util.List;

public class ListCircle<T> {

    private int size;

    private List<T> list;
    private int index = 0;

    public ListCircle(int size) {
        this.size = size;
        this.list = new ArrayList<>();
    }

    public void add(T element) {
        updateIndex();
        try {
            list.remove(index);
            list.set(index, element);
        } catch (IndexOutOfBoundsException e) {
            list.add(element);
        }
    }

    public List<T> getList() {
        return list;
    }

    private void updateIndex() {
        index = index % size + 1;
    }
}