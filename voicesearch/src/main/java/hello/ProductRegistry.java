package hello;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRegistry extends MongoRepository<Product, String> {
    public Product findByName(String name);
    public Product findByDescription(String description);
    public List<Product> findByDescriptionLike(String description);
}