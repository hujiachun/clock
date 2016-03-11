/**
 * hujiachun
 */
package com.meizu.clock.test;

import java.util.List;

import jp.jun_nama.test.utf7ime.helper.Utf7ImeHelper;
import android.graphics.Rect;

import com.meizu.automation.By;
import com.meizu.automation.Expectation;
import com.meizu.automation.Steps;
import com.meizu.automation.constants.AutoException;
import com.meizu.automation.service.Element;
import com.meizu.test.util.ShellUtil;

/**
 * @author hujiachun
 */
public class ClockMultiLangCase extends TestScript{

	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		System.out.println("-----setUp-----");
		this.pressHome();
		ShellUtil.setUtf7Input();
		this.startApp(ClockUtil.CLOCK_PACKAGE, ClockUtil.CLOCK_ACTIVITY);
	
	}
	
	
	@Override
	protected void runTest() throws Throwable {
		// TODO Auto-generated method stub
		
		System.out.println("-----runTest-----");
		try {	
			this.watcherS(); 
			this.runWatchers();
			super.runTest();
			ShellUtil.sendResult(this.getName(), true);
			
			System.out.println(true);
			System.out.println(true);
			System.out.println(true);
		} catch (Throwable e) {
			// TODO: handle exception
			ShellUtil.sendResult(getName(), false);
			
			System.out.println(false);
			System.out.println(false);
			System.out.println(false);
			throw e;
}
	}
	

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

	@Steps("闹钟设置")
	@Expectation("闹钟设置")   
	public void test001() throws AutoException{
		this.findElement(By.byClassName("android.support.v7.widget.LinearLayoutCompat")).findElement(By.byIndex(0)).click();
		this.setLockScreen(60000 * 30);
		List<Element> layouts = this.findElements("com.android.alarmclock:id/clock_layout");
		if(layouts.size() > 1){
			this.findElement("com.android.alarmclock:id/clock_layout").longClick();//删除闹钟
			this.click("com.android.alarmclock:id/mz_action_multi_choice_select_all_item");
			this.click("com.android.alarmclock:id/delete_menu");
		}
		else if(layouts.size() == 1){
			this.findElement("com.android.alarmclock:id/clock_layout").longClick();//删除闹钟
			this.click("com.android.alarmclock:id/delete_menu");
		}
		this.findElement(ClockUtil.ADD_CLOCK).click();
		this.sleep(2000);
		this.setClockTime(5);
		
		for(int i=0; i<4; i++){
			this.click("com.android.alarmclock:id/alarm_layout", 0);//设置重复clock
			this.click("android:id/text1", i);
			this.sleep(200);
			if(i== 3){
				for (int j = 0; j < 7; j++) {
					this.click("com.android.alarmclock:id/background_img", j);
					this.sleep(200);
				}
				this.click("android:id/button2");
				this.click("android:id/text1", 3);
				this.click("android:id/button1");
			}
		}
		
		this.click("com.android.alarmclock:id/alarm_layout", 1);//设置铃声
		this.click("com.android.alarmclock:id/text1");
		this.click("com.android.alarmclock:id/name", 0);
		this.pressBack();
		this.click("com.android.alarmclock:id/name", 1);
		this.pressBack();
		this.pressBack();
		
		Rect rect = findElement(ClockUtil.ADJUST_VOLUME_).getBounds();//设置音量
		int voLumeY = findElement(ClockUtil.ADJUST_VOLUME_).getBounds().centerY();
		for(int i = 0; i < 9; i++)
		{
			this.click(rect.left * 16 / 15, (rect.top + rect.bottom) / 2);
			}
		
		this.swipeUp();
		
		this.click("com.android.alarmclock:id/switchWidget");//设置震动
		
		for(int i=0; i<4; i++){
			this.click("com.android.alarmclock:id/lable_layout", 0);//设置推迟
			this.click("android:id/text1", i);
			this.sleep(200);
		}
		
		
		this.click("com.android.alarmclock:id/lable_layout", 1);//设置标签
		this.click("android:id/button2");
		this.click("com.android.alarmclock:id/lable_layout", 1);
		this.setTextById("com.android.alarmclock:id/edit", Utf7ImeHelper.e("test"));
		this.click("android:id/button1");
		
		if(isExistById("com.android.alarmclock:id/action_ok")){
			this.click("com.android.alarmclock:id/action_ok");
		}
		else{
			this.click("com.android.alarmclock:id/right_button");
		}
		
		this.click("com.android.alarmclock:id/clock_layout");
		if(isExistById("com.android.alarmclock:id/action_cancel")){
			this.click("com.android.alarmclock:id/action_cancel");
		}
		else{
			this.click("com.android.alarmclock:id/left_button");	
		}
		
		
	}
	
	@Steps("首界面")
	@Expectation("首界面")   
	public void test002() throws AutoException{
		this.findElement(By.byClassName("android.support.v7.widget.LinearLayoutCompat")).findElement(By.byIndex(0)).click();
		this.findElement(ClockUtil.ADD_CLOCK).click();
		if(isExistById("com.android.alarmclock:id/action_ok")){
			this.click("com.android.alarmclock:id/action_ok");
		}
		else{
			this.click("com.android.alarmclock:id/right_button");
		}
		this.findElement("com.android.alarmclock:id/clock_layout").longClick();//删除闹钟
		this.click("android:id/checkbox");
		this.click("com.android.alarmclock:id/mz_action_multi_choice_close_item");
		this.findElement("com.android.alarmclock:id/clock_layout").longClick();
		this.click("com.android.alarmclock:id/mz_action_multi_choice_select_all_item");
		this.click("com.android.alarmclock:id/mz_action_multi_choice_select_all_item");
		this.click("com.android.alarmclock:id/delete_menu");
		
	}
	
	@Steps("世界时钟")
	@Expectation("世界时钟")   
	public void test003() throws AutoException{
		this.findElement(By.byClassName("android.support.v7.widget.LinearLayoutCompat")).findElement(By.byIndex(1)).click();
		this.isAddWorldClock("");
		
	}
	
	@Steps("世界时钟编辑")
	@Expectation("世界时钟")   
	public void test004() throws AutoException{
		this.findElement(By.byClassName("android.support.v7.widget.LinearLayoutCompat")).findElement(By.byIndex(1)).click();
		this.isAddWorldClock("");
		this.findElement("com.android.alarmclock:id/analog_clock").longClick();
		this.click("com.android.alarmclock:id/mz_action_multi_choice_close_item");//取消
		List<Element> layouts = this.findElements("com.android.alarmclock:id/analog_clock");
		if(layouts.size() > 1){
			this.findElement("com.android.alarmclock:id/analog_clock").longClick();//删除闹钟
			this.click("com.android.alarmclock:id/mz_action_multi_choice_select_all_item");
			this.click("com.android.alarmclock:id/delete_menu");
		}
		else if(layouts.size() == 1){
			this.findElement("com.android.alarmclock:id/analog_clock").longClick();//删除闹钟
			this.click("com.android.alarmclock:id/delete_menu");
		}
		
		
		
	}
	
	@Steps("秒表")
	@Expectation("秒表")   
	public void test005() throws AutoException{//秒表
		this.findElement(By.byClassName("android.support.v7.widget.LinearLayoutCompat")).findElement(By.byIndex(2)).click();
		this.sleep(1000);
		int start_X = this.findElement("com.android.alarmclock:id/btn_start").getBounds().centerX();
		int start_Y = this.findElement("com.android.alarmclock:id/btn_start").getBounds().centerY();
		int right_X = this.findElement("com.android.alarmclock:id/btn_start").getBounds().right;
		int left_X = this.findElement("com.android.alarmclock:id/btn_start").getBounds().left;
		int lengh = (right_X - left_X) / 4;
		this.click("com.android.alarmclock:id/btn_start");
		this.sleep(1000);
		this.click(start_X - lengh, start_Y);//计次
		this.sleep(1000);
		this.click(start_X + lengh, start_Y);//暂停
		this.sleep(1000);
		this.click(start_X - lengh, start_Y);//复位
	    
	}
	
	@Steps("计时器")
	@Expectation("计时器")   
	public void test006() throws AutoException{//计时器
		this.findElement(By.byClassName("android.support.v7.widget.LinearLayoutCompat")).findElement(By.byIndex(3)).click();
		this.setTimerTime();
		this.click("com.android.alarmclock:id/btn_start_counting");
		
		this.waitForElement("android:id/button1", 65000);
		this.click("android:id/button1");
		
		this.click("com.android.alarmclock:id/set_timer_tone");//设置铃声
		this.click("com.android.alarmclock:id/text1");
		this.click("com.android.alarmclock:id/name", 0);
		this.pressBack();
		this.click("com.android.alarmclock:id/name", 1);
		this.pressBack();
		this.pressBack();
		
		this.click("com.android.alarmclock:id/vibrate_switch");
		this.click("com.android.alarmclock:id/vibrate_switch");
		this.click("com.android.alarmclock:id/keep_on_switch");
		this.click("com.android.alarmclock:id/keep_on_switch");
		
	}
	
	@Steps("计时器")
	@Expectation("计时器")   
	public void test007() throws AutoException{//计时器
		this.findElement(By.byClassName("android.support.v7.widget.LinearLayoutCompat")).findElement(By.byIndex(3)).click();
		this.setTimerTime();
		this.sleep(2000);
		int start_X = this.findElement("com.android.alarmclock:id/btn_start_counting").getBounds().centerX();
		int start_Y = this.findElement("com.android.alarmclock:id/btn_start_counting").getBounds().centerY();
		int right_X = this.findElement("com.android.alarmclock:id/btn_start_counting").getBounds().right;
		int left_X = this.findElement("com.android.alarmclock:id/btn_start_counting").getBounds().left;
		int lengh = (right_X - left_X) / 4;
		this.click("com.android.alarmclock:id/btn_start_counting");
		this.sleep(1000);
		this.click(start_X - lengh, start_Y);//暂停
		this.sleep(1000);
		this.click(start_X - lengh, start_Y);//继续
		this.sleep(1000);
		this.click(start_X + lengh, start_Y);//取消
		
		
		}
	
	@Steps("闹钟响起")
	@Expectation("闹钟响起")   
	public void test008() throws AutoException{
		this.findElement(By.byClassName("android.support.v7.widget.LinearLayoutCompat")).findElement(By.byIndex(0)).click();
		List<Element> layouts = this.findElements("com.android.alarmclock:id/clock_layout");
		if(layouts.size() > 1){
			this.findElement("com.android.alarmclock:id/clock_layout").longClick();//删除闹钟
			this.click("com.android.alarmclock:id/mz_action_multi_choice_select_all_item");
			this.click("com.android.alarmclock:id/delete_menu");
		}
		else if(layouts.size() == 1){
			this.findElement("com.android.alarmclock:id/clock_layout").longClick();//删除闹钟
			this.click("com.android.alarmclock:id/delete_menu");
		}
		this.findElement(ClockUtil.ADD_CLOCK).click();
		this.sleep(1000);
		this.setClockTime(2);
		this.swipeUp();
		this.click("com.android.alarmclock:id/lable_layout", 0);//设置推迟
		this.click("android:id/text1", 2);
		if(isExistById("com.android.alarmclock:id/action_ok")){
			this.click("com.android.alarmclock:id/action_ok");
		}
		else{
			this.click("com.android.alarmclock:id/right_button");
		}
		this.downNotify();
		this.findElement(ClockUtil.NOTIFIATION_SETTINGS).waitUntilGone(60000 * 3);
		int w = this.getDisplayWidth();
		int h = this.getDisplayHeight();
		this.click(w / 18 * 5, h / 1920 * 265);//点击推迟
		this.setLaterTime(0, 0, 9);
		this.downNotify();
		this.findElement(ClockUtil.NOTIFIATION_SETTINGS).waitUntilGone(60000 * 3);
		this.click(w / 1080 * 775, h / 1920 * 265);//点关闭
	}
	
	@Steps("锁屏闹钟响起")
	@Expectation("锁屏闹钟响起")   
	public void test009() throws AutoException{//锁屏闹钟
		this.findElement(By.byClassName("android.support.v7.widget.LinearLayoutCompat")).findElement(By.byIndex(0)).click();
		int x = this.findElement("com.android.alarmclock:id/mz_action_bar_tab_scroll_view").getBounds().centerX();
		int y = this.findElement("com.android.alarmclock:id/mz_action_bar_tab_scroll_view").getBounds().centerY();
		List<Element> layouts = this.findElements("com.android.alarmclock:id/clock_layout");
		if(layouts.size() > 1){
			this.findElement("com.android.alarmclock:id/clock_layout").longClick();//删除闹钟
			this.click("com.android.alarmclock:id/mz_action_multi_choice_select_all_item");
			this.click("com.android.alarmclock:id/delete_menu");
		}
		else if(layouts.size() == 1){
			this.findElement("com.android.alarmclock:id/clock_layout").longClick();//删除闹钟
			this.click("com.android.alarmclock:id/delete_menu");
		}
		this.findElement(ClockUtil.ADD_CLOCK).click();
		this.setClockTime(2);
		this.click("com.android.alarmclock:id/lable_layout", 0);//设置推迟
		this.click("android:id/text1", 2);
		if(isExistById("com.android.alarmclock:id/action_ok")){
			this.click("com.android.alarmclock:id/action_ok");
		}
		else{
			this.click("com.android.alarmclock:id/right_button");
		}
		this.sleep(1000);
		this.downNotify();
		this.findElement(ClockUtil.NOTIFIATION_SETTINGS).waitUntilGone(60000 * 3);
		this.sleep(1000);
		this.click(x, y);//进入锁屏闹钟
		this.click("com.android.alarmclock:id/snooze_button");
		this.setLaterTime(0, 0, 9);
		this.downNotify();
		this.findElement(ClockUtil.NOTIFIATION_SETTINGS).waitUntilGone(60000 * 3);
		this.sleep(1000);
		this.click(x, y);//进入锁屏闹钟
		this.sleep(2000);
		this.swipeClock();
		List<Element> layout_after = this.findElements("com.android.alarmclock:id/clock_layout");
		if(layout_after.size() > 1){
			this.findElement("com.android.alarmclock:id/clock_layout").longClick();//删除闹钟
			this.click("com.android.alarmclock:id/mz_action_multi_choice_select_all_item");
			this.click("com.android.alarmclock:id/delete_menu");
		}
		else if(layout_after.size() == 1){
			this.findElement("com.android.alarmclock:id/clock_layout").longClick();//删除闹钟
			this.click("com.android.alarmclock:id/delete_menu");
		}
		
	}
}
