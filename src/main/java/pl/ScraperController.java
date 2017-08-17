package pl;

import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

/**
 * Created by maciek on 8/16/17.
 */

@RestController
public class ScraperController {

    private final WebScraperService webScraperService;
    private final AnalyzeDataService analyzeDataService;

    @Autowired
    public ScraperController(WebScraperService webScraperService, AnalyzeDataService analyzeDataService) {
        this.webScraperService = webScraperService;
        this.analyzeDataService = analyzeDataService;
    }


    @PostMapping("/fetchwebsite")
    public Map<String, Integer> fetchWebsite(@RequestParam String url) throws IOException {
            Document website = webScraperService.getWebsite(url);
            String websiteBodyInString = webScraperService.parseHtmlDataToString(website);
        Map<String, Integer> stringIntegerMap = analyzeDataService.dataPreparation(websiteBodyInString);
        return stringIntegerMap;
    }


}
