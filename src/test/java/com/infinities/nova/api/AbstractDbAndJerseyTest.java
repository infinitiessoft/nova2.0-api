package com.infinities.nova.api;

import java.io.InputStream;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.glassfish.jersey.test.JerseyTest;
import org.hibernate.HibernateException;
import org.hibernate.internal.SessionImpl;
import org.junit.Before;

import com.infinities.skyport.jpa.EntityManagerFactoryBuilder;
import com.infinities.skyport.jpa.EntityManagerHelper;
import com.infinities.skyport.jpa.JpaProperties;

public abstract class AbstractDbAndJerseyTest extends JerseyTest {

	private EntityManagerFactory entityManagerFactory;
	private IDatabaseConnection connection;
	private IDataSet dataset;
	private EntityManager entityManager;


	@Before
	public void initEntityManager() throws HibernateException, DatabaseUnitException, SQLException {
		JpaProperties.PERSISTENCE_UNIT_NAME = "com.infinities.skyport.nova.jpa.test";
		entityManagerFactory = EntityManagerFactoryBuilder.emf;
		// EntityManagerFactoryBuilder.emf = entityManagerFactory;
		EntityManagerHelper.factoryLocal.set(entityManagerFactory);
		// entityManagerFactory =
		// Persistence.createEntityManagerFactory("com.infinities.skyport.nova.jpa.test");
		entityManager = entityManagerFactory.createEntityManager();
		connection = new DatabaseConnection(((SessionImpl) (entityManager.getDelegate())).connection());
		connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());

		FlatXmlDataSetBuilder flatXmlDataSetBuilder = new FlatXmlDataSetBuilder();
		flatXmlDataSetBuilder.setColumnSensing(true);
		InputStream dataSet = Thread.currentThread().getContextClassLoader().getResourceAsStream("test-data.xml");
		dataset = flatXmlDataSetBuilder.build(dataSet);
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataset);

		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}
}
