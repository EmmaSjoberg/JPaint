package se.miun.emsj2403.dt187g.jpaint.gui;

@FunctionalInterface
public interface OnChangeListener<T> {

    void onChange(T object);
}