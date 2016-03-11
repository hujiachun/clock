/**
 * hujiachun
 */
package com.meizu.clock.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;

import jp.jun_nama.test.utf7ime.helper.Utf7ImeHelper;
import android.R.integer;
import android.graphics.Rect;
import android.os.RemoteException;
import android.view.KeyEvent;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.meizu.automation.AutoWatcher;
import com.meizu.automation.By;
import com.meizu.automation.DefaultElement;
import com.meizu.automation.constants.AutoException;
import com.meizu.automation.service.Element;
import com.meizu.test.common.AutoAllInOneTestCase;
import com.meizu.test.util.AppInfo;
import com.meizu.test.util.FileUtil;
import com.meizu.test.util.ShellUtil;




/**
 * @author hujiachun
 */

public class TestScript extends AutoAllInOneTestCase{
	
	
	/**
	 * 切换时间制式
	 * @param bool
	 * @date 2015年11月11日上午9:57:20
	 * @author hujiachun
	 */
	public void setTimeType(boolean bool){
		String command = "am broadcast -a " + (bool? "sk.action.SET_TIME --ez USE_24H true" : "sk.action.SET_TIME --ez USE_24H false");
        try {
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		
		
	}

	/**
     * 设置悬浮球可见状态
     * @param status true:可见, false:隐藏
     */
    public void setFloatVisibility(boolean status){
        String command = "am broadcast -a " + (status? "sk.action.FLOAT_ON":"sk.action.FLOAT_OFF");
        try {
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        
    }
	
	
	/**
	 * 重写startApp,加入权限申请
	 */
	@Override
	public void startApp(String packageName, String launchActivity){
		super.startApp(packageName, launchActivity);
	
	       Element accessibility = new DefaultElement(By.byText("权限申请").id("android:id/alertTitle"));
	       Element permit = new DefaultElement(By.byText("始终允许").className("android.widget.CheckBox"));

	                if (permit.exists() && accessibility.exists()) {
	                    try {
	                        new DefaultElement(By.byId("android:id/button1")).click();
	                    } catch (AutoException e) {
	                        e.printStackTrace();
	                    }
	                   
	                }
	                
	     
	}
	
	
	
   /**
    * 解压文件
    * 
    */
	public void insetMusic(){
		
		String cpmic = "/sdcard/cpmic";
		String[]musics =
//			{ "zmusic1.mp3", "zmusic2.mp3","zmusic3.mp3",
			{ "zmusic4.mp3", "zmusic5.mp3"};
		byte[]bits = new byte [1024];
		int length;
			
		for(String music : musics){
		File file = new File(cpmic);
			try {
				if(!file.exists()){
					file.mkdir();
				}
				File cfot = new File(cpmic, music);
					InputStream is = this.getClass().getResourceAsStream("/resources/music/" + music);
					FileOutputStream  fos = new FileOutputStream(cfot);
					while ((length = is.read(bits)) != -1) {
						fos.write(bits, 0, length);
					}
				is.close();
				fos.flush();
				fos.close();
			} catch (Exception e) {
			}
		}
		
	}
	
	
	/**
	 * 准备音乐文件
	 * 
	 * @date 2015年8月10日上午11:34:41
	 * @author hujiachun
	 * @throws AutoException 
	 * @throws IOException 
	 */
	public void readyMusic() throws IOException, AutoException{
		ShellUtil.remove("/sdcard/cpmic");
		ShellUtil.remove("/sdcard/Music");
		ShellUtil.remove("/sdcard/Ringtones");
		new File("/sdcard/Music/").mkdirs();
		new File("/sdcard/Ringtones/").mkdirs();
		this.insetMusic();
		this.copyMusic("Music");
		this.copyMusic("Ringtones");
		this.exitApp(AppInfo.PACKAGE_FILEMANAGER);
//		this.sendWIFI("MZ-MEIZU", "20runMZ15");
		this.sleep(10000);
		
	}
	
	
	/**
	 * 复制文件
	 * @throws IOException
	 * @throws AutoException
	 * @date 2015年8月10日下午3:08:28
	 * @author hujiachun
	 */
	public void copyMusic(String toFile) throws IOException, AutoException{
		this.exitApp(AppInfo.PACKAGE_FILEMANAGER);
		this.startApp(AppInfo.PACKAGE_FILEMANAGER, ClockUtil.FILE_ACTIVITY);
		this.clickTextForFile("cpmic");
		this.findElement(By.byId(ClockUtil.FILE_ITEM).instance(0)).longClick();
		this.waitForText("全选", 10000);
		this.clickByText("全选");
		this.findElement(By.byId(ClockUtil.FILE_MENU)).findElement(By.byIndex(3)).click();//点击更多
		this.clickByText("复制");
		this.pressBack();
		this.findElement(By.byId(ClockUtil.FILE_LIST)).toVerticalList().scrollTextInto(toFile);
		this.sleep(1000);
		
		//寻找文件夹
		int fileString_top = findElementByText(toFile).getBounds().top;
		int fileText_bottom = findElement(By.byId(ClockUtil.FILE_TEXT)).getBounds().bottom;
		
		int fileString_bottom = findElementByText(toFile).getBounds().bottom;
		int copy_top = this.findElement(By.byId(ClockUtil.FILE_COPY_MENU)).getBounds().top;
			
		if (fileString_top < fileText_bottom) {//需要点击的文件顶端 < 存储盘文本下方
				this.swipeDownLittle();	
			}
			
		if (fileString_bottom > copy_top) {//需要点击的文件低部 被复制栏 遮住
			    this.swipeUpLittle();	
			}
		this.clickByText(toFile);
		this.sleep(1000);
	    this.clickByText("复制到这里");
	    this.sleep(10000);
	    
		
	}
	
	
	/**
	 * 在文件中点击某个文件夹名进入
	 * @param fileString
	 * @throws AutoException
	 * @date 2015年8月10日下午3:11:31
	 * @author hujiachun
	 */
	public void clickTextForFile(String fileString) throws AutoException{
		this.findElement(By.byId(ClockUtil.FILE_LIST)).toVerticalList().scrollTextInto(fileString);
		this.sleep(1000);
			
		int fileString_top = findElementByText(fileString).getBounds().top;
		int fileText_bottom = findElement(By.byId(ClockUtil.FILE_TEXT)).getBounds().bottom;
		
		int fileString_bottom = findElementByText(fileString).getBounds().bottom;
			
		if (fileString_top < fileText_bottom) {//需要点击的文件顶端 < 存储盘文本下方
				this.swipeDownLittle();	
			}
			
		if (fileString_bottom == this.getDisplayHeight()) {//需要点击的文件低部 隐藏
			    this.swipeUpLittle();	
			}
			
			
		this.clickByText(fileString);
		this.sleep(1000);
	}
	
	
    /**
     * 删除文件夹内文件
     * @param fileString
     * @param Lastmusic  剩余个数
     * @throws IOException
     * @throws AutoException
     * @date 2015年8月10日下午5:47:49
     * @author hujiachun
     */
    public void deleteMusic(String fileString , int lastMusic) throws IOException, AutoException{
	    this.exitApp(AppInfo.PACKAGE_FILEMANAGER);
	    this.startApp(AppInfo.PACKAGE_FILEMANAGER, ClockUtil.FILE_ACTIVITY);
		this.findElement(By.byId(ClockUtil.FILE_LIST)).toVerticalList().scrollTextInto(fileString);
		this.sleep(1000);
			
		this.clickTextForFile(fileString);
		
		if(findElementByText("空文件夹").exists() ){
			if (lastMusic > 0) {
				copyMusic(fileString);
				this.deleteMusic(fileString, lastMusic);
			}
			else {
				this.exitApp(AppInfo.PACKAGE_FILEMANAGER);
			}

			}

		
		else{
			int items = this.findElement(By.byId(ClockUtil.FILE_LIST)).getChildCount() ;
				
			while(items > lastMusic){
					
		    this.findElement(By.byId(ClockUtil.FILE_ITEM).instance(0)).longClick();
			this.sleep(1000);	
			this.findElement(By.byId(ClockUtil.FILE_DELETE)).click();
			this.findElement(By.byTextContains("删除")).click();
			items--;
				}
		    assertTrue(items == lastMusic);
			this.pressBack();
			this.pressBack();

			}	
			
	    }
	
    
    
    /**
     * 确定文件夹内文件至少个数
     * @param fileString
     * @param Lastmusic
     * @throws AutoException
     * @date 2015年8月10日下午5:50:02
     * @author hujiachun
     * @throws IOException 
     */
    public void assertMusicNumber(String fileString , int lastMusic) throws AutoException, IOException{
    	this.exitApp(AppInfo.PACKAGE_FILEMANAGER);
    	this.startApp(AppInfo.PACKAGE_FILEMANAGER, ClockUtil.FILE_ACTIVITY);
		this.findElement(By.byId(ClockUtil.FILE_LIST)).toVerticalList().scrollTextInto(fileString);
		this.sleep(1000);
			
		this.clickTextForFile(fileString);
		if(findElementByText("空文件夹").exists()){
			copyMusic(fileString);
			this.assertMusicNumber(fileString, lastMusic);

			}
		else {
			int items = this.findElement(By.byId(ClockUtil.FILE_LIST)).getChildCount() ;
			if (items < lastMusic) {
				this.copyMusic(fileString);
				this.exitApp(AppInfo.PACKAGE_FILEMANAGER);
			}
			else {
				this.exitApp(AppInfo.PACKAGE_FILEMANAGER);
			}
		}
		
		
		
    }
    
	
	/**
	 * 点击选项
	 * @param type （闹钟、世界时钟、秒表、计时器）
	 * @date 2015年7月30日上午9:57:31
	 * @author hujiachun
	 * @throws AutoException 
	 */
	public void chooseType(String type) throws AutoException{
	
//		UiDevice.getInstance().waitForIdle(10000);
//		if(this.findElement(ClockUtil.ALARM_CLOCK).exists()){
//			this.findElement(ClockUtil.ALARM_CLOCK).longClick();
//			if(waitForText("全选", 2000)){
//				this.clickByText("全选");
//			}
//			this.clickByText(ClockUtil.BTN_DELETE);
//		}


		if(!new UiObject(new UiSelector().description(type)).waitForExists(3000)){
			pressHome();
			this.startApp(AppInfo.PACKAGE_ALARMCLOCK, AppInfo.ACTIVITY_ALARMCLOCK);
			this.sleep(1000);
		}
		this.findElement(By.byDescription(type)).click();
	}
	
	
	/**
	 * 点击添加闹钟按钮
	 * @date 2015年7月30日上午11:20:03
	 * @author hujiachun
	 * @throws AutoException 
	 */
	public void clickAddClock() throws AutoException {
		chooseType("闹钟");
		this.findElement(ClockUtil.ADD_CLOCK).click();
		
	}
	
	
	/**
	 * 设置闹钟响起等待时间
	 * @param min 
	 * @throws AutoException
	 * @date 2015年7月30日上午11:27:29
	 * @author hujiachun
	 */
	public  void setClockTime(int min) throws AutoException {
		int mX = findElement(ClockUtil.CLOCK_MIN).getBounds().centerX();
		int mY = findElement(ClockUtil.CLOCK_MIN).getBounds().centerY();
		int hX = findElement(ClockUtil.CLOCK_HOUR).getBounds().centerX();
		int hY = findElement(ClockUtil.CLOCK_HOUR).getBounds().centerY();
		Calendar cl = Calendar.getInstance();
		int mm = cl.get(Calendar.MINUTE);
		int ss = cl.get(Calendar.SECOND);
		this.sleep(200);	
		
		
			if(mm > 57){
				
				UiDevice.getInstance().click(hX, hY * 4 / 3);//h点击1次
				this.sleep(100);
				for (int i = 0; i < min ; i++){
					UiDevice.getInstance().click(mX, mY * 4 / 3);//m点击min次
				    this.sleep(500);
				  }
			}
			else{
				if(ss <= 5 || ss > 50){
					for (int i = 0; i < min + 1; i++){
						
						UiDevice.getInstance().click(mX, mY * 4 / 3);//m点击min次
					    this.sleep(500);
					  }
				}
				
				else{
					for(int i = 0; i < min - 1; i++){
						
						UiDevice.getInstance().click(mX, mY * 4 / 3);//m点击 min - 1 次
						this.sleep(500);
					}
					
				}
			}
		
		
	
		
		
		/**
		
		//若当时1<秒针<35,且分针不等于59，闹钟等待时间为设置的min 
		if(ss < 35 && ss >= 0 && mm != 59){
			for (int i =0; i < min - 1; i++){
				UiDevice.getInstance().click(mX, mY * 4 / 3);
				this.sleep(500);
			   }
		}
		
		else{
		if(mm < 58){
		for (int i = 0; i < min; i++){
			UiDevice.getInstance().click(mX, mY * 4 / 3);
			this.sleep(500);
		   }
		}
		
		else{
			UiDevice.getInstance().click(hX, hY * 4 / 3);
			for (int i = 0; i < (60 - mm) ; i++){
				UiDevice.getInstance().click(mX, mY * 4 / 3);
			this.sleep(500);
			  }
		    }
		 }
		 
		*/
	}
	
	
	/**
	 * 设置当天闹钟重复
	 * @throws UiObjectNotFoundException
	 * @throws AutoException
	 * @date 2015年7月30日上午11:38:24
	 * @author hujiachun
	 */
	public void setRepeat()throws  AutoException{
		String day = null;
		Calendar cl = Calendar.getInstance();
		int week = cl.get(Calendar.DAY_OF_WEEK) - 1;
		
        switch (week) {
        case 0:
			day = "日";
			break;
		case 1:
			day = "一";
			break;
		case 2:
			day = "二";
			break;
		case 3:
			day = "三";
			break;
		case 4:
			day = "四";
			break;
		case 5:
			day = "五";
			break;	
		case 6:
			day = "六";
			break;	
		}
        findElement(By.byText(day)).click();
	}
	
	
	/**
	 * 新设置当天闹钟重复
	 * @param bool 是否自定义
	 * @param text 重复选项 
	 * @throws UiObjectNotFoundException
	 * @throws AutoException
	 * @date 2015年7月30日上午11:38:24
	 * @author hujiachun
	 */
	public void setNewRepeat(boolean bool, String text)throws  AutoException{
		String day = null;
		Calendar cl = Calendar.getInstance();
		int week = cl.get(Calendar.DAY_OF_WEEK) - 1;
		
		this.clickByTexts("重复", text);
		
		if (bool) {
			 switch (week) {
		        case 0:
					day = "日";
					break;
				case 1:
					day = "一";
					break;
				case 2:
					day = "二";
					break;
				case 3:
					day = "三";
					break;
				case 4:
					day = "四";
					break;
				case 5:
					day = "五";
					break;	
				case 6:
					day = "六";
					break;	
				}
		        findElement(By.byText(day)).click();
		        this.clickByText("确定");
		}
       
	}
	
	
	/**
	 * 设置休息日
	 * @param rest tue ：周六或周日, false : 周一至周五
	 * @date 2015年10月10日下午4:28:46
	 * @author hujiachun
	 */
	public void setDayOfWeek(boolean rest){
		
		Calendar cl = Calendar.getInstance();
		int week = cl.get(Calendar.DAY_OF_WEEK) - 1;
		if (rest) {
			 switch (week) {
		        case 0:
					
					break;
				case 1:
					this.setLaterTime(5, 0, 0);
					break;
				case 2:
					this.setLaterTime(4, 0, 0);
					break;
				case 3:
					this.setLaterTime(3, 0, 0);
					break;
				case 4:
					this.setLaterTime(2, 0, 0);
					break;
				case 5:
					this.setLaterTime(1, 0, 0);
					break;	
				case 6:
					
					break;	
				}
			
		}
		
		else {
			 switch (week) {
		        case 0:
		        	this.setLaterTime(1, 0, 0);
					break;
				case 1:
					
					break;
				case 2:
					
					break;
				case 3:
					
					break;
				case 4:
					
					break;
				case 5:
					
					break;	
				case 6:
					this.setLaterTime(2, 0, 0);
					break;	
				}
			 
		}
		Calendar cl2 = Calendar.getInstance();
		 System.out.println(cl2.get(Calendar.DAY_OF_WEEK) - 1);
	}
	
	
	/**
	 * 设置贪睡时间
	 * @param min 贪睡时间
	 * @date 2015年7月30日上午11:43:26
	 * @author hujiachun
	 * @throws AutoException 
	 */
	public void setSnooze(int min) throws AutoException{
		Element snooze = this.findElement(By.byId(ClockUtil.SNOOZE_TIME));
		
		//贪睡未打开时，点击打开
		if(snooze.getText().equals("推迟再响")){
			this.findElement(By.byId(ClockUtil.SNOOZE_ICON)).click();
			this.sleep(500);
		}
		this.findElement(By.byId(ClockUtil.SNOOZE_TIME)).click();
	    this.findElement(By.byText(min + "分钟")).click();
	}
	
	
	/**
	 * 关闭推迟
	 * @throws AutoException
	 * @date 2015年8月5日下午5:53:51
	 * @author hujiachun
	 */
	public void closeSnooze() throws AutoException{
		Element snooze = this.findElement(By.byId(ClockUtil.SNOOZE_TIME));
		
		if(!snooze.getText().equals("推迟再响")){
			this.findElement(By.byId(ClockUtil.SNOOZE_ICON)).click();
			this.sleep(500);
		}
	}
	
	
	/**
	 * 设置贪睡
	 * @param min
	 * @throws AutoException
	 * @date 2015年10月12日下午2:30:30
	 * @author hujiachun
	 */
	public void setNewSnooze(int min) throws AutoException{
		this.swipeUpLittle();
		this.clickByText("稍后提醒");
		this.findElement(By.byText(min + "分钟")).click();
		this.swipeDown();
	}
	
	/**
	 * 新关闭推迟
	 * 
	 * @date 2015年10月12日下午2:31:43
	 * @author hujiachun
	 * @throws AutoException 
	 */
	public void closeNewSnooze() throws AutoException{
		this.swipeUpLittle();
		this.clickByText("稍后提醒");
		this.findElement(By.byText("关闭")).click();
		this.swipeDown();
	}
	
	
	/**
	 * 关闭屏幕
	 * 
	 * @date 2015年8月5日下午5:55:06
	 * @author hujiachun
	 * @throws RemoteException 
	 */
	public void closeScreen() throws RemoteException{
		this.pressKeyCode(KeyEvent.KEYCODE_POWER);
//		ShellUtil.exec("input keyevent 26");//按电源键
		this.sleep(500);
		if(UiDevice.getInstance().isScreenOn()){
			this.pressKeyCode(KeyEvent.KEYCODE_POWER);
		}
	}
	
	
	
	/**
	 * 抓取toast
	 * @param tag
	 * @param text toast值
	 * @return
	 * @date 2015年7月30日下午2:02:30
	 * @author hujiachun
	 */
	public boolean logParsing(String tag, String text) {
		Process p = null;
		BufferedReader reader = null;
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String testLog = "logcat -v time";
		try {
			p = Runtime.getRuntime().exec(testLog);
			reader = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			stringBuilder.setLength(0);
			Date wifiTime = new Date(System.currentTimeMillis() + 10000);
			
			while (((line = reader.readLine()) != null)
					&& wifiTime.after(new Date(System.currentTimeMillis()))) {
				stringBuilder.append(line);
				if ( line.contains(text)) {
					System.out.println(line);
					return true;
				}
			}
			return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (p != null) {
			p.destroy();
		}
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	
	/**
	 * 打开节假日不提醒开关
	 * @date 2015年7月30日下午2:12:32
	 * @author hujiachun
	 * @throws AutoException 
	 */
	public void  openHoliday() throws AutoException {
		this.clickByTexts("日", "一", "二", "三", "四", "五", "六", "节日不提醒");
	}
	
	
	/**
	 * 连续点击text
	 * @param texts
	 * @throws AutoException
	 * @date 2015年7月30日下午2:15:36
	 * @author hujiachun
	 */
	public void clickByTexts(String... texts) throws AutoException {
		for (String text : texts) {
			this.findElement(By.byText(text)).click();
			this.sleep(500);
		}
		
	}
	
	
	/**
	 * 设置铃声
	 * @param type 若类型为空，选择系统默认铃声
	 * @param music
	 * @throws AutoException
	 * @date 2015年7月30日下午2:25:35
	 * @author hujiachun
	 */
	public void setFlyme(String type, String music) throws AutoException{
		//若类型为空，选择系统默认铃声
    	if(type.equals("")){
    		findElementByText("铃声").click();
    		this.findElement(ClockUtil.LIST).toVerticalList().scrollTextInto(music);
    		this.clickByText(music);
    		this.sleep(200);
    		this.pressBack();
    		
    	}
    	
    	else{
    		
    	findElementByText("铃声").click();
    	findElementByText(type).click();
    	this.sleep(1000);
    	findElementByText(music).click();
        this.pressBack();
        this.pressBack();
    	}
    }
	
	
	/**
	 * 保存闹钟
	 * @return
	 * @throws AutoException
	 * @date 2015年7月30日下午2:29:49
	 * @author hujiachun
	 */
	public  boolean saveClock()throws AutoException{
		Rect rect = findElement(ClockUtil.ADJUST_VOLUME_).getBounds();
		int voLumeY = findElement(ClockUtil.ADJUST_VOLUME_).getBounds().centerY();
		
		for(int i = 0; i < 9; i++)
		{
			this.click(rect.left * 16 / 15, (rect.top + rect.bottom) / 2);
			}
		return findElement(By.byText(ClockUtil.BTN_CONFIRM)).click();
	}
	
	
	/**
	 * 发送广播恢复系统时间
	 * 
	 * @date 2015年7月30日下午2:38:24
	 * @author hujiachun
	 */
	public void sendRecoverTime() {
		String command = "am broadcast -a sk.action.SET_TIME --ez AUTO true";		
		Process p = null;
			
		try {
			p = Runtime.getRuntime().exec(command);
			} catch (IOException e) {
				e.printStackTrace();
		}
		try {
			p.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
	
	/**
	 * 截图和收集结果
	 * @param value 用例名称
	 * @param result 测试结果
	 * @date 2015年7月30日下午2:42:20
	 * @author hujiachun
	 */
	public void sendResult(String value, boolean result){
		String command = "am broadcast -a sk.action.RESULT --es NAME " + value + " --ez RESULT " + result;
		
		try {
			Process p = Runtime.getRuntime().exec(command);
			p.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 删除闹钟
	 * @throws AutoException
	 * @date 2015年7月30日下午3:27:45
	 * @author hujiachun
	 */
	public void clearClock() throws AutoException {
		this.chooseType("闹钟");
		if (!findElement(By.byDescription("闹钟")).exists()) {
			this.chooseType("闹钟");
		}
		
		int sum = 0;
        while (this.findElement(ClockUtil.ALARM_CLOCK).exists() && sum < 50) {
        	this.findElement(ClockUtil.ALARM_CLOCK).longClick();
			this.clickByText(ClockUtil.BTN_DELETE);
			sum++;
		  }
		
		
//		
//		if(this.findElement(ClockUtil.ALARM_CLOCK).exists()){
//			this.findElement(ClockUtil.ALARM_CLOCK).longClick();
//			if(waitForText("全选", 2000)){
//				this.clickByText("全选");
//			}
//			this.clickByText(ClockUtil.BTN_DELETE);
//		}
		
		boolean clockexit = this.findElementByText("没有闹钟").exists();
//		assertTrue(clockexit);
	}
	
	
	/**
	 * 清除世界时钟
	 */
	public void clearWorldClock() throws AutoException {
		this.chooseType("世界时钟");
		int sum = 0;
//        while (this.findElement(ClockUtil.ALARM_CLOCK).exists() && sum < 50) {
//			this.enterAlarmPage();
//			this.clickOnText(ClockUtil.BTN_DELETE);
//			sum++;
//		  }
		if(this.findElement(ClockUtil.WORLD_TIME).exists()){
			this.findElement(ClockUtil.WORLD_TIME).longClick();
			if(waitForText("全选", 2000)){
				this.clickByText("全选");
			}
			this.clickByText(ClockUtil.BTN_DELETE);
		}
		
		boolean clockexit = this.findElementByText("没有世界时钟").exists();
		assertTrue(clockexit);
	}
	
	
	/**
	 * 进入闹钟
	 * @date 2015年7月30日下午3:25:35
	 * @author hujiachun
	 * @throws AutoException 
	 */
	public void enterAlarmPage() throws AutoException {
		this.findElement(ClockUtil.ALARM_CLOCK).click();
	}
	
	
	/**
	 * 设置dayup天 、hourup小时、minup分 后
	 * @param dayup
	 * @param hourup
	 * @param minup
	 * @date 2015年7月30日下午4:51:55
	 * @author hujiachun
	 */
	public  void setLaterTime(int dayup, int hourup, int minup){
		Calendar cl = Calendar.getInstance();
		cl.add(Calendar.MINUTE, dayup * 24 * 60 + hourup * 60 + minup );
		
	 	int year = cl.get(Calendar.YEAR);
	 	int month = cl.get(Calendar.MONTH);
	 	int day = cl.get(Calendar.DAY_OF_MONTH);
	 	int hour = cl.get(Calendar.HOUR_OF_DAY);
	 	int minute = cl.get(Calendar.MINUTE);
	 	int second = cl.get(Calendar.SECOND);
	 	
	 	System.out.println(year + "-" + month + "-" + day+"  " + hour + ":" + minute + ":" + second);
	 	sendSetTime(year, month, day, hour, minute, second);
	}
	
	
	/**
	 * 发送广播设置时间
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param min
	 * @param sec
	 * @date 2015年7月30日下午4:53:08
	 * @author hujiachun
	 */
	public void sendSetTime(int year, int month, int day, int hour, int min,
			int sec) {
		String command = "am broadcast -a sk.action.SET_TIME --ei YEAR "
				+ year
				+ " --ei MONTH "
				+ month
				+ " --ei DAY "
				+ day
				+ "  --ei HOUR "
				+ hour
				+ " --ei MINUTE "
				+ min
				+ " --ei SECOND " 
				+ sec + " --ez AUTO false";
		
		 this.exec(command);
	}
	
	
	/**
	 * 下拉通知栏
	 * 
	 * @date 2015年7月31日上午9:22:13
	 * @author hujiachun
	 */
	public void downNotify(){
		UiDevice.getInstance().openNotification();
		this.sleep(1000);
	}
	
	
	/**
	 * 离开通知栏
	 * 
	 * @date 2015年7月31日上午9:22:39
	 * @author hujiachun
	 */
	public void leaveNotify(){
	    int deviceHeight = this.getDisplayHeight();
	    int deviceWidth = this.getDisplayWidth();
	 
	    this.drag(deviceHeight, deviceWidth / 2, deviceHeight / 2, deviceWidth / 2, 100);
	}
	
	
	/**
	 * 关闭闹钟通知栏提示
	 * @throws AutoException
	 * @date 2015年7月31日上午11:18:34
	 * @author hujiachun
	 */
	public void  clearSnooze() throws AutoException {
		if(this.findElement(By.byId(ClockUtil.NOTIFIATION)).exists()){
	        this.findElement(By.byId(ClockUtil.NOTIFIATION)).toVerticalList().
	    			searchBy(By.byTextContains("闹钟已推迟，将于"), 20000);
			
			this.clickByText("取消");
			this.leaveNotify();
	    }
		else {
		    this.findElement(By.byTextContains("闹钟已推迟，将于")).exists();
			this.clickByText("取消");
			this.leaveNotify();
		}
	}
	
	
    /**
     * 确定选中的铃声
     * @param music
     * @return
     * @throws AutoException
     * @date 2015年7月31日上午11:34:21
     * @author hujiachun
     */
	public boolean isMusic(String music) throws AutoException{
	       Element result = findElement(By.byText("铃声")).toDown(By.byText(music));
	       this.sleep(500);
	       return result.exists();
	    }
	
	
	/**
	 * 发送广播设置系统时间年月日
	 * @param year
	 * @param month
	 * @param day
	 * @date 2015年7月31日下午6:26:25
	 * @author hujiachun
	 */
	public void setSystemTime(int year, int month, int day){
		Calendar cl = Calendar.getInstance();
		int hour = cl.get(Calendar.HOUR_OF_DAY);
	 	int minute = cl.get(Calendar.MINUTE);
	 	int second = cl.get(Calendar.SECOND);
	 	
	 	this.sendSetTime(year, month, day, hour, minute, second);
	}
	
	
	/**
	 * 设置时区
	 * @param local
	 * @throws IOException
	 * @throws Exception
	 * @date 2015年8月10日上午9:14:26
	 * @author hujiachun
	 */
	public void setLocalTime(String local) throws IOException, Exception{

		this.clickTextForSetting("语言和时间");
		this.swipeUp();
		Element format_switch = this.findElement(By.byText("自动确定日期和时间")).toRight(By.byId(ClockUtil.SETTINGS_ONOFF));
			 
		if (format_switch.isChecked()) {
				format_switch.click();
				this.sleep(1000);
			}
		this.clickByText("选择时区");
		this.sleep(2000);
		this.findElementByText("搜索").setText(Utf7ImeHelper.e(local));
		this.sleep(2000);
		this.clickByElement(ClockUtil.SETTINGS_SEARCH);//点击搜索结果
		this.sleep(2000);
	
	   this.closeSetting();
	}
	
	
	/**
     * 打开设置
     * @throws IOException 
     */ 
    public void openSetting() throws IOException{
    	this.startApp(AppInfo.PACKAGE_SETTINGS, AppInfo.ACTIVITY_SETTINGS);
    	this.sleep(500);
    }
    
    
    /**
     * 关闭设置
     * @throws IOException 
     */ 
    public void closeSetting(){
    	this.exitApp(AppInfo.PACKAGE_SETTINGS);
    	this.sleep(500);
    }
	
	
    /**
     * 搜索添加时钟
     * @param city
     * @return
     * @throws AutoException
     * @date 2015年8月3日上午11:32:23
     * @author hujiachun
     */
    public boolean isAddWorldClock(String city) throws AutoException{
    	this.clickByElement(ClockUtil.ADD_CLOCK);
    	if(city.equals("")){//随机
    		this.sleep(500);
    		boolean isclick = this.findElement(ClockUtil.WORLD_CITY_LIST).click();
    		
    		return isclick;
    	}
    	else if(city.equals("纽约")){//输入纽约并确定结果
    		this.sleep(500);
    		Element search = this.findElement(ClockUtil.WORLD_CITY_SEARCH);
    		search.click();
    		this.sleep(500);
    		search.setText(Utf7ImeHelper.e(city));
    		this.sleep(1000);
    		String name = findElementById(ClockUtil.CITY_NAME, 0).getText();
    		
    		assertEquals("纽约", name);
    		boolean isclick = this.findElement(ClockUtil.WORLD_CITY_LIST).click();
    		return isclick;	
    	}
    	
    	else{//搜索城市
    		this.sleep(500);
    		Element search = this.findElement(ClockUtil.WORLD_CITY_SEARCH);
    		search.click();
    		this.sleep(500);
    		search.setText(Utf7ImeHelper.e(city));
    		this.sleep(1000);
    		boolean isclick = this.findElement(ClockUtil.WORLD_CITY_LIST).click();
    		return isclick;
    	}
		

	}
    
    
    /**
     * 秒表复位
     * 
     * @date 2015年8月3日下午2:28:27
     * @author hujiachun
     * @throws AutoException 
     */
    public void clearStopWatch() throws AutoException{
    	 boolean start = this.findElementByText(ClockUtil.BTN_START).exists();
    	if (!start) {//开始按钮不存在
    		 boolean pause = this.findElementByText(ClockUtil.BTN_PAUSE).exists();
			if (pause) {
				this.clickByText(ClockUtil.BTN_PAUSE);
			}
			this.clickByText(ClockUtil.BTN_RESET);
		}
    }
    
    
    /**
     * 计时器复位
     * 
     * @date 2015年8月4日下午6:32:37
     * @author hujiachun
     * @throws AutoException 
     */
    public void clearTimer() throws AutoException{
    	if (!findElementByText(ClockUtil.BTN_START).exists()) {
			this.clickByTexts(ClockUtil.BTN_CANCEL, ClockUtil.BTN_CONFIRM);
			
		}
    }
    
    
    /**
     * 点击指针
     * @param index 1：小时  2：分  3：秒
     * @param count 点击次数
     * @throws AutoException
     * @date 2015年8月4日下午7:13:24
     * @author hujiachun
     */
    public void setTimer(int index, int count) throws AutoException{
    	Element pointer = this.findElement(ClockUtil.TIMER_POINTER + index);
    	int centerX = pointer.getBounds().centerX();
    	int bottom = pointer.getBounds().bottom;
    	int clickY = bottom  * 9 / 10;
    	for (int i = 0; i < count; i++) {
			this.click(centerX, clickY);
			this.sleep(500);
		}
    }
    
    
    /**
     * 解锁闹钟
     * 
     * @date 2015年8月5日下午4:37:51
     * @author hujiachun
     */
    public void swipeClock(){
    	
    	this.swipe(this.getDisplayWidth() / 2, (this.getDisplayHeight() / 10) * 9,
    			this.getDisplayWidth() / 2, this.getDisplayHeight() / 3, 50);
    	
    
    }
    
    
    /**
     * 设置事件时间
     * @throws AutoException
     * @date 2015年8月7日下午4:30:46
     * @author hujiachun
     */
    public void setTimerTime() throws AutoException{
    	String total = this.findElement(ClockUtil.TIMER_TOTAL).getText();
    	
    	int centerX = this.findElement(ClockUtil.TIME_VIEW).getBounds().centerX();
		int centerY = this.findElement(ClockUtil.TIME_VIEW).getBounds().centerY();
		this.sleep(1000);
    	if (!total.equals("1")) {
    		this.swipe(10, centerY, this.getDisplayWidth() - 10, centerY, 50);//清零
    		this.swipe(centerX, centerY, centerX - centerX / 20, centerY, 50);//滑动时间刻度
        	this.sleep(2000);
		}
    	
    	
    	
    }
    
    
    /**
     * 
     * 向上滑动小部分
     */
    public void swipeUpLittle() {
		this.swipe(this.getDisplayWidth() / 2, this.getDisplayHeight() / 2,
				this.getDisplayWidth() / 2, this.getDisplayHeight() / 3, 100);
	}
    
    /**
     * 
     * 向下滑动小部分
     */
    public void swipeDownLittle() {
		this.swipe(this.getDisplayWidth() / 2, this.getDisplayHeight() / 3,
				this.getDisplayWidth() / 2, this.getDisplayHeight() / 2, 100);
	}
    
    
    
    /**
     * 在设置中点击text
     * @param text 需要点击的选项
     * @date 2015年8月8日上午9:27:18
     * @author hujiachun
     * @throws AutoException 
     */
    public void clickTextForSetting(String text) throws AutoException{
    	this.exitApp(AppInfo.PACKAGE_SETTINGS);
 	    this.startApp(AppInfo.PACKAGE_SETTINGS, AppInfo.ACTIVITY_SETTINGS);
 	 
 	   if (this.isExistByText(text)) {
 		  this.sleep(1000);
 		   this.clickByText(text);
 		   this.sleep(1000);
 	    }
 	   else {
 		   this.findElement(By.byId(ClockUtil.SETTINGS_SCROLL)).toVerticalList().searchByText(text, 50000);
 		   this.sleep(1000);
 		   this.clickByText(text);
 		   this.sleep(1000);
 	   }
    }
    
    
    /**
     * 设置锁屏时间
     * @param milliscond 
     * @throws AutoException
     * @date 2015年8月8日上午9:45:57
     * @author hujiachun
     */
   public void setLockScreen(int milliscond) throws AutoException  {

//	 ShellUtil.exec("am broadcast -a sk.action.SCREEN_OFF_TIMEOUT --ei off_time_out " + milliscond);
	 String command = "am broadcast -a sk.action.SCREEN_OFF --ei TIME_OUT " + milliscond;
     try {
         Process p = Runtime.getRuntime().exec(command);
         p.waitFor();
     } catch (IOException e) {
         e.printStackTrace();
     } catch (InterruptedException e) {
         e.printStackTrace();
     }
	   
   }
    
   
   /**
    * 设置屏幕常亮
    * @param type true:打开  false:关闭 
    * @date 2015年8月8日上午10:09:17
    * @author hujiachun
    * @throws AutoException 
    */
   public void setClockScreen(boolean type) throws AutoException{
	 Element keep = this.findElement(By.byText("屏幕长亮")).toRight(By.byId(ClockUtil.KEEP_SWITCH));
	   if (type) {
		if (!keep.isChecked()) {
			keep.click();
		}
	}
	else {
		if (keep.isChecked()) {
			keep.click();
		}	
		}
   }
   
   
   /**
    * true:24H false:12H
    * @param type
    * @date 2015年8月8日下午2:16:52
    * @author hujiachun
    * @throws AutoException 
    */
   public void setTimeFormat(boolean type) throws AutoException{
	   this.clickTextForSetting("语言和时间");
	   this.swipeUp();
	   this.sleep(1000);
	   Element format_switch = this.findElement(By.byText("使用24小时制")).toRight(By.byId(ClockUtil.SETTINGS_ONOFF));
	   if (type) {
		 
		if (!format_switch.isChecked()) {
			format_switch.click();
		}
	}
	   else {
		  if (format_switch.isChecked()) {
			format_switch.click();
			}
		   
	}
	   this.exitApp(AppInfo.PACKAGE_SETTINGS);
   }
   
   
   /**
    * 验证闹钟响起,并点击下一步操作
    * @param action 为空时 未设置推迟
    * @throws AutoException
    * @date 2015年8月27日下午4:42:16
    * @author hujiachun
    */
   public void assertClockAndClick(String action) throws AutoException{
	   this.downNotify();
	   boolean clock_ring = this.findElement(ClockUtil.NOTIFIATION_SETTINGS).waitUntilGone(60000 * 4);
	   if (clock_ring) {
		   assertTrue("闹钟未响起", clock_ring);
		   this.sleep(1000);
		   int w = this.getDisplayWidth();
		   int h = this.getDisplayHeight();
		   if (action.equals(ClockUtil.BTN_SNOOZE)) {

//			   this.click(w / 18 * 5, h / 1920 * 265);
			   this.click(w / 2 - w / 4, h / 7);
		}
		   if (action.equals(ClockUtil.BTN_CLOSE)) {
//			   this.click(w / 1080 * 775, h / 1920 * 265);
			   this.click(w / 2 + w / 4, h / 7);
			    
		} 
		   if (action.equals("")) {
//			   this.click(w / 2, h / 1920 * 265);
			   this.click(w / 2, h / 7);
		}
	}
	   else {
		this.leaveNotify();
		assertTrue("闹钟未响起", false);
	}
	  
   }
   
   
   /**
    * 验证闹钟是否响起
    * @throws AutoException
    * @date 2015年8月28日上午9:51:02
    * @author hujiachun
    */
   public void assertClock() throws AutoException{
	   this.downNotify();
	   boolean clock_ring = this.findElement(ClockUtil.NOTIFIATION_SETTINGS).waitUntilGone(60000 * 3);
	   if (clock_ring) {
		   assertTrue("闹钟未响起", clock_ring);
	}
	   else {
		   this.leaveNotify();
		   assertTrue("闹钟未响起", false);
	}
   }
   
   
  
   
   
   /**
    * 绑定wifi
    * @param ssid
    * @param pwd
    * @date 2015年8月31日下午2:28:16
    * @author hujiachun
    */
   public void sendWIFI(String ssid, String pwd){
		String command = "am broadcast -a sk.action.WIFILOCK_REBIND --es SSID " + ssid + " --es PWD " + pwd;
		
		try {
			Process p = Runtime.getRuntime().exec(command);
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
   
   
   
	/**
	 * 监听器
	 * 
	 * @date 2015年7月30日下午2:45:46
	 * @author hujiachun
	 */
	 public void watcherS(){
	    	this.registerWatcher("允许", new AutoWatcher() {

				@Override
				public boolean checkForCondition() {
					if (this.findElement(By.byText("允许")).exists()) {
						try {
							this.findElement(By.byText("允许")).click();
						} catch (AutoException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					return false;
				}
			});

	    	 this.registerWatcher("停止运行", new AutoWatcher() {
	 			
	 			@Override
	 			public boolean checkForCondition() {
	 				if (findElement(By.byTextContains("停止运行")).exists()) {
	 					try {
	 						
	 				    	Process p = Runtime.getRuntime().exec("am broadcast -a sk.action.SCREENSHOT --es NAME "+ "stop" + new Date().getTime());
	 						p.waitFor();
	 						findElement(By.byText("确定")).click();
	 					} catch (Exception e) {
	 						e.printStackTrace();
	 					}
	 				}
	 				return false;
	 			}
	 		});
	    	
	    	 this.registerWatcher("无响应", new AutoWatcher() {
	  			
	  			@Override
	  			public boolean checkForCondition() {
	  				if (findElement(By.byTextContains("无响应")).exists()) {
	  					try {
	  						
	  				    	Process p = Runtime.getRuntime().exec("am broadcast -a sk.action.SCREENSHOT --es NAME "+ "ANR" + new Date().getTime());
	  						p.waitFor();
	  						findElement(By.byText("确定")).click();
	  					} catch (Exception e) {
	  						e.printStackTrace();
	  					}
	  				}
	  				return false;
	  			}
	  		});
	    	 
	    	 
	    	 this.registerWatcher("日历", new AutoWatcher() {
				
				@Override
				public boolean checkForCondition() {
					// TODO Auto-generated method stub
					if(findElement(By.byId("android:id/button2").text("稍后提醒")).exists()){
						try {
							findElement(By.byText("确定")).click();
						} catch (AutoException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					return false;
				}
			});
	    }
	 
	 
	//阿里云统计
	 
	    /**
		 * 判断是不是MA01的userdebug版本 //
		 * data/data/com.meizu.scriptkeeper/files/uitest/tools/aoctrace.sh
		 * 
		 * @throws IOException
		 */
		public boolean checkUserDebug(String model, String version) throws IOException {
			Process process = Runtime.getRuntime().exec("getprop ro.build.description");
			InputStreamReader ir = new InputStreamReader(process.getInputStream());
			BufferedReader input = new BufferedReader(ir);
			String result = input.readLine();
			if (result.contains(model) && result.contains(version)) {
				return true;
			}
			return false;

		}
		

		/**
		 * 函数调用统计
		 * 
		 * @throws IOException
		 */
		public void yunosTest(String cmd) throws IOException {
			String commandstr = "system/bin/sh /data/aoctrace.sh " + cmd;
			exec(commandstr);
			/*
			String[] commandstr = { "system/bin/sh /data/aoctrace.sh " + cmd };
			Process process = Runtime.getRuntime().exec("su");
			DataOutputStream os = new DataOutputStream(process.getOutputStream());
			for (String command : commandstr) {
				System.out.println(command);
				os.write(command.getBytes());
				os.writeBytes("\n");
				os.flush();
			}
			os.writeBytes("exit\n");
			os.flush();
			try {
				process.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			os.close();
			process.destroy();
			*/
		}
		
		
		//获取国家
	    public String getCountry(){
	    	
			return ShellUtil.getProperty("persist.sys.country");
			}
	    
	    //获取语言
	    public String getLanguage(){
	    	
			return ShellUtil.getProperty("persist.sys.language");
			}


	    
}
