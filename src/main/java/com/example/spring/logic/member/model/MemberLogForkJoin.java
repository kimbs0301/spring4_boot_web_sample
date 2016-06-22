package com.example.spring.logic.member.model;

import java.util.List;
import java.util.concurrent.RecursiveAction;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.example.spring.logic.member.dao.MemberLogDao;

/**
 * @author gimbyeongsu
 * 
 */
public class MemberLogForkJoin extends RecursiveAction {
	private static final long serialVersionUID = -9161102015402305193L;

	private final static int THRESHOLD = 10;
	private PlatformTransactionManager transactionManager;
	private MemberLogDao memberLogDao;
	private List<MemberLog> memberLogs;

	public MemberLogForkJoin(PlatformTransactionManager transactionManager, MemberLogDao memberLogDao, List<MemberLog> memberLogs) {
		this.transactionManager = transactionManager;
		this.memberLogDao = memberLogDao;
		this.memberLogs = memberLogs;
	}

	@Override
	protected void compute() {
		if (memberLogs.size() < THRESHOLD) {
			DefaultTransactionDefinition tx = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED);
			tx.setName("example-transaction");
			TransactionStatus status = transactionManager.getTransaction(tx);
			memberLogDao.insert(memberLogs);
			if (memberLogs.get(0).isRollback()) {
				transactionManager.rollback(status);
			} else {
				transactionManager.commit(status);
			}
		} else {
			final int size = memberLogs.size();
			final int toIndex = size / 2;
			List<MemberLog> task1 = memberLogs.subList(0, toIndex);
			List<MemberLog> task2 = memberLogs.subList(toIndex, size);
			invokeAll(new MemberLogForkJoin(transactionManager, memberLogDao, task1), new MemberLogForkJoin(transactionManager, memberLogDao, task2));
		}
	}

}
