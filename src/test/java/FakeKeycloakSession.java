import org.keycloak.component.ComponentModel;
import org.keycloak.models.*;
import org.keycloak.provider.InvalidationHandler;
import org.keycloak.provider.Provider;
import org.keycloak.services.clientpolicy.ClientPolicyManager;
import org.keycloak.sessions.AuthenticationSessionProvider;
import org.keycloak.vault.VaultTranscriber;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class FakeKeycloakSession  implements KeycloakSession {
    @Override
    public KeycloakContext getContext() {
        return null;
    }

    @Override
    public KeycloakTransactionManager getTransactionManager() {
        return null;
    }

    @Override
    public <T extends Provider> T getProvider(Class<T> aClass) {
        return null;
    }

    @Override
    public <T extends Provider> T getProvider(Class<T> aClass, String s) {
        return null;
    }

    @Override
    public <T extends Provider> T getComponentProvider(Class<T> aClass, String s) {
        return null;
    }

    @Override
    public <T extends Provider> T getComponentProvider(Class<T> aClass, String s, Function<KeycloakSessionFactory, ComponentModel> function) {
        return null;
    }

    @Override
    public <T extends Provider> T getProvider(Class<T> aClass, ComponentModel componentModel) {
        return null;
    }

    @Override
    public <T extends Provider> Set<String> listProviderIds(Class<T> aClass) {
        return null;
    }

    @Override
    public <T extends Provider> Set<T> getAllProviders(Class<T> aClass) {
        return null;
    }

    @Override
    public Class<? extends Provider> getProviderClass(String s) {
        return null;
    }

    @Override
    public Object getAttribute(String s) {
        return null;
    }

    @Override
    public <T> T getAttribute(String s, Class<T> aClass) {
        return null;
    }

    @Override
    public Object removeAttribute(String s) {
        return null;
    }

    @Override
    public void setAttribute(String s, Object o) {

    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public void invalidate(InvalidationHandler.InvalidableObjectType invalidableObjectType, Object... objects) {

    }

    @Override
    public void enlistForClose(Provider provider) {

    }

    @Override
    public KeycloakSessionFactory getKeycloakSessionFactory() {
        return null;
    }

    @Override
    public RealmProvider realms() {
        return null;
    }

    @Override
    public ClientProvider clients() {
        return null;
    }

    @Override
    public ClientScopeProvider clientScopes() {
        return null;
    }

    @Override
    public GroupProvider groups() {
        return null;
    }

    @Override
    public RoleProvider roles() {
        return null;
    }

    @Override
    public UserSessionProvider sessions() {
        return null;
    }

    @Override
    public UserLoginFailureProvider loginFailures() {
        return null;
    }

    @Override
    public AuthenticationSessionProvider authenticationSessions() {
        return null;
    }

    @Override
    public SingleUseObjectProvider singleUseObjects() {
        return null;
    }

    @Override
    public IdentityProviderStorageProvider identityProviders() {
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public UserProvider users() {
        return new FakeUserProvider();
    }

    @Override
    public KeyManager keys() {
        return null;
    }

    @Override
    public ThemeManager theme() {
        return null;
    }

    @Override
    public TokenManager tokens() {
        return null;
    }

    @Override
    public VaultTranscriber vault() {
        return null;
    }

    @Override
    public ClientPolicyManager clientPolicy() {
        return null;
    }

    @Override
    public boolean isClosed() {
        return false;
    }
}
