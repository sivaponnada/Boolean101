package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRegistry registry;

    @Autowired
    private DemandRegistry demandRegistry;

    @MessageMapping("/products")
    @SendTo("/topic/products")
    public ProductInfo findProduct(Product product) throws Exception {

        Product p = null;

        List<Product> productlist = new ArrayList<>();

        System.out.println(product.getName());

        p = registry.findByName(product.getName().trim().toLowerCase());
        productlist = registry.findByDescriptionLike(product.getName().trim().toLowerCase());

        System.out.println(p);

        if (p == null) {
            p = registry.findByDescription(product.getName().trim().toLowerCase());
            System.out.println(p);
        }
        StringBuffer productSuggestions = new StringBuffer("</br>");
//        if(0==productlist.size()){
//            productSuggestions.append("There are no product suggestions for, ");
//            productSuggestions.append(product.getName());
//
//        }else
        if(productlist.size()==1 && p != null){
            return new ProductInfo("Product you requested, " + p.getName() + " description:  " + p.getDescription() + " is available!");
        }else if(productlist.size()>1){
            productSuggestions.append("Also did you mean: </br>");
            for (Product product1:productlist ) {
                if(!product1.getName().equalsIgnoreCase(product.getName())){
                    productSuggestions.append(product1.name);
                    productSuggestions.append(" &nbsp (");
                    productSuggestions.append(product1.description);
                    productSuggestions.append(")");
                    productSuggestions.append("</br>");
                }
           }

        }

        if (null == p) {
            Demand demand = demandRegistry.findByQueryString(product.getName().trim().toLowerCase());

            if(null==demand || null == demand.id ) {
                demand = new Demand();
                demand.setQueryString(product.getName().trim().toLowerCase());
                demand.setTimes(1);
                demandRegistry.insert(demand);
            }else{
                int times = demand.getTimes();
                ++times;
                demand.setTimes(times);
                demandRegistry.save(demand);
            }
            return new ProductInfo("Sorry we couldn't find, " + product.getName() + " we will try to include it in our products list !" + "</br>"+productSuggestions);
        }
        return new ProductInfo("Product you requested, " + p.getName() + " (" + p.getDescription() + ")"+" is available!" + "</br>"+productSuggestions);

    }

}
