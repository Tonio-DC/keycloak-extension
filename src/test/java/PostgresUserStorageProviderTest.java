import com.anthony.keycloak.PostgresUserStorageProvider;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.credential.CredentialInput;
import org.keycloak.models.*;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.models.credential.dto.PasswordCredentialData;
import org.keycloak.models.credential.dto.PasswordSecretData;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
//public class PostgresUserStorageProviderTest {
//
//    private FakeKeycloakSession session;
//
//    private FakeRealmModel realm;
//
//    /*@Mock
//    private UserProvider userProvider;
//
//    @Mock
//    private UserModel userModel;*/
//
//    private UserModel userModel = new FakeUserModel("tonio");
//
//    private Connection connection;
//    private PostgresUserStorageProvider provider;
//
//    @BeforeEach
//    void setup() throws SQLException {
//        connection = DriverManager.getConnection("jdbc:postgresql://localhost:3303/lims", "limsuser", "limsuser");
//        Statement stmt = connection.createStatement();
//        stmt.execute("delete from ad.users where login = 'unittestuser'");
//        stmt.execute("insert into ad.users (nom, login, password) values ('TU', 'unittestuser', 'pwd_unittestuser')");
//
//        /*when(session.users()).thenReturn(userProvider);
//        when(userProvider.getUserByUsername(realm, "anthony")).thenReturn(null);
//        when(userProvider.addUser(realm, "anthony")).thenReturn(userModel);*/
//
//        provider = new PostgresUserStorageProvider(session, realm, connection);
//    }
//
//    @Test
//    void testGetUserByUsername_shouldReturnUserModel() {
//        UserModel result = provider.getUserByUsername(realm, "anthony");
//
//        assertNotNull(result);
//        verify(userModel).setSingleAttribute("externalId", "1");
//    }
//
//    @Test
//    void testIsValid_shouldReturnTrueForCorrectPassword() {
//        //Arrange
//        //when(userModel.getUsername()).thenReturn("tonio");
//
//        //CredentialInput input = new PasswordCredentialModel("secret");
//        int hashIterations = 0;
//        String algorithm = "";
//        String secret = "pwdtonio";
//        byte[] salt = new byte[0];
//        PasswordCredentialModel input = PasswordCredentialModel.createFromValues(
//                new PasswordCredentialData(hashIterations, algorithm),
//                new PasswordSecretData(secret, salt)
//        );
//        //Act
//        boolean valid = provider.isValid(realm, userModel, (CredentialInput)input);
//
//        //Assert
//        assertTrue(valid);
//    }
//
//    @Test
//    void testIsValid_shouldReturnFalseForWrongPassword() {
//        //Arrange
//        when(userModel.getUsername()).thenReturn("tonio");
//
//        //CredentialInput input = new PasswordCredentialModel("secret");
//        int hashIterations = 0;
//        String algorithm = "";
//        String secret = "wrong secret";
//        byte[] salt = new byte[0];
//        PasswordCredentialModel input = PasswordCredentialModel.createFromValues(
//                new PasswordCredentialData(hashIterations, algorithm),
//                new PasswordSecretData(secret, salt)
//        );
//        //Act
//        boolean valid = provider.isValid(realm, userModel, (CredentialInput)input);
//
//        //Assert
//        assertTrue(valid);
//    }
//
//    @AfterEach
//    void tearDown() throws SQLException {
//        connection.close();
//    }
//}