/**
 * hujiachun
 */
package com.meizu.clock.test;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.jun_nama.test.utf7ime.helper.Utf7ImeHelper;
import android.graphics.Rect;
import android.os.RemoteException;
import android.view.KeyEvent;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.meizu.automation.By;
import com.meizu.automation.Expectation;
import com.meizu.automation.Steps;
import com.meizu.automation.constants.AutoException;
import com.meizu.automation.service.Element;
import com.meizu.sift.ImageMatch;
import com.meizu.sift.MatchPoint;
import com.meizu.test.util.AppInfo;
import com.meizu.test.util.ShellUtil;
import com.meizu.uibridge.BridgeUtil;
import com.meizu.uibridge.NotRootException;




/**
 * @author hujiachun
 */
public class ClockSanityTestCase extends TestScript{
	
	public static final int MIN = 2;
	public static final int SNOOZE = 10;
	
	/* (non-Javadoc)
	 * @see com.android.uiautomator.testrunner.UiAutomatorTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		System.out.println("-----setUp-----");
		this.pressHome();
//		this.exec("ime set com.meizu.scriptkeeper/.services.Utf7ImeService");
		ShellUtil.setUtf7Input();
		this.startApp(ClockUtil.CLOCK_PACKAGE, ClockUtil.CLOCK_ACTIVITY);
		this.clearClock();
	}
	
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#runTest()
	 */
	@Override
	protected void runTest() throws Throwable {
		// TODO Auto-generated method stub
		boolean isMA01 = this.checkUserDebug("mt6795", "userdebug");
		if (isMA01) {
		
			this.yunosTest("start com.android.alarmclock");
			
		}
		System.out.println("-----runTest-----");
		try {
			
			this.watcherS(); 
			this.runWatchers();
			super.runTest();
			this.sendResult(this.getName(), true);
			System.out.println(true);
			System.out.println(true);
			System.out.println(true);
		} catch (Throwable e) {
			// TODO: handle exception
			this.sendResult(getName(), false);
			System.out.println(false);
			System.out.println(false);
			System.out.println(false);
			if (isMA01) {
				
				if (ClockUtil.METHOD_P1S.contains(getName())) {
					this.yunosTest("stop com.android.alarmclock key");
				}
				else {
					this.yunosTest("stop com.android.alarmclock ");
				}
				
			}
			throw e;
		}
		
		if (isMA01) {

			if (ClockUtil.METHOD_P1S.contains(getName())) {
				this.yunosTest("stop com.android.alarmclock key");
			}
			else {
				this.yunosTest("stop com.android.alarmclock ");
			}
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.android.uiautomator.testrunner.UiAutomatorTestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
		System.out.println("-----tearDown-----");
		this.sendRecoverTime();
		this.exitApp(ClockUtil.CLOCK_PACKAGE);
//		this.exec("ime set com.meizu.flyme.input/com.meizu.input.MzInputService");
		ShellUtil.setSystemInput();

	}
	
	@Steps("冷启动")
	@Expectation("冷启动")   
	public void test0000Start1() throws IOException {
		boolean isMA01 = this.checkUserDebug("mt6795", "userdebug");
		if(isMA01){
			this.exitApp(AppInfo.PACKAGE_ALARMCLOCK);	
			this.startApp(AppInfo.PACKAGE_ALARMCLOCK, AppInfo.ACTIVITY_ALARMCLOCK);
			this.yunosTest("start com.android.alarmclock");
			this.sleep(5000);
			this.yunosTest("stop com.android.alarmclock key");	
		}
		
		
		
	}
	
	@Steps("非首次启动")
	@Expectation("非首次启动")   
	public void test0000Start2() throws IOException {

	}
	
	@Steps("进入新增闹钟")
	@Expectation("进入新增闹钟")   
	public void test0000AddClock() throws IOException, AutoException {
		boolean isMA01 = this.checkUserDebug("mt6795", "userdebug");
		if(isMA01){
			this.findElement(ClockUtil.ADD_CLOCK).click();
			this.sleep(2000);
		}
		
		
	}
	
	@Steps("保存闹钟")
	@Expectation("保存闹钟")   
	public void test0000SaveClock() throws IOException, AutoException {
		boolean isMA01 = this.checkUserDebug("mt6795", "userdebug");
		if(isMA01){
			this.findElement(ClockUtil.ADD_CLOCK).click();
			this.waitForText(ClockUtil.BTN_CONFIRM, 30000);
			this.clickByText(ClockUtil.BTN_CONFIRM);
			this.sleep(2000);
		}
		
		
	}
	
	
	@Steps("初始化")
	@Expectation("初始化")   
	public void test000init() throws IOException, AutoException {
		this.readyMusic();
		this.setLockScreen(60000 * 30);
	}
	
	
	@Steps("滑动/点击切换闹钟、世界时钟、秒表和计时器")
	@Expectation("切换正常")   
	public void test001ChangeGUI() throws AutoException{
		this.chooseType("闹钟");
		boolean alarm_exist = this.waitForElement(By.byId(ClockUtil.ALARM_CLOCK)) || this.waitForText("没有闹钟", 2000);
		this.chooseType("世界时钟");
		boolean world_exist = this.waitForElement(By.byId(ClockUtil.WORLD_CLOCK)) || this.waitForText("没有世界时钟", 2000);
		this.chooseType("秒表");
		boolean stopwatch_exist = this.waitForElement(By.byId(ClockUtil.STOPWATCH_GUI));
		this.chooseType("计时器");
		boolean timer_exist = this.waitForElement(By.byId(ClockUtil.TIME_VIEW));
		assertTrue("alarm_exist:" + alarm_exist + " world_exist:" + world_exist + " stopwatch_exist:" + 
		stopwatch_exist + " timer_exist:" + timer_exist, alarm_exist && world_exist && stopwatch_exist && timer_exist);
	}
	
	
	@Steps("1.无重复日时")
	@Expectation("1.节日不提醒置灰无法点击")   
	public void test002SetHoliday1() throws AutoException, RemoteException, NotRootException {
		this.chooseType("闹钟");
		this.clickAddClock();
		long start2 = System.currentTimeMillis();
		List<MatchPoint> image =  new ArrayList<MatchPoint>();
		//在30秒内匹配		
		while( ( image == null || image.isEmpty()) && System.currentTimeMillis() - start2 < 30000 ){
					image = ImageMatch.findObject("resources/image/close.png");
				}
		
		assertTrue("未找到图片", image.size() == 1);
	}
	
//	@Steps("2.有重复日无节日信息时,点取消/打开")
//	@Expectation("2.弹框提示用户,取消弹框/跳转日历")   
//	public void test002SetHoliday2() throws AutoException, RemoteException, NotRootException {
//		this.setSystemTime(2015, 9, 1);//设置为2015-10-1
//		ShellUtil.clearCache(AppInfo.PACKAGE_CALENDAR);
//		this.chooseType("闹钟");
//		this.clickAddClock();
//		this.openHoliday();
//		assertTrue("未弹出提示", isExistById(ClockUtil.MESSAGE, 10000));
//		
//		this.clickByText("取消");
//		assertTrue("提示未取消", !isExistById(ClockUtil.MESSAGE, 10000));
//		this.clickByTexts("节日不提醒", "打开日历");//点打开
//		assertTrue("未跳转日历", isExistById(ClockUtil.CALENDAR, 10000));
//	}
	
	
	@Steps("添加闹钟")
	@Expectation("添加成功（最多能添加50个闹钟）") 
	public void test003AddClock() throws AutoException{
//		this.clearClock();
		for (int i = 0; i < 50; i++) {
			this.clickAddClock();
			this.clickByText(ClockUtil.BTN_CONFIRM);
			this.sleep(500);
		}
		assertTrue("添加闹钟按钮没有消失", !this.findElement(ClockUtil.ADD_CLOCK).isEnabled());
		
	}
	
	
	@Steps("添加闹钟,点【确定】按钮")
	@Expectation("闹钟到点正常提醒") 
	public void test004AddClock() throws AutoException{
//		this.clearClock();
		this.clickAddClock();
		this.setClockTime(MIN);
		this.clickByText(ClockUtil.BTN_CONFIRM);
		this.assertClockAndClick(ClockUtil.BTN_CLOSE);
	}
	
	
	@Steps("添加有重复日的闹钟，点击【保存】")
	@Expectation("闹钟在相应的重复日时间点提醒") 
	public void test005AddRepeatClock() throws AutoException{
//		this.clearClock();
		this.clickAddClock();
		this.setRepeat();//设置当天闹钟重复
		this.setClockTime(MIN);
		this.saveClock();
		this.assertClockAndClick(ClockUtil.BTN_CLOSE);
		this.setLaterTime(6, 23, 59);
		this.assertClockAndClick(ClockUtil.BTN_CLOSE);
	}
	
	
	
	@Steps("设置推迟再响闹钟，等待推迟再响闹钟提醒点【推迟】")
	@Expectation("推迟再响闹钟正常响起，推迟后通知栏提示下次提醒时间") 
	public void test006SetSnoozeClock() throws AutoException {
//		this.clearClock();
		this.clickAddClock();
		this.setSnooze(SNOOZE);
		this.setClockTime(MIN);
		this.saveClock();
		this.assertClockAndClick(ClockUtil.BTN_SNOOZE);
		this.downNotify();
		if(this.findElement(By.byId(ClockUtil.NOTIFIATION)).exists()){
	    	boolean bool = this.findElement(By.byId(ClockUtil.NOTIFIATION)).toVerticalList().
	    			searchBy(By.byTextContains("闹钟已推迟，将于"), 20000);
			this.leaveNotify();
			assertTrue("2", bool);
	    }
		else {
			boolean bool = this.findElement(By.byTextContains("闹钟已推迟，将于")).exists();
			this.leaveNotify();
			assertTrue("3", bool);
		}
		
		this.setLaterTime(0, 0, 9);
		this.assertClockAndClick(ClockUtil.BTN_CLOSE);
	}
	
	
	@Steps("设置推迟再响闹钟，等待推迟再响闹钟提醒点【关闭】")
	@Expectation("推迟再响闹钟正常响起，确定后关闭提醒") 
	public void test007SetSnoozeClock() throws AutoException, IOException {
		boolean isMA01 = this.checkUserDebug("mt6795", "userdebug");
	
		if(isMA01){
			
		}else{

//		this.clearClock();
		this.clickAddClock();
		this.setSnooze(SNOOZE);
		this.setClockTime(MIN);
		this.saveClock();
		this.assertClockAndClick(ClockUtil.BTN_CLOSE);
		this.sleep(5000);
		this.exitApp(AppInfo.PACKAGE_ALARMCLOCK);
		this.startApp(AppInfo.PACKAGE_ALARMCLOCK, AppInfo.ACTIVITY_ALARMCLOCK);
		this.sleep(2000);
		boolean isChecked = this.findElement(By.byId(ClockUtil.CLOCK_SWITCH)).isChecked();
		System.out.println(isChecked);
		assertTrue("推迟闹钟开关未关闭", !isChecked);
		}
		
	}
	
	
	@Steps("有重复的推迟再响闹钟推迟后，关闭通知栏提示")
	@Expectation("该推迟再响闹钟在闹钟列表显示打开状态") 
	public void test008SetSnoozeClockNotification() throws AutoException{
//		this.clearClock();
		this.clickAddClock();
		this.setSnooze(SNOOZE);
		this.setRepeat();
		this.setClockTime(MIN);
		this.saveClock();
		this.assertClockAndClick(ClockUtil.BTN_SNOOZE);
		this.downNotify();
		this.clearSnooze();
		boolean isChecked = this.findElement(By.byId(ClockUtil.CLOCK_SWITCH)).isChecked();
		System.out.println(isChecked);
		assertTrue("推迟闹钟开关未打开", isChecked);
	}
	
	
	@Steps("无重复的推迟再响闹钟推迟后，关闭通知栏提示")
	@Expectation("该推迟再响闹钟在闹钟列表显示关闭状态") 
	public void test009SetSnoozeClockNotification() throws AutoException, IOException{
//		this.clearClock();
		boolean isMA01 = this.checkUserDebug("mt6795", "userdebug");
		
		if(isMA01){
			
		}else{
		this.clickAddClock();
		this.setSnooze(SNOOZE);
		this.setClockTime(MIN);
		this.saveClock();
		this.assertClockAndClick(ClockUtil.BTN_SNOOZE);
		this.downNotify();
		this.clearSnooze();
		this.sleep(5000);
		this.exitApp(AppInfo.PACKAGE_ALARMCLOCK);
		this.startApp(AppInfo.PACKAGE_ALARMCLOCK, AppInfo.ACTIVITY_ALARMCLOCK);
		this.sleep(2000);
		boolean isChecked = this.findElement(By.byId(ClockUtil.CLOCK_SWITCH)).isChecked();
	
		assertTrue("推迟闹钟开关未关闭", !isChecked);
		}
	}
	
	
	@Steps("设置静音铃声的闹钟，等待闹钟响起")
	@Expectation("闹钟铃声正常，并且关闭或推迟再响时闹钟无异常。") 
	public void test010SetMuteRing() throws AutoException{
//		this.clearClock();
		this.clickAddClock();
		this.setFlyme("", "静音");
		this.setClockTime(MIN);
		this.saveClock();
		this.findElement(By.byId(ClockUtil.ALARM_CLOCK)).click();
		assertTrue("设置铃声显示错误", this.isMusic("静音"));
		this.assertClockAndClick(ClockUtil.BTN_CLOSE);
	}
	
	
	@Steps("设置系统铃声的闹钟，等待闹钟响起")
	@Expectation("闹钟铃声正常，并且关闭或推迟再响时闹钟无异常。") 
	public void test011SetSytemRing() throws AutoException{
//		this.clearClock();
		this.clickAddClock();
		this.setFlyme("", "水晶");
		this.setClockTime(MIN);
		this.saveClock();
		this.findElement(By.byId(ClockUtil.ALARM_CLOCK)).click();
		assertTrue("设置铃声显示错误", this.isMusic("水晶"));
		this.assertClockAndClick(ClockUtil.BTN_CLOSE);
	}
	
	
	@Steps("设置Flyme音乐铃声的闹钟，等待闹钟响起")
	@Expectation("铃声显示音频名称，闹钟铃声正常，并且关闭或推迟再响时闹钟无异常。") 
	public void test012SetFlymeRing() throws AutoException, IOException{
		this.deleteMusic("Music", 0);
		this.assertMusicNumber("Music", 2);
//		this.clearClock();
		this.clickAddClock();
		this.setFlyme("Flyme 音乐", "zmusic1.mp3");
		this.setClockTime(MIN);
		this.saveClock();
		this.findElement(By.byId(ClockUtil.ALARM_CLOCK)).click();
		assertTrue("设置铃声显示错误", this.isMusic("zmusic1.mp3"));
		this.assertClockAndClick(ClockUtil.BTN_CLOSE);
		
	}
	
	
	@Steps("Flyme音乐至少有2个音频文件，设置随机铃声的闹钟，等待闹钟响起")
	@Expectation("铃声显示随机铃声，闹钟提醒随机播放完所有音频才结束") 
	public void test013SetRandomRing() throws AutoException, IOException{
		this.assertMusicNumber("Music", 2);
		this.deleteMusic("Music", 2);
//		this.clearClock();
		this.clickAddClock();
		this.setFlyme("Flyme 音乐", "随机播放");
		this.closeSnooze();
		this.setClockTime(MIN);
		this.saveClock();
		this.findElement(By.byId(ClockUtil.ALARM_CLOCK)).click();
		assertTrue("设置铃声显示错误", this.isMusic("随机播放"));
		this.pressBack();
		this.assertClock();
		this.downNotify();
		long before = new Date().getTime(); 
		System.out.println(before);
		boolean ietc = this.isExistByTextContains("已错过", 60000 * 2);
		this.leaveNotify();
        assertTrue("闹钟未结束", ietc);
		long after = new Date().getTime(); 
		System.out.println(after);
		long difference = (after - before) / 1000;
		System.out.println(difference);
	    assertTrue("响铃时间错误", difference > 50 && difference < 70);
	}
	
	
	@Steps("设置随机铃声的闹钟，删除Flyme音乐少于2条，等待闹钟响起")
	@Expectation("铃声显示随机铃声，闹钟提醒播放完音乐后自动结束") 
	public void test014SetRandomRing() throws AutoException, IOException{
		this.assertMusicNumber("Music", 2);
//		this.clearClock();
		this.clickAddClock();
		this.setFlyme("Flyme 音乐", "随机播放");
		this.closeSnooze();
		this.setClockTime(MIN + 1);
		this.saveClock();
		this.deleteMusic("Music", 1);
		this.findElement(By.byId(ClockUtil.ALARM_CLOCK)).click();
		assertTrue("设置铃声显示错误", this.isMusic("随机播放"));
		this.pressBack();
        this.assertClock();
        this.downNotify();
        boolean ietc = this.isExistByTextContains("已错过", 60000 * 2);
        this.leaveNotify();
        assertTrue("闹钟未结束", ietc);
		
	}
	
	
	@Steps("设置随机铃声的闹钟，删除所有Flyme音乐，等待闹钟响起")
	@Expectation("闹钟铃声变成晨露，铃声显示为晨露") 
	public void test015SetRandomRing() throws AutoException, IOException{
		this.assertMusicNumber("Music", 2);
//		this.clearClock();
		this.clickAddClock();
		this.setFlyme("Flyme 音乐", "随机播放");
		this.setClockTime(MIN + 1);
		this.saveClock();
		this.findElement(By.byId(ClockUtil.ALARM_CLOCK)).click();
		assertTrue("设置铃声显示错误", this.isMusic("随机播放"));
		this.pressBack();
		this.deleteMusic("Music", 0);
		this.findElement(By.byId(ClockUtil.ALARM_CLOCK)).click();
		assertTrue("设置铃声显示错误", this.isMusic("华尔兹"));
		this.assertClockAndClick(ClockUtil.BTN_CLOSE);
		
		
	}
	
	
	@Steps("设置Ringtones自定义铃声的闹钟，等待闹钟响起")
	@Expectation("铃声显示音频名称，闹钟铃声正常，并且关闭或推迟再响时闹钟无异常。") 
	public void test016SetRingtonesRing() throws AutoException, IOException{
		this.assertMusicNumber("Ringtones", 2);
//		this.clearClock();
		this.clickAddClock();
		this.setFlyme("Ringtones", "zmusic5.mp3");
		this.setClockTime(MIN);
		this.saveClock();
		this.findElement(By.byId(ClockUtil.ALARM_CLOCK)).click();
		assertTrue("设置铃声显示错误", this.isMusic("zmusic5.mp3"));
		this.assertClockAndClick(ClockUtil.BTN_CLOSE);
	}
	
	
	@Steps("设置Ringtones铃声的闹钟，删除设置的自定义铃声，等待闹钟响起")
	@Expectation("闹钟铃声变成晨露，铃声显示为晨露") 
	public void test017SetRingtonesRing() throws AutoException, IOException{
		this.assertMusicNumber("Ringtones", 1);
//		this.clearClock();
		this.clickAddClock();
		this.setFlyme("Ringtones", "zmusic5.mp3");
		this.setClockTime(MIN + 1);
		this.saveClock();
		this.findElement(By.byId(ClockUtil.ALARM_CLOCK)).click();
		assertTrue("设置铃声显示错误", this.isMusic("zmusic5.mp3"));
		this.pressBack();
		this.deleteMusic("Ringtones", 0);
		this.findElement(By.byId(ClockUtil.ALARM_CLOCK)).click();
		assertTrue("设置铃声显示错误", this.isMusic("华尔兹"));
		this.assertClockAndClick(ClockUtil.BTN_CLOSE);
	}
	
	
	@Steps("设置一个闹钟后，再设置第二个闹钟，查看第二个闹钟的默认铃声显示，并且等待第二个闹钟响起")
	@Expectation("第二个闹钟的默认铃声显示为第一个闹钟设置的铃声，响起为显示的铃声") 
	public void test018SetTwoClock() throws AutoException{
//		this.clearClock();
		this.clickAddClock();
		this.setFlyme("", "科技");
		this.saveClock();
		this.clickAddClock();
		assertTrue("二个闹钟的默认铃声显示错误", this.isMusic("科技"));
		this.setClockTime(MIN);
		this.saveClock();
		this.assertClockAndClick(ClockUtil.BTN_CLOSE);
	}
	
	
	@Steps("新增闹钟，开启节日不提醒，等待闹钟响起")
	@Expectation("开启时闹钟在节假日不提醒") 
	public void test019OpenHolidayClock() throws AutoException, RemoteException, NotRootException, IOException{
//		this.clearClock();
		boolean isMA01 = this.checkUserDebug("mt6795", "userdebug");
		
		if(isMA01){
			
		}else{
		this.clickAddClock();
		//清除日历缓存
		ShellUtil.clearCache(AppInfo.PACKAGE_CALENDAR);
		this.startApp(AppInfo.PACKAGE_CALENDAR, AppInfo.ACTIVITY_CALENDAR);
		this.sleep(2000);
		this.exitApp(AppInfo.PACKAGE_CALENDAR);
		this.openHoliday();
		this.setClockTime(MIN);
		this.setSystemTime(2015, 9, 1);//设置为2015-10-1
		this.saveClock();
		this.downNotify();
	    boolean clock_ring = this.findElement(ClockUtil.NOTIFIATION_SETTINGS).waitUntilGone(65000 * 2);
	    this.leaveNotify();
	    if (!clock_ring) {
	    	 int w = this.getDisplayWidth();
			 int h = this.getDisplayHeight();
			 this.click(w / 2, h / 1920 * 265);
		}
	    assertTrue("闹钟响起了", !clock_ring);
		}
	}
	
	@Steps("新增闹钟，关闭节日不提醒，等待闹钟响起")
	@Expectation("关闭后闹钟在重复周期都会提醒") 
	public void test020CloseHolidayClock() throws AutoException, IOException{
//		this.clearClock();
		boolean isMA01 = this.checkUserDebug("mt6795", "userdebug");
		
		if(isMA01){
			
		}else{
		this.clickAddClock();
		//清除日历缓存
		ShellUtil.clearCache(AppInfo.PACKAGE_CALENDAR);
		this.startApp(AppInfo.PACKAGE_CALENDAR, AppInfo.ACTIVITY_CALENDAR);
		this.sleep(2000);
		this.exitApp(AppInfo.PACKAGE_CALENDAR);
		this.setClockTime(MIN);
		this.setSystemTime(2015, 9, 1);//设置为2015-10-1
		this.saveClock();
		this.assertClockAndClick(ClockUtil.BTN_CLOSE);
		}
	}
	
	
	@Steps("1、编辑闹钟，编辑各项参数，点击【保存】")
	@Expectation("1、成功保存，闹钟按保存后的值提醒") 
	public void test021EditClock() throws AutoException{
//		this.clearClock();
		this.clickAddClock();
		this.setClockTime(MIN + 1);
		this.saveClock();
		this.enterAlarmPage();
		//编辑标签
//		this.findElement(By.byId(ClockUtil.CLOCK_LAY)).toVerticalList().searchByText("标签", 20000);
		this.swipe(getDisplayWidth() / 3, getDisplayHeight() / 5 * 3, getDisplayWidth() / 3, getDisplayHeight() / 5 * 2, 50);
		this.clickByText("标签");
		this.findElement(By.byId(ClockUtil.CLOCK_EDIT)).clearTextField();
		this.findElement(By.byId(ClockUtil.CLOCK_EDIT)).setText(Utf7ImeHelper.e("TEST"));
		this.clickByTexts(ClockUtil.BTN_CONFIRM, ClockUtil.BTN_SAVE);//确定保存
		assertTrue("闹钟不显示TEST", this.waitForText("TEST", 10000));
		this.assertClockAndClick(ClockUtil.BTN_CLOSE);
		
	}
	
	
	@Steps("1、编辑闹钟，编辑各项参数，点击【返回】")
	@Expectation("1、取消保存，闹钟按之前的值提醒") 
	public void test022EditClock() throws AutoException{
//		this.clearClock();
		this.clickAddClock();
		this.setClockTime(MIN + 1);
		this.saveClock();
		this.enterAlarmPage();
		//编辑标签
		this.swipe(getDisplayWidth() / 3, getDisplayHeight() / 5 * 3, getDisplayWidth() / 3, getDisplayHeight() / 5 * 2, 50);
		this.clickByText("标签");
		this.findElement(By.byId(ClockUtil.CLOCK_EDIT)).clearTextField();
		this.findElement(By.byId(ClockUtil.CLOCK_EDIT)).setText(Utf7ImeHelper.e("TEST"));
		this.clickByText(ClockUtil.BTN_CONFIRM);
		this.pressBack();//返回不保存
		assertTrue("闹钟显示TEST", !this.waitForText("TEST", 10000));
		this.assertClockAndClick(ClockUtil.BTN_CLOSE);
	}
	
   
	@Steps("1.设置有推迟的闹钟，锁屏，等待闹钟到点响起2.上滑关闭")
	@Expectation("1.闹钟正常响起，有推迟动画正常2.正常关闭闹钟") 
	public void test023LockClock() throws AutoException, RemoteException{
//		this.clearClock();
		this.clickAddClock();
		this.setSnooze(SNOOZE);
		this.setClockTime(MIN);
		this.saveClock();
		this.sleep(1000);
		this.closeScreen();
		assertTrue("锁屏闹钟未响起", waitForText("上滑停止闹钟", 120000));
		boolean snooze = this.waitForText("推迟", 12000);
		this.swipeClock();//解锁闹钟
		assertTrue("没有推迟", snooze);
		assertTrue("锁屏闹钟未关闭", waitForElement(ClockUtil.ADD_CLOCK, 20000));
	
	}


	@Steps("1.设置有推迟的闹钟，锁屏，等待闹钟到点响起2.点击推迟")
	@Expectation("1.闹钟正常响起，无推迟动画正常2.进入推迟再响") 
	public void test024LockClock() throws AutoException, RemoteException{
		this.setFloatVisibility(false);
		this.setLockScreen(60000 * 30);
//		this.clearClock();
		this.clickAddClock();
		this.setSnooze(SNOOZE);
		this.setClockTime(MIN);
		this.saveClock();
		this.sleep(1000);
		this.closeScreen();
		assertTrue("锁屏闹钟未响起", waitForText("上滑停止闹钟", 120000));
		this.clickByText("推迟");
		assertTrue("未锁屏", !waitForText("推迟", 8000));
		this.setLaterTime(0, 0, 9);
		assertTrue("锁屏闹钟未响起", waitForText("上滑停止闹钟", 80000));
		this.swipeClock();//解锁闹钟
	}
	
	
	@Steps("1.设置无推迟的闹钟，锁屏，等待闹钟到点响起2.上滑关闭")
	@Expectation("1.闹钟正常响起，无推迟动画正常2.正常关闭闹钟") 
	public void test025LockClock() throws AutoException, RemoteException{
//		this.clearClock();
		this.clickAddClock();
		this.closeSnooze();
		this.setClockTime(MIN);
		this.saveClock();
		this.sleep(2000);
		this.closeScreen();
		assertTrue("锁屏闹钟未响起", waitForText("上滑停止闹钟", 120000));
		boolean snooze = this.waitForText("推迟", 5000);
		this.swipeClock();//解锁闹钟
		assertTrue("有推迟", !snooze);
		assertTrue("锁屏闹钟未关闭", waitForElement(ClockUtil.ADD_CLOCK, 20000));
	}
	
	
	@Steps("亮屏闹钟响起点时击power/音量键")
	@Expectation("提醒界面变为锁屏提醒，震动消失，正常拉起音量控件") 
	public void test026ClickPower() throws AutoException, RemoteException{
//		this.clearClock();
		this.clickAddClock();
		this.setClockTime(MIN);
		this.saveClock();
		this.assertClock();
		this.closeScreen();//点击power键
		assertTrue("未锁屏提醒", waitForText("上滑停止闹钟", 20000));
		this.swipeClock();//解锁闹钟
	}
	
	
	@Steps("设置推迟，锁屏闹钟响起点时击power")
	@Expectation("设置推迟了会进入推迟闹钟") 
	public void test027ClickPower() throws AutoException, RemoteException{
//		this.clearClock();
		this.clickAddClock();
		this.setSnooze(SNOOZE);
		this.setClockTime(MIN);
	    this.saveClock();
	    this.closeScreen();
		assertTrue("锁屏闹钟未响起", waitForText("上滑停止闹钟", 120000));
		this.pressPower();
		assertTrue("未提醒进入推迟闹钟", waitForText("10分钟后再次响铃", 5000));
		assertTrue("提醒推迟闹钟未消失", this.findElement(By.byText("10分钟后再次响铃")).waitUntilGone(5000));
		this.setLaterTime(0, 0, 9);
		assertTrue("推迟闹钟未响起", waitForText("上滑停止闹钟", 120000));
		this.swipeClock();
	}
	
	
	@Steps("设置推迟，锁屏闹钟响起点时击音量键上")
	@Expectation("设置推迟了会进入推迟闹钟") 
	public void test028ClickVOLUME_UP() throws AutoException, RemoteException{
//		this.clearClock();
		this.clickAddClock();
		this.setSnooze(SNOOZE);
		this.setClockTime(MIN);
	    this.saveClock();
	    this.closeScreen();
		assertTrue("锁屏闹钟未响起", waitForText("上滑停止闹钟", 120000));
		this.pressKeyCode(KeyEvent.KEYCODE_VOLUME_UP);
		assertTrue("未提醒进入推迟闹钟", waitForText("10分钟后再次响铃", 5000));
		assertTrue("提醒推迟闹钟未消失", this.findElement(By.byText("10分钟后再次响铃")).waitUntilGone(5000));
		this.pressPower();
		this.swipeClock();
	}
	
	
	@Steps("设置推迟，锁屏闹钟响起点时击音量键下")
	@Expectation("设置推迟了会进入推迟闹钟") 
	public void test029ClickVOLUME_DOWN() throws AutoException, RemoteException{
//		this.clearClock();
		this.clickAddClock();
		this.setSnooze(SNOOZE);
		this.setClockTime(MIN);
	    this.saveClock();
	    this.closeScreen();
		assertTrue("锁屏闹钟未响起", waitForText("上滑停止闹钟", 120000));
		this.pressKeyCode(KeyEvent.KEYCODE_VOLUME_DOWN);
		assertTrue("未提醒进入推迟闹钟", waitForText("10分钟后再次响铃", 5000));
		assertTrue("提醒推迟闹钟未消失", this.findElement(By.byText("10分钟后再次响铃")).waitUntilGone(5000));
		this.pressPower();
		this.swipeClock();
	}
	
	
	@Steps("不设置推迟，锁屏闹钟响起点时击power")
	@Expectation("物理键能退出闹钟") 
	public void test030ClickPower() throws AutoException, RemoteException{
//		this.clearClock();
		this.clickAddClock();
		this.closeSnooze();
		this.setClockTime(MIN);
	    this.saveClock();
	    this.closeScreen();
		assertTrue("锁屏闹钟未响起", waitForText("上滑停止闹钟", 120000));
		this.pressPower();
		assertTrue("闹钟未退出", this.findElement(By.byText("上滑停止闹钟")).waitUntilGone(5000));
		assertTrue("已进入推迟闹钟", !waitForElement(By.byTextContains("再次响铃")));
		this.sleep(2000);
		this.pressPower();
		this.swipeClock();
	}
	
	
	@Steps("不设置推迟，锁屏闹钟响起点时击音量上")
	@Expectation("物理键能退出闹钟") 
	public void test031ClickVOLUME_UP() throws AutoException, RemoteException{
//		this.clearClock();
		this.clickAddClock();
		this.closeSnooze();
		this.setClockTime(MIN);
	    this.saveClock();
	    this.closeScreen();
		assertTrue("锁屏闹钟未响起", waitForText("上滑停止闹钟", 120000));
		this.pressKeyCode(KeyEvent.KEYCODE_VOLUME_UP);
		assertTrue("闹钟未退出", this.findElement(By.byText("上滑停止闹钟")).waitUntilGone(5000));
		assertTrue("已进入推迟闹钟", !waitForElement(By.byTextContains("再次响铃")));
		this.sleep(2000);
		this.pressPower();
		this.swipeClock();
	}
	
	
	@Steps("不设置推迟，锁屏闹钟响起点时击音量下")
	@Expectation("物理键能退出闹钟") 
	public void test032ClickVOLUME_DOWN() throws AutoException, RemoteException{
//		this.clearClock();
		this.clickAddClock();
		this.closeSnooze();
		this.setClockTime(MIN);
	    this.saveClock();
	    this.closeScreen();
		assertTrue("锁屏闹钟未响起", waitForText("上滑停止闹钟", 120000));
		this.pressKeyCode(KeyEvent.KEYCODE_VOLUME_DOWN);
		assertTrue("闹钟未退出", this.findElement(By.byText("上滑停止闹钟")).waitUntilGone(5000));
		assertTrue("已进入推迟闹钟", !waitForElement(By.byTextContains("再次响铃")));
		this.sleep(2000);
		this.pressPower();
		this.swipeClock();
	}
	
	
	@Steps("通知栏闹钟响起通知栏点击推迟")
	@Expectation("正常进入推迟再响") 
	public void test033NotificationClock() throws AutoException{
//		this.clearClock();
		this.clickAddClock();
		this.setSnooze(SNOOZE);
		this.setClockTime(MIN);
	    this.saveClock();
	    this.assertClock();
	    this.downNotify();
	    if(this.findElement(By.byId(ClockUtil.NOTIFIATION)).exists()){
    	   this.findElement(By.byId(ClockUtil.NOTIFIATION)).toVerticalList().
	    			searchBy(By.byText("推迟"), 20000);
		    this.clickByText("推迟");
			this.leaveNotify();
			
	    }
		else {
			this.clickByText("推迟");
			this.leaveNotify();
			
		}
	    
	   this.setLaterTime(0, 0, 9);
	   this.assertClockAndClick(ClockUtil.BTN_CLOSE);
	
	}
	
	
	@Steps("通知栏闹钟响起通知栏点击关闭")
	@Expectation("关闭提醒") 
	public void test034NotificationClock() throws AutoException{
		this.setFloatVisibility(false);
		this.setLockScreen(60000 * 30);
//		this.clearClock();
		this.clickAddClock();
		this.setSnooze(SNOOZE);
		this.setClockTime(MIN);
	    this.saveClock();
	    this.assertClock();
	    this.downNotify();
		if (this.findElement(By.byId(ClockUtil.NOTIFIATION)).exists()) {
			this.findElement(By.byId(ClockUtil.NOTIFIATION)).toVerticalList()
					.searchBy(By.byText("关闭"), 20000);
			this.clickByText("关闭");
			this.leaveNotify();

		} else {
			this.clickByText("关闭");
			this.leaveNotify();
		}
		this.setLaterTime(0, 0, 9);
		this.downNotify();
		boolean clock_ring = this.findElement(ClockUtil.NOTIFIATION_SETTINGS)
				.waitUntilGone(80000);
		this.leaveNotify();
		assertTrue("闹钟响起了", !clock_ring);
	
	}
	
	
	@Steps("天气成功定位1.设置有贪睡的闹钟，锁屏，等待闹钟到点响起")
	@Expectation("界面显示正常，正确显示天气信息，关闭提醒/进入推迟再响（界面2秒显示：推迟N分钟）") 
	public void test034WeatherSnoozeClock() throws AutoException, IOException{
		boolean isMA01 = this.checkUserDebug("mt6795", "userdebug");
		
		if(isMA01){
			
		}else{
		this.startApp(AppInfo.PACKAGE_WEATHER, AppInfo.ACTIVITY_WEATHER);
		this.swipeDown();
		this.sleep(5000);
		this.exitApp(AppInfo.PACKAGE_WEATHER);
		if(!isExistById(ClockUtil.ADD_CLOCK)){
			this.pressBack();
		}
//		this.clearClock();
		this.clickAddClock();
		this.setSnooze(SNOOZE);
		this.setClockTime(MIN);
	    this.saveClock();
	    this.sleep(1000);
	    this.pressPower();
	    assertTrue("未出现闹钟",   waitForText("上滑停止闹钟", 60000 * 2));
	    assertTrue("未出现天气闹钟", isExistByTextContains("°C", 10000));
	    this.clickByText("推迟");
	    this.setLaterTime(0, 0, 9);
	    boolean isexist = waitForText("上滑停止闹钟", 60000 * 2);
		this.swipeClock();
		assertTrue("推迟闹钟未响起", isexist); 
		}
	}
	
	
	@Steps("天气成功定位1.设置无贪睡的闹钟，锁屏，等待闹钟到点响起")
	@Expectation("界面显示正常，正确显示天气信息，关闭提醒") 
	public void test034WeatherClock() throws AutoException, IOException{
		boolean isMA01 = this.checkUserDebug("mt6795", "userdebug");
		
		if(isMA01){
			
		}else{
		this.startApp(AppInfo.PACKAGE_WEATHER, AppInfo.ACTIVITY_WEATHER);
		this.sleep(5000);
		this.exitApp(AppInfo.PACKAGE_WEATHER);
		if(!isExistById(ClockUtil.ADD_CLOCK)){
			this.pressBack();
		}
//		this.clearClock();
		this.clickAddClock();
		this.setClockTime(MIN);
	    this.saveClock();
	    this.sleep(1000);
	    this.pressPower();
	    assertTrue("未出现闹钟",   waitForText("上滑停止闹钟", 60000 * 2));
	    boolean isebtc = isExistByTextContains("°C", 10000);
	    this.swipeClock();
	    assertTrue("未出现天气闹钟", isebtc);
		}
	    
	}
	
	
	
	@Steps("1.编辑中点击【删除】2.列表长按后点击【删除】3.列表长按后点击【全选】再点击【删除】")
	@Expectation("1.成功删除闹钟，闹钟列表没有该闹钟2.闹钟正常被删除3.删除全部闹钟，闹钟列表为空显示“没有闹钟”") 
	public void test035DeleteClock() throws AutoException{
	    List<Element> clocks = this.findElements(ClockUtil.ALARM_CLOCK);
	    if (clocks.size() < 3) {
	    	for (int i = 0; i < 3 - clocks.size(); i++) {
	    		this.clickAddClock();
			    this.clickByText(ClockUtil.BTN_CONFIRM);
			    this.sleep(500);
			}
		}
	    List<Element> clocks_before = this.findElements(ClockUtil.ALARM_CLOCK);
	    System.out.println(clocks_before.size());;
	    this.findElements(ClockUtil.ALARM_CLOCK).get(0).longClick();
	    this.sleep(2000);
	    this.clickByText(ClockUtil.BTN_DELETE);
	    List<Element> clocks_after = this.findElements(ClockUtil.ALARM_CLOCK);
	    assertEquals(2, clocks_after.size());
	    
	    this.findElements(ClockUtil.ALARM_CLOCK).get(0).longClick();
	    this.clickByTexts("全选", ClockUtil.BTN_DELETE);
	    assertTrue("闹钟未删除", waitForText("没有闹钟"));
	    
	    this.clickAddClock();
	    this.clickByText(ClockUtil.BTN_CONFIRM);
	    this.sleep(500);
	    this.findElement(ClockUtil.ALARM_CLOCK).click();
	    this.clickByText(ClockUtil.BTN_DELETE);
	    assertTrue("闹钟未删除", waitForText("没有闹钟"));
	}
	
	/*
    public void test025Roboot() throws AutoException, RemoteException, InterruptedException, NotRootException{
    	System.out.println("before reboot 1");
    	if(!BridgeUtil.isSystemReboot()){
    		System.out.println("before reboot 2");
    		this.startApp(ClockUtil.CLOCK_PACKAGE, ClockUtil.CLOCK_ACTIVITY);
    		this.clearClock();
    		this.clickAddClock();
    		this.setClockTime(MIN);
    		this.saveClock();
    		this.sleep(2000);
            ShellUtil.exec("reboot -p");
    		System.out.println("before reboot 3");//重启前
    	}
    	System.out.println("after reboot 1");//重启后
    	boolean wft = waitForText("上滑停止闹钟", 120000);
    	this.swipeClock();
    	assertTrue("闹钟没有响起", wft);
    	System.out.println("after reboot 2");//重启后
  }
   
  */
    
    // --------                    世界时钟                                    --------
    
    @Steps("时钟默认显示")
	@Expectation("世界时钟默认显示时钟表盘、“北京” 城市、日期时间、添加按钮+") 
    public void test036WorldClock() throws IOException, Exception{
    	boolean isMA01 = this.checkUserDebug("mt6795", "userdebug");
    	
		if(isMA01){
			
		}else{
        ShellUtil.clearCache(AppInfo.PACKAGE_ALARMCLOCK);
        this.startApp(ClockUtil.CLOCK_PACKAGE, ClockUtil.CLOCK_ACTIVITY);
        this.chooseType("世界时钟");
        assertTrue("1", waitForElement(ClockUtil.WORLD_CLOCK));//时钟表盘
        assertTrue("2", waitForElement(ClockUtil.WORLD_DATE));//日期
        assertTrue("3", waitForElement(ClockUtil.WORLD_TIME));//时间
        assertTrue("4", waitForElement(ClockUtil.ADD_CLOCK));//添加按钮+
        assertTrue("5", findElement(ClockUtil.CITY_NAME).getText().equals("北京"));//“北京” 城市
		}
    }
    
    
    @Steps("1.添加时钟2.点击添加，选择一个城市")
	@Expectation("世界时钟添加成功") 
    public void test037AddWorldClock() throws AutoException {
    	 this.chooseType("世界时钟");
    	 this.clearWorldClock();
    	 this.isAddWorldClock("上海");
    	 boolean world_exist = this.waitForElement(By.byId(ClockUtil.WORLD_CLOCK)) || this.waitForText("没有世界时钟", 2000);
 		 assertTrue("未跳转世界时钟显示界面", world_exist);
 		 assertTrue("未显示上海", findElement(ClockUtil.CITY_NAME).getText().equals("上海"));
    	 
    }
    
    
    @Steps("搜索时钟1.在输入框输入条件，点击“X”")
   	@Expectation("优先显示搜索结果，点击“X”搜索输入清空") 
       public void test038SearchWorldClock() throws AutoException{
       	this.chooseType("世界时钟");
        this.clearWorldClock();
       	this.clickByElement(ClockUtil.ADD_CLOCK);
       	Element search = this.findElement(ClockUtil.WORLD_CITY_SEARCH);
		search.click();
		this.sleep(500);
		search.setText(Utf7ImeHelper.e("beijing"));
		assertTrue("未出现搜索结果", waitForText("北京"));
		this.click(ClockUtil.CLEAR_SEARCH);
		assertTrue("搜索结果未清空", !waitForText("北京"));
       }
    
    
    @Steps("搜索时钟在输入框输入条件，点击搜索结果")
	@Expectation("优先显示搜索结果，点击添加成功") 
    public void test039SearchWorldClock() throws AutoException{
    	this.chooseType("世界时钟");
    	this.clearWorldClock();
    	assertTrue("添加失败", this.isAddWorldClock("纽约"));
    	boolean world_exist = this.waitForElement(By.byId(ClockUtil.WORLD_CLOCK)) || this.waitForText("没有世界时钟", 2000);
 		assertTrue("未跳转世界时钟显示界面", world_exist);
    	assertTrue("搜索时钟未添加成功", waitForText("纽约"));
    }
    
    
    @Steps("添加时钟至上限")
	@Expectation("上限为50个世界城市，添加达上限后添加按钮消失") 
    public void test040AddWorldClockLimit() throws AutoException{
    	this.chooseType("世界时钟");
    	this.clearWorldClock();
    	for (int i = 0; i < 50; i++) {
			this.isAddWorldClock("");
		}
    	
    	assertTrue("世界时钟按钮变灰", !this.findElement(ClockUtil.ADD_CLOCK).isEnabled());
    	
    }
    
    
    @Steps("在设置-修改时间格式为24小时制/12小时制，只有本地时钟时查看时间显示")
   	@Expectation("本地时钟时间格式随着系统时间格式而变化") 
    public void test041LocalWorldClock() throws AutoException {
//    	this.chooseType("世界时钟");
//    	this.clearWorldClock();
//    	this.isAddWorldClock("beijing");
//    	this.setTimeFormat(true);//24H制式
//    	Element before_time = this.findElement(ClockUtil.CLEAR_PM_AM);//上午 || 下午显示ID
//    
//    	assertTrue("24H格式显示错误", !before_time.exists());
//    	
//    	this.setTimeFormat(false);//12H制式
//    	Element after_time = this.findElement(ClockUtil.CLEAR_PM_AM);
//    	
//    	assertTrue("12H格式显示错误", after_time.exists());
    	assertTrue(false);
    }
    
    
    @Steps("长按列表删除全部时钟")
   	@Expectation("成功删除全部世界时钟，界面显示没有世界时钟") 
    public void test042DeleteWorldClock() throws AutoException{
    	this.chooseType("世界时钟");
    	boolean display = this.waitForText("没有世界时钟", 5000);
    	if (display) {//如果界面显示“没有世界时钟”
			assertTrue(true);
		}
    	else {
    		List<Element> world_clocks = this.findElements(ClockUtil.WORLD_CLOCK);
			if (world_clocks.size() < 2) {//界面世界时钟个数<2
				this.isAddWorldClock("");
			}
	
			this.findElement(ClockUtil.WORLD_TIME).longClick();
			this.clickByText("全选");
		    this.clickByText(ClockUtil.BTN_DELETE);
			boolean clockexit = this.findElementByText("没有世界时钟").exists();
			assertTrue(clockexit);
			
		}
    }
    
    
    // --------                    秒表                                   --------
    
    
    @Steps("进入秒表，【开始】计时")
   	@Expectation("正常开始计时，计时动画正确") 
    public void test043StartStopwatch() throws AutoException {
    	this.chooseType("秒表");
    	this.clearStopWatch();
    	this.clickByText(ClockUtil.BTN_START);
    	String time = this.findElement(ClockUtil.STOPWATCH_GUI).getText();
    	System.out.println(time);
    	assertTrue("计时未开始", !time.equals("00:00"));
    }
    
    
    @Steps("计时中【暂停】计时后【继续】计时")
   	@Expectation("正常暂停计时，动画暂停，正常的接着暂停时时间继续计时") 
    public void test044TimingPause() throws AutoException {
    	this.chooseType("秒表");
    	this.clearStopWatch();
    	this.clickByText(ClockUtil.BTN_START);
        this.sleep(1000);
        this.clickByText(ClockUtil.BTN_PAUSE);
        assertTrue("未暂停", waitForText(ClockUtil.BTN_CONTINUE));
        this.clickByText(ClockUtil.BTN_CONTINUE);
        assertTrue("未继续", waitForText(ClockUtil.BTN_PAUSE));
    
    }
    
    
    @Steps("点击【计次】进行计次")
   	@Expectation("均能够正常计次，最新一次计次排在列表最上面，计次动画正常") 
    public void test045TiminCount() throws AutoException{
    	this.chooseType("秒表");
    	this.clearStopWatch();
    	this.clickByText(ClockUtil.BTN_START);
        this.sleep(1000);
        for(int i = 0; i < 5; i++){
			this.findElementByText(ClockUtil.BTN_COUNT).click();
		}
        this.clickByText(ClockUtil.BTN_PAUSE); 
        String text1 = this.findElement(By.byId(ClockUtil.STOPWATCH_COUNT).instance(0)).getText();
        String text2 = this.findElement(By.byId(ClockUtil.STOPWATCH_COUNT).instance(1)).getText();
        assertTrue("排序错误", text1.equals("05") && text2.equals("04"));
        
    }
    
    
    @Steps("点击音量键上键/下键，下键计次")
	@Expectation("均能够正常计次，最新一次计次排在列表最下面，计次动画正常")
	public void test046VolCount() throws AutoException{
    	this.chooseType("秒表");
    	this.clearStopWatch();
    	this.clickByText(ClockUtil.BTN_START);
    	for (int i = 0; i < 5; i++) {
			this.pressKeyCode(KeyEvent.KEYCODE_VOLUME_UP);
		}
    	for (int i = 0; i < 5; i++) {
			this.pressKeyCode(KeyEvent.KEYCODE_VOLUME_DOWN);
		}
    	String text1 = this.findElement(By.byId(ClockUtil.STOPWATCH_COUNT).instance(0)).getText();
    	assertTrue("音量键上键/下键错误", text1.equals("10"));
    }
    
    
    @Steps("【开始】计时后点【暂停】再点【复位】")
   	@Expectation("能够正常复位，秒表计时全归零，计次列表清空")
   	public void test047Reset() throws AutoException {
    	this.chooseType("秒表");
    	this.clearStopWatch();
    	this.clickByText(ClockUtil.BTN_START);
    	this.clickByTexts(ClockUtil.BTN_PAUSE, ClockUtil.BTN_RESET);
    	String time = this.findElement(ClockUtil.STOPWATCH_GUI).getText();
    	String mSecond = this.findElement(ClockUtil.STOPWATCH_SECOND).getText();
    	assertTrue("未复位成功", time.equals("00 00") && mSecond.equals("00"));
    	
    }
    
    
    @Steps("进入秒表，开始计时，按home键回到桌面，结束时钟进程")
   	@Expectation("后台计时正确，且通知栏有后台正在计时标志图，停止计时，通知栏没有秒表图标")
   	public void test048BackgroundTiming() throws AutoException {
    	this.chooseType("秒表");
    	this.clearStopWatch();
    	this.clickByText(ClockUtil.BTN_START);
    	this.pressHome();
    	this.sleep(1000);
    	this.downNotify();
    	if(this.findElement(By.byId(ClockUtil.NOTIFIATION)).exists()){
    		boolean result = this.findElement(By.byId(ClockUtil.NOTIFIATION)).toVerticalList().
	    			searchBy(By.byTextContains("正在计时("), 20000);
		
			this.leaveNotify();
			assertTrue("状态栏未显示计时", result);
	    }
		else {
			boolean result = this.findElement(By.byTextContains("正在计时(")).exists();
			
			this.leaveNotify();
			assertTrue("状态栏未显示计时", result);
		}
    }
    
    
    
    // --------                    计时器                                   --------
    
    
    
    @Steps("进入计时器，选择时长点【开始】，提醒弹框点击【确定】")
   	@Expectation("可正常选择时长，点击开始后进入倒计时界面开始倒计时，正常关闭倒计时提醒")
   	public void test049Timer() throws AutoException{
//    	ShellUtil.clearCache(AppInfo.PACKAGE_ALARMCLOCK);
//    	this.startApp(AppInfo.PACKAGE_ALARMCLOCK, AppInfo.ACTIVITY_ALARMCLOCK);
    	this.chooseType("计时器");
    	this.clearTimer();
//    	this.setTimer(2, 1);
    	this.setTimerTime();
    	this.clickByText(ClockUtil.BTN_START);
    	assertTrue("未跳转倒计时界面", waitForText(ClockUtil.BTN_PAUSE) && waitForText(ClockUtil.BTN_CANCEL));
    	assertTrue("未出现倒计时结束提醒", waitForText("计时结束", 70000));
    	this.clickByText(ClockUtil.BTN_CONFIRM);
    	assertTrue("倒计时结束提醒未消失", !waitForText("计时结束", 5000));
    }
    
    
    @Steps("正在倒计时中点【暂停】后点【继续】")
   	@Expectation("成功暂停倒计时，动画暂停，继续倒计时，动画和时间显示正常")
   	public void test050TimerPause() throws AutoException{
    	this.chooseType("计时器");
    	this.clearTimer();
    	this.setTimerTime();
    	this.clickByText(ClockUtil.BTN_START);
    	assertTrue("未跳转倒计时界面", waitForText(ClockUtil.BTN_PAUSE) && waitForText(ClockUtil.BTN_CANCEL));
    	this.clickByText(ClockUtil.BTN_PAUSE);
    	assertTrue("未暂停", waitForText(ClockUtil.BTN_CONTINUE));
    	this.sleep(2000);
    	this.clickByText(ClockUtil.BTN_CONTINUE);
    	assertTrue("未继续 ", waitForText(ClockUtil.BTN_PAUSE));
    	assertTrue("未出现倒计时结束提醒", waitForText("计时结束", 58000));
    	this.clickByText(ClockUtil.BTN_CONFIRM);
    }
    
    
    @Steps("计时器铃声设置静音")
   	@Expectation("提醒铃声为所设定的值")
   	public void test051SetMuteTimer() throws AutoException{
    	this.chooseType("计时器");
    	this.clearTimer();
    	this.setTimerTime();
    	this.setFlyme("", "静音");
    	boolean sure_set = this.findElement(By.byText("铃声")).toDown(By.byText("静音")).exists();
    	assertTrue("未设置成功", sure_set);
    	this.setTimerTime();
    	this.clickByText(ClockUtil.BTN_START);
    	assertTrue("未跳转倒计时界面", waitForText(ClockUtil.BTN_PAUSE) && waitForText(ClockUtil.BTN_CANCEL));
    	assertTrue("未出现倒计时结束提醒", waitForText("计时结束", 70000));
    	this.clickByText(ClockUtil.BTN_CONFIRM);
    }
    
    
    @Steps("计时器铃声设置系统铃声")
   	@Expectation("提醒铃声为所设定的值")
   	public void test052SetSystemRing() throws AutoException{
    	this.chooseType("计时器");
    	this.clearTimer();
    	this.setTimerTime();
    	this.setFlyme("", "科技");
    	boolean sure_set = this.findElement(By.byText("铃声")).toDown(By.byText("科技")).exists();
    	assertTrue("未设置成功", sure_set);
    	this.setTimerTime();
    	this.clickByText(ClockUtil.BTN_START);
    	assertTrue("未跳转倒计时界面", waitForText(ClockUtil.BTN_PAUSE) && waitForText(ClockUtil.BTN_CANCEL));
    	assertTrue("未出现倒计时结束提醒", waitForText("计时结束", 70000));
    	this.clickByText(ClockUtil.BTN_CONFIRM);
    }
    
    
    @Steps("计时器铃声设置自定义铃声")
   	@Expectation("提醒铃声为所设定的值")
   	public void test053SetSystemRing() throws AutoException, IOException{
    	this.assertMusicNumber("Music", 1);
    	this.chooseType("计时器");
    	this.clearTimer();
    	this.setTimerTime();
    	this.setFlyme("Flyme 音乐", "zmusic5.mp3");
    	boolean sure_set = this.findElement(By.byText("铃声")).toDown(By.byText("zmusic5.mp3")).exists();
    	assertTrue("未设置成功", sure_set);
    	this.setTimerTime();
    	this.clickByText(ClockUtil.BTN_START);
    	assertTrue("未跳转倒计时界面", waitForText(ClockUtil.BTN_PAUSE) && waitForText(ClockUtil.BTN_CANCEL));
    	assertTrue("未出现倒计时结束提醒", waitForText("计时结束", 70000));
    	this.clickByText(ClockUtil.BTN_CONFIRM);
    	
    }
    
    
    @Steps("通知栏点击关闭")
   	@Expectation("正常关闭倒计时提醒")
   	public void test054Notification () throws AutoException{
    	this.chooseType("计时器");
    	this.clearTimer();
    	this.setTimerTime();
    	this.clickByText(ClockUtil.BTN_START);
    	assertTrue("未跳转倒计时界面", waitForText(ClockUtil.BTN_PAUSE) && waitForText(ClockUtil.BTN_CANCEL));
    	assertTrue("未出现倒计时结束提醒", waitForText("计时结束", 70000));
    	this.openNotification();
    	
    	if(this.findElement(By.byId(ClockUtil.NOTIFIATION)).exists()){
	    	this.findElement(By.byId(ClockUtil.NOTIFIATION)).toVerticalList().
		    			searchBy(By.byText("计时器"), 20000);
	    
//	    	Rect bounds = this.findElement(By.byText("计时器")).getBounds();
//	    	System.out.println(bounds.centerX());
//	    	this.swipe(bounds.centerX(), bounds.top, bounds.centerX(), bounds.bottom + bounds.bottom - bounds.top, 50);
//			this.clickByText("完成");
			this.leaveNotify();
			this.sleep(3000);
				
		    }
			else {
//				Rect bounds = this.findElement(By.byText("计时器")).getBounds();
//		    	System.out.println(bounds.centerX());
//		    	this.swipe(bounds.centerX(), bounds.top, bounds.centerX(), bounds.bottom + bounds.bottom - bounds.top, 50);	
//				
			this.clickByText("完成");
			this.leaveNotify();
			this.sleep(3000);
				
			}
    	assertTrue("未离开状态栏", waitForElement(By.byId(ClockUtil.TIME_VIEW)));
    	
    	assertTrue("未出现倒计时结束提醒", !waitForText("计时结束"));
    }
    
    
    @Steps("倒计时响起点击power键")
   	@Expectation("物理键能退出倒计时提醒")
   	public void test055TimerClickPower() throws AutoException, RemoteException{
     	this.chooseType("计时器");
    	this.clearTimer();
    	this.setTimerTime();
    	this.clickByText(ClockUtil.BTN_START);
    	assertTrue("未跳转倒计时界面", waitForText(ClockUtil.BTN_PAUSE) && waitForText(ClockUtil.BTN_CANCEL));
    	assertTrue("未出现倒计时结束提醒", waitForText("计时结束", 70000));
    	this.closeScreen();
    	this.sleep(500);
    	assertTrue("未消失倒计时结束提醒", !waitForText("计时结束", 4000));
    	
    }
    
    
    @Steps("倒计时响起点击音量上键")
   	@Expectation("物理键能退出倒计时提醒")
   	public void test056TimerClickVOLUME_UP() throws AutoException{
     	this.chooseType("计时器");
    	this.clearTimer();
    	this.setTimerTime();
    	this.clickByText(ClockUtil.BTN_START);
    	assertTrue("未跳转倒计时界面", waitForText(ClockUtil.BTN_PAUSE) && waitForText(ClockUtil.BTN_CANCEL));
    	assertTrue("未出现倒计时结束提醒", waitForText("计时结束", 70000));
    	this.pressKeyCode(KeyEvent.KEYCODE_VOLUME_UP);
    	this.sleep(500);
    	assertTrue("未出现倒计时结束提醒", !waitForText("计时结束", 4000));
    	
    }
    
    
    @Steps("倒计时响起点击音量下键")
   	@Expectation("物理键能退出倒计时提醒")
   	public void test057TimerClickVOLUME_DOWN() throws AutoException{
     	this.chooseType("计时器");
    	this.clearTimer();
    	this.setTimerTime();
    	this.clickByText(ClockUtil.BTN_START);
    	assertTrue("未跳转倒计时界面", waitForText(ClockUtil.BTN_PAUSE) && waitForText(ClockUtil.BTN_CANCEL));
    	assertTrue("未出现倒计时结束提醒", waitForText("计时结束", 70000));
    	this.pressKeyCode(KeyEvent.KEYCODE_VOLUME_DOWN);
    	this.sleep(1000);
    	assertTrue("未出现倒计时结束提醒", !waitForText("计时结束", 4000));
    	
    }
    
    
    @Steps("停止计时器1.正在计时，点【取消】，提示框点【取消】2.正在计时，点【取消】，提示框点【确定】")
   	@Expectation("1.弹出框退出，继续倒计时2.正常取消倒计时，恢复初始界面")
   	public void test058TimerCancel() throws AutoException{
     	this.chooseType("计时器");
    	this.clearTimer();
    	this.setTimerTime();
    	this.clickByText(ClockUtil.BTN_START);
    	assertTrue("未跳转倒计时界面", waitForText(ClockUtil.BTN_PAUSE) && waitForText(ClockUtil.BTN_CANCEL));
    	this.clickByText(ClockUtil.BTN_CANCEL);
//    	this.clickByTexts(ClockUtil.BTN_CANCEL, ClockUtil.BTN_CANCEL);
//    	assertTrue("取消失败", waitForText(ClockUtil.BTN_PAUSE) && waitForText(ClockUtil.BTN_CANCEL));
//    	this.clickByTexts(ClockUtil.BTN_CANCEL, ClockUtil.BTN_CONFIRM);
    	assertTrue("确定失败", waitForElement(By.byId(ClockUtil.TIME_VIEW)));
    }
    
    
    @Steps("设置倒计时时间比锁屏时间长，关闭屏幕长亮，等待倒计时提醒")
   	@Expectation("关闭屏幕长亮跟随系统设置熄屏")
   	public void test059CloseKeepScreen() throws AutoException, RemoteException{
     	this.chooseType("计时器");
    	this.clearTimer();
    	this.setLockScreen(30000);
    	this.setClockScreen(false);
    	this.setTimerTime();
    	this.clickByText(ClockUtil.BTN_START);
    	this.sleep(39000);
    	boolean result = !UiDevice.getInstance().isScreenOn();
    	System.out.println(result);
    	if (result) {
    		this.pressPower();
        	this.swipeClock();
        	this.setLockScreen(60000 * 30);
        	assertTrue("1", result);
		}
    	else {
    		this.setLockScreen(60000 * 30);
    		assertTrue("2", false);
		}
    	
    }
    
    
    @Steps("设置倒计时时间比锁屏时间长，开启屏幕长亮，等待倒计时提醒")
   	@Expectation("开启屏幕长亮处于计时器界面不会熄屏")
   	public void test060OpenKeepScreen() throws AutoException, RemoteException{
     	this.chooseType("计时器");
    	this.clearTimer();
    	this.setLockScreen(30000);
    	this.setClockScreen(true);
    	this.setTimerTime();
    	this.clickByText(ClockUtil.BTN_START);
    	this.sleep(39000);
    	boolean result = UiDevice.getInstance().isScreenOn();
    	this.setLockScreen(60000 * 30);
    	assertTrue("屏幕被灭", result);
    	
    }
    
    

    @Steps("忽略提醒,等待倒计时提醒，忽略提醒")
   	@Expectation("倒计时提醒1分钟后自动关闭提醒")
   	public void test061IgnoreReminder() throws AutoException{
        this.chooseType("计时器");
	    this.clearTimer();
	    this.setTimerTime();
	    this.clickByText(ClockUtil.BTN_START);
	    assertTrue("未跳转倒计时界面", waitForText(ClockUtil.BTN_PAUSE) && waitForText(ClockUtil.BTN_CANCEL));
	    assertTrue("未出现倒计时结束提醒", waitForText("计时结束", 65000));
        assertTrue("提醒未忽略", waitForText(ClockUtil.BTN_START, 65000));
    }
    	
    
    @Steps("开始计时，按home键回到桌面，后台倒计时，结束时钟进程")
   	@Expectation("进入后台倒计时，且通知栏有后台正在倒计时图标，计时器后台继续计时，通知栏保留图标，倒计时结束会提醒")
   	public void test062BackgroundTimer() throws AutoException{
    	this.chooseType("计时器");
 	    this.clearTimer();
 	    this.setTimerTime();
 	    this.clickByText(ClockUtil.BTN_START);
 	    assertTrue("未跳转倒计时界面", waitForText(ClockUtil.BTN_PAUSE) && waitForText(ClockUtil.BTN_CANCEL));
 	    this.pressHome();
 	    this.downNotify();
 	    if(this.findElement(By.byId(ClockUtil.NOTIFIATION)).exists()){
   		boolean result = this.findElement(By.byId(ClockUtil.NOTIFIATION)).toVerticalList().
	    			searchBy(By.byTextContains("计时器"), 20000);
		
			this.leaveNotify();
			assertTrue("状态栏未显示计时器", result);
	    }
		else {
			boolean result = this.findElement(By.byTextContains("计时器")).exists();
			
			this.leaveNotify();
			assertTrue("状态栏未显示计时器", result);
		}
 	   assertTrue("未出现倒计时结束提醒", waitForText("计时结束", 65000));
 	    
    }
    
    
	
}