package demo.webflux.data;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component("UriWebRest")
public class UriWebRest {


    @Value("${rest.scheme}")
    public String domainSchame;

    @Value("${rest.ip}")
    public String servDomainIp;

    @Value("${rest.port}")
    public String servDomainPort;

    @Value("${rest.url}")
    public String endPoint;

    public String getEndPoint(Integer id) {

       // Map<String, String> urlParams = new HashMap<>();
       // urlParams.put("page", id.toString());

        UriComponents uri = UriComponentsBuilder.newInstance()
//                .scheme(domainSchame).host(servDomainIp + ":" + servDomainPort)
                .scheme(domainSchame)
                .host(servDomainIp)
                .path(endPoint)
                //
                .queryParam("page", id.toString())
                .build()
                ;

        return uri.toUriString();

    }    
    
}
