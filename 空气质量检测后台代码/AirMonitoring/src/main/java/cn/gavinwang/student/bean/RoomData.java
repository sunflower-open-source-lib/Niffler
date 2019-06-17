package cn.gavinwang.student.bean;

import cn.gavinwang.student.db.AirInfo;
import cn.gavinwang.student.db.Room;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoomData {
    private Room room;
    private AirInfo airInfo;
}
