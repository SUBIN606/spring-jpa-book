package book.example.board.service;

import book.example.board.domain.posts.Posts;
import book.example.board.domain.posts.PostsRepository;
import book.example.board.web.dto.PostsListResponseDto;
import book.example.board.web.dto.PostsResponseDto;
import book.example.board.web.dto.PostsSaveRequestDto;
import book.example.board.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id = " + id));

        return new PostsResponseDto(entity);
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id = " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

}
