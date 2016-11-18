package com.epam.demo.batch.reader;

import com.epam.demo.DTO.MidBeanDTO;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zmazula on 11/11/16.
 */
@Component
public class MidServiceRestItemReader implements ItemReader<MidBeanDTO> {

    private String apiUrl;

    @Value("${mid-service-rest-path}")
    private String midServiceRestPath;

    @Value("${mid-service-name}")
    private String midServiceName;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    private int nextElementIndex;
    private List<MidBeanDTO> midBeanData;

    public MidServiceRestItemReader() {
        nextElementIndex = 0;
    }

    @Override
    public MidBeanDTO read() throws Exception {
        initializeApiUrl();
        if (DataIsNotInitialized()) {
            midBeanData = fetchDataFromAPI();
        }

        MidBeanDTO nextElement = null;

        if (nextElementIndex < midBeanData.size()) {
            nextElement = midBeanData.get(nextElementIndex);
            nextElementIndex++;
        }

        return nextElement;
    }

    private void initializeApiUrl() {
        if (apiUrl==null) {
            this.apiUrl = discoveryClient.getInstances(midServiceName).get(0).getUri().toString().concat(midServiceRestPath);
        }
    }

    private boolean DataIsNotInitialized() {
        return this.midBeanData == null;
    }

    private List<MidBeanDTO> fetchDataFromAPI() {
        ResponseEntity<MidBeanDTO[]> response = restTemplate.getForEntity(
                apiUrl,
                MidBeanDTO[].class
        );
        MidBeanDTO[] midBeanData = response.getBody();
        return Arrays.asList(midBeanData);
    }
}
