package kr.co.fastcampus.board.repository;

import kr.co.fastcampus.board.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
@RepositoryRestResource
public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {
}
