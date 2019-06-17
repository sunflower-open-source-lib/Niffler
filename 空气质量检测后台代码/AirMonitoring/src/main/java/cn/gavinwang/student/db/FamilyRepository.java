package cn.gavinwang.student.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface FamilyRepository extends JpaRepository<Family,Integer> {
    Family findByFid(int fid);
}
