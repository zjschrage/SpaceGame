package game.utils;

public interface Listener<T> {

    public void update(T oldInfo, T newInfo);
    public void destroy();

}
