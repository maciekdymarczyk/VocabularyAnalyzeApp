package pl.datapreparation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.translator.TranslationService;
import pl.translator.Word;
import pl.translator.WordRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by maciek on 8/16/17.
 */
@Service
public class AnalyzeDataService {

    private final WordRepository wordRepository;

    @Autowired
    public AnalyzeDataService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }
    
    public List<ResultWord> dataPreparation(String body){
        String bodyLowercase = body.toLowerCase().replaceAll("[^a-żA-Ż]", " ");
        List<String> words = Arrays.asList(bodyLowercase.split("\\s+"));
        Map<String, Integer> map = new HashMap<>();

        for(String word : words){
            Integer count = map.get(word);
            map.put(word,(count==null)?1:count+1);
        }

        Map<Word, Integer> result = new HashMap<>();

        List<Word> allByEnglishWord = wordRepository.findByEnglishWordIn(words);
//        System.out.println(allByEnglishWord);
        allByEnglishWord.forEach(word ->{
           String englishWord = word.getEnglishWord();
           Integer integer = map.get(englishWord.toLowerCase());
           result.put(word, integer);
        });

        Map<Word, Integer> result2 = result.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        //TODO FIX IT
        List<ResultWord> resultWords = new LinkedList<>();

        result2.forEach((r,k)->{
            resultWords.add(new ResultWord(r, k));
        });

        return resultWords;
    }
}



