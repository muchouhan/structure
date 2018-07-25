package com.mukund.structure.metatool;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.stream.Stream;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

/**
 * -file
 * "D:\Mukund\primefaces\structure-web\src\main\java\com\mukund\structure\metatool\patch.txt"
 * -driverClassName "com.mysql.jdbc.Driver" -url "jdbc:mysql://localhost/ns"
 * -dbUsername "root" -dbPassword "root"
 * -commit false
 * 
 * @author hp
 *
 */
public class MetadataTool {

	public static void main(String[] args) {
		CliOptions opts = new CliOptions();

		JCommander cli = null;
		try {
			cli = new JCommander(opts, args);
		} catch (ParameterException e) {
			cli.usage();
			System.exit(1);
		}
		if (opts.help) {
			cli.usage();
			System.exit(0);
		}

		// Start your code
		try {
			PatchExecuter.get().execute(PatchReader.get().read(opts.fileName), opts);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static final class CliOptions {
		@Parameter(names = "-file", description = "Please provide file name", required = true)
		public String fileName;

		@Parameter(names = "-driverClassName", description = "Please provide driverClassName", required = true)
		public String driverClassName;

		@Parameter(names = "-url", description = "Please provide db url", required = true)
		public String url;

		@Parameter(names = "-dbUsername", description = "Please provide dbUsername", required = true)
		public String dbUsername; 

		@Parameter(names = "-dbPassword", description = "Please provide dbPassword", required = true)
		public String dbPassword;

		@Parameter(names = "-commit", description = "wheter to commit ot rollback on database")
		public boolean isCommit;

		@Parameter(names = "-help", help = true, description = "this is help")
		public boolean help;

		@Override
		public String toString() {
			return "CliOptions [fileName=" + fileName + ", driverClassName=" + driverClassName + ", url=" + url
					+ ", dbUsername=" + dbUsername + ", dbPassword=" + dbPassword + ", isCommit=" + isCommit + ", help="
					+ help + "]";
		}

	}

	private static class PatchReader {

		private static final int INDEX_NOT_FOUND = -1;
		private final String COMMENT = "#";
		private final String DELIMETER = "#@@@#";
		private static PatchReader instance;

		private PatchReader() {

		}

		public static PatchReader get() {
			if (instance == null)
				instance = new PatchReader();
			return instance;
		}

		public List<String> read(String path) {
			List<String> commands = new ArrayList<>();
			try (Stream<String> stream = Files.lines(Paths.get(path))) {
				stream.forEach(command -> {
					command = command.trim();
					if (command.length() > 0 && !command.startsWith(COMMENT))
						commands.add(command);
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
			return commands;
		}

		public String getCommandName(String command) {
			if (command.indexOf("(") < 0)
				return command;
			else
				return command.substring(0, command.indexOf("(")).trim();
		}

		public Object[] getParameters(String command) {
			
			List<String> params = new ArrayList<>();
			int start = command.indexOf("(");
			if (start != INDEX_NOT_FOUND) {
				int end = command.lastIndexOf(")");
				if (end != INDEX_NOT_FOUND) {
					StringTokenizer token = new StringTokenizer(command.substring(start + "(".length(), end),
							DELIMETER);
					while (token.hasMoreTokens()) {
						params.add(token.nextToken().trim());
					}
				}
			}
			return params.toArray();
		}
	}

	private static class PatchExecuter {
		private static PatchExecuter instance;
		private CliOptions opts;
		private DataSourceTransactionManager transactionManager;
		private JdbcTemplate template;

		private PatchExecuter() throws SQLException {
			
		}

		public static PatchExecuter get() throws SQLException {
			if (instance == null)
				instance = new PatchExecuter();
			return instance;
		}

		public void execute(List<String> commands, CliOptions opts) throws SQLException {
			this.opts = opts;
			template = getJdbcTemplate();
			this.transactionManager = new DataSourceTransactionManager(template.getDataSource());
			TransactionDefinition def = new DefaultTransactionDefinition();
			TransactionStatus status = transactionManager.getTransaction(def);
			
			try {
				// get constructor that takes a String as argument
				Constructor<MetaToolDAOImpl> constructor = MetaToolDAOImpl.class.getConstructor(JdbcTemplate.class);
				MetaToolDAOImpl myObject = (MetaToolDAOImpl) constructor.newInstance(template);

				commands.forEach(command -> {
					String methodName = PatchReader.get().getCommandName(command);
					Object[] param = PatchReader.get().getParameters(command);

					Optional<Method> method = Arrays.asList(MetaToolDAOImpl.class.getMethods()).stream()
							.filter(method1 -> methodName.equals(method1.getName()))
							.filter(method2 -> param.length == method2.getParameterCount()).findAny();

					if (!method.isPresent()) {
						System.out.println(methodName + " not found");
						return;
					}
					try {
						method.get().invoke(myObject, param);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				});

				System.out.println("Is Commit = " + opts.isCommit);
				if (opts.isCommit)
					transactionManager.commit(status);
				else
					transactionManager.rollback(status);

			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| InstantiationException | NoSuchMethodException | SecurityException e) {
				transactionManager.rollback(status);
				e.printStackTrace();
			}
		}

		private JdbcTemplate getJdbcTemplate() throws SQLException {
			JdbcTemplate template = new JdbcTemplate();
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setDriverClassName(this.opts.driverClassName);
			dataSource.setUrl(this.opts.url);
			dataSource.setUsername(this.opts.dbUsername);
			dataSource.setPassword(this.opts.dbPassword);
			template.setDataSource(dataSource);
			return template;
		}
	}

}
