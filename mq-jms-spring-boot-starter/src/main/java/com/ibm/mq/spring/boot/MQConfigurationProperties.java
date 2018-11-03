/*
 * Copyright © 2018 IBM Corp. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package com.ibm.mq.spring.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.time.Duration;

/**
 * There are many properties that can be set on an MQ Connection Factory, but these are the most commonly-used
 * for both direct and client connections. If you use TLS for client connectivity, most properties related to that
 * (keystore, certificates etc) must be set independently.
 * <p>
 * This class allows for setting the CipherSuite/CipherSpec property, and an indication of whether or not
 * to use the IBM JRE maps for Cipher names - that's not something that is standardised.
 * <p>
 * The default values have been set to match the settings of the
 * <a href="https://github.com/ibm-messaging/mq-docker">MQ Docker</a>
 * container.
 *
 * <ul>
 * <li>queueManager = QM1
 * <li>connName = localhost(1414)
 * <li>channel = DEV.ADMIN.SVRCONN
 * <li>user = admin
 * <li>password = passw0rd
 * </ul>
 */
@ConfigurationProperties(prefix = "ibm.mq")
public class MQConfigurationProperties {

	/**
	 * MQ Queue Manager name
	 */
	private String queueManager = "QM1";

	/**
	 * Channel - for example "SYSTEM.DEF.SVRCONN"
	 **/
	private String channel = "DEV.ADMIN.SVRCONN";

	/**
	 * Connection Name - hostname or address and port. Format like 'system.example.com(1414)'
	 **/
	private String connName = "localhost(1414)";

	/**
	 * MQ Client ID
	 */
	private String clientId;

	/**
	 * MQ user name
	 */
	private String user = "admin";

	/**
	 * MQ password
	 */
	private String password = "passw0rd";

	/**
	 * Override the authentication mode. This
	 * should not normally be needed with current maintenance levels of MQ V8 or V9, but some earlier levels
	 * sometimes got get it wrong and then this flag can be set to "false".
	 *
	 * @see <a href="https://www.ibm.com/support/knowledgecenter/en/SSFKSJ_9.0.0/com.ibm.mq.sec.doc/q118680_.htm">the KnowledgeCenter</a>
	 */
	private boolean userAuthenticationMQCSP = true;

	/**
	 * For TLS connections, you can set either the sslCipherSuite or sslCipherSpec property.
	 * For example, "SSL_ECDHE_RSA_WITH_AES_256_GCM_SHA384"
	 *
	 * @see <a href="https://www.ibm.com/support/knowledgecenter/en/SSFKSJ_9.0.0/com.ibm.mq.dev.doc/q113210_.htm">the KnowledgeCenter</a>
	 */
	private String sslCipherSuite;

	/**
	 * For TLS connections, you can set either the sslCipherSuite or sslCipherSpec property.
	 * For example, "ECDHE_RSA_AES_256_GCM_SHA384"
	 *
	 * @see <a href="https://www.ibm.com/support/knowledgecenter/en/SSFKSJ_9.0.0/com.ibm.mq.dev.doc/q113210_.htm">the KnowledgeCenter</a>
	 */
	private String sslCipherSpec;

	/**
	 * Type a distinguished name skeleton that must match that provided by the queue manager.
	 *
	 * @see <a href="https://www.ibm.com/support/knowledgecenter/en/SSFKSJ_9.0.0/com.ibm.mq.dev.doc/q112720_.htm">the KnowledgeCenter</a>
	 */
	private String sslPeerName;

	/**
	 * Set to true for the IBM JRE CipherSuite name maps; set to false to use the Oracle JRE CipherSuite mapping
	 */
	private boolean useIBMCipherMappings = true;

	/**
	 * Enter the uniform resource locator (URL) that identifies the name and location of the file that contains
	 * the client channel definition table and specifies how the file can be accessed.
	 * You must set a value for either the Channel property or for the Client Channel Definition Table URL property but not both.
	 * For example, "file:///home/admdata/ccdt1.tab"
	 *
	 * @see <a href="https://www.ibm.com/support/knowledgecenter/en/SSFKSJ_9.0.0/com.ibm.mq.dev.doc/q032510_.html">the KnowledgeCenter</a>
	 */
	private String ccdtUrl;

	@NestedConfigurationProperty
	private PoolProperties pool = new PoolProperties();


	public String getQueueManager() {
		return queueManager;
	}

	public void setQueueManager(String queueManager) {
		this.queueManager = queueManager;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getConnName() {
		return connName;
	}

	public void setConnName(String connName) {
		this.connName = connName;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientId() {
		return clientId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSslCipherSuite() {
		return sslCipherSuite;
	}

	public void setSslCipherSuite(String sslCipherSuite) {
		this.sslCipherSuite = sslCipherSuite;
	}

	public String getSslCipherSpec() {
		return sslCipherSpec;
	}

	public void setSslCipherSpec(String sslCipherSpec) {
		this.sslCipherSpec = sslCipherSpec;
	}

	public boolean isUseIBMCipherMappings() {
		return useIBMCipherMappings;
	}

	public void setUseIBMCipherMappings(boolean useIBMCipherMappings) {
		System.setProperty("com.ibm.mq.cfg.useIBMCipherMappings", Boolean.toString(useIBMCipherMappings));
		this.useIBMCipherMappings = useIBMCipherMappings;
	}

	public boolean isUserAuthenticationMQCSP() {
		return userAuthenticationMQCSP;
	}

	public void setUserAuthenticationMQCSP(boolean userAuthenticationMQCSP) {
		this.userAuthenticationMQCSP = userAuthenticationMQCSP;
	}

	public String getSslPeerName() {
		return sslPeerName;
	}

	public void setSslPeerName(String sslPeerName) {
		this.sslPeerName = sslPeerName;
	}

	public String getCcdtUrl() {
		return ccdtUrl;
	}

	public void setCcdtUrl(String ccdtUrl) {
		this.ccdtUrl = ccdtUrl;
	}

	public PoolProperties getPool() {
		return pool;
	}


	class PoolProperties {

		/**
		 * Whether a JmsPoolConnectionFactory should be created, instead of a regular
		 * ConnectionFactory.
		 */
		private boolean enabled;

		/**
		 * Whether to block when a connection is requested and the pool is full. Set it to
		 * false to throw a "JMSException" instead.
		 */
		private boolean blockIfFull = true;

		/**
		 * Blocking period before throwing an exception if the pool is still full.
		 */
		private Duration blockIfFullTimeout = Duration.ofMillis(-1);

		/**
		 * Connection idle timeout.
		 */
		private Duration idleTimeout = Duration.ofSeconds(30);

		/**
		 * Maximum number of pooled connections.
		 */
		private int maxConnections = 1;

		/**
		 * Maximum number of pooled sessions per connection in the pool.
		 */
		private int maxSessionsPerConnection = 500;

		/**
		 * Time to sleep between runs of the idle connection eviction thread. When negative,
		 * no idle connection eviction thread runs.
		 */
		private Duration timeBetweenExpirationCheck = Duration.ofMillis(-1);

		/**
		 * Whether to use only one anonymous "MessageProducer" instance. Set it to false to
		 * create one "MessageProducer" every time one is required.
		 */
		private boolean useAnonymousProducers = true;

		public boolean isEnabled() {
			return this.enabled;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public boolean isBlockIfFull() {
			return this.blockIfFull;
		}

		public void setBlockIfFull(boolean blockIfFull) {
			this.blockIfFull = blockIfFull;
		}

		public Duration getBlockIfFullTimeout() {
			return this.blockIfFullTimeout;
		}

		public void setBlockIfFullTimeout(Duration blockIfFullTimeout) {
			this.blockIfFullTimeout = blockIfFullTimeout;
		}

		public Duration getIdleTimeout() {
			return this.idleTimeout;
		}

		public void setIdleTimeout(Duration idleTimeout) {
			this.idleTimeout = idleTimeout;
		}

		public int getMaxConnections() {
			return this.maxConnections;
		}

		public void setMaxConnections(int maxConnections) {
			this.maxConnections = maxConnections;
		}

		public int getMaxSessionsPerConnection() {
			return this.maxSessionsPerConnection;
		}

		public void setMaxSessionsPerConnection(int maxSessionsPerConnection) {
			this.maxSessionsPerConnection = maxSessionsPerConnection;
		}

		public Duration getTimeBetweenExpirationCheck() {
			return this.timeBetweenExpirationCheck;
		}

		public void setTimeBetweenExpirationCheck(Duration timeBetweenExpirationCheck) {
			this.timeBetweenExpirationCheck = timeBetweenExpirationCheck;
		}

		public boolean isUseAnonymousProducers() {
			return this.useAnonymousProducers;
		}

		public void setUseAnonymousProducers(boolean useAnonymousProducers) {
			this.useAnonymousProducers = useAnonymousProducers;
		}

	}

}
