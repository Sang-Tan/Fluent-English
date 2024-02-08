package com.fluentenglish.web.learning.material.word;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fluentenglish.web.common.fileresource.CloudinaryFileResourceDto;
import com.fluentenglish.web.common.fileresource.FileResourceDto;
import com.fluentenglish.web.learning.material.word.admin.dto.WordCreateUpdateDto;
import com.fluentenglish.web.upload.cloud.StorageExecutorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WordMediaServiceImpl implements WordMediaService {
    private final ObjectMapper objectMapper;

    private final StorageExecutorService storageExecutorService;

    public WordMediaServiceImpl(ObjectMapper objectMapper,
                                StorageExecutorService storageExecutorService) {
        this.objectMapper = objectMapper;
        this.storageExecutorService = storageExecutorService;
    }

    @Override
    public void saveMediaOnCreated(WordCreateUpdateDto WordCreateUpdateDto) {
        getMediaIds(WordCreateUpdateDto.getSound(), WordCreateUpdateDto.getImage())
                .forEach(storageExecutorService::makeFilePermanent);
    }

    @Override
    public void saveMediaOnUpdated(WordCreateUpdateDto WordCreateUpdateDto, Word persistedWord) {
        List<String> updatedMediaIds =
                getMediaIds(WordCreateUpdateDto.getSound(),WordCreateUpdateDto.getImage());
        Set<String> mediaIdsToRemove = new HashSet<>(getMediaIdsFromWord(persistedWord));

        List<String> mediaIdsToSave = new ArrayList<>();
        updatedMediaIds.forEach(mediaId -> {
            if (!mediaIdsToRemove.contains(mediaId)) {
                mediaIdsToSave.add(mediaId);
            } else {
                mediaIdsToRemove.remove(mediaId);
            }
        });

        mediaIdsToSave.forEach(storageExecutorService::makeFilePermanent);
        mediaIdsToRemove.forEach(storageExecutorService::deleteFile);
    }

    @Override
    public void deleteMediaOnDeleted(Word Word) {
        getMediaIdsFromWord(Word).forEach(storageExecutorService::deleteFile);
    }

    private List<String> getMediaIdsFromWord(Word Word) {
        try {
            CloudinaryFileResourceDto sound = objectMapper.readValue(Word.getSound(), CloudinaryFileResourceDto.class);
            CloudinaryFileResourceDto image = objectMapper.readValue(Word.getImage(), CloudinaryFileResourceDto.class);

            return getMediaIds(sound, image);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getMediaIds(FileResourceDto sound, FileResourceDto image) {
        List<String> mediaIds = new ArrayList<>();
        if (sound instanceof CloudinaryFileResourceDto) {
            mediaIds.add(((CloudinaryFileResourceDto) sound).getPublicId());
        }
        if (image instanceof CloudinaryFileResourceDto) {
            mediaIds.add(((CloudinaryFileResourceDto) image).getPublicId());
        }

        return mediaIds;
    }
}
