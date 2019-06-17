package cn.gavinwang.student.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface AirInfoRepository extends JpaRepository<AirInfo,Long> {
    List<AirInfo> findAllByDeviceId(String deviceId);
    AirInfo findByDeviceId(String deviceId);

}
