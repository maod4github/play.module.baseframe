package baseframe.timingtasks;

import play.jobs.Job;
import play.jobs.On;
import controllers.baseframe.Base;

@On("0 0 0 * * ? *")
public class ResetOfEverydayTimingTask extends Job {

	@Override
	public void doJob() throws Exception {
		Base.resetSMVcodeGetTimesOfToday();
	}

}
