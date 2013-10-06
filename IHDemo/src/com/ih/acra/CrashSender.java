package com.ih.acra;

import org.acra.ReportField;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class CrashSender implements ReportSender {

	Context c = null;
	String crashReport;

	public CrashSender(Context c) {

		this.c = c;
	}

	@Override
	public void send(CrashReportData report) throws ReportSenderException {
		// Iterate over the CrashReportData instance and do whatever
		// you need with each pair of ReportField key / String value

		crashReport = report.getProperty(ReportField.PACKAGE_NAME) + "\n"
				+ report.getProperty(ReportField.APP_VERSION_CODE) + "\n"
				+ report.getProperty(ReportField.ANDROID_VERSION) + "\n"
				+ report.getProperty(ReportField.PHONE_MODEL) + "\n" + "\n"
				+ report.getProperty(ReportField.STACK_TRACE) + "\n";
		System.out.println("" + crashReport);

		new MailSenderTask().execute();

	}

	class MailSenderTask extends AsyncTask<Void, Void, Exception> {
		@Override
		protected Exception doInBackground(Void... urls) {
			try {
				GmailSender sender = new GmailSender("ihcrashes@gmail.com",
						"abhi9226", c);
				sender.sendMail("Crash Report", "ihcrashes@gmail.com",
						"ihcrashes@gmail.com", null, null, crashReport);

			} catch (Exception e) {
				Log.e("SendMail", e.getMessage(), e);
			}
			return null;
		}

	}
}