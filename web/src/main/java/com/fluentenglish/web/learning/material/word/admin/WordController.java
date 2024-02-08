package com.fluentenglish.web.learning.material.word.admin;

import com.fluentenglish.web.learning.material.word.dto.WordCreateUpdateDto;
import com.fluentenglish.web.learning.material.word.dto.WordDetailDto;
import com.fluentenglish.web.learning.material.word.dto.WordDto;
import com.fluentenglish.web.learning.material.word.dto.WordSearchDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController("AdminWordController")
@RequestMapping("/admin/api/words")
public class WordController {
    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @PostMapping
    public ResponseEntity<Void> createWord(@RequestBody @Valid WordCreateUpdateDto wordCreateUpdateDto) {
        int wordId = wordService.createWord(wordCreateUpdateDto);

        return ResponseEntity.created(URI.create(String.format("/admin/api/words/%d", wordId))).build();
    }

    @GetMapping("/{wordId}")
    public ResponseEntity<WordDto> getWord(@PathVariable int wordId) {
        WordDto word = wordService.getWordById(wordId);

        return ResponseEntity.ok(word);
    }

    @GetMapping("/{wordId}/detail")
    public ResponseEntity<WordDetailDto> getWordDetail(@PathVariable int wordId) {
        WordDetailDto wordDetail = wordService.getWordDetailById(wordId);

        return ResponseEntity.ok(wordDetail);
    }

    @GetMapping()
    public ResponseEntity<List<WordDto>> getWords(@RequestParam(name = "q", required = false, defaultValue = "") String wordSearch) {
        WordSearchDto wordSearchDto = new WordSearchDto();
        wordSearchDto.setText(wordSearch.trim());
        List<WordDto> words = wordService.getWords(wordSearchDto);

        return ResponseEntity.ok(words);
    }

    @PutMapping("/{wordId}")
    public ResponseEntity<Void> updateWord(@PathVariable int wordId,
                                               @RequestBody @Valid WordCreateUpdateDto wordUpdateDto) {
        wordService.updateWord(wordId, wordUpdateDto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{wordId}")
    public ResponseEntity<Void> deleteWord(@PathVariable int wordId) {
        wordService.deleteWord(wordId);

        return ResponseEntity.noContent().build();
    }
}
