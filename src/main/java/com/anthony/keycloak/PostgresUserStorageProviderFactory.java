package com.anthony.keycloak;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.storage.UserStorageProviderFactory;

import java.sql.*;
import java.util.*;

public class PostgresUserStorageProviderFactory implements UserStorageProviderFactory<PostgresUserStorageProvider> {

    @Override
    public String getId() {
        return "lims-postgres-user-provider"; // ID utilisé dans le SPI
    }

    @Override
    public PostgresUserStorageProvider create(KeycloakSession session, ComponentModel model) {
        try {
            String url = model.getConfig().getFirst("dbUrl");
            String user = model.getConfig().getFirst("dbUser");
            String password = model.getConfig().getFirst("dbPassword");
            //String url = model.getConfig().getFirst("jdbc:postgresql://localhost:3303/lims");
            //String user = model.getConfig().getFirst("limsuser");
            //String password = model.getConfig().getFirst("limsuser");

            Connection connection = DriverManager.getConnection(url, user, password);
            RealmModel realm = session.getContext().getRealm();

            return new PostgresUserStorageProvider(session, realm, connection, model);
        } catch (SQLException e) {
            throw new RuntimeException("Erreur de connexion à la base PostgreSQL", e);
        }
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        List<ProviderConfigProperty> configProperties = new ArrayList<>();

        configProperties.add(new ProviderConfigProperty(
                "dbUrl",
                "Database URL",
                "URL de connexion à la base PostgreSQL",
                ProviderConfigProperty.STRING_TYPE,
                "jdbc:postgresql://localhost:3303/lims"
        ));

        configProperties.add(new ProviderConfigProperty(
                "dbUser",
                "Database User",
                "Nom d'utilisateur pour la base",
                ProviderConfigProperty.STRING_TYPE,
                "limsuser"
        ));

        configProperties.add(new ProviderConfigProperty(
                "dbPassword",
                "Database Password",
                "Mot de passe pour la base",
                ProviderConfigProperty.PASSWORD,
                "limsuser"
        ));

        configProperties.add(new ProviderConfigProperty(
                "dbTable",
                "table cible (schéma.table)",
                "Nom complet de la table cible",
                ProviderConfigProperty.STRING_TYPE,
                "ad.users"
        ));

        return configProperties;
    }
}
