package cn.gavinwang.student.db;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Data
@Table(name = "family")
public class Family {
    @Id
    @Column(name = "fid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fid;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "qrcode_url")
    private String qrcodeUrl; //二维码url

}
