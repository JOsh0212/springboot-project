package kr.co.fastcampus.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
//@EntityListeners(AuditingEntityListener.class)    //AuditingFields로 옮김
public class ArticleComment extends AuditingFields{   // 게시글댓글
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //댓글 아이디



    @Setter @ManyToOne(optional = false) private Article article;    //게시글 아이디
    @Setter @ManyToOne(optional = false) @JoinColumn(name = "userId") private UserAccount userAccount;   //유저정보 ID
    @Setter @Column(nullable = false, length = 500) private String content; //댓글 내용

    //메타데이터 -> 데이터 추출(권장하지 않음)
//    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt;    //생성일자
//    @CreatedBy @Column(nullable = false,length = 100) private String createdBy;   //생성자
//    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt;   //수정일자
//    @LastModifiedBy @Column(nullable = false,length = 100) private String modifiedBy;  //수정자

//    @Embedded Metadate metadate;  // 이런 방법도 있다.
//    class Metadate{
//        @CreatedDate @Column(nullable = false) private LocalDateTime createdAt;    //생성일자
//        @CreatedBy @Column(nullable = false,length = 100) private String createdBy;   //생성자
//        @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt;   //수정일자
//        @LastModifiedBy @Column(nullable = false,length = 100) private String modifiedBy;  //수정자
//    }


    protected ArticleComment() {
    }

    private ArticleComment(Article article,UserAccount userAccount, String content) {
        this.userAccount = userAccount;
        this.article = article;
        this.content = content;
    }

    public static ArticleComment of(Article article,UserAccount userAccount,String content){
        return new ArticleComment(article,userAccount,content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof  ArticleComment articleComment)) return false;
        return id!=null && id.equals(articleComment.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
