package cn.gavinwang.student.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Integer> {
    //Room save(Room room);
    List<Room> findAllByFid(int fid);
}
