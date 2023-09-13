package kr.co.fastcampus.board.domain;

import java.time.LocalDateTime;

public class ArticleComment {   // 게시글댓글
    private Long id;    //댓글 아이디
    private Long article_id;    //게시글 아이디
    private String content; //댓글 내용

    //메타데이터
    private LocalDateTime createdAt;    //생성일자
    private String createdBy;   //생성자
    private LocalDateTime modifiedAt;   //수정일자
    private String modifiedBy;  //수정자
}
