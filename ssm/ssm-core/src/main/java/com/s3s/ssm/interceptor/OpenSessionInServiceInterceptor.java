/*
 * OpenSessionInServiceInterceptor
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */

package com.s3s.ssm.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * The interceptor warranties that the methods are performed inside the session.
 * 
 * @author Phan Hong Phuc
 * @since Nov 7, 2011
 * 
 */
public class OpenSessionInServiceInterceptor implements MethodInterceptor {
    /**
     * The logger.
     */
    private static Logger s_logger = LoggerFactory.getLogger(OpenSessionInServiceInterceptor.class);

    /**
     * Is single session mode used?
     */
    private boolean m_singleSession = true;

    /**
     * The Hibernate session factory.
     */
    private SessionFactory m_sessionFactory;

    /**
     * The flushing stragtegy to use.
     */
    private FlushMode m_flushMode = FlushMode.COMMIT;

    /**
     * @return the singleSession
     */
    public boolean isSingleSession() {
        return m_singleSession;
    }

    /**
     * @param singleSession
     *            the singleSession to set
     */
    public void setSingleSession(boolean singleSession) {
        this.m_singleSession = singleSession;
    }

    /**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
        return m_sessionFactory;
    }

    /**
     * @param sessionFactory
     *            the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.m_sessionFactory = sessionFactory;
    }

    /**
     * @return the flushMode
     */
    public FlushMode getFlushMode() {
        return m_flushMode;
    }

    /**
     * @param flushMode
     *            the flushMode to set
     */
    public void setFlushMode(FlushMode flushMode) {
        this.m_flushMode = flushMode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        boolean participate = false;

        if (isSingleSession()) {
            // single session mode
            if (TransactionSynchronizationManager.hasResource(m_sessionFactory)) {
                // Do not modify the Session: just set the participate flag.
                participate = true;
            } else {
                s_logger.debug("Opening single Hibernate Session in " + "OpenSessionInServiceInterceptor");
                Session session = getSession(m_sessionFactory);
                TransactionSynchronizationManager.bindResource(m_sessionFactory, new SessionHolder(session));
            }
        } else {
            // deferred close mode
            if (SessionFactoryUtils.isDeferredCloseActive(m_sessionFactory)) {
                // Do not modify deferred close: just set the participate flag.
                participate = true;
            } else {
                SessionFactoryUtils.initDeferredClose(m_sessionFactory);
            }
        }

        try {
            return invocation.proceed();
        } finally {
            if (!participate) {
                if (isSingleSession()) {
                    // single session mode
                    SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager
                            .unbindResource(m_sessionFactory);
                    s_logger.debug("Closing single Hibernate Session in " + "OpenSessionInServiceInterceptor");
                    closeSession(sessionHolder.getSession());
                } else {
                    // deferred close mode
                    SessionFactoryUtils.processDeferredClose(m_sessionFactory);
                }
            }
        }
    }

    /**
     * Get a Session for the SessionFactory that this filter uses. Note that this just applies in single session mode!
     * <p>
     * The default implementation delegates to the <code>SessionFactoryUtils.getSession</code> method and sets the
     * <code>Session</code>'s flush mode to "NEVER".
     * <p>
     * Can be overridden in subclasses for creating a Session with a custom entity interceptor or JDBC exception
     * translator.
     * 
     * @param sessionFactory
     *            the SessionFactory that this filter uses
     * @return the Session to use
     * @throws DataAccessResourceFailureException
     *             if the Session could not be created
     * @see org.springframework.orm.hibernate3.SessionFactoryUtils#getSession(SessionFactory, boolean)
     * @see org.hibernate.FlushMode#COMMIT
     */
    protected Session getSession(SessionFactory sessionFactory) throws DataAccessResourceFailureException {

        Session session = SessionFactoryUtils.getSession(sessionFactory, true);
        FlushMode flushMode = getFlushMode();
        if (flushMode != null) {
            session.setFlushMode(flushMode);
        }

        return session;
    }

    /**
     * Close the given Session. Note that this just applies in single session mode!
     * <p>
     * Can be overridden in subclasses, e.g. for flushing the Session before closing it. See class-level javadoc for a
     * discussion of flush handling. Note that you should also override getSession accordingly, to set the flush mode to
     * something else than NEVER.
     * 
     * @param session
     *            the Session used for filtering
     */
    protected void closeSession(Session session) {
        SessionFactoryUtils.closeSession(session);
    }
}
