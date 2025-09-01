package com.anthony.keycloak;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.*;
import org.keycloak.credential.*;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserQueryProvider;

import java.sql.*;
import java.util.*;
import java.util.stream.Stream;

public class PostgresUserStorageProvider implements
        UserStorageProvider,
        UserLookupProvider,
        UserQueryProvider,
        CredentialInputValidator {

    private final KeycloakSession session;
    private final RealmModel realm;
    private final Connection connection;
    private final ComponentModel model;
    private final String tableFullName;
    private final UserRepository userRepository;

    public PostgresUserStorageProvider(KeycloakSession session, RealmModel realm, Connection connection, ComponentModel model) {
        this.session = session;
        this.realm = realm;
        this.connection = connection;
        this.model = model;
        this.tableFullName = model.getConfig().getFirst("dbTable");
        DatabaseConfigurationModel configurationModel = new DatabaseConfigurationModel(
            model.getConfig().getFirst("dbTable")
        );
        this.userRepository = new UserRepository(configurationModel, connection);
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // On ajoutera les autres méthodes juste après
    @Override
    public UserModel getUserByUsername(RealmModel realm, String username) {
        UserEntity userEntity = userRepository.getUserByLogin(username);
        if(userEntity != null){
            UserModel user = session.users().getUserByUsername(realm, username);
            if (user == null) {
                user = session.users().addUser(realm, username);
            }
            user.setSingleAttribute("externalId", userEntity.getId().toString());
            return user;
        }
        return null;
    }

    @Override
    public UserModel getUserById(RealmModel realmModel, String userId) {
        UserEntity userEntity = userRepository.getUserById(Integer.getInteger(userId));
        if(userEntity != null){
            //UserModel user = session.userLocalStorage().getUserByUsername(username, realm);
            UserModel user = session.users().getUserById(realm, userId);
            if (user != null) {
                user.setSingleAttribute("username", userEntity.getUsername());
                return user;
            }
        }
        return null;
    }

    @Override
    public UserModel getUserByEmail(RealmModel realmModel, String s) {
        return null;
    }

    @Override
    public boolean supportsCredentialType(String credentialType) {
        return PasswordCredentialModel.PASSWORD.equals(credentialType);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        return supportsCredentialType(credentialType);
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput input) {
        if (!(input instanceof PasswordCredentialModel)) return false;

        String username = user.getUsername();
        String password = input.getChallengeResponse();

        String storedUserPassword = userRepository.getUserPasswordByLogin(username);
        if(storedUserPassword != null){
            return storedUserPassword.equals(password); // 🔐 à remplacer par du hash plus tard
        }
        return false;
    }

    @Override
    public Stream<UserModel> searchForUserStream(RealmModel realm, Map<String, String> attributes, Integer firstResult, Integer maxResults) {
        List<UserEntity> matchedUsers = new ArrayList<>();

        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if ("username".equalsIgnoreCase(key) || "login".equalsIgnoreCase(key)) {
                matchedUsers.addAll(userRepository.searchUsersByLogin(value, firstResult, maxResults));
            } else if ("name".equalsIgnoreCase(key)) {
                matchedUsers.addAll(userRepository.searchUsersByName(value, firstResult, maxResults));
            }
        }

        return matchedUsers.stream()
                .map(user -> new PostgresUserAdapter(session, realm, model, user.getUsername()));
    }

    @Override
    public Stream<UserModel> getGroupMembersStream(RealmModel realm, GroupModel group, Integer firstResult, Integer maxResults) {
        // Si les groupes ne sont pas gérés côté PostgreSQL, retourne vide
        return Stream.empty();
    }

    @Override
    public Stream<UserModel> searchForUserByUserAttributeStream(RealmModel realm, String attributeName, String attributeValue) {
        List<UserEntity> matchedUsers = new ArrayList<>();

        if ("name".equalsIgnoreCase(attributeName)) {
            matchedUsers = userRepository.searchUsersByName(attributeValue, 0, 50);
        } else if ("login".equalsIgnoreCase(attributeName)) {
            matchedUsers = userRepository.searchUsersByLogin(attributeValue, 0, 50);
        }

        return matchedUsers.stream()
                .map(user -> new PostgresUserAdapter(session, realm, model, user.getUsername()));
    }
}