package com.fluentenglish.web.learning.material.word.user;

import com.fluentenglish.web.learning.material.word.dto.WordDto;
import com.fluentenglish.web.learning.material.word.dto.WordSearchDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController("UserWordController")
@RequestMapping("/api/words")
public class WordController {
    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping()
    public ResponseEntity<List<WordDto>> getWords(
            @RequestParam(name = "q", required = false, defaultValue = "") String wordSearch,
            @RequestParam(name = "wordIds", required = false) String wordIdsStr) {
        WordSearchDto wordSearchDto = new WordSearchDto();
        wordSearchDto.setText(wordSearch.trim());

        if (wordIdsStr != null) {
            wordSearchDto.setWordIds(Stream.of(wordIdsStr.split(",")).map(Integer::parseInt).toList());
        }

        List<WordDto> words = wordService.getWords(wordSearchDto);

        return ResponseEntity.ok(words);
    }

    @GetMapping("/{wordId}")
    public ResponseEntity<WordDto> getWord(@PathVariable int wordId) {
        WordDto word = wordService.getWordById(wordId);

        return ResponseEntity.ok(word);
    }
}
