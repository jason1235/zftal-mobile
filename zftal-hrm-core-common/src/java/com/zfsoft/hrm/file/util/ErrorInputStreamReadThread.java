package com.zfsoft.hrm.file.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ErrorInputStreamReadThread extends Thread {
	private Process process = null;
	@SuppressWarnings("unused")
	private boolean over = false;

	private String result = null;

	public String getResult() {
		return result;
	}

	public ErrorInputStreamReadThread(Process p) {
		process = p;
		over = false;
	}

	public void run() {
		try {
			if (process == null) {
				System.out.println("process is null!");
				return;
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					process.getErrorStream()));
			StringBuilder builder = new StringBuilder();

			// while (true) {
			// if (process == null || over) {
			// // System.out.println("process is over!");
			// break;
			// }
			String temp;
			while ((temp = br.readLine()) != null) {
//				 System.out.println(temp);
				builder.append(temp);
			}
			// }
			this.result = builder.toString();
		} catch (Throwable e) {
			this.result = e.getMessage();
			e.printStackTrace();
		} 
	}

	public void setOver(boolean over) {
		this.over = over;
	}
	
	public void finalize() throws Throwable {
		if (this.process != null) {
			this.process.destroy();
		}
		super.finalize();
	}

}
