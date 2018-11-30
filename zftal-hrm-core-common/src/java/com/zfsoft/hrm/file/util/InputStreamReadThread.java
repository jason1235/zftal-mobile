package com.zfsoft.hrm.file.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class InputStreamReadThread extends Thread {
	private Process process = null;
	@SuppressWarnings("unused")
	private boolean over = false;

	private String result = null;

	public String getResult() {
		return result;
	}

	public InputStreamReadThread(Process p) {
		process = p;
		over = false;
	}

	public void run() {
		try {
			if (process == null) {
				return;
			}

			// 瀵硅緭鍏ユ祦锛屽彲鑳芥槸涓�釜鍥炶溅涔嬬被鐨勮緭鍏�
			BufferedReader br = new BufferedReader(new InputStreamReader(
					process.getInputStream()));
			StringBuilder builder = new StringBuilder();
			// while (true) {
			// if (process == null || over) {
			// break;
			// }
			@SuppressWarnings("unused")
			String temp;

			while ((temp = br.readLine()) != null) {
//				 System.out.println(temp);
				// logger.info("杈撳叆娴佷俊鎭�" + temp);//濡傝繖浜涗俊鎭�NOTICE processing
				// PDF page 10 (595x842:0:0) (move:0:0)绛夌瓑鐨勬墦鍗版椂鎻愮ず淇℃伅
				// ;
				// builder.append(temp);
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
