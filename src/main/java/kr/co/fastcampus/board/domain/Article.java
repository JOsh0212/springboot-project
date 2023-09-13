package kr.co.fastcampus.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
public class Article {  //게시글
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //게시글 아이디

    @Setter @Column(nullable = false) private String title;   //게시글 제목
    @Setter @Column(nullable = false,length = 10000)private String content; //게시글 내용

    @Setter private String hashtag; //게시글 해시태그

    @ToString.Exclude   //순환참조가 일어날 수있어 제외
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)    //실무에서는 양방향 바인드를 안씀
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    //메타데이터
    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt;    //생성일자
    @CreatedBy @Column(nullable = false,length = 100) private String createdBy;   //생성자
    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt;   //수정일자
    @LastModifiedBy @Column(nullable = false,length = 100) private String modifiedBy;  //수정자

    protected Article() {}

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }
    public static Article of(String title, String content, String hashtag) {    //팩터리 메서드
        return new Article(title,content,hashtag);
    }

    //equals and hashcode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Article article = (Article) o;
        if(!(o instanceof  Article article)) return false;  //pattern variable - java14이상
        return id!=null && id.equals(article.id);   // id가 부여되지 않으면 false && 아이디가 있다면 동등성 검사
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
