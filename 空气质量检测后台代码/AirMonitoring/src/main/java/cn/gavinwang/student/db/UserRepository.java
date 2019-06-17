package cn.gavinwang.student.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUserName(String username);
    User findByUserId(int userId);
}
