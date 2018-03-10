package hello;

import org.springframework.data.annotation.Id;

public class Demand {

    @Id
    public String id;

    public String queryString;

    public Integer times;

    public Demand(){}

    public Demand(String queryString){
        this.queryString=queryString;
    }

    @Override
    public String toString() {
        return String.format(
                "Query string [queryString='%s'] number of searches :: "+times,
                queryString);
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
        this.times = times;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }
}
