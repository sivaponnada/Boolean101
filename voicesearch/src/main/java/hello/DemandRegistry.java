package hello;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@Document(collection="demand")
public interface DemandRegistry extends MongoRepository<Demand, String > {

    public Demand findByQueryString(String name);
    public List<Demand> findAllByOrderByTimesDesc();
}
