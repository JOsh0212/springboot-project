package kr.co.fastcampus.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@ToString(callSuper = true) //tostring을 객체들 안까지 출력하기 위해
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
public class Article extends AuditingFields{  //게시글
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //게시글 아이디

    @Setter @ManyToOne(optional = false) @JoinColumn(name = "userId") private UserAccount userAccount;   // 유저정보 id


    @Setter @Column(nullable = false) private String title;   //게시글 제목
    @Setter @Column(nullable = false,length = 10000)private String content; //게시글 내용

    @Setter private String hashtag; //게시글 해시태그

    @ToString.Exclude   //순환참조가 일어날 수있어 제외
    @OrderBy("createdAt DESC")  //시간 차순으로
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)    //실무에서는 양방향 바인드를 안씀
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();
    protected Article() {}
    private Article(UserAccount userAccount,String title, String content, String hashtag) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }
    public static Article of(UserAccount userAccount,String title, String content, String hashtag) {    //팩터리 메서드
        return new Article(userAccount,title,content,hashtag);
    }
    private Article(UserAccount userAccount, String title, String content) {
        this.userAccount = userAccount;
        this.title = title;
        this.content = content;
    }
    public static Article of(UserAccount userAccount, String title, String content) {
        return new Article(userAccount, title, content);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof  Article article)) return false;
//        return id!=null && id.equals(article.id);
        return this.getId() !=null && this.getId().equals(article.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
