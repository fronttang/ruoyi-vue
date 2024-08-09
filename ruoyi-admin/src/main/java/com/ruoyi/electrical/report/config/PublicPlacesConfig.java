package com.ruoyi.electrical.report.config;

import com.deepoove.poi.data.FilePictureRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.data.style.PictureStyle;
import com.deepoove.poi.data.style.Style;
import com.deepoove.poi.xwpf.WidthScalePattern;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.electrical.report.annotation.HighConfig;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * <p>
 * 公共场所
 * </p>
 *
 * @author fronttang
 * @since 2024-07-12
 */
@Data
@SuppressWarnings("unused")
@HighConfig("5")
public class PublicPlacesConfig {

//	/**
//	 * 是否属于消防安全重点单位1是0否
//	 */
//	private String fireSafetyUnit;
//
//	private TextRenderData fireSafetyUnit1;
//
//	private TextRenderData fireSafetyUnit0;
//
//	public TextRenderData getFireSafetyUnit1() {
//		return fireSafetyUnit1;
//	}
//
//	public TextRenderData getFireSafetyUnit0() {
//		return fireSafetyUnit0;
//	}

	/**
	 * 是否建立逐级消防安全责任制，明确单位消防安全管理人员
	 */
	private String accountability;

	private TextRenderData accountability1;

	private TextRenderData accountability0;

	public TextRenderData getAccountability1() {
		if ("1".equalsIgnoreCase(this.accountability)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getAccountability0() {
		if ("0".equalsIgnoreCase(this.accountability)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 是否制定符合本单位实际的消防安全制度1是0否
	 */
	private String fireSafetySystem;

	private TextRenderData fireSafetySystem1;

	private TextRenderData fireSafetySystem0;

	public TextRenderData getFireSafetySystem1() {
		if ("1".equalsIgnoreCase(this.fireSafetySystem)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getFireSafetySystem0() {
		if ("0".equalsIgnoreCase(this.fireSafetySystem)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * ABC干粉灭火器数量
	 */
	private Integer abcDpfeQuantity;

	/**
	 * ABC干粉灭火器是否完好1是0否
	 */
	private String abcDpfeStatus;

	private TextRenderData abcDpfeStatus1;

	private TextRenderData abcDpfeStatus0;

	public TextRenderData getAbcDpfeStatus1() {
		if ("1".equalsIgnoreCase(this.abcDpfeStatus)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getAbcDpfeStatus0() {
		if ("0".equalsIgnoreCase(this.abcDpfeStatus)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 消防给水及消火栓系统有无1有0无
	 */
	private String hydrantSystemHas;

	private TextRenderData hydrantSystemHas1;

	private TextRenderData hydrantSystemHas0;

	public TextRenderData getHydrantSystemHas1() {
		if ("1".equalsIgnoreCase(this.hydrantSystemHas)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getHydrantSystemHas0() {
		if ("0".equalsIgnoreCase(this.hydrantSystemHas)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 消防给水及消火栓系统是否完好1是0否
	 */
	private String hydrantSystemStatus;

	private TextRenderData hydrantSystemStatus1;

	private TextRenderData hydrantSystemStatus0;

	public TextRenderData getHydrantSystemStatus1() {
		if ("1".equalsIgnoreCase(this.hydrantSystemStatus)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getHydrantSystemStatus0() {
		if ("0".equalsIgnoreCase(this.hydrantSystemStatus)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 火灾自动报警系统有无1有0无
	 */
	private String alarmSystemHas;

	private TextRenderData alarmSystemHas1;

	private TextRenderData alarmSystemHas0;

	public TextRenderData getAlarmSystemHas1() {
		if ("1".equalsIgnoreCase(this.alarmSystemHas)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getAlarmSystemHas0() {
		if ("0".equalsIgnoreCase(this.alarmSystemHas)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 火灾自动报警系统是否完好1是0否
	 */
	private String alarmSystemStatus;

	private TextRenderData alarmSystemStatus1;

	private TextRenderData alarmSystemStatus0;

	public TextRenderData getAlarmSystemStatus1() {
		if ("1".equalsIgnoreCase(this.alarmSystemStatus)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getAlarmSystemStatus0() {
		if ("0".equalsIgnoreCase(this.alarmSystemStatus)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 自动灭火系统有无1有0无
	 */
	private String automaticFireSystemHas;

	private TextRenderData automaticFireSystemHas1;

	private TextRenderData automaticFireSystemHas0;

	public TextRenderData getAutomaticFireSystemHas1() {
		if ("1".equalsIgnoreCase(this.automaticFireSystemHas)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getAutomaticFireSystemHas0() {
		if ("0".equalsIgnoreCase(this.automaticFireSystemHas)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 自动灭火系统是否完好1是0否
	 */
	private String automaticFireSystemStatus;

	private TextRenderData automaticFireSystemStatus1;

	private TextRenderData automaticFireSystemStatus0;

	public TextRenderData getAutomaticFireSystemStatus1() {
		if ("1".equalsIgnoreCase(this.automaticFireSystemStatus)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getAutomaticFireSystemStatus0() {
		if ("0".equalsIgnoreCase(this.automaticFireSystemStatus)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 防/排烟系统有无1有0无
	 */
	private String exhaustSystemHas;

	private TextRenderData exhaustSystemHas1;

	private TextRenderData exhaustSystemHas0;

	public TextRenderData getExhaustSystemHas1() {
		if ("1".equalsIgnoreCase(this.exhaustSystemHas)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getExhaustSystemHas0() {
		if ("0".equalsIgnoreCase(this.exhaustSystemHas)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 防/排烟系统是否完好1是0否
	 */
	private String exhaustSystemStatus;

	private TextRenderData exhaustSystemStatus1;

	private TextRenderData exhaustSystemStatus0;

	public TextRenderData getExhaustSystemStatus1() {
		if ("1".equalsIgnoreCase(this.exhaustSystemStatus)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getExhaustSystemStatus0() {
		if ("0".equalsIgnoreCase(this.exhaustSystemStatus)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 总平面布局、灭火救援设施是否符合要求1是0否
	 */
	private String rescueFacilities;

	private TextRenderData rescueFacilities1;

	private TextRenderData rescueFacilities0;

	public TextRenderData getRescueFacilities1() {
		if ("1".equalsIgnoreCase(this.rescueFacilities)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getRescueFacilities0() {
		if ("0".equalsIgnoreCase(this.rescueFacilities)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 耐火等级、防火分区、平面布置是否符合要求1是0否
	 */
	private String fireResistantLevel;

	private TextRenderData fireResistantLevel1;

	private TextRenderData fireResistantLevel0;

	public TextRenderData getFireResistantLevel1() {
		if ("1".equalsIgnoreCase(this.fireResistantLevel)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getFireResistantLevel0() {
		if ("0".equalsIgnoreCase(this.fireResistantLevel)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 防火构造及室内装修是否符合规范要求1是0否
	 */
	private String fireproofStructure;

	private TextRenderData fireproofStructure1;

	private TextRenderData fireproofStructure0;

	public TextRenderData getFireproofStructure1() {
		if ("1".equalsIgnoreCase(this.fireproofStructure)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getFireproofStructure0() {
		if ("0".equalsIgnoreCase(this.fireproofStructure)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 安全疏散设施及管理是否符合要求1是0否
	 */
	private String safeEvacuation;

	private TextRenderData safeEvacuation1;

	private TextRenderData safeEvacuation0;

	public TextRenderData getSafeEvacuation1() {
		if ("1".equalsIgnoreCase(this.safeEvacuation)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getSafeEvacuation0() {
		if ("0".equalsIgnoreCase(this.safeEvacuation)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 是否设置消防控制室1物业所有0否
	 */
	private String fireControlRoom;

	private TextRenderData fireControlRoom1;

	private TextRenderData fireControlRoom2;

	private TextRenderData fireControlRoom0;

	public TextRenderData getFireControlRoom1() {
		if ("1".equalsIgnoreCase(this.fireControlRoom)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getFireControlRoom2() {
		if ("2".equalsIgnoreCase(this.fireControlRoom)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getFireControlRoom0() {
		if ("0".equalsIgnoreCase(this.fireControlRoom)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 消防控制室的管理是否符合要求1是0否
	 */
	private String fireControlRoomMgr;

	private TextRenderData fireControlRoomMgr1;

	private TextRenderData fireControlRoomMgr0;

	public TextRenderData getFireControlRoomMgr1() {
		if ("1".equalsIgnoreCase(this.fireControlRoomMgr)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getFireControlRoomMgr0() {
		if ("0".equalsIgnoreCase(this.fireControlRoomMgr)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 电气线路敷设、电气设备使用是否符合要求1是0否
	 */
	private String electricalEquipment;

	private TextRenderData electricalEquipment1;

	private TextRenderData electricalEquipment0;

	public TextRenderData getElectricalEquipment1() {
		if ("1".equalsIgnoreCase(this.electricalEquipment)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getElectricalEquipment0() {
		if ("0".equalsIgnoreCase(this.electricalEquipment)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 是否开展经常性消防安全宣传工作1是0否
	 */
	private String safetyPromotion;

	private TextRenderData safetyPromotion1;

	private TextRenderData safetyPromotion0;

	public TextRenderData getSafetyPromotion1() {
		if ("1".equalsIgnoreCase(this.safetyPromotion)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getSafetyPromotion0() {
		if ("0".equalsIgnoreCase(this.safetyPromotion)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 是否组织员工每半年至少开展一次消防培训1是0否
	 */
	private String fireTraining;

	private TextRenderData fireTraining1;

	private TextRenderData fireTraining0;

	public TextRenderData getFireTraining1() {
		if ("1".equalsIgnoreCase(this.fireTraining)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getFireTraining0() {
		if ("0".equalsIgnoreCase(this.fireTraining)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 新上岗的员工是否开展岗前消防培训1是0否
	 */
	private String prejobFireTraining;

	private TextRenderData prejobFireTraining1;

	private TextRenderData prejobFireTraining0;

	public TextRenderData getPrejobFireTraining1() {
		if ("1".equalsIgnoreCase(this.prejobFireTraining)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getPrejobFireTraining0() {
		if ("0".equalsIgnoreCase(this.prejobFireTraining)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 是否在人群密集场所区域内张贴通知1是0否
	 */
	private String postNotice;

	private TextRenderData postNotice1;

	private TextRenderData postNotice0;

	public TextRenderData getPostNotice1() {
		if ("1".equalsIgnoreCase(this.postNotice)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getPostNotice0() {
		if ("0".equalsIgnoreCase(this.postNotice)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 用火、动火是否符合要求1是0否
	 */
	private String meetsEquirements;

	private TextRenderData meetsEquirements1;

	private TextRenderData meetsEquirements0;

	public TextRenderData getMeetsEquirements1() {
		if ("1".equalsIgnoreCase(this.meetsEquirements)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getMeetsEquirements0() {
		if ("0".equalsIgnoreCase(this.meetsEquirements)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 是否每日开展防火巡查1是0否
	 */
	private String fireInspection;

	private TextRenderData fireInspection1;

	private TextRenderData fireInspection0;

	public TextRenderData getFireInspection1() {
		if ("1".equalsIgnoreCase(this.fireInspection)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getFireInspection0() {
		if ("0".equalsIgnoreCase(this.fireInspection)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 是否制定灭火和应急疏散预案，并定期开展演练1是0否
	 */
	private String emergencyEvacuationPlan;

	private TextRenderData emergencyEvacuationPlan1;

	private TextRenderData emergencyEvacuationPlan0;

	public TextRenderData getEmergencyEvacuationPlan1() {
		if ("1".equalsIgnoreCase(this.emergencyEvacuationPlan)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	public TextRenderData getEmergencyEvacuationPlan0() {
		if ("0".equalsIgnoreCase(this.emergencyEvacuationPlan)) {
			return new TextRenderData("R", new Style("Wingdings 2", 12));
		} else {
			return new TextRenderData("\u00A3", new Style("Wingdings 2", 12));
		}
	}

	/**
	 * 门头照
	 */
	private String doorPic;

	private String doorPicture;

	public FilePictureRenderData getDoorPicture() {
		if (StrUtil.isBlank(this.doorPic)) {
			return null;
		}
		// 本地资源路径
		String localPath = RuoYiConfig.getProfile();
		// 数据库资源地址
		String filePath = localPath + StringUtils.substringAfter(this.doorPic, Constants.RESOURCE_PREFIX);

		FilePictureRenderData qualification = new FilePictureRenderData(filePath);
		PictureStyle pictureStyle = new PictureStyle();
		pictureStyle.setScalePattern(WidthScalePattern.FIT);
		qualification.setPictureStyle(pictureStyle);
		return qualification;
	}

}
