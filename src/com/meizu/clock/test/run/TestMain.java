
package com.meizu.clock.test.run;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestMain {

	public static void main(String[] args) {
		Runtime rt = Runtime.getRuntime();
		String strBuild = "cmd /c cd D:\\Jira\\clock5.0\\ && ant build";
		String strRun = "cmd /c adb push D:\\Jira\\clock5.0\\bin\\clock.jar "
				+ "/data/local/tmp && adb shell uiautomator runtest clock.jar -c "
				+ "com.meizu.clock.test.ClockSanityTestCase#" + "test0000Start1";
		try {
			// 开始编译测试脚本
			System.out.print("开始运行编译\n");
			Process pr1 = rt.exec(strBuild);
			// 输出测试脚本编译状况
			BufferedReader input1 = new BufferedReader(new InputStreamReader(pr1.getInputStream(), "UTF-8"));
			String line1 = null;
			while ((line1 = input1.readLine()) != null) {
				System.out.println(line1);
			}
			// 编译完成
			System.out.print("已经编译完成\n");
			// 等待3秒编译时间，进行脚本测试
			Thread.sleep(3000);
			// 开始运行脚本
			System.out.print("开始运行测试脚本\n");
			Process pr2 = rt.exec(strRun);
			// 输出脚本运行情况
			BufferedReader input2 = new BufferedReader(new InputStreamReader(pr2.getInputStream(), "UTF-8"));
			String line2 = null;
			while ((line2 = input2.readLine()) != null) {
				System.out.println(line2);
			}
			// 运行完毕
			System.out.print("已经运行完成\n");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

