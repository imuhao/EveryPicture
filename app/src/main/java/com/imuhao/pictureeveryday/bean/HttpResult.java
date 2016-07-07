package com.imuhao.pictureeveryday.bean;

/**
 * @author Smile
 * @time 2016/6/24  11:43
 * @desc ${TODD}
 */
public class HttpResult<T> {

    private boolean error;
    private T results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    public String toString() {
        return "HttpResult{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
