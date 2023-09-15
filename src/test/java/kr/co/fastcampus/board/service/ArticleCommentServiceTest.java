package kr.co.fastcampus.board.service;

import jakarta.persistence.EntityNotFoundException;
import kr.co.fastcampus.board.domain.Article;
import kr.co.fastcampus.board.domain.ArticleComment;
import kr.co.fastcampus.board.domain.UserAccount;
import kr.co.fastcampus.board.dto.ArticleCommentDTO;
import kr.co.fastcampus.board.dto.UserAccountDTO;
import kr.co.fastcampus.board.repository.ArticleCommentRepository;
import kr.co.fastcampus.board.repository.ArticleRepository;
import kr.co.fastcampus.board.repository.UserAccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {

    @InjectMocks private ArticleCommentService sut;
    @Mock private ArticleRepository articleRepository;
    @Mock private ArticleCommentRepository articleCommentRepository;

    @Mock private UserAccountRepository userAccountRepository;

    @DisplayName("게시글 ID로 조회하면, 해당하는 댓글 리스트를 반환한다.")
    @Test
    void givenArticleId_whenSearchingArticleComments_thenReturnsArticleComments(){
        //Given
        Long articleId = 1L;
        ArticleComment expected=createArticleComment("content");
        given(articleCommentRepository.findByArticle_Id(articleId)).willReturn(List.of(expected));

        //When
        List<ArticleCommentDTO> actual = sut.searchArticleComments(articleId);
        //Then
        assertThat(actual)
                .hasSize(1)
                .first().hasFieldOrPropertyWithValue("content",expected.getContent());
        then(articleCommentRepository).should().findByArticle_Id(articleId);
    }
    @DisplayName("댓글 정보를 입력하면, 댓글을 저장")
    @Test
    void givenArticleCommentInfo_whenSavingArticleComment_thenSaveArticleComment(){
        //Given
        ArticleCommentDTO dto = createArticleCommentDTO("댓글");
        given(articleRepository.getReferenceById(dto.articleId())).willReturn(createArticle());
        given(userAccountRepository.getReferenceById(dto.userAccountDTO().userId())).willReturn(createUserAccount());
        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);
        //When
        sut.saveArticleComment(dto);
        //Then
        then(articleRepository).should().getReferenceById(dto.articleId());
        then(userAccountRepository).should().getReferenceById(dto.userAccountDTO().userId());
        then(articleCommentRepository).should().save(any(ArticleComment.class));
    }
    @DisplayName("댓글 저장을 시도 했는데 맞는 게시글이 없으면 경고 로그를 찍고 아무것도 안한다.")
    @Test
    void givenNonexistentArticle_whenSavingArticleComment_thenLogsSituationAndDoesNothing(){
        //Given
        ArticleCommentDTO dto = createArticleCommentDTO("댓글");
        given(articleRepository.getReferenceById(dto.articleId())).willThrow(EntityNotFoundException.class);
        //When
        sut.saveArticleComment(dto);
        //Then
        then(articleRepository).should().getReferenceById(dto.articleId());
        then(articleCommentRepository).shouldHaveNoInteractions();
    }
    @DisplayName("댓글 정보를 입력하면, 댓글을 수정한다")
    @Test
    void givenArticleCommentInfo_whenUpdatingArticleComment_thenUpdatesArticleComment(){
        //Given
        String oldContent = "content";
        String updateContent = "댓글";
        ArticleComment articleComment=createArticleComment(oldContent);
        ArticleCommentDTO dto = createArticleCommentDTO(updateContent);
        given(articleCommentRepository.getReferenceById(dto.id())).willReturn(articleComment);
        //When
        sut.updateArticleComment(dto);

        //Then
        assertThat(articleComment.getContent())
                .isNotEqualTo(oldContent)
                .isEqualTo(updateContent);
        then(articleCommentRepository).should().getReferenceById(dto.id());
    }
    @DisplayName("없는 댓글 정보를 수정하려고 하면, 경고 로그를 찍고 아무 것도 안 한다.")
    @Test
    void givenNonexistentArticleComment_whenUpdatingArticleComment_thenLogsWarningAndDoesNothing() {
        // Given
        ArticleCommentDTO dto = createArticleCommentDTO("댓글");
        given(articleCommentRepository.getReferenceById(dto.id())).willThrow(EntityNotFoundException.class);

        // When
        sut.updateArticleComment(dto);

        // Then
        then(articleCommentRepository).should().getReferenceById(dto.id());
    }
    @DisplayName("댓글 ID를 입력하면, 댓글을 삭제한다.")
    @Test
    void givenArticleCommentId_whenDeletingArticleComment_thenDeletesArticleComment() {
        //Given
        Long articleCommentId = 1L;
        willDoNothing().given(articleCommentRepository).deleteById(articleCommentId);
        //When
        sut.deleteArticleComment(articleCommentId);
        //Then
        then(articleCommentRepository).should().deleteById(articleCommentId);
    }
    private Article createArticle() {
        return Article.of(
                createUserAccount(),
                "title",
                "content",
                "#java"
        );
    }

    private ArticleComment createArticleComment(String content) {
        return ArticleComment.of(
                Article.of(createUserAccount(), "title", "content", "hashtag"),
                createUserAccount(),
                content
        );
    }
    private ArticleCommentDTO createArticleCommentDTO(String content) {
        return ArticleCommentDTO.of(
                1L,
                1L,
                createUserAccountDTO(),
                content,
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno"
        );
    }
    private UserAccountDTO createUserAccountDTO() {
        return UserAccountDTO.of(
                "uno",
                "password",
                "uno@mail.com",
                "Uno",
                "This is memo",
                LocalDateTime.now(),
                "uno",
                LocalDateTime.now(),
                "uno"
        );
    }
    private UserAccount createUserAccount() {
        return UserAccount.of(
                "uno",
                "password",
                "uno@email.com",
                "Uno",
                null
        );
    }
}