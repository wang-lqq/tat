package com.example.sb.entity;

import io.geekidea.springbootplus.framework.common.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import io.geekidea.springbootplus.framework.core.validator.groups.Update;

/**
 * 
 *
 * @author wanglonglong
 * @since 2021-03-29
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SbComputerRecord对象")
public class SbComputerRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    private Integer id;

    @ApiModelProperty("mac地址")
    private String mac;

    @ApiModelProperty("部门id")
    private Integer departmentId;

    @ApiModelProperty("部门名称")
    private String departmentName;

    @ApiModelProperty("姓名")
    private String fullName;

    @ApiModelProperty("ip地址")
    private String ipAddress;

    @ApiModelProperty("域用户名")
    private String domainUsername;

    @ApiModelProperty("计算机名")
    private String computerName;

    @ApiModelProperty("新资产编码")
    private String newAssetcode;

    @ApiModelProperty("操作系统")
    private String operatingSystem;

    @ApiModelProperty("管理员密码")
    private String administratorPassword;

    @ApiModelProperty("邮箱")
    private String mailbox;

    @ApiModelProperty("usb")
    private String usb;

    @ApiModelProperty("连接设备")
    private String connectingDevices;

    @ApiModelProperty("admin权限")
    private String adminPermission;

    @ApiModelProperty("上网")
    private String internet;

    @ApiModelProperty("打印机")
    private String printer;

    @ApiModelProperty("扫描")
    private String scanning;

    @ApiModelProperty("office")
    private String office;

    @ApiModelProperty("SW (●盗版 )(√ 正版,网络版)")
    private String sw;

    @ApiModelProperty("SW_PDM
")
    private String swPdm;

    @ApiModelProperty("Autocad(●盗版 )中望2015 网络版 (√ 正版)")
    private String autocad;

    @ApiModelProperty("k3")
    private String k3;

    @ApiModelProperty("亿赛通")
    private String yisaitong;

    @ApiModelProperty(" IP-GURAD")
    private String ipGurad;

    @ApiModelProperty("Officescan")
    private String officescan;

    @ApiModelProperty("系统还原备份")
    private String systemRestoreBackup;

    @ApiModelProperty("cutePDF")
    private String cutepdf;

    @ApiModelProperty("福昕阅读器")
    private String foxitReader;

    @ApiModelProperty("DocuWorks")
    private String docuworks;

    @ApiModelProperty("RAR")
    private String rar;

    @ApiModelProperty("ZIP")
    private String zip;

    @ApiModelProperty("Adobe Flash")
    private String adobeFlash;

    @ApiModelProperty("Adobe Reader")
    private String adobeReader;

    @ApiModelProperty("Acrobat")
    private String acrobat;

    @ApiModelProperty("WPTx64")
    private String wptx64;

    @ApiModelProperty("VBA")
    private String vba;

    @ApiModelProperty("JAVA")
    private String java;

    @ApiModelProperty("VISIO")
    private String visio;

    @ApiModelProperty("Cimatron")
    private String cimatron;

    @ApiModelProperty("PROE")
    private String proe;

    @ApiModelProperty("UG")
    private String ug;

    @ApiModelProperty("Catia")
    private String catia;

    @ApiModelProperty("考勤")
    private String checkWorkAttendance;

    @ApiModelProperty("D6OraClient11g")
    private String d6Oraclient11g;

    @ApiModelProperty("photoshop及其它")
    private String photoshop;

    @ApiModelProperty("备注")
    private String remarks;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("修改时间")
    private Date updateTime;

}
