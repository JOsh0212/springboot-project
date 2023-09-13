package kr.co.fastcampus.board.domain;

import java.time.LocalDateTime;

public class Article {  //게시글
    private Long id;    //게시글 아이디
    private String title;   //게시글 제목
    private String content; //게시글 내용
    private String hashtag; //게시글 해시태그

    //메타데이터
    private LocalDateTime createdAt;    //생성일자
    private String createdBy;   //생성자
    private LocalDateTime modifiedAt;   //수정일자
    private String modifiedBy;  //수정자
}
