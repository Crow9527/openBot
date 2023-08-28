package com.crow.qqbot.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;

/**
 * <p>
 * </p>
 * 
 * @author crow
 * @since 2023年4月6日 下午4:34:58
 */
public class SystemUtil {
	/**
	 * 运行系统命令并返回结果
	 * 
	 * @param cmd
	 * @return
	 */
	public static String syncExecute(String cmd) {
		Executor executor = new DefaultExecutor();
		CommandLine commandLine = CommandLine.parse(cmd);

		// 接收异常结果流
		ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
		// 接收正常结果流
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		// 设置超时时间10秒
		ExecuteWatchdog watchdog = new ExecuteWatchdog(10000);
		executor.setWatchdog(watchdog);

		executor.setStreamHandler(new PumpStreamHandler(outputStream, errorStream));
		executor.setExitValues(null);
		try {

			// execute = 0 执行成功
			executor.execute(commandLine);
			return outputStream.toString("UTF-8");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				errorStream.close();
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
}
