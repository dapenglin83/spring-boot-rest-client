package test.springboot.restclient.data;

public class SGXResponse<T> {

    Meta meta;
    T data;

    public Meta getMeta() {
        return meta;
    }

    public T getData() {
        return data;
    }
}
