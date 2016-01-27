package baseframe.timingtasks;

import play.jobs.Every;
import play.jobs.Job;
import baseframe.factorys.InstanceFactory;

/**
 * 实例工厂复位定时任务<br>
 * <b>作者 : </b>Morton<br>
 * <b>创建时间 : </b>2015年12月3日,上午12:04:23
 */
@Every("1h")
public class InstanceFactoryResetTimingTask extends Job {

	@Override
	public void doJob() throws Exception {
		InstanceFactory.reset();
	}
	
}
