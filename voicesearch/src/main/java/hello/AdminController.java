package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private DemandRegistry  demandRegistry;

    @MessageMapping("/queries")
    @SendTo("/topic/queries")
    public QueryInfo  getDemands() throws Exception {

        System.out.print("############################# Intercepted  ###########################");

        List<Demand> demands = demandRegistry.findAll();

        StringBuffer sb = new StringBuffer("");
        if(null==demands || demands.size() ==0){
            sb.append(" No queries");
        }else {
            for (Demand d : demands) {
                sb.append(d.getQueryString()+" searched ("+d.getTimes()  + ")<br>");
            }
        }

        return  new QueryInfo(sb.toString());

    }

}
