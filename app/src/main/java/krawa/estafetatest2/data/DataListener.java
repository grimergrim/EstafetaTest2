package krawa.estafetatest2.data;

public interface DataListener<T> {
    void onResult(T result);
    void onFailure(String error);
}
