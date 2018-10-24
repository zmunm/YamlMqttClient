package com.zmunm.narvcorp.sample.mqtt.subscriber.config

import com.zmunm.narvcorp.sample.core.mysql.dao.AdminDAO
import com.zmunm.narvcorp.sample.core.mysql.option.DatabaseOption
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.mapping.Environment
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory
import org.apache.ibatis.session.Configuration
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import org.apache.ibatis.session.SqlSession
import org.apache.tomcat.jdbc.pool.DataSource

object DatabaseConfig {
	val sqlSessionFactory: SqlSessionFactory by lazy {
		SqlSessionFactoryBuilder()
				.build(Configuration(
						Environment.Builder("sample").apply {
							transactionFactory(JdbcTransactionFactory())
							dataSource(DatabaseOption.Local.configureDataSource(DataSource()))
						}.build())
						.apply {
							addMapper(AdminDAO::class.java)
						})

	}

	var session: SqlSession? = null

	inline fun <reified T> getDao(): T = (session
            ?: sqlSessionFactory.openSession().also { session = it })
			.getMapper(T::class.java)
}
