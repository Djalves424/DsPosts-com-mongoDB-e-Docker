package com.djalves.workshopmongodocker.services;

import com.djalves.workshopmongodocker.models.dto.PostDTO;
import com.djalves.workshopmongodocker.models.entities.Post;
import com.djalves.workshopmongodocker.repositories.PostRepository;
import com.djalves.workshopmongodocker.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    public PostDTO findById(String id) {
        Post entity = getEntityById(id);
        return new PostDTO(entity);
    }

    private Post getEntityById(String id) {
        Optional<Post> result = repository.findById(id);
        return result.orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado"));
    }

    public List<PostDTO> findByTitle(String text) {
        List<Post> list = repository.searchTitle(text);
        return list.stream().map(x -> new PostDTO(x)).collect(Collectors.toList());
    }

    public List<PostDTO> fullSearch(String text, String start, String end) {
        Instant startMoment = convertMoment(start, Instant.ofEpochMilli(0L));
        Instant endMoment = convertMoment(end, Instant.now());
        List<Post> list = repository.fullSearch(text, startMoment, endMoment);
        return list.stream().map(x -> new PostDTO(x)).collect(Collectors.toList());
    }

    private Instant convertMoment(String originalText, Instant alternative) {
        try {
            return Instant.parse(originalText);
        } catch (DateTimeParseException e) {
            return alternative;
        }
    }
}
