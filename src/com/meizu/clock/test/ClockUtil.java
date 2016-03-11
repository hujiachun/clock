/**
 * hujiachun
 */
package com.meizu.clock.test;

/**
 * @author hujiachun
 */
public class ClockUtil {

	
	/**
	 * 相关按钮
	 */
	public static final String BTN_CONFIRM = "确定";
	public static final String BTN_CANCEL = "取消";
	public static final String BTN_START = "开始";
	public static final String BTN_PAUSE = "暂停";
	public static final String BTN_RESET = "复位";
	public static final String BTN_CONTINUE = "继续";
	public static final String BTN_STOP = "停止";
	public static final String BTN_SNOOZE = "推迟";
	public static final String BTN_SAVE = "保存";
	public static final String BTN_COUNT = "计次";
	public static final String BTN_DELETE = "删除";
	public static final String BTN_CLOSE = "关闭";
	public static final String SETTINGS_DISPLAY = "显示和亮度";
	
	/**
	 * 包名相关
	 */
	public static final String CLOCK_PACKAGE = "com.android.alarmclock";
	public static final String CLOCK_ACTIVITY = ".AlarmClock";
	public static final String FILE_ACTIVITY = "com.meizu.flyme.filemanager.activity.FileManagerActivity";
	
	
	
	/**
	 * 闹钟相关id
	 */
	public static final String ADD_CLOCK = "com.android.alarmclock:id/fab";
	public static final String CLOCK_MIN = "com.android.alarmclock:id/scroll2";
	public static final String CLOCK_HOUR = "com.android.alarmclock:id/scroll1";
	public static final String SNOOZE_TIME = "com.android.alarmclock:id/snooze";
	public static final String SNOOZE_ICON = "com.android.alarmclock:id/snooze_icon";
	public static final String LIST = "android:id/list";
	public static final String ADJUST_VOLUME_ = "com.android.alarmclock:id/volume_seekbar";
	public static final String ALARM_CLOCK = "com.android.alarmclock:id/clock_layout";
	public static final String SELECT_ALL = "com.android.alarmclock:id/mz_action_multi_choice_select_all_item";
	public static final String CLOCK_SWITCH = "com.android.alarmclock:id/switch_onoff_sign";
	public static final String CLOCK_EDIT = "com.android.alarmclock:id/edit";
	public static final String CLOCK_WEATHER = "com.android.alarmclock:id/lock_text_info";
	public static final String CLOCK_LAY = "com.android.alarmclock:id/alarm_layout";
	
	
	/**
	 * 世界时钟相关
	 */
	public static final String WORLD_CLOCK = "com.android.alarmclock:id/analog_clock";
	public static final String WORLD_DATE = "com.android.alarmclock:id/city_day";
	public static final String WORLD_TIME = "com.android.alarmclock:id/time";
	public static final String CITY_NAME = "com.android.alarmclock:id/city_name";
	public static  final String  WORLD_CITY_LIST="com.android.alarmclock:id/mc_item_container";
	public static  final String  WORLD_CITY_SEARCH="com.android.alarmclock:id/mc_search_edit";
	public static  final String  CLEAR_SEARCH="com.android.alarmclock:id/mc_search_icon_input_clear";
	public static  final String  CLEAR_PM_AM="com.android.alarmclock:id/am_pm";
	
	
	/**
	 * 秒表相关
	 */
	public static final String STOPWATCH_GUI = "com.android.alarmclock:id/total_time";
	public static final String STOPWATCH_COUNT = "android:id/text1";
	public static final String STOPWATCH_SECOND = "com.android.alarmclock:id/total_time_millis";
	

	/**
	 * 计时器相关
	 */
//	public static final String TIMER_GUI = "com.android.alarmclock:id/column_parent";
	public static final String TIMER_POINTER = "com.android.alarmclock:id/scroll";
	public static final String TIMER_DAtA = "com.android.alarmclock:id/timer_progressbar";
	public static final String TIMER_TIME = "com.android.alarmclock:id/time_counting";
	public static final String VIBRATE_SWITCH = "com.android.alarmclock:id/vibrate_switch";
	public static final String KEEP_SWITCH = "com.android.alarmclock:id/keep_on_switch";
	public static final String TIME_VIEW = "com.android.alarmclock:id/horizontal_wheel_view";
	public static final String TIMER_START = "com.android.alarmclock:id/btn_start_counting";
	public static final String TIMER_TOTAL = "com.android.alarmclock:id/total_time";
	
	
	
	/**
	 * 通知栏相关
	 */
	public static final String NOTIFIATION = "com.android.systemui:id/scroll";
	public static final String NOTIFIATION_SETTINGS = "com.android.systemui:id/settings_button";
	
	
	/**
	 * 设置相关
	 */
	public static final String SETTINGS_SEARCH = "com.android.settings:id/mc_item_container";
	public static final String SETTINGS_ONOFF = "com.android.settings:id/switchWidget";
	public static final String SETTINGS_UP = "android:id/up";
	public static final String SETTINGS_SCROLL = "com.android.settings:id/dashboard";
	public static final String SETTINGS_CLASS_DISPLAY = "android.widget.ListView";
	
	
	/**
	 * 文档相关
	 */
	public static final String FILE_LIST = "com.meizu.filemanager:id/file_list";
	public static final String FILE_TEXT = "com.meizu.filemanager:id/truncated_text";
	public static final String FILE_ITEM = "com.meizu.filemanager:id/file_item";
	public static final String FILE_DELETE = "com.meizu.filemanager:id/menu_delete";
	public static final String FILE_MENU = "com.meizu.filemanager:id/mz_action_mode_menu_view";
	public static final String FILE_COPY_MENU = "com.meizu.filemanager:id/mz_action_menu_view";
	
	
	/**
	 * 其他
	 */
	public static final String MESSAGE = "android:id/message";
	public static final String CALENDAR = "com.android.calendar:id/fab";
	
	public static final String METHOD_P1S =
			
			
			
			"testSaveClock "+
			"testAddClock "+
			"testStart2 "+
			"test005AddRepeatClock "+
            "test006SetSnoozeClock "+
			"test007SetSnoozeClock "+
			"test008SetSnoozeClockNotification "+
			"test009SetSnoozeClockNotification "+
			"test013SetRandomRing "+
			"test016SetRingtonesRing "+
			"test019OpenHolidayClock "+
			"test020CloseHolidayClock "+
			"test023LockClock "+
			"test024LockClock "+
			"test025LockClock "+
			"test034WeatherSnoozeClock "+
			"test034WeatherClock "+
			"test026ClickPower "+
			"test027ClickPower "+
			"test028ClickVOLUME_UP "+
			"test029ClickVOLUME_DOWN "+
			"test030ClickPower "+
			"test031ClickVOLUME_UP "+
			"test032ClickVOLUME_DOWN "+
			"test033NotificationClock "+
			"test034NotificationClock "+
			"test035DeleteClock "+
			"test037AddWorldClock "+
			"test042DeleteWorldClock "+
			"test045TiminCount "+
			"test046VolCount "+
			"test049Timer "+
			"test054Notification "+
			"test055TimerClickPower "+
			"test056TimerClickVOLUME_UP "+
			"test057TimerClickVOLUME_DOWN "+
			"test059CloseKeepScreen "+
			"test060OpenKeepScreen "+
			"test061IgnoreReminder "
	
			;
}
