package baseframe.timingtasks;

import java.util.Date;

import play.jobs.Every;
import play.jobs.Job;
import baseframe.consts.CommonConst;

/**
 * 消息通知定时任务 <br>
 * <b>作者 : </b>maodun <br>
 * <b>创建时间 : </b>2015年9月18日,下午9:31:49
 */
public final class MsgNoticeTimingTask {

	/**
	 * 短信 <br>
	 * <b>作者 : </b>maodun <br>
	 * <b>创建时间 : </b>2015年9月18日,下午9:31:40
	 */
	@Every("5s")
	protected final class SMSJob extends Job {

		@Override
		public void doJob() throws Exception {
			if (!CommonConst.SMS_TOGGLE) {
				return;
			}
			final long s = new Date().getTime();
			// TODO send...
			final long e = new Date().getTime();
			final long elapsed = e - s;
			if (elapsed > 5000) {
				System.out.println("The sm send is too long, elapsed:" + elapsed + "ms");
			}
		}

	}

	/**
	 * 电子邮件 <br>
	 * <b>作者 : </b>maodun <br>
	 * <b>创建时间 : </b>2015年9月18日,下午9:31:58
	 */
	@Every("10s")
	protected final class EMailJob extends Job {

		@Override
		public void doJob() throws Exception {
			if (!CommonConst.EMS_TOGGLE) {
				return;
			}
			final long s = new Date().getTime();
			// TODO send...
			final long e = new Date().getTime();
			final long elapsed = e - s;
			if (elapsed > 5000) {
				System.out.println("The em send is too long, elapsed:" + elapsed + "ms");
			}
		}

	}

}
