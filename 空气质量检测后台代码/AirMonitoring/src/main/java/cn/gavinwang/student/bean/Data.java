package cn.gavinwang.student.bean;

import cn.gavinwang.student.db.Room;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class Data {
    private int fid;
    private List<RoomData> roomData;
}
