package com.example.spring.batch;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.example.spring.logic.member.model.MemberLog;
import com.example.spring.logic.member.service.MemberService;

/**
 * @author gimbyeongsu
 * 
 */
@Component
public class MemberBatch implements Batch {
	private static final Logger LOGGER = LoggerFactory.getLogger(MemberBatch.class);

	private boolean inProgress;
	private boolean shutdown = false;

	@Autowired
	private Environment environment;
	@Autowired
	private MemberService memberService;

	@Override
	public String getName() {
		return "MEMBER_LOG";
	}

	@Override
	public String getExpression() {
		return "1 * * * * ?";
	}

	@Override
	public boolean isInProgress() {
		return inProgress;
	}

	@Override
	public void setShutdownEnabled() {
		shutdown = true;
	}

	@Override
	public boolean isEnabled() {
		return environment.getRequiredProperty("batch.member.enabled", Boolean.class);
	}

	@Override
	public void run() {
		if (shutdown || !isEnabled()) {
			return;
		}

		inProgress = true;

		LOGGER.debug("[{}] member log start", getName());
		List<MemberLog> memberLogs = new ArrayList<>();
		for (int i = 0; i < 10000; ++i) {
			MemberLog log = new MemberLog();
			memberLogs.add(log);

			log.setId(i);
		}

		// 배치 인설트 (샘플 제작중이니 일단 막아둠)
		LOGGER.debug("[{}] member log batch insert start", getName());
		// memberService.insert(memberLogs);
		LOGGER.debug("[{}] member log batch insert end", getName());

		LOGGER.debug("[{}] member log insert start", getName());
		// for (MemberLog memberLog : memberLogs) {
		// memberService.insert(memberLog);
		// }
		LOGGER.debug("[{}] member log insert end", getName());

		LOGGER.debug("[{}] member log end", getName());

		inProgress = false;
	}
}