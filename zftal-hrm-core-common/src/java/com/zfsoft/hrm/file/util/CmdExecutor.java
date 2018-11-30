package com.zfsoft.hrm.file.util;

import java.io.IOException;
import java.util.List;

public class CmdExecutor {

	private String result = null;

	private String errResult = null;

	private Process process = null;

	public String getResult() {
		return this.result;
	}

	/**
	 * 鍒ゆ柇鏄惁鏄疻indows鎿嶄綔绯荤粺
	 * 
	 * @return
	 */
	public boolean isWindows() {

		String os = System.getProperties().getProperty("os.name");

		return (os.startsWith("win") || os.startsWith("Win"));
	}

	public String getErrResult() {
		return this.errResult;
	}

	public Process execImedCommand(List<String> command) throws IOException,
			InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command(command);
		System.out.println("command:" + command);
		Process process = processBuilder.start();


		// dealWith(process);//鏀圭敤涓嬮潰鐨勬柟寮忔潵澶勭悊锛�
		InputStreamReadThread inputReadThread = new InputStreamReadThread(
				process);
		inputReadThread.start();
		ErrorInputStreamReadThread errorInputReadThread = new ErrorInputStreamReadThread(
				process);
		errorInputReadThread.start();
		// process.waitFor();// 绛夊緟瀛愯繘绋嬬殑缁撴潫锛屽瓙杩涚▼灏辨槸绯荤粺璋冪敤鏂囦欢杞崲杩欎釜鏂拌繘绋�
		// inputReadThread.join();
		// errorInputReadThread.join();
		// inputReadThread.setOver(true);// 杞崲瀹岋紝鍋滄娴佺殑澶勭悊
		// errorInputReadThread.setOver(true);
		this.result = inputReadThread.getResult();
		this.errResult = errorInputReadThread.getResult();
		System.out.println("command:鎵ц缁撴潫");
		return process;
	}

	public boolean execCommand(List<String> command) throws IOException,
			InterruptedException {
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command(command);
		process = processBuilder.start();

		// dealWith(process);//鏀圭敤涓嬮潰鐨勬柟寮忔潵澶勭悊锛�
		InputStreamReadThread inputReadThread = new InputStreamReadThread(
				process);
		inputReadThread.start();
		ErrorInputStreamReadThread errorInputReadThread = new ErrorInputStreamReadThread(
				process);
		errorInputReadThread.start();
		process.waitFor();// 绛夊緟瀛愯繘绋嬬殑缁撴潫锛屽瓙杩涚▼灏辨槸绯荤粺璋冪敤鏂囦欢杞崲杩欎釜鏂拌繘绋�
		inputReadThread.join();
		errorInputReadThread.join();
		// inputReadThread.setOver(true);// 杞崲瀹岋紝鍋滄娴佺殑澶勭悊
		// errorInputReadThread.setOver(true);
		this.result = inputReadThread.getResult();
		this.errResult = errorInputReadThread.getResult();
		return (this.errResult == null) || ("".equals(this.errResult.trim())) ? true
				: false;
	}

	/**
	 * @return the process
	 */
	public Process getProcess() {
		return process;
	}

	/**
	 * @param process
	 *            the process to set
	 */
	public void setProcess(Process process) {
		this.process = process;
	}

}