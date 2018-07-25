package com.mukund.structure.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.mukund.structure.model.User;
import com.mukund.structure.model.UserPrivilege;

@Repository
public class CSVUserAuthenticationRepositoryImpl implements UserAuthenticationRepository {
	private static final String CSV_USER_FILE = "D:\\Mukund\\primefaces\\structure-web\\src\\main\\resources\\users.csv";
	private static final String CSV_PERMS_FILE = "D:\\Mukund\\primefaces\\structure-web\\src\\main\\resources\\perms.csv";
	private List<User> users;
	private List<UserPrivilege> perms;

	public CSVUserAuthenticationRepositoryImpl() {
		loadUser();
		loadPerms();
	}

	private void loadUser() {
		try (Stream<String> lines = Files.lines(Paths.get(CSV_USER_FILE))) {
			users = lines.skip(1).map(line -> {
				String[] item = line.split(",");
				return new User(item[0], item[1], item[2], item[3], item[4], item[5]);
			}).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadPerms() {
		try (Stream<String> lines = Files.lines(Paths.get(CSV_PERMS_FILE))) {
			perms = lines.skip(1).filter(t -> !t.isEmpty()).map(line -> {
					String[] arr = new String[5];
					String[] item = line.split(",");
					System.arraycopy(item, 0, arr, 0, item.length);
					return new UserPrivilege(arr[0], arr[1], arr[2], arr[3], arr[4]);
				}).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean authenticate(String user, String password) {
		return users.stream().anyMatch(new Predicate<User>() {
			@Override
			public boolean test(User t) {
				return user.equals(t.getUserid());
			}
		});
	}

	@Override
	public List<UserPrivilege> fetchRoles(String user) {
		return perms.stream().filter(new Predicate<UserPrivilege>() {
			@Override
			public boolean test(UserPrivilege t) {
				return user.equals(t.getUserid());
			}
		}).collect(Collectors.toList());
	}
}
